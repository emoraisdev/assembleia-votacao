package com.sicredi.assembleiaservice.service.producer;

import com.sicredi.assembleiaservice.service.producer.event.AssociadoAtualizacaoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class AssociadoEventProducer {

    private final KafkaTemplate<String, AssociadoAtualizacaoEvent> kafkaTemplate;
    private final String topicName;

    public AssociadoEventProducer(
            KafkaTemplate<String, AssociadoAtualizacaoEvent> kafkaTemplate,
            @Value("${spring.kafka.topics.associado-atualizacao}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @TransactionalEventListener
    public void handleAssociadoAtualizacaoEvent(AssociadoAtualizacaoEvent event) {

        try {
            kafkaTemplate.send(topicName, event.id().toString(), event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
