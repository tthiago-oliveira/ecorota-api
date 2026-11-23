package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.enums.StatusViagem;
import jakarta.validation.constraints.NotNull;

public record StatusViagemRequestDTO(
        @NotNull(message = "O novo status da viagem é obrigatório")
        StatusViagem status
) {}