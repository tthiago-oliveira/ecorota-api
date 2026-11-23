package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.CapacidadeCarga;
import java.time.LocalDate;

public record CapacidadeCargaResponseDTO(
        Integer idCapacidade,
        Integer idAtrativo,
        String nomeAtrativo,
        LocalDate vigenciaInicio,
        LocalDate vigenciaFim,
        Integer cotaDiaria,
        Integer cotaPorJanela
) {
    public CapacidadeCargaResponseDTO(CapacidadeCarga c) {
        this(
                c.getIdCapacidade(),
                c.getAtrativo().getIdAtrativo(),
                c.getAtrativo().getNome(),
                c.getVigenciaInicio(),
                c.getVigenciaFim(),
                c.getCotaDiaria(),
                c.getCotaPorJanela()
        );
    }
}