package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.CriterioSustentabilidade;
import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;

public record CriterioSustentabilidadeResponseDTO(
        Integer idCriterio,
        EixoSustentabilidade eixo,
        String descricao,
        Integer peso,
        Boolean obrigatorio
) {
    public CriterioSustentabilidadeResponseDTO(CriterioSustentabilidade c) {
        this(c.getIdCriterio(), c.getEixo(), c.getDescricao(), c.getPeso(), c.getObrigatorio());
    }
}