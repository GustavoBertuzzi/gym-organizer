package br.com.gustavo.gym.organizer.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "email não pode ser vazio")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Password não pode ser vazio")
        String password)

{}
