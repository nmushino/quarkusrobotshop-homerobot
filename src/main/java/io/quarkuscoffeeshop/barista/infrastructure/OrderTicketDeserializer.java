package io.quarkuscoffeeshop.homerobot.infrastructure;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkuscoffeeshop.domain.valueobjects.OrderIn;

public class OrderTicketDeserializer extends ObjectMapperDeserializer<OrderIn> {

    public OrderTicketDeserializer() {
        super(OrderIn.class);
    }
}
