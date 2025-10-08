package com.transporte.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("El nombre de usuario '" + username + "' ya está en uso.");
    }
}