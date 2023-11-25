package ru.abdullaeva.sber.backend.kafka.service.interf;


import ru.abdullaeva.sber.backend.kafka.model.Inbox;

import java.util.List;
import java.util.Set;

public interface InboxService {
    /**
     *
     * @param inboxMessage
     * @return
     */
    Inbox writeUniqueMessageAtInbox(Inbox inboxMessage);

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
