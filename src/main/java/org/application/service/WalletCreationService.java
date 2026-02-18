package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.repository.WalletRepository;
import org.application.repository.entities.WalletEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletCreationService {

    private final WalletRepository walletRepository;

    public UUID createUnexcitingWallet(UUID walletId) {

        if (walletRepository.existsById(walletId)) {
            throw new IllegalArgumentException("Wallet already exists");
        }

        WalletEntity wallet = new WalletEntity();
        wallet.setWalletId(walletId);
        wallet.setBalance(BigDecimal.ZERO);

        walletRepository.save(wallet);

        return walletId;
    }
}
