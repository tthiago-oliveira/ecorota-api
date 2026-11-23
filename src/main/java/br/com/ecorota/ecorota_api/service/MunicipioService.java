package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.MunicipioRequestDTO;
import br.com.ecorota.ecorota_api.dto.MunicipioResponseDTO;
import br.com.ecorota.ecorota_api.model.Municipio;
import br.com.ecorota.ecorota_api.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository repository;

    public List<MunicipioResponseDTO> listarTodos() {
        return repository.findAll().stream().map(MunicipioResponseDTO::new).toList();
    }

    public MunicipioResponseDTO buscarPorId(Integer id) {
        Municipio municipio = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Município não encontrado"));
        return new MunicipioResponseDTO(municipio);
    }

    public MunicipioResponseDTO criar(MunicipioRequestDTO dto) {
        String ibge = (dto.codigoIbge() == null || dto.codigoIbge().isBlank()) ? null : dto.codigoIbge().trim();

        if (ibge != null && repository.existsByCodigoIbge(ibge)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código IBGE já cadastrado");
        }

        Municipio municipio = new Municipio();
        municipio.setNome(dto.nome().trim());
        municipio.setUf(dto.uf().trim().toUpperCase());
        municipio.setCodigoIbge(ibge);

        return new MunicipioResponseDTO(repository.save(municipio));
    }
}