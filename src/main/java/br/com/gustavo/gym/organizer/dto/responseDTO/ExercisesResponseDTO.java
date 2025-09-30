package br.com.gustavo.gym.organizer.dto.responseDTO;

import java.util.List;

public record ExercisesResponseDTO(
        Long exerciseId,
        String name,
        String muscleGroup,
        String equipment,
        String description
) {}
