package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RespostaAvaliacaoRequestDTO(
        @NotBlank(message = "A resposta do fornecedor é obrigatória")
        @Size(max = 1000)
        String resposta
) {}