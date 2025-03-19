package org.example.service;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.example.dto.WalletBalanceResponse;
import org.example.dto.WalletOperationRequest;
import org.example.exception.InsufficientFundsException;
import org.example.exception.WalletNotFoundException;
import org.example.model.OperationType;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public void processOperation(WalletOperationRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet();
                    newWallet.setWalletId(request.getWalletId());
                    newWallet.setBalance(BigDecimal.ZERO);
                    return newWallet;
                });

        BigDecimal amount = request.getAmount();

        if (request.getOperationType() == OperationType.DEPOSIT) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } else if (request.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Not enough balance");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }

        walletRepository.save(wallet);
    }

    @Override
    public WalletBalanceResponse getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return  new WalletBalanceResponse(wallet.getWalletId(), wallet.getBalance());
    }
}
