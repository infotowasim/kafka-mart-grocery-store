package com.kafkamart.productservice.event.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.kafkamart.productservice.event.payload.ProductCreatedEvent;

@Component
public class ProductEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public ProductEventProducer(
	        KafkaTemplate<String, Object> kafkaTemplate) {

	    this.kafkaTemplate = kafkaTemplate;
	}

    public void publishProductCreatedEvent(
            ProductCreatedEvent event) {

        kafkaTemplate.send(
                "product-created-topic",
                event);
    }
}
