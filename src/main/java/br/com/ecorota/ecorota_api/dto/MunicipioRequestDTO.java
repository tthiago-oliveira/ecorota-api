package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MunicipioRequestDTO(
        @NotBlank(message = "Nome do município é obrigatório")
        @Size(max = 120)
        String nome,

        @NotBlank(message = "UF é obrigatória")
        @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
        String uf,

        @Size(min = 7, max = 7, message = "Código IBGE deve ter 7 dígitos")
        String codigoIbge
) {}