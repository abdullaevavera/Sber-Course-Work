package ru.abdullaeva.sber.backend.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;
import ru.abdullaeva.sber.backend.documents.dto.IdsDto;
import ru.abdullaeva.sber.backend.documents.service.interf.DocumentService;

import java.util.List;

/**
 * Рест-контроллер для обработки HTTP-запросов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto save(@RequestBody DocumentDto dto) {
        return documentService.save(dto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentDto> get() {
        return documentService.findAll();
    }

    @PostMapping(
            path = "send",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto send(@RequestBody IdDto id) {
        return documentService.update(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }

    @DeleteMapping
    public void deleteAll(@RequestBody IdsDto idsDto) {
        documentService.deleteAll(idsDto.getIds());
    }
}
