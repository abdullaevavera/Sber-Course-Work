package ru.abdullaeva.sber.backend.documents.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.model.Document;
import ru.abdullaeva.sber.backend.documents.dto.Status;

/**
 * Кастомный маппер документов
 */
@Component
@RequiredArgsConstructor
public class CustomDocumentMapper {

    /**
     * Метод маппит сущность "документ" на дто документа.
     */
    public DocumentDto documentToDocumentDto(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(document.getId());
        documentDto.setType(document.getType());
        documentDto.setOrganization(document.getOrganization());
        documentDto.setDate(document.getDate());
        documentDto.setDescription(document.getDescription());
        documentDto.setPatient(document.getPatient());
        documentDto.setStatus(initStatus(document.getStatus()));
        return documentDto;
    }

    /**
     * Метод устанавливает название статуса в зависимости от установленного кода.
     */
    private Status initStatus (String statusCode){
            switch (statusCode) {
                case ("NEW") -> {
                    return Status.of(statusCode, "Новый");
                }
                case ("IN_PROCESS") -> {
                    return Status.of(statusCode, "В процессе");
                }
                case ("ACCEPTED") -> {
                    return Status.of(statusCode, "Принят");
                }
                case ("DECLINED") -> {
                    return Status.of(statusCode, "Отклонен");
                }
                default -> {
                }
            }
            return new Status();
        }
}