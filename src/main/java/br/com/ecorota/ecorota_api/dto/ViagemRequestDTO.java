package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ViagemRequestDTO(
        @NotNull(message = "O idViajante é obrigatório")
        Integer idViajante,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim
) {}