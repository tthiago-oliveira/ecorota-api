package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Atrativo;
import java.math.BigDecimal;

public record AtrativoResponseDTO(
        Integer idAtrativo,
        Integer idDestino,
        String nomeDestino,
        String nome,
        String tipo,
        BigDecimal latitude,
        BigDecimal longitude,
        Boolean exigeGuia,
        BigDecimal valorIngresso
) {
    public AtrativoResponseDTO(Atrativo a) {
        this(
                a.getIdAtrativo(),
                a.getDestino().getIdDestino(),
                a.getDestino().getNome(),
                a.getNome(),
                a.getTipo(),
                a.getLatitude(),
                a.getLongitude(),
                a.getExigeGuia(),
                a.getValorIngresso()
        );
    }
}