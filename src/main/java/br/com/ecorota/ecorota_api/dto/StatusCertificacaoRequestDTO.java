package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import jakarta.validation.constraints.NotNull;

public record StatusCertificacaoRequestDTO(
        @NotNull(message = "O novo status é obrigatório (em_analise, verificada ou recusada)")
        StatusCertificacao status
) {}