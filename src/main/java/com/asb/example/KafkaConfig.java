package com.asb.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

	@Bean
	ProducerFactory<String, String> producerFactory() {
		Map<String, Object> config = new HashMap<>();
		  
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "my-cluster-kafka-bootstrap-amq-streams.apps.claro.co:443");
		//config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "my-cluster-kafka-bootstrap.amq-streams.svc:9091");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 70000);
		/*config.put("enable.auto.commit", "false");
		config.put("auto.offset.reset", "earliest");
		config.put("security.protocol", "SASL_PLAINTEXT");
		config.put("basic.auth.credentials.source", "USER_INFO");
		config.put("basic.auth.user.info", "${user}:${pass}");
		config.put("sasl.kerberos.service.name", "kafka");
		config.put("auto.register.schemas", "false");
		config.put("schema.registry.url", "${https://your_url}");
		config.put("schema.registry.ssl.truststore.location", "client_truststore.jks");
		config.put("schema.registry.ssl.truststore.password", "${password}");*/
		
		return new DefaultKafkaProducerFactory<String, String>(config);
		
	
	}

	@Bean
	KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}
}