package org.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.application.dto.WalletBalanceResponse;
import org.application.dto.WalletOperationRequest;
import org.application.dto.model.OperationType;
import org.application.exception.InsufficientFundsException;
import org.application.exception.WalletNotFoundException;
import org.application.repository.entities.WalletEntity;
import org.application.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public BigDecimal processOperation(WalletOperationRequest request) {
        WalletEntity wallet = walletRepository.findById(request.getWalletId()).orElse(null);

        if (wallet == null) {
            wallet = new WalletEntity();
            wallet.setWalletId(request.getWalletId());
            wallet.setBalance(BigDecimal.ZERO);
        }

        BigDecimal amount = request.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be more than zero");
        }

        if (request.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance().add(amount));
        }

        if (request.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Not enough balance");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }

        return wallet.getBalance();
    }

    @Override
    public WalletBalanceResponse getBalance(UUID walletId) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return new WalletBalanceResponse(wallet.getWalletId(), wallet.getBalance());
    }
}
