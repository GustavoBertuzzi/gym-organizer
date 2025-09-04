package br.com.gustavo.gym.organizer.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "Username não pode ser vazio")
        @Email(message = "Email inválido")
        String username,

        @NotBlank(message = "Password não pode ser vazio")
        String password)

{}
