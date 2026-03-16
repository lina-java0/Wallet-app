package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletBalanceResponse;
import org.application.dto.WalletOperationRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletCreationService creationService;
    private final WalletTransactionalService transactionalService;
    private final WalletGetBalanceService getBalanceService;

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
        return getBalanceService.getBalance(walletId);
    }
}