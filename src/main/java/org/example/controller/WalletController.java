package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.WalletOperationRequest;
import org.example.dto.WalletBalanceResponse;
import org.example.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController //Этот класс будет обрабатывать REST-запросы и возвращать JSON-ответы
@RequestMapping("/api/v1/wallets") /* Задаёт общий путь для всех методов в этом контроллере.
Всё, что внутри — будет доступно по /api/v1/wallets/... */
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> processOperation(@Valid @RequestBody WalletOperationRequest request) {
        walletService.processOperation(request);
        return ResponseEntity.ok().build();
    }

    /*
    @RequestBody WalletOperationRequest request
    говорит Spring: "разбери тело запроса и создай объект WalletOperationRequest".
    @Valid запускает валидацию.
    Если поля будут недопустимыми (например, null), Spring выбросит исключение MethodArgumentNotValidException.
    Если всё ок — контроллер вызывает: walletService.processOperation(request);
    бизнес-логика уходит в WalletServiceImpl.
     */

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable UUID walletId) {
        WalletBalanceResponse response = walletService.getBalance((walletId));
        return ResponseEntity.ok(response);
    }

    /*
    @PathVariable UUID walletId
    говорит Spring: "вытащи walletId из URL и передай в метод".
     */
}
