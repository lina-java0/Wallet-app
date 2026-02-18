package org.application.service.operation;

import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DepositStrategy implements OperationStrategy {


    @Override
    public OperationType getType() {
        return OperationType.DEPOSIT;
    }

    @Override
    public void apply(WalletEntity wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));
    }
}
