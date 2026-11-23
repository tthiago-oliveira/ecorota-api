package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record AtrativoRequestDTO(
        @NotNull(message = "O idDestino é obrigatório")
        Integer idDestino,

        @NotBlank(message = "Nome do atrativo é obrigatório")
        @Size(max = 150)
        String nome,

        @Size(max = 40)
        String tipo,

        BigDecimal latitude,
        BigDecimal longitude,
        Boolean exigeGuia,

        @PositiveOrZero(message = "O valor do ingresso não pode ser negativo")
        BigDecimal valorIngresso
) {}