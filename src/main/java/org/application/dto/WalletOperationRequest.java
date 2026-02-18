package org.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.application.service.model.OperationType;
import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletOperationRequest {
    @NotNull(message = "Wallet id must not be null")
    private UUID walletId;

    @NotNull(message = "Operation type must not be null")
    private OperationType operationType;

    @NotNull(message = "Amount must not be null")
    @DecimalMin("0.01")
    private BigDecimal amount;
}
