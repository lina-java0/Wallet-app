package org.application.service;

import org.application.dto.WalletBalanceResponse;
import org.application.dto.WalletOperationRequest;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    BigDecimal processOperation(WalletOperationRequest request);

    WalletBalanceResponse getBalance(UUID walletId);
}

