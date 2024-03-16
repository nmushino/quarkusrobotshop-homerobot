package io.quarkusrobotshop.homerobot.infrastructure;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkusrobotshop.homerobot.TestUtil;
import io.quarkusrobotshop.homerobot.infrastructure.KafkaService;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;
import io.quarkusrobotshop.domain.valueobjects.OrderUp;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySink;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(KafkaTestResource.class)
public class KafkaServiceOrderUpTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaServiceOrderUpTest.class);

    @Inject
    KafkaService kafkaService;

    @Inject
    @Any
    InMemoryConnector ordersInConnector;

    InMemorySource<OrderIn> ordersIn;

    @Inject
    @Any
    InMemoryConnector ordersOutConnector;

    InMemorySink<OrderUp> ordersOut;

    @BeforeEach
    public void setUp(){

        ordersIn = ordersInConnector.source("orders-in");
        ordersOut = ordersOutConnector.sink("orders-out");
    }

    @Test
    public void testSingleBlackCoffee() {

        OrderIn orderIn = TestUtil.getOrderTicket();
        ordersIn.send(orderIn);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<? extends Message<OrderUp>> ordersUp = ordersOut.received();
        assertNotNull(ordersUp);
        assertEquals(1, ordersUp.size());
        OrderUp orderUp = ordersUp.get(0).getPayload();
        assertNotNull(orderUp);
        assertEquals(orderUp.orderId, orderIn.getOrderId());
        assertEquals(orderUp.lineItemId, orderIn.getLineItemId());
        assertEquals(orderUp.item, orderIn.getItem());
        assertEquals(orderUp.name, orderIn.getName());
    }
}
