package org.example.repository;

import org.example.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/*
JpaRepository — интерфейс от Spring Data JPA,
который уже умеет делать основные операции: save, findById, delete, и т.д.

Аннотация @Repository сообщает Spring, что это компонент, отвечающий за доступ к данным.
Spring сможет его внедрить (@Autowired или через конструктор).
 */

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
