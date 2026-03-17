package org.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import org.application.service.model.OperationType;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletOperationRequest {

    @NotNull(message = "Wallet id must not be null")
    private UUID walletId;

    @NotNull(message = "Operation type must not be null")
    private OperationType operationType;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
}
