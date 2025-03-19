package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import org.example.model.OperationType;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletOperationRequest {
    private UUID walletId;
    private OperationType operationType;
    private BigDecimal amount;
}
