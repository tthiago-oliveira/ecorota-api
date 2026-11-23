package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ItemAuditoriaRequestDTO(
        @NotNull(message = "O idCriterio é obrigatório")
        Integer idCriterio,

        @NotNull(message = "Informe se o critério foi atendido (true/false)")
        Boolean atendido,

        @Size(max = 255)
        String evidenciaUrl,

        @Size(max = 500)
        String observacao
) {}