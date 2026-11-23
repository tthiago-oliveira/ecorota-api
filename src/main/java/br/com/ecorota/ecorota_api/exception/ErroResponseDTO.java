package br.com.ecorota.ecorota_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        Map<String, String> campos
) {
    public ErroResponseDTO(int status, String erro, String mensagem) {
        this(LocalDateTime.now(), status, erro, mensagem, null);
    }

    public ErroResponseDTO(int status, String erro, String mensagem, Map<String, String> campos) {
        this(LocalDateTime.now(), status, erro, mensagem, campos);
    }
}