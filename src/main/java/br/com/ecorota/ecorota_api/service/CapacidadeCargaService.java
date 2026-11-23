package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.CapacidadeCargaRequestDTO;
import br.com.ecorota.ecorota_api.dto.CapacidadeCargaResponseDTO;
import br.com.ecorota.ecorota_api.model.Atrativo;
import br.com.ecorota.ecorota_api.model.CapacidadeCarga;
import br.com.ecorota.ecorota_api.repository.AtrativoRepository;
import br.com.ecorota.ecorota_api.repository.CapacidadeCargaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CapacidadeCargaService {

    private final CapacidadeCargaRepository capacidadeRepository;
    private final AtrativoRepository atrativoRepository;

    public List<CapacidadeCargaResponseDTO> listar(Integer idAtrativo) {
        List<CapacidadeCarga> lista = (idAtrativo != null)
                ? capacidadeRepository.findByAtrativoIdAtrativo(idAtrativo)
                : capacidadeRepository.findAll();
        return lista.stream().map(CapacidadeCargaResponseDTO::new).toList();
    }

    public CapacidadeCargaResponseDTO criar(CapacidadeCargaRequestDTO dto) {
        if (dto.vigenciaFim().isBefore(dto.vigenciaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data final deve ser igual ou posterior à data inicial");
        }
        if (dto.cotaPorJanela() > dto.cotaDiaria()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A cota por janela não pode ultrapassar a cota diária");
        }

        Atrativo atrativo = atrativoRepository.findById(dto.idAtrativo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atrativo não encontrado"));

        CapacidadeCarga capacidade = new CapacidadeCarga();
        capacidade.setAtrativo(atrativo);
        capacidade.setVigenciaInicio(dto.vigenciaInicio());
        capacidade.setVigenciaFim(dto.vigenciaFim());
        capacidade.setCotaDiaria(dto.cotaDiaria());
        capacidade.setCotaPorJanela(dto.cotaPorJanela());

        return new CapacidadeCargaResponseDTO(capacidadeRepository.save(capacidade));
    }
}