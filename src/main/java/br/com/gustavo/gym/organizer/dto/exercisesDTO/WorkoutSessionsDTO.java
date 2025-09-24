package br.com.gustavo.gym.organizer.dto.exercisesDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record WorkoutSessionsDTO(
        @Min(value = 0, message = "As horas de sono não podem ser negativas")
        @Max(value = 24, message = "As horas de sono não podem ultrapassar 24h")
        Long sleepHours,

        Boolean preWorkout,

        @Size(max = 500, message = "As anotações devem ter no máximo 500 caracteres")
        String notes
) {}
