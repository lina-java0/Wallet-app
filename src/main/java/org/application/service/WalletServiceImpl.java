package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletBalanceResponse;
import org.application.dto.WalletOperationRequest;
import org.application.exception.WalletNotFoundException;
import org.application.repository.entities.WalletEntity;
import org.application.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletCreationService creationService;
    private final WalletTransactionalService transactionalService;
    private final WalletRepository walletRepository;

    @Override
    public UUID createWallet(UUID walletId) {
        return creationService.createUnexcitingWallet(walletId);
    }

    @Override
    public BigDecimal processOperation(WalletOperationRequest request) {
        return transactionalService.process(request);
    }

    @Override
    public WalletBalanceResponse getBalance(UUID walletId) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return new WalletBalanceResponse(wallet.getWalletId(), wallet.getBalance());
    }
}