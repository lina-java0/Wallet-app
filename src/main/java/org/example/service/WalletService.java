package org.example.service;

import org.example.dto.WalletBalanceResponse;
import org.example.dto.WalletOperationRequest;

import java.util.UUID;

public interface WalletService {

    void processOperation(WalletOperationRequest request);

    WalletBalanceResponse getBalance(UUID walletId);
}

