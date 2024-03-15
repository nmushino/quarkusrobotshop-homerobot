package io.quarkuscoffeeshop.barista.domain;

import io.quarkuscoffeeshop.domain.Item;

import java.util.Arrays;
import java.util.Collection;

public class EightySixCoffeeException extends Exception {

    public Collection<EightySixEvent> getEvents() {
        return Arrays.asList(new EightySixEvent(Item.CP0FB2_BLACK), new EightySixEvent(Item.CP1FC3_HOME));
    }

}
