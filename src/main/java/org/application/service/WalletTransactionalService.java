package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletOperationRequest;
import org.application.exception.WalletNotFoundException;
import org.application.repository.WalletRepository;
import org.application.repository.entities.WalletEntity;
import org.application.service.operation.OperationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletTransactionalService {

    private final WalletRepository walletRepository;
    private final List<OperationStrategy> strategies;

    @Transactional
    public BigDecimal process(WalletOperationRequest request) {
        WalletEntity wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        OperationStrategy strategy = strategies.stream()
                .filter(s -> s.getType() == request.getOperationType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operation"));

        strategy.apply(wallet, request.getAmount());

        walletRepository.save(wallet);

        return wallet.getBalance();
    }
}
