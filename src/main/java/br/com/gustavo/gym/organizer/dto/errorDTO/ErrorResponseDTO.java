package br.com.gustavo.gym.organizer.dto.errorDTO;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fields
) {
}
