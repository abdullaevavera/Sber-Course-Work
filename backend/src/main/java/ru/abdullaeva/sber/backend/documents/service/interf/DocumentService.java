package ru.abdullaeva.sber.backend.documents.service.interf;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;

import java.util.List;
import java.util.Set;

public interface DocumentService {
    /**
     * Сохранить документ
     * @param documentDto документ
     * @return сохраненный документ
     */
    DocumentDto save(DocumentDto documentDto);

    /**
     * Удалить документ
     * @param ids идентификаторы документов
     */
    void deleteAll(Set<Long> ids);

    /**
     * Удалить документ по ид
     * @param id идентификатор документа
     */
    void delete(Long id);

    /**
     * Обновить документ
     * @param id айди документа
     * @return обновленный документ
     */
    DocumentDto update(IdDto id);

    /**
     * Получить все документы
     * @return список документов
     */
    List<DocumentDto> findAll();

    /**
     * Получить документ по номеру
     * @param id идентификатор
     * @return документ
     */
    DocumentDto get(Long id);
}
