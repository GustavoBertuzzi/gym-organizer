package br.com.gustavo.gym.organizer.mapper;

import br.com.gustavo.gym.organizer.dto.responseDTO.ExercisesAddResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutExerciseResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;

import java.util.stream.Collectors;

public class WorkoutSessionsMapper {

    public static WorkoutSessionsResponseDTO toResponse(WorkoutSessionsModel session) {
        return new WorkoutSessionsResponseDTO(
                session.getSessionId(),
                session.getExercises().stream()
                        .collect(Collectors.groupingBy(we -> we.getExercise()))
                        .entrySet().stream()
                        .map(entry -> {
                            var exercise = entry.getKey();
                            var workoutExercises = entry.getValue().stream()
                                    .map(we -> new WorkoutExerciseResponseDTO(
                                            we.getWorkoutExerciseId(),
                                            we.getWeight(),
                                            we.getRepetitions(),
                                            we.getSets(),
                                            we.getBreakTime()
                                    ))
                                    .toList();

                            return new ExercisesAddResponseDTO(
                                    exercise.getExerciseId(),
                                    exercise.getName(),
                                    exercise.getMuscleGroup(),
                                    exercise.getEquipment(),
                                    exercise.getDescription(),
                                    exercise.getUser().getEmail(),
                                    workoutExercises
                            );
                        })
                        .toList()
        );
    }
}
