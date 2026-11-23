package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.TrechoDeslocamento;
import java.math.BigDecimal;
import java.math.RoundingMode;

public record TrechoDeslocamentoResponseDTO(
        Integer idTrecho,
        Integer idFator,
        String nomeModal,
        String origem,
        String destino,
        BigDecimal distanciaKm,
        BigDecimal kgco2ePorKm,
        BigDecimal emissaoTrechoKgco2e
) {
    public TrechoDeslocamentoResponseDTO(TrechoDeslocamento t) {
        this(
                t.getIdTrecho(),
                t.getFatorEmissao().getIdFator(),
                t.getFatorEmissao().getModal().getNome(),
                t.getOrigem(),
                t.getDestino(),
                t.getDistanciaKm(),
                t.getFatorEmissao().getKgco2ePorKm(),
                t.getDistanciaKm().multiply(t.getFatorEmissao().getKgco2ePorKm()).setScale(3, RoundingMode.HALF_UP)
        );
    }
}