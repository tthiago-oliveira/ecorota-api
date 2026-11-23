package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Destino;

public record DestinoResponseDTO(
        Integer idDestino,
        Integer idMunicipio,
        String nomeMunicipio,
        String uf,
        String nome,
        String tipo
) {
    public DestinoResponseDTO(Destino d) {
        this(
                d.getIdDestino(),
                d.getMunicipio().getIdMunicipio(),
                d.getMunicipio().getNome(),
                d.getMunicipio().getUf(),
                d.getNome(),
                d.getTipo()
        );
    }
}