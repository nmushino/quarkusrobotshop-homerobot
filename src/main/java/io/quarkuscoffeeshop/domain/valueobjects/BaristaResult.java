package io.quarkusrobotshop.domain.valueobjects;

import io.quarkusrobotshop.homerobot.domain.EightySixEvent;

public class HomerobotResult {

    private OrderUp orderUp;

    private EightySixEvent eightySixEvent;

    private boolean isEightySixed;

    public HomerobotResult(OrderUp orderUp) {
        this.orderUp = orderUp;
    }

    public HomerobotResult(EightySixEvent eightySixEvent) {
        this.eightySixEvent = eightySixEvent;
        this.isEightySixed = true;
    }

    public EightySixEvent getEightySixEvent() {
        return eightySixEvent;
    }

    public void setEightySixEvent(EightySixEvent eightySixEvent) {
        this.eightySixEvent = eightySixEvent;
    }

    public OrderUp getOrderUp() {
        return orderUp;
    }

    public void setOrderUp(OrderUp orderUp) {
        this.orderUp = orderUp;
    }

    public boolean isEightySixed() {
        return isEightySixed;
    }

    public void setEightySixed(boolean eightySixed) {
        isEightySixed = eightySixed;
    }
}
