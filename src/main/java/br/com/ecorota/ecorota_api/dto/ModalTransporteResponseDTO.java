package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.ModalTransporte;

public record ModalTransporteResponseDTO(
        Integer idModal,
        String nome,
        String categoria
) {
    public ModalTransporteResponseDTO(ModalTransporte m) {
        this(m.getIdModal(), m.getNome(), m.getCategoria());
    }
}