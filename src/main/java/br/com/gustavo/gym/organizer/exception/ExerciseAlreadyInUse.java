package br.com.gustavo.gym.organizer.exception;

public class ExerciseAlreadyInUse extends RuntimeException {
    public ExerciseAlreadyInUse(String message) {
        super(message);
    }
}
