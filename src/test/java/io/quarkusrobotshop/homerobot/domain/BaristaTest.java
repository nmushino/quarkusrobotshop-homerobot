package io.quarkusrobotshop.homerobot.domain;

import io.quarkusrobotshop.homerobot.TestUtil;
import io.quarkusrobotshop.homerobot.domain.Homerobot;
import io.quarkusrobotshop.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkusrobotshop.domain.valueobjects.HomerobotResult;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;
import io.quarkusrobotshop.domain.valueobjects.OrderUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class HomerobotTest {

    @Inject
    Homerobot homerobot;

    Jsonb jsonb = JsonbBuilder.create();

    @BeforeEach
    public void restock() {
        homerobot.restockItem(Item.CP0FB2_BLACK);
    }

    @Test
    public void testBlackCoffeeOrder() throws ExecutionException, InterruptedException {

        OrderIn orderIn = TestUtil.getOrderTicket();

        HomerobotResult homerobotResult = homerobot.make(orderIn);

        OrderUp orderUp = homerobotResult.getOrderUp();

        await().atLeast(Duration.ofSeconds(5000));

        assertNotNull(orderUp);
        assertEquals(orderUp.orderId, orderIn.getOrderId());
        assertEquals(orderUp.lineItemId, orderIn.getLineItemId());
        assertEquals(orderUp.item, orderIn.getItem());
        assertEquals(orderUp.name, orderIn.getName());

    }


}
