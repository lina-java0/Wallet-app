package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class WalletBalanceResponse {
    private UUID walletId;
    private BigDecimal balance;
}
