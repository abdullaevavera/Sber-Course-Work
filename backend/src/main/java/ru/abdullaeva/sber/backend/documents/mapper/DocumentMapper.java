package ru.abdullaeva.sber.backend.documents.mapper;

import org.mapstruct.Mapper;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.model.Document;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto documentToDocumentDto(Document document);
    Document documentDtoToDocument(DocumentDto documentDto);
    List<DocumentDto> documentListToDocumentDtoList(List<Document> List);
    List<Document> documentDtoListToDocumentList(List<DocumentDto> documentDtoList);
}
