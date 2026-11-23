package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.ModalTransporteRequestDTO;
import br.com.ecorota.ecorota_api.dto.ModalTransporteResponseDTO;
import br.com.ecorota.ecorota_api.model.ModalTransporte;
import br.com.ecorota.ecorota_api.repository.ModalTransporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModalTransporteService {

    private final ModalTransporteRepository repository;

    public List<ModalTransporteResponseDTO> listarTodos() {
        return repository.findAll().stream().map(ModalTransporteResponseDTO::new).toList();
    }

    public ModalTransporteResponseDTO criar(ModalTransporteRequestDTO dto) {
        ModalTransporte modal = new ModalTransporte();
        modal.setNome(dto.nome().trim());
        modal.setCategoria(dto.categoria());
        return new ModalTransporteResponseDTO(repository.save(modal));
    }
}