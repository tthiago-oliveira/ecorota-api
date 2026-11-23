package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.JanelaVisitacao;
import java.time.LocalDate;
import java.time.LocalTime;

public record JanelaVisitacaoResponseDTO(
        Integer idJanela,
        Integer idAtrativo,
        String nomeAtrativo,
        LocalDate dataVisita,
        LocalTime horaInicio,
        Integer vagasTotal,
        Integer vagasOcupadas,
        Integer vagasDisponiveis
) {
    public JanelaVisitacaoResponseDTO(JanelaVisitacao j) {
        this(
                j.getIdJanela(),
                j.getAtrativo().getIdAtrativo(),
                j.getAtrativo().getNome(),
                j.getDataVisita(),
                j.getHoraInicio(),
                j.getVagasTotal(),
                j.getVagasOcupadas(),
                j.getVagasTotal() - j.getVagasOcupadas()
        );
    }
}