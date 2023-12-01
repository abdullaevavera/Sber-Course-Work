package ru.abdullaeva.sber.backend.kafka.service.interf;


import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;

import java.util.List;
import java.util.Set;

public interface InboxService {
    /**
     *
     * @param resultDto
     * @param key
     * @return
     */
    Inbox writeUniqueMessageAtInbox(KafkaHandlingResultDto resultDto, Long key);

    /**
     *
     * @return
     */
    List<Inbox> getUnreadMessageFromInbox();

    /**
     *
     * @param ids
     * @return
     */
    List<Inbox> markMessageAtInboxAsRead(Set<Long> ids);
}
