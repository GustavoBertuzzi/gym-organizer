package br.com.gustavo.gym.organizer.mapper;

import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutExerciseResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;

import java.util.stream.Collectors;

public class Mapper {

    public static WorkoutSessionsResponseDTO toResponse(WorkoutSessionsModel session) {
        return new WorkoutSessionsResponseDTO(
                session.getSessionId(),
                session.getUser().getUserId(),
                session.getSessionDate(),
                session.getSleepHours(),
                session.getPreWorkout(),
                session.getNotes(),
                session.getExercises().stream()
                        .map(Mapper::toResponse)
                        .collect(Collectors.toList())
        );
    }

    public static WorkoutExerciseResponseDTO toResponse(WorkoutExerciseModel exercise) {
        return new WorkoutExerciseResponseDTO(
                exercise.getWorkoutExerciseId(),
                exercise.getWeight(),
                exercise.getRepetitions(),
                exercise.getSets(),
                exercise.getBreakTime()
        );
    }
}
