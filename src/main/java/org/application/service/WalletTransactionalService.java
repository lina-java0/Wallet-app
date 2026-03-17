package org.application.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletTransactionalService {

    private final WalletRepository walletRepository;
    private final OperationStrategyFactory strategyFactory;

    @Retryable(
            retryFor = {
                    PessimisticLockException.class,
                    CannotAcquireLockException.class,
                    org.springframework.dao.PessimisticLockingFailureException.class
            },
            maxAttemptsExpression = "#{${wallet.retry.max-attempts}}",
            backoff = @Backoff(
                    delayExpression = "#{${wallet.retry.delay}}",
                    multiplierExpression = "#{${wallet.retry.multiplier}}"
            )
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
