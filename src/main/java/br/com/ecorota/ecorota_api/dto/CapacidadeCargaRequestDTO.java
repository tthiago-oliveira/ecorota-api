package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CapacidadeCargaRequestDTO(
        @NotNull(message = "O idAtrativo é obrigatório")
        Integer idAtrativo,

        @NotNull(message = "A data de início da vigência é obrigatória")
        LocalDate vigenciaInicio,

        @NotNull(message = "A data de fim da vigência é obrigatória")
        LocalDate vigenciaFim,

        @NotNull(message = "A cota diária é obrigatória")
        @Positive(message = "A cota diária deve ser maior que zero")
        Integer cotaDiaria,

        @NotNull(message = "A cota por janela é obrigatória")
        @Positive(message = "A cota por janela deve ser maior que zero")
        Integer cotaPorJanela
) {}