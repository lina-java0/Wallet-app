package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.example.model.OperationType;
import java.math.BigDecimal;
import java.util.UUID;

/*
Суть WalletOperationRequest: это DTO, который приходит в теле запроса @RequestBody.
Он содержит информацию о том, что хочет сделать пользователь.
 */

@Getter
@Setter
public class WalletOperationRequest {
    @NotNull
    private UUID walletId;

    @NotNull
    private OperationType operationType;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
