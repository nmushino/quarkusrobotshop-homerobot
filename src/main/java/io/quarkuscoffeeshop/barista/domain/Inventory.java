package io.quarkuscoffeeshop.barista.domain;

import io.quarkuscoffeeshop.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@ApplicationScoped
public class Inventory {

    static Map<Item, Integer> stock;
    Logger LOGGER = LoggerFactory.getLogger(Inventory.class.getName());

    public Inventory() {
        super();
    }

    /*
        CP0FB2_BLACK and CP1FC3_HOME are simply tracked as CP0FB2_BLACK
     */
    @PostConstruct
    private void createStock() {
        stock = new HashMap<>();
        Stream.of(Item.values()).forEach(v -> {
            stock.put(v, ThreadLocalRandom.current().nextInt(55, 99));
        });
        stock.entrySet().stream().forEach(entrySet -> {
            LOGGER.debug(entrySet.getKey() + " " + entrySet.getValue());
        });

        // Account for coffee
        Integer totalCoffee = stock.get(Item.CP0FB2_BLACK).intValue() + stock.get(Item.CP1FC3_HOME).intValue();
        stock.remove(Item.CP0FB2_BLACK);
        stock.remove(Item.CP1FC3_HOME);
        stock.put(Item.CP0FB2_BLACK, totalCoffee);
    }

    public boolean decrementItem(Item item) {

        LOGGER.debug("decrementing {}", item);

        if (item == Item.CP1FC3_HOME) item = Item.CP0FB2_BLACK;

        Integer itemCount = stock.get(item);
        LOGGER.debug("current inventory for {} is {}", item, itemCount);

        if (itemCount <= 0) return false;

        itemCount--;
        stock.replace(item, itemCount);
        LOGGER.debug("updated inventory for {} is {}", item, stock.get(item));
        return true;
    }

    public Map<Item, Integer> getStock() {
        return stock;
    }

    public Integer getTotalCoffee() {
        return stock.get(Item.CP0FB2_BLACK);
    }

    public void restock(Item item) {
        stock.put(item, ThreadLocalRandom.current().nextInt(55, 99));
    }
}
