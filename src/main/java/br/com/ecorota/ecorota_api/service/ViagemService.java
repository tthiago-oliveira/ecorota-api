package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.*;
import br.com.ecorota.ecorota_api.model.*;
import br.com.ecorota.ecorota_api.model.enums.StatusViagem;
import br.com.ecorota.ecorota_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final ViajanteRepository viajanteRepository;
    private final FatorEmissaoRepository fatorEmissaoRepository;
    private final TrechoDeslocamentoRepository trechoRepository;
    private final PegadaViagemRepository pegadaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ServicoContratadoRepository servicoRepository;

    @Transactional(readOnly = true)
    public List<ViagemResponseDTO> listar(Integer idViajante) {
        List<Viagem> viagens = (idViajante != null)
                ? viagemRepository.findByViajanteIdViajante(idViajante)
                : viagemRepository.findAll();
        return viagens.stream().map(this::montarResponse).toList();
    }

    @Transactional(readOnly = true)
    public ViagemResponseDTO buscarPorId(Integer id) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));
        return montarResponse(viagem);
    }

    @Transactional
    public ViagemResponseDTO criar(ViagemRequestDTO dto) {
        if (dto.dataFim() != null && dto.dataFim().isBefore(dto.dataInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de fim deve ser igual ou posterior à data de início");
        }

        Viajante viajante = viajanteRepository.findById(dto.idViajante())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viajante não encontrado"));

        Viagem viagem = new Viagem();
        viagem.setViajante(viajante);
        viagem.setDataInicio(dto.dataInicio());
        viagem.setDataFim(dto.dataFim());
        viagem.setStatus(StatusViagem.planejada);

        return montarResponse(viagemRepository.save(viagem));
    }

    @Transactional
    public ViagemResponseDTO atualizarStatus(Integer id, StatusViagemRequestDTO dto) {
        Viagem viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        viagem.setStatus(dto.status());
        return montarResponse(viagemRepository.save(viagem));
    }

    @Transactional
    public ViagemResponseDTO adicionarTrecho(Integer idViagem, TrechoDeslocamentoRequestDTO dto) {
        Viagem viagem = viagemRepository.findById(idViagem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        FatorEmissao fator = fatorEmissaoRepository.findById(dto.idFator())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fator de emissão não encontrado"));

        TrechoDeslocamento trecho = new TrechoDeslocamento();
        trecho.setViagem(viagem);
        trecho.setFatorEmissao(fator);
        trecho.setOrigem(dto.origem().trim());
        trecho.setDestino(dto.destino().trim());
        trecho.setDistanciaKm(dto.distanciaKm());

        trechoRepository.save(trecho);
        recalcularPegadaCarbono(viagem);

        return montarResponse(viagem);
    }

    @Transactional
    public ViagemResponseDTO adicionarServico(Integer idViagem, ServicoContratadoRequestDTO dto) {
        Viagem viagem = viagemRepository.findById(idViagem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        ServicoContratado servico = new ServicoContratado();
        servico.setViagem(viagem);
        servico.setFornecedor(fornecedor);
        servico.setTipoServico(dto.tipoServico().trim());
        servico.setValor(dto.valor());

        servicoRepository.save(servico);
        return montarResponse(viagem);
    }

    private void recalcularPegadaCarbono(Viagem viagem) {
        List<TrechoDeslocamento> trechos = trechoRepository.findByViagemIdViagem(viagem.getIdViagem());

        BigDecimal totalCo2 = trechos.stream()
                .map(t -> t.getDistanciaKm().multiply(t.getFatorEmissao().getKgco2ePorKm()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(3, RoundingMode.HALF_UP);

        PegadaViagem pegada = pegadaRepository.findByViagemIdViagem(viagem.getIdViagem())
                .orElseGet(() -> {
                    PegadaViagem nova = new PegadaViagem();
                    nova.setViagem(viagem);
                    return nova;
                });

        pegada.setTotalKgco2e(totalCo2);
        pegada.setCalculadoEm(LocalDateTime.now());
        pegada.setVersaoMetodologia("GHG-Protocol-v1.0");
        pegadaRepository.save(pegada);
    }

    private ViagemResponseDTO montarResponse(Viagem viagem) {
        List<TrechoDeslocamentoResponseDTO> trechos = trechoRepository.findByViagemIdViagem(viagem.getIdViagem())
                .stream()
                .map(TrechoDeslocamentoResponseDTO::new)
                .toList();

        List<ServicoContratadoResponseDTO> servicos = servicoRepository.findByViagemIdViagem(viagem.getIdViagem())
                .stream()
                .map(ServicoContratadoResponseDTO::new)
                .toList();

        PegadaViagemResponseDTO pegada = pegadaRepository.findByViagemIdViagem(viagem.getIdViagem())
                .map(PegadaViagemResponseDTO::new)
                .orElse(null);

        return new ViagemResponseDTO(viagem, pegada, trechos, servicos);
    }
}