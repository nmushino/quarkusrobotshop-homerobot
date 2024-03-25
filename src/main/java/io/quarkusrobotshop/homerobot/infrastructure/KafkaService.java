package io.quarkusrobotshop.homerobot.infrastructure;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkusrobotshop.homerobot.domain.Homerobot;
import io.quarkusrobotshop.domain.valueobjects.OrderIn;
import io.quarkusrobotshop.domain.valueobjects.OrderUp;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@RegisterForReflection
public class KafkaService {

    Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Inject
    Homerobot homerobot;

    @Inject
    @Channel("orders-up")
    Emitter<OrderUp> orderUpEmitter;

    @Inject
    @Channel("eighty-six")
    Emitter<String> eightySixEmitter;

    @Incoming("orders-in")
    public CompletableFuture onOrderIn(final OrderIn orderIn) {

        logger.debug("OrderTicket received: {}", orderIn);

        return CompletableFuture.supplyAsync(() -> {

            return homerobot.make(orderIn);
        }).thenApply(homerobotResult -> {

            if (homerobotResult.isEightySixed()) {

                eightySixEmitter.send(orderIn.getItem().toString());
            }else{

                logger.debug( "OrderUp: {}", homerobotResult.getOrderUp());
                orderUpEmitter.send(homerobotResult.getOrderUp());
            }

            return null;
        });
    }
}
