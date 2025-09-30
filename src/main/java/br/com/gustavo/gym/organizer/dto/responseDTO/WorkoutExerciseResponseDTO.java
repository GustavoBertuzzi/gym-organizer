package br.com.gustavo.gym.organizer.dto.responseDTO;

public record WorkoutExerciseResponseDTO(
        Long workoutExerciseId,
        Double weight,
        Integer repetitions,
        Integer sets,
        Integer breakTime, ExercisesResponseDTO exerciseDTO) {
}
