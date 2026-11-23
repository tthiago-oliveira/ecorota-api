package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.FatorEmissao;
import java.math.BigDecimal;

public record FatorEmissaoResponseDTO(
        Integer idFator,
        Integer idModal,
        String nomeModal,
        Short anoVigencia,
        BigDecimal kgco2ePorKm,
        String fonte
) {
    public FatorEmissaoResponseDTO(FatorEmissao f) {
        this(
                f.getIdFator(),
                f.getModal().getIdModal(),
                f.getModal().getNome(),
                f.getAnoVigencia(),
                f.getKgco2ePorKm(),
                f.getFonte()
        );
    }
}