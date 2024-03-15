package io.quarkusrobotshop.homerobot;

import io.quarkusrobotshop.domain.Item;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;

import java.util.UUID;

public class TestUtil {

    public static OrderIn getOrderTicket() {

        return new OrderIn(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Item.CP0FB2_BLACK,
                "Lemmy"
        );
    }

}
