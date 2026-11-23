package br.com.ecorota.ecorota_api.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponseDTO> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ErroResponseDTO body = new ErroResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getReason() != null ? ex.getReason() : "Erro ao processar a requisição"
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            campos.put(error.getField(), error.getDefaultMessage());
        }
        ErroResponseDTO body = new ErroResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Verifique os campos enviados no formulário",
                campos
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> handleJsonInvalido(HttpMessageNotReadableException ex) {
        ErroResponseDTO body = new ErroResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "JSON inválido",
                "Formato de dados inválido ou valor fora das opções permitidas"
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDTO> handleIntegridadeBanco(DataIntegrityViolationException ex) {
        ErroResponseDTO body = new ErroResponseDTO(
                HttpStatus.CONFLICT.value(),
                "Conflito de integridade",
                "Operação violou uma restrição do banco de dados (registro duplicado, vínculo inexistente ou regra de CHECK)"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}