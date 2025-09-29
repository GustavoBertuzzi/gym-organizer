package br.com.gustavo.gym.organizer.dto.responseDTO;

import br.com.gustavo.gym.organizer.model.WorkoutSessionsModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record WorkoutSessionsGetResponseDTO(
        Long id,
        Long userId,
        LocalDateTime sessionDate,
        Long sleepHours,
        Boolean preWorkout,
        String notes,
        List<WorkoutExerciseGetResponseDTO> exercises
) {

    public WorkoutSessionsGetResponseDTO(WorkoutSessionsModel session) {
        this(
                session.getSessionId(),
                session.getUser().getUserId(),
                session.getSessionDate(),
                session.getSleepHours(),
                session.getPreWorkout(),
                session.getNotes(),
                session.getExercises() != null
                        ? session.getExercises().stream()
                        .map(WorkoutExerciseGetResponseDTO::new)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }
}
