package com.jfcorugedo.reactivedemo.wallet;

import com.jfcorugedo.reactivedemo.wallet.dao.WalletRepository;
import com.jfcorugedo.reactivedemo.wallet.model.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("wallet")
public class WalletController {

    private WalletRepository walletRepository;

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

    @PostMapping
    public Mono<ResponseEntity<Wallet>> create() {

        return walletRepository
                .save(Wallet.empty())
                .map(w -> ResponseEntity.status(201).body(w));
    }
}
