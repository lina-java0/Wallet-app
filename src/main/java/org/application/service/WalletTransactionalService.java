package org.application.service;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletOperationRequest;
import org.application.exception.WalletNotFoundException;
import org.application.repository.WalletRepository;
import org.application.repository.entities.WalletEntity;
import org.application.service.operation.OperationStrategy;
import org.application.service.operation.OperationStrategyFactory;
import org.hibernate.PessimisticLockException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletTransactionalService {

    private final WalletRepository walletRepository;
    private final OperationStrategyFactory strategyFactory;

    @Retryable(
            retryFor = {
                    PessimisticLockException.class,
                    CannotAcquireLockException.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 50)
    )
    @Transactional
    public BigDecimal process(WalletOperationRequest request) {

        WalletEntity wallet = walletRepository.findByIdForUpdate(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        OperationStrategy strategy = strategyFactory.getStrategy(request.getOperationType());

        strategy.apply(wallet, request);

        return wallet.getBalance();
    }
}
