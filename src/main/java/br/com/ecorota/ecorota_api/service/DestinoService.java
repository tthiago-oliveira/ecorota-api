package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.DestinoRequestDTO;
import br.com.ecorota.ecorota_api.dto.DestinoResponseDTO;
import br.com.ecorota.ecorota_api.model.Destino;
import br.com.ecorota.ecorota_api.model.Municipio;
import br.com.ecorota.ecorota_api.repository.DestinoRepository;
import br.com.ecorota.ecorota_api.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinoService {

    private final DestinoRepository destinoRepository;
    private final MunicipioRepository municipioRepository;

    public List<DestinoResponseDTO> listar(Integer idMunicipio) {
        List<Destino> lista = (idMunicipio != null)
                ? destinoRepository.findByMunicipioIdMunicipio(idMunicipio)
                : destinoRepository.findAll();
        return lista.stream().map(DestinoResponseDTO::new).toList();
    }

    public DestinoResponseDTO criar(DestinoRequestDTO dto) {
        Municipio municipio = municipioRepository.findById(dto.idMunicipio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Município não encontrado"));

        Destino destino = new Destino();
        destino.setMunicipio(municipio);
        destino.setNome(dto.nome().trim());
        destino.setTipo(dto.tipo());

        return new DestinoResponseDTO(destinoRepository.save(destino));
    }
}