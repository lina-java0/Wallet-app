package org.example.service;

import jakarta.transaction.Transactional;
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
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    /*
    @Transactional — говорит Spring: "все действия в методе должны быть выполнены в рамках одной транзакции".
    Если возникнет исключение — всё откатится.
     */
    public void processOperation(WalletOperationRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElse(null);

        if (wallet == null) {
            wallet = new Wallet();
            wallet.setWalletId(request.getWalletId());
            wallet.setBalance(BigDecimal.ZERO);
        }

        BigDecimal amount = request.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

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

        return new WalletBalanceResponse(wallet.getWalletId(), wallet.getBalance());
    }
}
