package com.sicredi.assembleiaservice.service.producer;

import com.sicredi.assembleiaservice.service.producer.event.AssociadoAtualizacaoEvent;
import com.sicredi.assembleiaservice.service.producer.event.SessaoVotacaoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
public class SessaoVotacaoEventProducer {

    private final KafkaTemplate<String, SessaoVotacaoEvent> kafkaTemplate;
    private final String topicName;

    public SessaoVotacaoEventProducer(
            KafkaTemplate<String, SessaoVotacaoEvent> kafkaTemplate,
            @Value("${spring.kafka.topics.sessao-votacao}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @TransactionalEventListener
    public void handleSessaoVotacaoEvent(SessaoVotacaoEvent event) {

        try {
            kafkaTemplate.send(topicName, event.id().toString(), event);
        } catch (Exception e) {
            log.error("Erro enviar evento SessaoVotacaoEvent para o Kafka", e);
        }
    }
}
