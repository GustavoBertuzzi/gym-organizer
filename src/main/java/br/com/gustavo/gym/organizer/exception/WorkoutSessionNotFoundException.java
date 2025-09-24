package br.com.gustavo.gym.organizer.exception;

public class WorkoutSessionNotFoundException extends RuntimeException {
    public WorkoutSessionNotFoundException(String message) {
        super(message);
    }
}
