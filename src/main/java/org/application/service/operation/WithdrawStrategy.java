package org.application.service.operation;

import lombok.RequiredArgsConstructor;
import org.application.dto.WalletOperationRequest;
import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;
import org.application.validation.OperationWithdrawValidation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawStrategy implements OperationStrategy {

    private final OperationWithdrawValidation validation;

    @Override
    public OperationType getType() {
        return OperationType.WITHDRAW;
    }

    @Override
    public void apply(WalletEntity wallet, WalletOperationRequest request) {
        validation.validateOperation(wallet, request);
        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
    }
}
