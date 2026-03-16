package org.application.service.operation;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletOperationRequest;
import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;
import org.application.validation.OperationDepositValidation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositStrategy implements OperationStrategy {

    private final OperationDepositValidation validation;

    @Override
    public OperationType getType() {
        return OperationType.DEPOSIT;
    }

    @Override
    public void apply(WalletEntity wallet, WalletOperationRequest request) {
        validation.validateOperation(wallet, request);
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
    }
}
