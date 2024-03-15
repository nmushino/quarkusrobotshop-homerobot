package io.quarkusrobotshop.homerobot.domain;

import io.quarkusrobotshop.domain.Item;

import java.util.Arrays;
import java.util.Collection;

public class EightySixCoffeeException extends Exception {

    public Collection<EightySixEvent> getEvents() {
        return Arrays.asList(new EightySixEvent(Item.CP0FB2_BLACK), new EightySixEvent(Item.CP1FC3_HOME));
    }

}
