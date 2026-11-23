package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Fornecedor;
import br.com.ecorota.ecorota_api.model.enums.OrigemCadastro;
import java.time.LocalDateTime;

public record FornecedorResponseDTO(
        Integer idFornecedor,
        Integer idMunicipio,
        String nomeMunicipio,
        String uf,
        String nomeFantasia,
        String tipo,
        OrigemCadastro origemCadastro,
        Boolean perfilReivindicado,
        LocalDateTime dataReivindicacao,
        String cnpj,
        String razaoSocial
) {
    public FornecedorResponseDTO(Fornecedor f) {
        this(
                f.getIdFornecedor(),
                f.getMunicipio().getIdMunicipio(),
                f.getMunicipio().getNome(),
                f.getMunicipio().getUf(),
                f.getNomeFantasia(),
                f.getTipo(),
                f.getOrigemCadastro(),
                f.getPerfilReivindicado(),
                f.getDataReivindicacao(),
                f.getCnpj(),
                f.getRazaoSocial()
        );
    }
}