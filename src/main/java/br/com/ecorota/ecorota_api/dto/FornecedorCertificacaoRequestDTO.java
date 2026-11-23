package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record FornecedorCertificacaoRequestDTO(
        @NotNull(message = "O idFornecedor é obrigatório")
        Integer idFornecedor,

        @NotBlank(message = "O número da certificação é obrigatório")
        @Size(max = 40)
        String numero,

        @NotNull(message = "A data de emissão é obrigatória")
        LocalDate dataEmissao,

        @NotNull(message = "A data de validade é obrigatória")
        LocalDate dataValidade,

        @Size(max = 255)
        String urlComprovante
) {}