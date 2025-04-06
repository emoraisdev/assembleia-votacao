package com.sicredi.votacaoservice.service.consumer.config;

import com.sicredi.votacaoservice.service.consumer.event.AssociadoAtualizacaoEvent;
import com.sicredi.votacaoservice.service.consumer.event.SessaoVotacaoEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServersConfig;

    @Bean
    public ConsumerFactory<String, AssociadoAtualizacaoEvent> associadoConsumerFactory() {
        Map<String, Object> props = baseConsumeConfigs();

        // Configuração específica do desserializador
        JsonDeserializer<AssociadoAtualizacaoEvent> deserializer = new JsonDeserializer<>(AssociadoAtualizacaoEvent.class);
        baseDeserializarConfig(deserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<String, SessaoVotacaoEvent> sessaoVotacaoConsumerFactory() {
        Map<String, Object> props = baseConsumeConfigs();

        // Configuração específica do desserializador
        JsonDeserializer<SessaoVotacaoEvent> deserializer = new JsonDeserializer<>(SessaoVotacaoEvent.class);
        baseDeserializarConfig(deserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }


    private static void baseDeserializarConfig(JsonDeserializer<?> deserializer) {
        deserializer.addTrustedPackages(
                "com.sicredi.assembleiaservice.service.producer.event",
                "com.sicredi.votacaoservice.service.consumer.event"
        );
        deserializer.setUseTypeHeaders(false);
    }

    private Map<String, Object> baseConsumeConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "votacao-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AssociadoAtualizacaoEvent> associadoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AssociadoAtualizacaoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(associadoConsumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SessaoVotacaoEvent> sessaoVotacaoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SessaoVotacaoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sessaoVotacaoConsumerFactory());

        return factory;
    }

}
