package ru.abdullaeva.sber.backend.documents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;
import ru.abdullaeva.sber.backend.documents.mapper.CustomDocumentMapper;
import ru.abdullaeva.sber.backend.documents.model.Document;
import ru.abdullaeva.sber.backend.documents.model.StatusEnum;
import ru.abdullaeva.sber.backend.documents.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CustomDocumentMapper customDocumentMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, CustomDocumentMapper customDocumentMapper) {
        this.documentRepository = documentRepository;
        this.customDocumentMapper = customDocumentMapper;
    }

    @Override
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
    public void deleteAll(Set<Long> ids) {
        ids.forEach(id -> documentRepository.deleteById(id.intValue()));
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id.intValue());
    }

    @Override
    public DocumentDto update(IdDto id) {
        return null;
    }

    @Override
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
    public DocumentDto get(Long id) {
        Document document = documentRepository.findById(id.intValue()).orElse(null);
        if (document == null) {
            return new DocumentDto();
        }
        DocumentDto documentDto = customDocumentMapper.documentToDocumentDto(document);
        return documentDto;
    }
}
