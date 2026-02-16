package org.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.application.dto.WalletOperationRequest;
import org.application.dto.WalletBalanceResponse;
import org.application.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public WalletBalanceResponse processOperation(@Valid @RequestBody WalletOperationRequest request) {
        BigDecimal balance = walletService.processOperation(request);
        return new WalletBalanceResponse(request.getWalletId(), balance);
    }

    @GetMapping("/{walletId}")
    public WalletBalanceResponse getBalance(@PathVariable UUID walletId) {
        return walletService.getBalance(walletId);
    }
}
