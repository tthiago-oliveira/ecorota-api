package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.enums.OrigemCadastro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(
        @NotNull(message = "O idMunicipio é obrigatório")
        Integer idMunicipio,

        @NotBlank(message = "O nome fantasia é obrigatório")
        @Size(max = 150)
        String nomeFantasia,

        @Size(max = 40)
        String tipo,

        @NotNull(message = "A origem do cadastro é obrigatória (cadastur, viajante ou proprio)")
        OrigemCadastro origemCadastro,

        @Size(min = 14, max = 14, message = "CNPJ deve conter 14 dígitos numéricos")
        String cnpj,

        @Size(max = 150)
        String razaoSocial
) {}