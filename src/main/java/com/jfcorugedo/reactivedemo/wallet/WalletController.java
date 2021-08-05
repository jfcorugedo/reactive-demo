package com.jfcorugedo.reactivedemo.wallet;

import com.jfcorugedo.reactivedemo.wallet.dao.WalletRepository;
import com.jfcorugedo.reactivedemo.wallet.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.data.relational.core.query.Query.query;

@RestController
@RequestMapping("wallet")
@Slf4j
public class WalletController {

    private WalletRepository walletRepository;

    @Autowired
    private R2dbcEntityTemplate template;

    public WalletController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Wallet>> get(@PathVariable("id") String id) {

        return walletRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("count")
    public Mono<ResponseEntity<Long>> count() {

        return walletRepository
                .count()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Wallet>> create() {

        return walletRepository
                .save(Wallet.empty())
                .map(w -> ResponseEntity.status(201).body(w));
    }

    @PostMapping("/entityTemplate")
    public Mono<ResponseEntity<Wallet>> insert() {

        log.info("Inserting using R2dbcEntityTemplate");
        return template.insert(new Wallet(null, BigDecimal.ZERO))
                .map(ResponseEntity::ok);
    }
}
