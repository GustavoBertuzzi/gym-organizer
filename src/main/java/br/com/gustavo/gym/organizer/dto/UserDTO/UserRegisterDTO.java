package br.com.gustavo.gym.organizer.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank(message = "Username não pode ser vazio")
        @Email(message = "Email inválido")
        String username,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                message = "Senha inválida. A senha deve atender aos seguintes requisitos:\n" +
                        "- Tamanho entre 8 e 20 caracteres\n" +
                        "- Pelo menos uma letra maiúscula\n" +
                        "- Pelo menos uma letra minúscula\n" +
                        "- Pelo menos um número\n" +
                        "- Pelo menos um caractere especial (@$!%*?&)"
        )
        String password)

{}
