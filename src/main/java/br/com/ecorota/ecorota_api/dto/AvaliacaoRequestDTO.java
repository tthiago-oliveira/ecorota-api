package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.*;

public record AvaliacaoRequestDTO(
        @NotNull(message = "O idFornecedor é obrigatório")
        Integer idFornecedor,

        @NotNull(message = "O idViajante é obrigatório")
        Integer idViajante,

        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer nota,

        @NotBlank(message = "O comentário é obrigatório")
        @Size(max = 1000)
        String comentario
) {}