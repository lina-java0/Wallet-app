package org.application.mapper;

import org.application.dto.WalletBalanceResponse;
import org.application.repository.entities.WalletEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class WalletMapper {

    public WalletEntity toEntity(UUID walletId) {
        return WalletEntity.builder()
                .walletId(walletId)
                .balance(BigDecimal.ZERO)
                .build();
    }

    public WalletBalanceResponse toResponse(WalletEntity walletEntity) {
        return WalletBalanceResponse.builder()
                .walletId(walletEntity.getWalletId())
                .balance(walletEntity.getBalance())
                .build();
    }
}
