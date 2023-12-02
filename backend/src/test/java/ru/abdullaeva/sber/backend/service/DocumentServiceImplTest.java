/*package ru.abdullaeva.sber.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.abdullaeva.sber.backend.configuration.JacksonConfiguration;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;
import ru.abdullaeva.sber.backend.documents.dto.Status;
import ru.abdullaeva.sber.backend.documents.mapper.CustomDocumentMapper;
import ru.abdullaeva.sber.backend.documents.model.Document;
import ru.abdullaeva.sber.backend.documents.model.StatusEnum;
import ru.abdullaeva.sber.backend.documents.repository.DocumentRepository;
import ru.abdullaeva.sber.backend.documents.service.impl.DocumentServiceImpl;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;
import ru.abdullaeva.sber.backend.kafka.service.interf.InboxService;
import ru.abdullaeva.sber.backend.kafka.service.interf.OutboxService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceImplTest {
    private static final String PATH = "mock_files/save_document_service_test_file.json";
    private static final String UPDATE_DTO_PATH = "mock_files/update_document_dto_service_test_file.json";
    private static final String UPDATE_PATH = "mock_files/update_document_service_test_file.json";

    private final ObjectMapper mapper = new JacksonConfiguration().objectMapper();
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private CustomDocumentMapper customDocumentMapper;
    @Mock
    private OutboxService outboxService;
    @Mock
    private InboxService inboxService;
    @InjectMocks
    private DocumentServiceImpl documentService;
    @Test
    public void successWhenSaveDocumentStatusNewTest() throws Exception {
        Resource resource = new ClassPathResource(PATH);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);

        Document document = new Document();
        document.setType(documentDto.getType());
        document.setOrganization(documentDto.getOrganization());
        document.setDescription(documentDto.getDescription());
        document.setPatient(documentDto.getPatient());
        document.setDate(documentDto.getDate());
        document.setId(1L);
        document.setStatus("NEW");

        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentDto newDocumentDto = documentService.save(documentDto);
        assertEquals(documentDto.getType(), newDocumentDto.getType(), "Not equal");
        assertEquals(documentDto.getOrganization(), newDocumentDto.getOrganization(), "Not equal");
        assertEquals(documentDto.getDescription(), newDocumentDto.getDescription(), "Not equal");
        assertEquals(documentDto.getPatient(), newDocumentDto.getPatient(), "Not equal");
        assertEquals(Status.of(StatusEnum.NEW.name(), "Новый"), newDocumentDto.getStatus());

    }
    @Test
    public void successWhenUpdateDocumentStatusInProcessTest() throws Exception {
        Resource resource = new ClassPathResource(UPDATE_DTO_PATH);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);
        Resource resource1 = new ClassPathResource(UPDATE_PATH);
        InputStream inputStream1 = new BufferedInputStream(resource1.getInputStream());
        Document document = mapper.readValue(inputStream1, Document.class);
        document.setDate(new Date());

        when(documentRepository.findById(documentDto.getId())).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);
        when(outboxService.writeMessageAtOutbox(any(DocumentDto.class))).thenReturn(new Outbox(1L, "message"));

        DocumentDto updateResultDto = documentService.update(new IdDto(1L));
        assertEquals(documentDto.getId(), updateResultDto.getId(), "Not equal");
        assertEquals(documentDto.getType(), updateResultDto.getType(), "Not equal");
        assertEquals(documentDto.getOrganization(), updateResultDto.getOrganization(), "Not equal");
        assertEquals(documentDto.getDescription(), updateResultDto.getDescription(), "Not equal");
        assertEquals(documentDto.getPatient(), updateResultDto.getPatient(), "Not equal");
        assertEquals(Status.of(StatusEnum.IN_PROCESS.name(), "В процессе"), updateResultDto.getStatus());
    }

    @Test
    public void successWhenGetDocumentTest() throws Exception {
        Resource resource = new ClassPathResource(UPDATE_PATH);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        Document document = mapper.readValue(inputStream, Document.class);
        Resource resource1 = new ClassPathResource(UPDATE_DTO_PATH);
        InputStream inputStream1 = new BufferedInputStream(resource1.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream1, DocumentDto.class);

        when(documentRepository.findById(documentDto.getId())).thenReturn(Optional.of(document));
        DocumentDto getGto = documentService.get(documentDto.getId());
        assertEquals(documentDto, getGto);
    }

}*/
