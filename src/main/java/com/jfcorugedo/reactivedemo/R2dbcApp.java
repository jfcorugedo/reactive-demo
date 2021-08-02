package com.jfcorugedo.reactivedemo;

import com.jfcorugedo.reactivedemo.people.model.Person;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

@Slf4j
public class R2dbcApp {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);

        template.getDatabaseClient().sql("CREATE TABLE person" +
                "(id VARCHAR(255) PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT)")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        template.insert(Person.class)
                .using(new Person("joe", "Joe", 34))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        template.select(Person.class)
                .first()
                .doOnNext(it -> log.info("Records stored in the database: {}", it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
