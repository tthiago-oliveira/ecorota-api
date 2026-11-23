package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record ItemReservaRequestDTO(
        @NotNull(message = "O idJanela é obrigatório")
        Integer idJanela,

        @NotNull(message = "A quantidade de pessoas é obrigatória")
        @Positive(message = "A quantidade de pessoas deve ser pelo menos 1")
        Integer qtdPessoas,

        @PositiveOrZero(message = "O valor não pode ser negativo")
        BigDecimal valor
) {}