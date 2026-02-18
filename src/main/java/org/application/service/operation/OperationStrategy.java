package org.application.service.operation;

import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;

import java.math.BigDecimal;

public interface OperationStrategy {

    OperationType getType();

    void apply(WalletEntity wallet, BigDecimal amount);
}
