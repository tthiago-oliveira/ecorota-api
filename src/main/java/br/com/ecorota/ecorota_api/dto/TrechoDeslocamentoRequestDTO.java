package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record TrechoDeslocamentoRequestDTO(
        @NotNull(message = "O idFator é obrigatório")
        Integer idFator,

        @NotBlank(message = "A origem é obrigatória")
        @Size(max = 120)
        String origem,

        @NotBlank(message = "O destino é obrigatório")
        @Size(max = 120)
        String destino,

        @NotNull(message = "A distância em km é obrigatória")
        @Positive(message = "A distância deve ser maior que zero")
        BigDecimal distanciaKm
) {}