package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ServicoContratadoRequestDTO(
        @NotNull(message = "O idFornecedor é obrigatório")
        Integer idFornecedor,

        @NotBlank(message = "O tipo de serviço é obrigatório")
        @Size(max = 40)
        String tipoServico,

        @NotNull(message = "O valor é obrigatório")
        @PositiveOrZero(message = "O valor não pode ser negativo")
        BigDecimal valor
) {}