package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
/*
@Entity — указывает, что класс связан с таблицей в базе данных.
@Table(name = "wallets") — явно задаём имя таблицы.
 */
@Getter
@Setter
public class Wallet {

    @Id
    private UUID walletId;
    /*
    @Id — помечает поле как первичный ключ таблицы.
    UUID walletId — уникальный идентификатор кошелька. Его генерирует клиент (в запросе), а не база.
     */

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    /*
    @Column(nullable = false) — это поле не может быть null в базе.
     */
}
