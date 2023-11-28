package ru.abdullaeva.sber.backend.documents.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;
import ru.abdullaeva.sber.backend.documents.mapper.CustomDocumentMapper;
import ru.abdullaeva.sber.backend.documents.model.Document;
import ru.abdullaeva.sber.backend.documents.model.StatusEnum;
import ru.abdullaeva.sber.backend.documents.repository.DocumentRepository;
import ru.abdullaeva.sber.backend.documents.service.interf.DocumentService;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;
import ru.abdullaeva.sber.backend.kafka.service.interf.InboxService;
import ru.abdullaeva.sber.backend.kafka.service.interf.OutboxService;

import java.util.*;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CustomDocumentMapper customDocumentMapper;
    private final OutboxService outboxService;
    private final InboxService inboxService;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, CustomDocumentMapper customDocumentMapper, OutboxService outboxService, InboxService inboxService) {
        this.documentRepository = documentRepository;
        this.customDocumentMapper = customDocumentMapper;
        this.outboxService = outboxService;
        this.inboxService = inboxService;
    }

    @Override
    @Transactional
    public DocumentDto save(DocumentDto documentDto) {
        if (documentDto != null) {
            Document document = Document.builder()
                    .type(documentDto.getType())
                    .date(new Date())
                    .organization(documentDto.getOrganization())
                    .description(documentDto.getDescription())
                    .patient(documentDto.getPatient())
                    .status(StatusEnum.NEW.name())
                    .build();
            return customDocumentMapper.documentToDocumentDto(documentRepository.save(document));
        }
        log.warn("save: parameter documentDto is null");
        return new DocumentDto();
    }

    @Override
    @Transactional
    public void deleteAll(Set<Long> ids) {
        ids.forEach(documentRepository::deleteById);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DocumentDto update(IdDto id) {
        Document document = documentRepository.findById(id.getId()).orElse(null);
        if (document == null) {
            log.warn("update: Document with id: " + id.getId() + " not found");
            return new DocumentDto();
        }
        if (!StatusEnum.NEW.name().equals(document.getStatus())) {
            log.warn("update: Document with id: " + document.getStatus() + " cannot be sent for processing");
            return new DocumentDto();
        }
        document.setStatus(StatusEnum.IN_PROCESS.name());
        DocumentDto updateDocumentDto = customDocumentMapper.documentToDocumentDto(documentRepository.save(document));
        outboxService.writeMessageAtOutbox(updateDocumentDto);
        return updateDocumentDto;
    }

    @Override
    @Transactional
    public List<DocumentDto> findAll() {
        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> documentDtoList = new ArrayList<>();

        if (documentList.isEmpty()) {
            log.warn("findAll: documentList is empty");
            return documentDtoList;
        }
        for (Document document : documentList) {
            DocumentDto documentDto = customDocumentMapper.documentToDocumentDto(document);
            documentDtoList.add(documentDto);
        }
        return documentDtoList;
    }

    @Override
    @Transactional
    public DocumentDto get(Long id) {
        Document document = documentRepository.findById(id).orElse(null);
        if (document == null) {
            log.warn("get: Document with id: " + id + " not found");
            return new DocumentDto();
        }
        return customDocumentMapper.documentToDocumentDto(document);
    }
    /**
     * @return
     */
    @Scheduled(fixedDelay = 3000)
    @Transactional
    public List<Document> readFromInboxSetHandlingResult() {
        List<Inbox> unreadMessages = inboxService.getUnreadMessageFromInbox();
        Set<Long> ids = new HashSet<>();
        Map<Long, String> documentsIdsAndStatusesMap = new HashMap<>();
        for (Inbox unreadMessage : unreadMessages) {
            ids.add(unreadMessage.getId());
            documentsIdsAndStatusesMap.put
                    (unreadMessage.getMessage().getIdDocument(), unreadMessage.getMessage().getCode());
        }
        List<Document> documents = documentRepository.findAllById(documentsIdsAndStatusesMap.keySet());

        for (Document document : documents) {
            String status = documentsIdsAndStatusesMap.get(document.getId());

            if (document.getStatus().equals(StatusEnum.IN_PROCESS.name())) {
                if (status == null || !status.equals(StatusEnum.ACCEPTED.name()) && !status.equals(StatusEnum.DECLINED.name())) {
                    document.setStatus(status);
                }
            } else {
                log.warn("Document changes cannot be set due to incorrect status");
            }
        }
        documents = documentRepository.saveAll(documents);
        inboxService.markMessageAtInboxAsRead(ids);
        return documents;
    }

}
