package br.com.gustavo.gym.organizer.dto.responseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutSessionsResponseDTO(
        Long workoutSessionId,
        Long userId,
        LocalDateTime sessionDate,
        Long sleepHours,
        Boolean preWorkout,
        String notes,
        List<WorkoutExerciseResponseDTO> exercises
) {}
