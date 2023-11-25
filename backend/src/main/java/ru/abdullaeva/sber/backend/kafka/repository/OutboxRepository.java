package ru.abdullaeva.sber.backend.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;

/**
 * Репозиторий для работы с исходящими сообщениями
 */
@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
