package br.com.gustavo.gym.organizer.mapper;

import br.com.gustavo.gym.organizer.dto.responseDTO.ExercisesResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutExerciseResponseDTO;
import br.com.gustavo.gym.organizer.dto.responseDTO.WorkoutSessionsResponseDTO;
import br.com.gustavo.gym.organizer.model.ExercisesModel;
import br.com.gustavo.gym.organizer.model.WorkoutExerciseModel;
import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;

import java.util.stream.Collectors;

public class Mapper {

    public static ExercisesResponseDTO toExerciseDTO(ExercisesModel model) {
        return new ExercisesResponseDTO(
                model.getExerciseId(),
                model.getName(),
                model.getMuscleGroup(),
                model.getEquipment(),
                model.getDescription()
        );
    }


    public static WorkoutExerciseResponseDTO toWorkoutExerciseDTO(WorkoutExerciseModel model) {
        return new WorkoutExerciseResponseDTO(
                model.getWorkoutExerciseId(),
                model.getWeight(),
                model.getRepetitions(),
                model.getSets(),
                model.getBreakTime(),
                toExerciseDTO(model.getExercise())
        );
    }


    public static WorkoutSessionsResponseDTO toWorkoutSessionDTO(WorkoutSessionsModel model) {
        return new WorkoutSessionsResponseDTO(
                model.getSessionId(),
                model.getUser().getUserId(),
                model.getSessionDate(),
                model.getSleepHours(),
                model.getPreWorkout(),
                model.getNotes(),
                model.getExercises().stream()
                        .map(Mapper::toWorkoutExerciseDTO)
                        .toList()
        );
    }

}

