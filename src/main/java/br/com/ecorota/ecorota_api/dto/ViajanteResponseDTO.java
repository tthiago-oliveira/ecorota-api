package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Viajante;

public record ViajanteResponseDTO(
        Integer idViajante,
        String nome,
        String email,
        String cpf
) {
    public ViajanteResponseDTO(Viajante v) {
        this(v.getIdViajante(), v.getNome(), v.getEmail(), v.getCpf());
    }
}