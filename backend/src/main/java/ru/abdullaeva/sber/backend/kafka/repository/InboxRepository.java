package ru.abdullaeva.sber.backend.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;

import java.util.List;

/**
 * Репозиторий для работы со входящими сообщениями
 */
@Repository
public interface InboxRepository extends JpaRepository<Inbox, Long> {
    /**
     * Метод для поиска непрочитанных сообщений полученных из кафка
     */
    public List<Inbox> findAllByCheckbox(boolean checkbox);
}
