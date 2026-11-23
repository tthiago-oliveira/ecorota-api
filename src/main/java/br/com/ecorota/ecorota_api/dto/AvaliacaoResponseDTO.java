package br.com.ecorota.ecorota_api.dto;

import br.com.ecorota.ecorota_api.model.Avaliacao;
import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(
        Integer idAvaliacao,
        Integer idFornecedor,
        String nomeFornecedor,
        Integer idViajante,
        String nomeViajante,
        Integer nota,
        String comentario,
        LocalDateTime dataAvaliacao,
        String resposta,
        LocalDateTime dataResposta
) {
    public AvaliacaoResponseDTO(Avaliacao a) {
        this(
                a.getIdAvaliacao(),
                a.getFornecedor().getIdFornecedor(),
                a.getFornecedor().getNomeFantasia(),
                a.getViajante().getIdViajante(),
                a.getViajante().getNome(),
                a.getNota(),
                a.getComentario(),
                a.getDataAvaliacao(),
                a.getResposta(),
                a.getDataResposta()
        );
    }
}