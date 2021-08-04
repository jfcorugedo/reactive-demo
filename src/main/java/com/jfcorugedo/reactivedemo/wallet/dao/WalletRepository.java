package com.jfcorugedo.reactivedemo.wallet.dao;

import com.jfcorugedo.reactivedemo.wallet.model.Wallet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface WalletRepository extends ReactiveCrudRepository<Wallet, String> {
}
