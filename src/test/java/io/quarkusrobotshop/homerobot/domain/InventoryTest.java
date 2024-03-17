package io.quarkusrobotshop.homerobot.domain;

import io.quarkusrobotshop.homerobot.domain.Inventory;
import io.quarkusrobotshop.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;

//import static org.junit.Assert.*;

@QuarkusTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryTest.class);

    @Inject
    Inventory inventory;

    @Test @Order(1)
    public void testStockIsPopulated() {

        Map<Item, Integer> inStock = inventory.getStock();
        //assertNotNull(inStock);
        inStock.forEach((k,v) -> {
            LOGGER.info(k + " " + v);
        });
    }

    @Test @Order(2)
    public void testDecrementrobot() {

        Integer totalrobot = inventory.getTotalrobot();
        LOGGER.info("total robot: {}", totalrobot);
        //assertTrue(inventory.decrementItem(Item.CP1FC3_HOME));
        Integer updatedrobot = inventory.getTotalrobot();
        LOGGER.info("total robot after decrementing: {}", updatedrobot);
        //assertTrue(updatedrobot == totalrobot - 1);
    }

    @Test @Order(3)
    public void testEightySixrobot() {

        Integer totalrobot = inventory.getTotalrobot();
        for (int i = 0; i < totalrobot; i++) {
            //assertTrue(inventory.decrementItem(Item.CP0FB2_BLACK));
        }
        //assertFalse(inventory.decrementItem(Item.CP0FB2_BLACK));
    }
}
