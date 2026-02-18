package org.application.exception;

public class WalletConflictException extends RuntimeException {
    public WalletConflictException(String message) {
        super(message);
    }
}