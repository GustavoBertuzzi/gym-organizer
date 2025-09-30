package br.com.gustavo.gym.organizer.dto.responseDTO;

public record WorkoutExerciseResponseDTO(
        Long workoutExerciseId,
        ExercisesResponseDTO exerciseDTO,
        Double weight,
        Integer repetitions,
        Integer sets,
        Integer breakTime) {
}
