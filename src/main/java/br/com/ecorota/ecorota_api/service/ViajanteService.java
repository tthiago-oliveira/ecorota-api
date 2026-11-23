package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.ViajanteRequestDTO;
import br.com.ecorota.ecorota_api.dto.ViajanteResponseDTO;
import br.com.ecorota.ecorota_api.model.Viajante;
import br.com.ecorota.ecorota_api.repository.ViajanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajanteService {

    private final ViajanteRepository repository;

    public List<ViajanteResponseDTO> listarTodos() {
        return repository.findAll().stream().map(ViajanteResponseDTO::new).toList();
    }

    public ViajanteResponseDTO buscarPorId(Integer id) {
        Viajante viajante = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viajante não encontrado"));
        return new ViajanteResponseDTO(viajante);
    }

    public ViajanteResponseDTO criar(ViajanteRequestDTO dto) {
        String email = dto.email().trim().toLowerCase();
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

        String cpf = (dto.cpf() == null || dto.cpf().isBlank()) ? null : dto.cpf().trim();
        if (cpf != null && repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        Viajante viajante = new Viajante();
        viajante.setNome(dto.nome().trim());
        viajante.setEmail(email);
        viajante.setCpf(cpf);

        return new ViajanteResponseDTO(repository.save(viajante));
    }
}