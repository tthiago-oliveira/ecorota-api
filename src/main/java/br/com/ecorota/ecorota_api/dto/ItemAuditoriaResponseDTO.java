package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.ItemAuditoria;
import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;

public record ItemAuditoriaResponseDTO(
        Integer idItemAuditoria,
        Integer idCriterio,
        EixoSustentabilidade eixo,
        String descricaoCriterio,
        Integer peso,
        Boolean obrigatorio,
        Boolean atendido,
        String evidenciaUrl,
        String observacao
) {
    public ItemAuditoriaResponseDTO(ItemAuditoria item) {
        this(
                item.getIdItemAuditoria(),
                item.getCriterio().getIdCriterio(),
                item.getCriterio().getEixo(),
                item.getCriterio().getDescricao(),
                item.getCriterio().getPeso(),
                item.getCriterio().getObrigatorio(),
                item.getAtendido(),
                item.getEvidenciaUrl(),
                item.getObservacao()
        );
    }
}