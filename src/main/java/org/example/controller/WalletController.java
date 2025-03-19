package org.example.controller;

import org.example.dto.WalletOperationRequest;
import org.example.dto.WalletBalanceResponse;
import org.example.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> processOperation(@RequestBody WalletOperationRequest request) {
        walletService.processOperation(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable UUID walletId) {
        WalletBalanceResponse response = walletService.getBalance((walletId));
        return ResponseEntity.ok(response);
    }
}
