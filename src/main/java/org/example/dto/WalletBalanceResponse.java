package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/*
Суть WalletBalanceResponse: DTO для отправки информации о балансе обратно клиенту.
Используется в controller.getBalance(...).
 */

@Getter
@AllArgsConstructor
public class WalletBalanceResponse {
    private UUID walletId;
    private BigDecimal balance;
}
