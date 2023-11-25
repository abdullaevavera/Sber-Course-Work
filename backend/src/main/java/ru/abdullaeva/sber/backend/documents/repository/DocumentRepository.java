package ru.abdullaeva.sber.backend.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdullaeva.sber.backend.documents.model.Document;

/**
 * Репозиторий для работы с документами
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}

