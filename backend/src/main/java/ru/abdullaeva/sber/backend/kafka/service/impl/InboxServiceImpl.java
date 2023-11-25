package ru.abdullaeva.sber.backend.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;
import ru.abdullaeva.sber.backend.kafka.repository.InboxRepository;
import ru.abdullaeva.sber.backend.kafka.service.interf.InboxService;

import java.util.List;
import java.util.Set;

@Service
public class InboxServiceImpl implements InboxService {

    private final InboxRepository inboxRepository;

    @Autowired
    public InboxServiceImpl(InboxRepository inboxRepository) {
        this.inboxRepository = inboxRepository;
    }

    @Override
    public Inbox writeUniqueMessageAtInbox(Inbox inboxMessage) {
        Inbox inboxUniqueMessage = inboxRepository.findById(inboxMessage.getId()).orElse(null);
        if (inboxUniqueMessage != null) {
            throw new RuntimeException("inboxMessage with id = " + inboxMessage.getId() + " already exists");
        }
        return inboxRepository.save(inboxMessage);
    }

    @Override
    public List<Inbox> markMessageAtInboxAsRead(Set<Long> ids) {
        List<Inbox> inboxMessages = inboxRepository.findAllById(ids);
        for (Inbox inboxMessage : inboxMessages) {
            inboxMessage.setCheckbox(true);
        }
        return inboxRepository.saveAll(inboxMessages);
    }

    @Override
    public List<Inbox> getUnreadMessageFromInbox() {
        return inboxRepository.findAllByCheckbox(false);
    }
}
