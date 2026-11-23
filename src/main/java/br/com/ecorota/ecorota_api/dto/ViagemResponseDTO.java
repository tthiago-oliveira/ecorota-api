package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Viagem;
import br.com.ecorota.ecorota_api.model.enums.StatusViagem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ViagemResponseDTO(
        Integer idViagem,
        Integer idViajante,
        String nomeViajante,
        LocalDate dataInicio,
        LocalDate dataFim,
        StatusViagem status,
        BigDecimal gastoTotalServicos,
        PegadaViagemResponseDTO pegadaCarbono,
        List<TrechoDeslocamentoResponseDTO> trechos,
        List<ServicoContratadoResponseDTO> servicos
) {
    public ViagemResponseDTO(
            Viagem v,
            PegadaViagemResponseDTO pegada,
            List<TrechoDeslocamentoResponseDTO> trechos,
            List<ServicoContratadoResponseDTO> servicos
    ) {
        this(
                v.getIdViagem(),
                v.getViajante().getIdViajante(),
                v.getViajante().getNome(),
                v.getDataInicio(),
                v.getDataFim(),
                v.getStatus(),
                servicos.stream().map(ServicoContratadoResponseDTO::valor).reduce(BigDecimal.ZERO, BigDecimal::add),
                pegada,
                trechos,
                servicos
        );
    }
}