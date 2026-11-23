package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.ItemReserva;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record ItemReservaResponseDTO(
        Integer idItem,
        Integer idJanela,
        String nomeAtrativo,
        LocalDate dataVisita,
        LocalTime horaInicio,
        Integer qtdPessoas,
        BigDecimal valor
) {
    public ItemReservaResponseDTO(ItemReserva item) {
        this(
                item.getIdItem(),
                item.getJanela().getIdJanela(),
                item.getJanela().getAtrativo().getNome(),
                item.getJanela().getDataVisita(),
                item.getJanela().getHoraInicio(),
                item.getQtdPessoas(),
                item.getValor()
        );
    }
}