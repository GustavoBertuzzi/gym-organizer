package br.com.gustavo.gym.organizer.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegisterDTO(
        @NotBlank(message = "email não pode ser vazio")
        @Email(message = "Email inválido")
        String email,

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
