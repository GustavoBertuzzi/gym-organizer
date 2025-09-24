package br.com.gustavo.gym.organizer.dto.responseDTO;

import java.util.List;

public record WorkoutSessionsResponseDTO(
        Long workoutSessionId,
        List<ExercisesAddResponseDTO> exercises
) {}
