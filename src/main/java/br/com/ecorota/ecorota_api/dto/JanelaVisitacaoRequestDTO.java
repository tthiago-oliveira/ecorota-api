package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

public record JanelaVisitacaoRequestDTO(
        @NotNull(message = "O idAtrativo é obrigatório")
        Integer idAtrativo,

        @NotNull(message = "A data da visita é obrigatória")
        LocalDate dataVisita,

        @NotNull(message = "A hora de início é obrigatória")
        LocalTime horaInicio,

        @NotNull(message = "O total de vagas é obrigatório")
        @Positive(message = "O total de vagas deve ser maior que zero")
        Integer vagasTotal
) {}