package io.quarkusrobotshop.homerobot.domain;

import io.quarkusrobotshop.domain.Item;
import io.quarkusrobotshop.domain.valueobjects.HomerobotResult;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;
import io.quarkusrobotshop.domain.valueobjects.OrderUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;

@ApplicationScoped
public class Homerobot {

    static final Logger logger = LoggerFactory.getLogger(Homerobot.class);
    @Inject
    Inventory inventory;
    private String madeBy;

    @PostConstruct
    void setHostName() {
        try {
            madeBy = InetAddress.getLocalHost().getHostName();
        } catch (IOException e) {
            logger.debug("unable to get hostname");
            madeBy = "unknown";
        }
    }

    public HomerobotResult make(final OrderIn orderIn) {

        logger.debug("making: {}" + orderIn.getItem());

        if (inventory.decrementItem(orderIn.getItem())) {

            sleepyTimeTime(orderIn.getItem());

            return new HomerobotResult(new OrderUp(
                    orderIn.getOrderId(),
                    orderIn.getLineItemId(),
                    orderIn.getItem(),
                    orderIn.getName(),
                    Instant.now(),
                    madeBy));
        } else {

            return new HomerobotResult(new EightySixEvent(orderIn.getItem()));
        }

    }

    private void sleepyTimeTime(final Item item) {
        int i = calculateDelay(item);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int calculateDelay(final Item item) {
        int delay;
        switch (item) {
            case CP0FB2_BLACK:
                delay = 5000;
                break;
            case CP1FC3_HOME:
                delay = 5000;
                break;
            case CK9FA3:
                delay = 7000;
                break;
            case CS92C3:
                delay = 7000;
                break;
            case CH99A9:
                delay = 10000;
                break;
            default:
                delay = 3000;
                break;
        }
        ;
        return delay;
    }


    public void restockItem(Item item) {
        inventory.restock(item);
    }
}
