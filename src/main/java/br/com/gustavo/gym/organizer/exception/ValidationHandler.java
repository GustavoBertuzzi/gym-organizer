package br.com.gustavo.gym.organizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                // error.getField() = nome do campo
                // error.getDefaultMessage() = a mensagem definida na anotação
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Retorna status 400 com o JSON de erros
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
