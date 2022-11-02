package com.microservices.demo.kafka.producer.config;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.config.KafkaProducerConfigdata;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducerConfigdata kafkaProducerConfigdata;

    public KafkaProducerConfig(KafkaConfigData configData, KafkaProducerConfigdata producerConfigdata) {
        this.kafkaConfigData = configData;
        this.kafkaProducerConfigdata = producerConfigdata;
    }

    @Bean
    public Map<String, Object> producerConfig()
    {
        Map<String, Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigdata.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigdata.getValueSerializerClass());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigdata.getBatchSize() * kafkaProducerConfigdata.getBatchSizeBoostFactor());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigdata.getLingerMs());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigdata.getCompressionType());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigdata.getAcks());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigdata.getRequestTimeoutMs());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigdata.getRetryCount());

        return props;
    }

    @Bean
    public ProducerFactory<K, V> producerFactory()
    {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
}
