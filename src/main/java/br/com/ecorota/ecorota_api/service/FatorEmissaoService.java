package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.FatorEmissaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.FatorEmissaoResponseDTO;
import br.com.ecorota.ecorota_api.model.FatorEmissao;
import br.com.ecorota.ecorota_api.model.ModalTransporte;
import br.com.ecorota.ecorota_api.repository.FatorEmissaoRepository;
import br.com.ecorota.ecorota_api.repository.ModalTransporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FatorEmissaoService {

    private final FatorEmissaoRepository fatorRepository;
    private final ModalTransporteRepository modalRepository;

    public List<FatorEmissaoResponseDTO> listar(Integer idModal) {
        List<FatorEmissao> lista = (idModal != null)
                ? fatorRepository.findByModalIdModal(idModal)
                : fatorRepository.findAll();
        return lista.stream().map(FatorEmissaoResponseDTO::new).toList();
    }

    public FatorEmissaoResponseDTO criar(FatorEmissaoRequestDTO dto) {
        ModalTransporte modal = modalRepository.findById(dto.idModal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modal de transporte não encontrado"));

        if (fatorRepository.existsByModalIdModalAndAnoVigencia(dto.idModal(), dto.anoVigencia())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe fator de emissão cadastrado para este modal neste ano");
        }

        FatorEmissao fator = new FatorEmissao();
        fator.setModal(modal);
        fator.setAnoVigencia(dto.anoVigencia());
        fator.setKgco2ePorKm(dto.kgco2ePorKm());
        fator.setFonte(dto.fonte().trim());

        return new FatorEmissaoResponseDTO(fatorRepository.save(fator));
    }
}