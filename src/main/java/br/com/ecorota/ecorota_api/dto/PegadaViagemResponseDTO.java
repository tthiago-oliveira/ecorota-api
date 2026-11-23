package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.PegadaViagem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PegadaViagemResponseDTO(
        Integer idPegada,
        BigDecimal totalKgco2e,
        LocalDateTime calculadoEm,
        String versaoMetodologia
) {
    public PegadaViagemResponseDTO(PegadaViagem p) {
        this(p.getIdPegada(), p.getTotalKgco2e(), p.getCalculadoEm(), p.getVersaoMetodologia());
    }
}