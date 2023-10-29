package ru.abdullaeva.sber.backend.documents.service;

import ru.abdullaeva.sber.backend.documents.dto.DocumentDto;
import ru.abdullaeva.sber.backend.documents.dto.IdDto;

import java.util.List;
import java.util.Set;

public class DocumentServiceImpl implements DocumentService{
    @Override
    public DocumentDto save(DocumentDto documentDto) {
        return null;
    }

    @Override
    public void deleteAll(Set<Long> ids) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public DocumentDto update(IdDto id) {
        return null;
    }

    @Override
    public List<DocumentDto> findAll() {
        return null;
    }

    @Override
    public DocumentDto get(Long id) {
        return null;
    }
}
