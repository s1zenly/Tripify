package com.tripify.pack.kafka.consumer;

import com.tripify.pack.domain.AspectType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketsPackViewConsumer {

    private final PackViewEventConsumerSupport packViewEventConsumerSupport;

    @KafkaListener(
            topics = "${tripify.kafka.topics.tickets-pack-viewed}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        packViewEventConsumerSupport.consume(record, AspectType.TICKET, acknowledgment);
    }
}
