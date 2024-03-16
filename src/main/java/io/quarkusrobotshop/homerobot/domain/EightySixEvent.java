package io.quarkusrobotshop.homerobot.domain;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkusrobotshop.domain.Item;

@RegisterForReflection
public class EightySixEvent implements Event {

        Item item;

        EventType eventType = EventType.EIGHTY_SIX;

        public EightySixEvent() {
        }

        public EightySixEvent(Item item) {
            this.item = item;
        }

        @Override
        public EventType getEventType() {
                return this.eventType;
        }
}
