package ru.abdullaeva.sber.backend.documents.service.interf;

import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;

import java.util.List;
import java.util.Set;

/**
 * Сервис, содержащий методы для работы с документами
 */
public interface DocumentService {

    /**
     * Метод сохраняет документ и возвращает сохранённый документ.
     * @param documentDto - докумен, который нужно сохранить.
     * @return - сохраненный документ.
     */
    DocumentDto save(DocumentDto documentDto);

    /**
     * Метод удаляет выбранные документы.
     * @param ids - идентификаторы документов, которые нужно удалить.
     */
    void deleteAll(Set<Long> ids);

    /**
     * Метод удаляет документ по идентификатору.
     * @param id - идентификатор документа, который нужно удалить.
     */
    void delete(Long id);

    /**
     * Метод производит обновление данных документа.
     * @param id - идентификатор документа, который нужно обновить.
     * @return - обновленный документ.
     */
    DocumentDto update(IdDto id);

    /**
     * Метод для получения всех документов.
     * @return - список существующих документов.
     */
    List<DocumentDto> findAll();

    /**
     * Метод для получения документа по идентификатору
     * @param id - идентификатор документа, который нужно получить.
     * @return - полученный документ.
     */
    DocumentDto get(Long id);
}
