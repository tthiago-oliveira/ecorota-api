package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.*;
import br.com.ecorota.ecorota_api.model.CriterioSustentabilidade;
import br.com.ecorota.ecorota_api.model.Fornecedor;
import br.com.ecorota.ecorota_api.model.FornecedorCertificacao;
import br.com.ecorota.ecorota_api.model.ItemAuditoria;
import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import br.com.ecorota.ecorota_api.repository.CriterioSustentabilidadeRepository;
import br.com.ecorota.ecorota_api.repository.FornecedorCertificacaoRepository;
import br.com.ecorota.ecorota_api.repository.FornecedorRepository;
import br.com.ecorota.ecorota_api.repository.ItemAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorCertificacaoService {

    private final FornecedorCertificacaoRepository certificacaoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final CriterioSustentabilidadeRepository criterioRepository;
    private final ItemAuditoriaRepository itemAuditoriaRepository;

    @Transactional(readOnly = true)
    public List<FornecedorCertificacaoResponseDTO> listar(Integer idFornecedor, StatusCertificacao status) {
        List<FornecedorCertificacao> lista;
        if (idFornecedor != null) {
            lista = certificacaoRepository.findByFornecedorIdFornecedor(idFornecedor);
        } else if (status != null) {
            lista = certificacaoRepository.findByStatus(status);
        } else {
            lista = certificacaoRepository.findAll();
        }
        return lista.stream().map(this::montarResponse).toList();
    }

    @Transactional(readOnly = true)
    public FornecedorCertificacaoResponseDTO buscarPorId(Integer id) {
        FornecedorCertificacao cert = certificacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificação não encontrada"));
        return montarResponse(cert);
    }

    @Transactional
    public FornecedorCertificacaoResponseDTO criar(FornecedorCertificacaoRequestDTO dto) {
        if (!dto.dataValidade().isAfter(dto.dataEmissao())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de validade deve ser posterior à data de emissão");
        }
        if (certificacaoRepository.existsByNumero(dto.numero().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Número de certificação já cadastrado");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        FornecedorCertificacao cert = new FornecedorCertificacao();
        cert.setFornecedor(fornecedor);
        cert.setNumero(dto.numero().trim());
        cert.setDataEmissao(dto.dataEmissao());
        cert.setDataValidade(dto.dataValidade());
        cert.setUrlComprovante(dto.urlComprovante());
        cert.setStatus(StatusCertificacao.em_analise);

        return montarResponse(certificacaoRepository.save(cert));
    }

    @Transactional
    public FornecedorCertificacaoResponseDTO registrarItemAuditoria(Integer idCertificacao, ItemAuditoriaRequestDTO dto) {
        FornecedorCertificacao cert = certificacaoRepository.findById(idCertificacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificação não encontrada"));

        CriterioSustentabilidade criterio = criterioRepository.findById(dto.idCriterio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Critério de sustentabilidade não encontrado"));

        if (itemAuditoriaRepository.existsByFornecedorCertificacaoIdFornCertAndCriterioIdCriterio(idCertificacao, dto.idCriterio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este critério já foi auditado nesta certificação");
        }

        ItemAuditoria item = new ItemAuditoria();
        item.setFornecedorCertificacao(cert);
        item.setCriterio(criterio);
        item.setAtendido(dto.atendido());
        item.setEvidenciaUrl(dto.evidenciaUrl());
        item.setObservacao(dto.observacao());

        itemAuditoriaRepository.save(item);
        return montarResponse(cert);
    }

    @Transactional
    public FornecedorCertificacaoResponseDTO atualizarStatus(Integer idCertificacao, StatusCertificacaoRequestDTO dto) {
        FornecedorCertificacao cert = certificacaoRepository.findById(idCertificacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificação não encontrada"));

        if (dto.status() == StatusCertificacao.verificada) {
            List<ItemAuditoria> itens = itemAuditoriaRepository.findByFornecedorCertificacaoIdFornCert(idCertificacao);
            if (itens.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Não é possível verificar uma certificação sem itens de auditoria registrados"
                );
            }

            boolean falhouObrigatorio = itens.stream()
                    .anyMatch(item -> Boolean.TRUE.equals(item.getCriterio().getObrigatorio()) && !Boolean.TRUE.equals(item.getAtendido()));

            if (falhouObrigatorio) {
                throw new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "A certificação não pode ser verificada porque há critérios obrigatórios não atendidos"
                );
            }
        }

        cert.setStatus(dto.status());
        return montarResponse(certificacaoRepository.save(cert));
    }

    private FornecedorCertificacaoResponseDTO montarResponse(FornecedorCertificacao cert) {
        List<ItemAuditoriaResponseDTO> itens = itemAuditoriaRepository
                .findByFornecedorCertificacaoIdFornCert(cert.getIdFornCert())
                .stream()
                .map(ItemAuditoriaResponseDTO::new)
                .toList();
        return new FornecedorCertificacaoResponseDTO(cert, itens);
    }
}