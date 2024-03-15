# Docs
Please see the Github Pages Site for complete documentation: [quarkusrobotshop.github.io](https://quarkusrobotshop.github.io)

# About 
This repo contains the Homerobot microservice which is responsible for making drinks.  The Homerobot microservice listens on a Kafka topic for incoming orders, applies the business logic for making an order, and then sends an update on another Kafka topic.

This project uses Quarkus, the Supersonic Subatomic Java Framework.  If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Local deveplomnent steps 

This project requires Kafka.  The quarkusrobotshop-support project contains a Docker compose file that will start Kafka.

```
git clone https://github.com/quarkusrobotshop/quarkusrobotshop-support.git
git clone https://github.com/quarkusrobotshop/quarkusrobotshop-homerobot.git
```

From inside the quarkusrobotshop-support folder run:

```
docker compose up
```

From inside the quarkusrobotshop-homerobot folder run:
```
./mvnw quarkus:dev
```

## Packaging the application

The application is packageable using `./mvnw package`.
It produces the executable `quarkusrobotshop-homerobot-1.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkusrobotshop-homerobot-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/quarkusrobotshop-homerobot-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .

## Running with Docker

Quarkus' configuration can be environment specific: https://quarkus.io/guides/config

```shell
docker run -i --network="host" quarkusrobotshop-homerobot/quarkus-cafe-homerobot:latest
```
