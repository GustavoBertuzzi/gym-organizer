package br.com.gustavo.gym.organizer.exception;

import br.com.gustavo.gym.organizer.dto.errorDTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Existem campos inválidos",
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            DuplicateUserException.class,
            CredentialAlreadyInUseException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleUserExceptions(RuntimeException ex) {
        HttpStatus status;
        String error;

        if (ex instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            error = "Usuário não encontrado";
        } else {
            status = HttpStatus.BAD_REQUEST;
            error = "Erro de usuário";
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                error,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler({
            ExerciseNotFoundException.class,
            ExerciseAlreadyInUse.class,
            WorkoutSessionNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleExerciseExceptions(RuntimeException ex) {
        HttpStatus status;
        String error;

        if (ex instanceof ExerciseNotFoundException || ex instanceof WorkoutSessionNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            error = "Recurso não encontrado";
        } else {
            status = HttpStatus.BAD_REQUEST;
            error = "Erro de exercício";
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                error,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciais inválidas",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handlePermissionDenied(PermissionDeniedException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Permissão negada",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericRuntimeException(RuntimeException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
