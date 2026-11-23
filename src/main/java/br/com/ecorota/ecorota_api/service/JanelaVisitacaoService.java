package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.JanelaVisitacaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.JanelaVisitacaoResponseDTO;
import br.com.ecorota.ecorota_api.model.Atrativo;
import br.com.ecorota.ecorota_api.model.CapacidadeCarga;
import br.com.ecorota.ecorota_api.model.JanelaVisitacao;
import br.com.ecorota.ecorota_api.repository.AtrativoRepository;
import br.com.ecorota.ecorota_api.repository.CapacidadeCargaRepository;
import br.com.ecorota.ecorota_api.repository.JanelaVisitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JanelaVisitacaoService {

    private final JanelaVisitacaoRepository janelaRepository;
    private final AtrativoRepository atrativoRepository;
    private final CapacidadeCargaRepository capacidadeRepository;

    public List<JanelaVisitacaoResponseDTO> listar(Integer idAtrativo, LocalDate dataVisita) {
        List<JanelaVisitacao> lista;
        if (idAtrativo != null && dataVisita != null) {
            lista = janelaRepository.findByAtrativoIdAtrativoAndDataVisita(idAtrativo, dataVisita);
        } else if (idAtrativo != null) {
            lista = janelaRepository.findByAtrativoIdAtrativo(idAtrativo);
        } else {
            lista = janelaRepository.findAll();
        }
        return lista.stream().map(JanelaVisitacaoResponseDTO::new).toList();
    }

    public JanelaVisitacaoResponseDTO criar(JanelaVisitacaoRequestDTO dto) {
        Atrativo atrativo = atrativoRepository.findById(dto.idAtrativo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atrativo não encontrado"));

        Optional<CapacidadeCarga> regraVigente = capacidadeRepository
                .buscarVigenciaNaData(dto.idAtrativo(), dto.dataVisita());

        if (regraVigente.isPresent()) {
            CapacidadeCarga capacidade = regraVigente.get();
            if (dto.vagasTotal() > capacidade.getCotaPorJanela()) {
                throw new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Vagas da janela (" + dto.vagasTotal() + ") excedem a cota por janela vigente (" + capacidade.getCotaPorJanela() + ")"
                );
            }

            Integer vagasJaAlocadasNoDia = janelaRepository.somarVagasTotalNoDia(dto.idAtrativo(), dto.dataVisita());
            if (vagasJaAlocadasNoDia + dto.vagasTotal() > capacidade.getCotaDiaria()) {
                throw new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Soma de vagas no dia ultrapassa a cota diária vigente (" + capacidade.getCotaDiaria() + ")"
                );
            }
        }

        JanelaVisitacao janela = new JanelaVisitacao();
        janela.setAtrativo(atrativo);
        janela.setDataVisita(dto.dataVisita());
        janela.setHoraInicio(dto.horaInicio());
        janela.setVagasTotal(dto.vagasTotal());
        janela.setVagasOcupadas(0);

        return new JanelaVisitacaoResponseDTO(janelaRepository.save(janela));
    }
}