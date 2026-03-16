package org.application.service.operation;

import org.application.dto.WalletOperationRequest;
import org.application.repository.entities.WalletEntity;
import org.application.service.model.OperationType;

public interface OperationStrategy {

    OperationType getType();

    void apply(WalletEntity wallet, WalletOperationRequest request);
}
