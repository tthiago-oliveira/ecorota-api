package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ReservaRequestDTO(
        @NotNull(message = "O idViajante é obrigatório")
        Integer idViajante,

        @NotEmpty(message = "A reserva deve conter pelo menos um item")
        @Valid
        List<ItemReservaRequestDTO> itens
) {}