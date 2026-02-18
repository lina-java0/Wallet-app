package org.application.service.operation;

import org.application.exception.InsufficientFundsException;
import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WithdrawStrategy implements OperationStrategy {

    @Override
    public OperationType getType() {
        return OperationType.WITHDRAW;
    }

    @Override
    public void apply(WalletEntity wallet, BigDecimal amount) {

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
    }
}
