package ru.abdullaeva.sber.backend.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;
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
    @Transactional
    public Inbox writeUniqueMessageAtInbox(KafkaHandlingResultDto resultDto, Long key) {
        String result = resultDto.toString();
        Inbox inboxUniqueMessage = inboxRepository.findById(key).orElse(null);
        if (inboxUniqueMessage != null) {
            throw new RuntimeException("inboxMessage with id = " + key + " already exists");
        }
        Inbox newInboxMessage = new Inbox(key, result);
        newInboxMessage = inboxRepository.save(newInboxMessage);
        return newInboxMessage;
    }

    @Override
    @Transactional
    public List<Inbox> markMessageAtInboxAsRead(Set<Long> ids) {
        List<Inbox> inboxMessages = inboxRepository.findAllById(ids);
        for (Inbox inboxMessage : inboxMessages) {
            inboxMessage.setCheckbox(true);
        }
        return inboxRepository.saveAll(inboxMessages);
    }

    @Override
    @Transactional
    public List<Inbox> getUnreadMessageFromInbox() {
        return inboxRepository.findAllByCheckbox(false);
    }
}
