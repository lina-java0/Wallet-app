package org.application.validation;

import org.application.dto.WalletOperationRequest;
import org.application.exception.InsufficientFundsException;
import org.application.repository.entities.WalletEntity;
import org.springframework.stereotype.Component;

@Component
public class OperationWithdrawValidation {

    public void validateOperation(WalletEntity wallet, WalletOperationRequest request) {
        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Not enough balance");
        }
    }
}
