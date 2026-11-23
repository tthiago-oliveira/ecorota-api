package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Municipio;

public record MunicipioResponseDTO(
        Integer idMunicipio,
        String nome,
        String uf,
        String codigoIbge
) {
    public MunicipioResponseDTO(Municipio m) {
        this(m.getIdMunicipio(), m.getNome(), m.getUf(), m.getCodigoIbge());
    }
}