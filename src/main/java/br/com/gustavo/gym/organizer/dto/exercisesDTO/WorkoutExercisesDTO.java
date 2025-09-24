package br.com.gustavo.gym.organizer.dto.exercisesDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record WorkoutExercisesDTO(
        @NotNull(message = "O ID da sessão é obrigatório")
        Long sessionId,

        @NotNull(message = "O ID do exercício é obrigatório")
        Long exerciseId,

        @PositiveOrZero(message = "O peso deve ser maior ou igual a 0")
        Double weight,

        @Positive(message = "As repetições devem ser maiores que 0")
        int repetitions,

        @Positive(message = "As séries devem ser maiores que 0")
        int sets,

        @PositiveOrZero(message = "O tempo de descanso deve ser maior ou igual a 0")
        Integer breakTime

) {}
