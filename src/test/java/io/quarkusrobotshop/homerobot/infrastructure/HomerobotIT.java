package io.quarkusrobotshop.homerobot.infrastructure;

import io.quarkusrobotshop.domain.*;
import io.quarkusrobotshop.infrastructure.KafkaIT;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.StringReader;
import java.time.Duration;
import java.util.*;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(KafkaTestResource.class)
public class HomerobotIT {

    Jsonb jsonb = JsonbBuilder.create();

    public HomerobotIT() {
    }

    @Test
    public void testOrderIn() {
        OrderInEvent orderIn = new OrderInEvent(EventType.BEVERAGE_ORDER_IN, UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Lemmy", Item.CP0FB2_BLACK);
        producerMap.get("homerobot-in").send(new ProducerRecord<>("homerobot-in", jsonb.toJson(orderIn)));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            assertNull(e);
        }

        // Get the appropriate consumer, point to the first message, and pull all messages
        final KafkaConsumer homerobotConsumer = consumerMap.get("orders");
        homerobotConsumer.seekToBeginning(new ArrayList<TopicPartition>());
        final ConsumerRecords<String, String> homerobotRecords = homerobotConsumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : homerobotRecords) {
            System.out.println(record.value());
            //[{"item":"CP0FB2_BLACK","itemId":"901f1fb5-7ebf-4d2d-b0cd-0a80fa5a91e2","name":"Lemmy","orderId":"8a44cc4c-df49-4180-b0c5-c4ef34def5be","eventType":"BEVERAGE_ORDER_UP","madeBy":"jedavis-mac"}]
            System.out.println(record.value());
            JsonReader jsonReader = Json.createReader(new StringReader(record.value()));
            JsonObject jsonObject = jsonReader.readObject();
            assertEquals("Lemmy", jsonObject.getString("name"));
            assertEquals(Item.CP0FB2_BLACK.toString(), jsonObject.getString("item"));
            assertEquals(EventType.BEVERAGE_ORDER_UP.toString(), jsonObject.getString("eventType"));
        }
    }


}
