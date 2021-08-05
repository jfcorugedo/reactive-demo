package com.jfcorugedo.reactivedemo.config;

import com.jfcorugedo.reactivedemo.wallet.model.Wallet;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@ConditionalOnProperty(name = "dababase.vendor", havingValue = "h2")
@Slf4j
public class H2R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${database.connectionUrl}")
    private String connectionUrl;

    @Override
    @Bean("r2dbcConnectionFactory")
    public ConnectionFactory connectionFactory() {
        log.info("Creating connection factory");
        return ConnectionFactories.get(connectionUrl);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory r2dbcConnectionFactory) {
        log.info("Initializing database with DDL");
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(r2dbcConnectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                        new ByteArrayResource(
                                "DROP TABLE IF EXISTS wallet; CREATE TABLE wallet (id VARCHAR(36) PRIMARY KEY, balance NUMBER NOT NULL)".getBytes()
                        )
                )
        );

        return initializer;
    }

    @Bean
    BeforeConvertCallback<Wallet> idGenerator() {
        return (entity, table) -> entity.getId() == null ? Mono.just(entity.withId(UUID.randomUUID().toString())) : Mono.just(entity);
    }
}
