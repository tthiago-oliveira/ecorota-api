package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CriterioSustentabilidadeRequestDTO(
        @NotNull(message = "O eixo é obrigatório (ambiental, social, economico ou gestao)")
        EixoSustentabilidade eixo,

        @NotBlank(message = "A descrição do critério é obrigatória")
        @Size(max = 255)
        String descricao,

        @NotNull(message = "O peso é obrigatório")
        @Positive(message = "O peso deve ser maior que zero")
        Integer peso,

        Boolean obrigatorio
) {}