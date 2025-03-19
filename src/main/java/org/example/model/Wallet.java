package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet {

    @Id
    private UUID walletId;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

}
