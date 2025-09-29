package br.com.gustavo.gym.organizer.dto.responseDTO;

import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;

public record WorkoutExerciseGetResponseDTO(
        Long id,
        Long exerciseId,
        String exerciseName,
        Double weight,
        int repetitions,
        int sets,
        Integer breakTime
) {

    public WorkoutExerciseGetResponseDTO(WorkoutExerciseModel exercise) {
        this(
                exercise.getWorkoutExerciseId(),
                exercise.getExercise().getExerciseId(),
                exercise.getExercise().getName(),
                exercise.getWeight(),
                exercise.getRepetitions(),
                exercise.getSets(),
                exercise.getBreakTime()
        );
    }
}
