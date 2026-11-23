package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Reserva;
import br.com.ecorota.ecorota_api.model.enums.StatusReserva;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReservaResponseDTO(
        Integer idReserva,
        Integer idViajante,
        String nomeViajante,
        LocalDateTime datahora,
        StatusReserva status,
        BigDecimal valorTotal,
        List<ItemReservaResponseDTO> itens
) {
    public ReservaResponseDTO(Reserva r, List<ItemReservaResponseDTO> itens) {
        this(
                r.getIdReserva(),
                r.getViajante().getIdViajante(),
                r.getViajante().getNome(),
                r.getDatahora(),
                r.getStatus(),
                itens.stream().map(ItemReservaResponseDTO::valor).reduce(BigDecimal.ZERO, BigDecimal::add),
                itens
        );
    }
}