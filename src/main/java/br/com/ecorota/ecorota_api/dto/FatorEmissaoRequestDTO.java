package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record FatorEmissaoRequestDTO(
        @NotNull(message = "O idModal é obrigatório")
        Integer idModal,

        @NotNull(message = "O ano de vigência é obrigatório")
        Short anoVigencia,

        @NotNull(message = "O fator kgCO2e/km é obrigatório")
        @PositiveOrZero(message = "O fator de emissão não pode ser negativo")
        BigDecimal kgco2ePorKm,

        @NotBlank(message = "A fonte do fator de emissão é obrigatória")
        @Size(max = 150)
        String fonte
) {}