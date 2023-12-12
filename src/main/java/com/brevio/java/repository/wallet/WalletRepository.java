package com.brevio.java.repository.wallet;

import com.brevio.java.entity.wallet.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {
}
