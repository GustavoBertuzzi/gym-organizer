package br.com.gustavo.gym.organizer.exception;

public class CredentialAlreadyInUseException extends RuntimeException {
    public CredentialAlreadyInUseException(String message) {
        super(message);
    }
}
