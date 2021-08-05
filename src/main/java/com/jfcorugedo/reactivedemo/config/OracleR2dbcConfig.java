package com.jfcorugedo.reactivedemo.config;

import com.jfcorugedo.reactivedemo.wallet.model.Wallet;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@ConditionalOnProperty(name = "dababase.vendor", havingValue = "oracle")
@Slf4j
public class OracleR2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${database.host:localhost}")
    private String host;

    @Value("${database.port:1521}")
    private int port;

    @Value("${database.serviceName}")
    private String serviceName;

    @Value("${database.user}")
    private String user;

    @Override
    @Bean("r2dbcConnectionFactory")
    public ConnectionFactory connectionFactory() {
        String descriptor = "(DESCRIPTION=" +
                "(ADDRESS=(HOST=" + host + ")(PORT=" + port + ")(PROTOCOL=tcp))" +
                "(CONNECT_DATA=(SERVICE_NAME=" + serviceName + ")))";

        log.info("Creating connection factory with descriptor " + descriptor);

        String r2dbcUrl = "r2dbc:oracle://?oracleNetDescriptor="+descriptor;
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(r2dbcUrl)
                .mutate()
                .option(ConnectionFactoryOptions.USER, user)
                .option(ConnectionFactoryOptions.PASSWORD, System.getenv("DB_PASSWORD"))
                .build());
    }

    @Bean
    BeforeConvertCallback<Wallet> idGenerator() {
        return (entity, table) -> entity.getId() == null ? Mono.just(entity.withId(UUID.randomUUID().toString())) : Mono.just(entity);
    }
}
