package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DestinoRequestDTO(
        @NotNull(message = "O idMunicipio é obrigatório")
        Integer idMunicipio,

        @NotBlank(message = "Nome do destino é obrigatório")
        @Size(max = 120)
        String nome,

        @Size(max = 40)
        String tipo
) {}