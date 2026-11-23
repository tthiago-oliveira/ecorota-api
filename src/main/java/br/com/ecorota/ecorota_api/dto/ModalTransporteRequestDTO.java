package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModalTransporteRequestDTO(
        @NotBlank(message = "Nome do modal é obrigatório")
        @Size(max = 60)
        String nome,

        @Size(max = 40)
        String categoria
) {}