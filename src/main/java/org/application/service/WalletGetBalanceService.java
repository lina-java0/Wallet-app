package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletBalanceResponse;
import org.application.exception.WalletNotFoundException;
import org.application.mapper.WalletMapper;
import org.application.repository.WalletRepository;
import org.application.repository.entities.WalletEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletGetBalanceService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Transactional(readOnly = true)
    public WalletBalanceResponse getBalance(UUID walletId) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return walletMapper.toResponse(wallet);
    }
}
