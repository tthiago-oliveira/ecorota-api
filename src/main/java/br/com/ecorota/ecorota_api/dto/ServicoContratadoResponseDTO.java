package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.ServicoContratado;
import java.math.BigDecimal;

public record ServicoContratadoResponseDTO(
        Integer idServico,
        Integer idFornecedor,
        String nomeFornecedor,
        String tipoServico,
        BigDecimal valor
) {
    public ServicoContratadoResponseDTO(ServicoContratado s) {
        this(
                s.getIdServico(),
                s.getFornecedor().getIdFornecedor(),
                s.getFornecedor().getNomeFantasia(),
                s.getTipoServico(),
                s.getValor()
        );
    }
}