package io.quarkusrobotshop.homerobot.infrastructure;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;

public class OrderTicketDeserializer extends ObjectMapperDeserializer<OrderIn> {

    public OrderTicketDeserializer() {
        super(OrderIn.class);
    }
}
