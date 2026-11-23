package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.AvaliacaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.AvaliacaoResponseDTO;
import br.com.ecorota.ecorota_api.dto.RespostaAvaliacaoRequestDTO;
import br.com.ecorota.ecorota_api.model.Avaliacao;
import br.com.ecorota.ecorota_api.model.Fornecedor;
import br.com.ecorota.ecorota_api.model.Viajante;
import br.com.ecorota.ecorota_api.repository.AvaliacaoRepository;
import br.com.ecorota.ecorota_api.repository.FornecedorRepository;
import br.com.ecorota.ecorota_api.repository.ViajanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ViajanteRepository viajanteRepository;

    public List<AvaliacaoResponseDTO> listar(Integer idFornecedor, Integer idViajante) {
        List<Avaliacao> lista;
        if (idFornecedor != null) {
            lista = avaliacaoRepository.findByFornecedorIdFornecedor(idFornecedor);
        } else if (idViajante != null) {
            lista = avaliacaoRepository.findByViajanteIdViajante(idViajante);
        } else {
            lista = avaliacaoRepository.findAll();
        }
        return lista.stream().map(AvaliacaoResponseDTO::new).toList();
    }

    public Map<String, Object> obterResumoFornecedor(Integer idFornecedor) {
        if (!fornecedorRepository.existsById(idFornecedor)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado");
        }
        Double media = avaliacaoRepository.calcularMediaPorFornecedor(idFornecedor);
        long total = avaliacaoRepository.findByFornecedorIdFornecedor(idFornecedor).size();
        return Map.of(
                "idFornecedor", idFornecedor,
                "notaMedia", Math.round(media * 10.0) / 10.0,
                "totalAvaliacoes", total
        );
    }

    public AvaliacaoResponseDTO criar(AvaliacaoRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        Viajante viajante = viajanteRepository.findById(dto.idViajante())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viajante não encontrado"));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setFornecedor(fornecedor);
        avaliacao.setViajante(viajante);
        avaliacao.setNota(dto.nota());
        avaliacao.setComentario(dto.comentario().trim());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        return new AvaliacaoResponseDTO(avaliacaoRepository.save(avaliacao));
    }

    public AvaliacaoResponseDTO responder(Integer idAvaliacao, RespostaAvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoRepository.findById(idAvaliacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        if (!Boolean.TRUE.equals(avaliacao.getFornecedor().getPerfilReivindicado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Apenas fornecedores com perfil reivindicado podem responder avaliações"
            );
        }

        avaliacao.setResposta(dto.resposta().trim());
        avaliacao.setDataResposta(LocalDateTime.now());

        return new AvaliacaoResponseDTO(avaliacaoRepository.save(avaliacao));
    }
}