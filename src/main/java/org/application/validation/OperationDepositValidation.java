package org.application.validation;

import org.application.dto.WalletOperationRequest;
import org.application.repository.entities.WalletEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OperationDepositValidation {

    public void validateOperation(WalletEntity wallet, WalletOperationRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
