package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReivindicarFornecedorDTO(
        @NotBlank(message = "O CNPJ é obrigatório para reivindicar o perfil")
        @Size(min = 14, max = 14, message = "CNPJ deve conter 14 dígitos numéricos")
        String cnpj,

        @NotBlank(message = "A razão social é obrigatória")
        @Size(max = 150)
        String razaoSocial
) {}