package ru.abdullaeva.sber.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.abdullaeva.sber.backend.configuration.JacksonConfiguration;
import ru.abdullaeva.sber.backend.documents.controller.DocumentController;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;
import ru.abdullaeva.sber.backend.documents.dto.IdsDto;
import ru.abdullaeva.sber.backend.documents.service.impl.DocumentServiceImpl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class DocumentControllerTest {
    private static final String PATH = "/documents";
    private static final String SEND_PATH = "/documents/send";
    private static final String SAVE_NEW_DOCUMENT_MOCK = "mock_files/save_document_test_file.json";
    private static final String SAVE_DOCUMENT_TOO_LONG_MOCK = "mock_files/save_document_with_too_long_field_test_file.json";

    private final ObjectMapper mapper = new JacksonConfiguration().objectMapper();
    private MockMvc mockMvc;

    @MockBean
    private DocumentServiceImpl service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void successWhenSaveTest() throws Exception {
        when(service.save(any())).thenReturn(any());
        Resource resource = new ClassPathResource(SAVE_NEW_DOCUMENT_MOCK);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);
        mockMvc.perform(postAction(PATH, documentDto)).andExpect(status().isOk());
    }

    @Test
    public void errorWhenSaveWithTooLonFieldTest() throws Exception {
        when(service.save(any())).thenThrow(new IllegalStateException("Error: Invalid data - data is too long."));
        Resource resource = new ClassPathResource(SAVE_DOCUMENT_TOO_LONG_MOCK);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);
        mockMvc.perform(postAction(PATH, documentDto)).andExpect(status().is5xxServerError());
    }

    @Test
    public void successWhenGetTest() throws Exception {
        Resource resource = new ClassPathResource(SAVE_NEW_DOCUMENT_MOCK);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);
        when(service.findAll()).thenReturn(List.of(documentDto));
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(documentDto))));
    }

    @Test
    public void successWhenSendTest() throws Exception {
        Resource resource = new ClassPathResource(SAVE_NEW_DOCUMENT_MOCK);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        DocumentDto documentDto = mapper.readValue(inputStream, DocumentDto.class);
        IdDto idDto = new IdDto(1L);
        when(service.update(idDto)).thenReturn(documentDto);
        mockMvc.perform(postAction(SEND_PATH, idDto)).andExpect(status().isOk());
    }

    @Test
    public void successWhenDeleteTest() throws Exception {
        Long id = 1L;
        doNothing().when(service).delete(id);
        mockMvc.perform(delete(PATH + "/{id}", id)).andExpect(status().isOk());
    }

    @Test
    public void successWhenDeleteAllTest() throws Exception {
        IdsDto idsDto = new IdsDto();
        idsDto.setIds(Set.of(5L, 6L));
        doNothing().when(service).deleteAll(idsDto.getIds());
        mockMvc.perform(deleteAction(PATH, idsDto)).andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder postAction(String uri, Object dto) throws JsonProcessingException {
        return post(uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }

    private MockHttpServletRequestBuilder deleteAction(String uri, Object dto) throws JsonProcessingException {
        return delete(uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }
}

