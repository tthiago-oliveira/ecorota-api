package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.FornecedorCertificacao;
import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import java.time.LocalDate;
import java.util.List;

public record FornecedorCertificacaoResponseDTO(
        Integer idFornCert,
        Integer idFornecedor,
        String nomeFornecedor,
        String numero,
        LocalDate dataEmissao,
        LocalDate dataValidade,
        String urlComprovante,
        StatusCertificacao status,
        Integer pontuacaoObtida,
        Integer pontuacaoMaxima,
        List<ItemAuditoriaResponseDTO> itensAuditoria
) {
    public FornecedorCertificacaoResponseDTO(FornecedorCertificacao fc, List<ItemAuditoriaResponseDTO> itens) {
        this(
                fc.getIdFornCert(),
                fc.getFornecedor().getIdFornecedor(),
                fc.getFornecedor().getNomeFantasia(),
                fc.getNumero(),
                fc.getDataEmissao(),
                fc.getDataValidade(),
                fc.getUrlComprovante(),
                fc.getStatus(),
                itens.stream().filter(ItemAuditoriaResponseDTO::atendido).mapToInt(ItemAuditoriaResponseDTO::peso).sum(),
                itens.stream().mapToInt(ItemAuditoriaResponseDTO::peso).sum(),
                itens
        );
    }
}