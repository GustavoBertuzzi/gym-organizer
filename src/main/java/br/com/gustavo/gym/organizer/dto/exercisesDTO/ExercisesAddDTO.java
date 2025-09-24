package br.com.gustavo.gym.organizer.dto.exercisesDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ExercisesAddDTO(

        @NotBlank(message = "O nome do exercício não pode estar vazio")
        @Size(max = 100, message = "O nome do exercício deve ter no máximo 100 caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "O nome do exercício só pode conter letras, números e espaços")
        String name,

        @NotBlank(message = "O grupo muscular não pode estar vazio")
        @Size(max = 50, message = "O grupo muscular deve ter no máximo 50 caracteres")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O grupo muscular só pode conter letras e espaços")
        String muscleGroup,

        @NotBlank(message = "O equipamento não pode estar vazio")
        @Size(max = 50, message = "O equipamento deve ter no máximo 50 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "O equipamento só pode conter letras, números e espaços")
        String equipment,

        @NotBlank(message = "A descrição não pode estar vazia")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @Pattern(regexp = "^[\\p{L}0-9\\s.,!?()\\-]+$", message = "A descrição contém caracteres inválidos")
        String description

) {}
