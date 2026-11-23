package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.AtrativoRequestDTO;
import br.com.ecorota.ecorota_api.dto.AtrativoResponseDTO;
import br.com.ecorota.ecorota_api.model.Atrativo;
import br.com.ecorota.ecorota_api.model.Destino;
import br.com.ecorota.ecorota_api.repository.AtrativoRepository;
import br.com.ecorota.ecorota_api.repository.DestinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtrativoService {

    private final AtrativoRepository atrativoRepository;
    private final DestinoRepository destinoRepository;

    public List<AtrativoResponseDTO> listar(Integer idDestino) {
        List<Atrativo> lista = (idDestino != null)
                ? atrativoRepository.findByDestinoIdDestino(idDestino)
                : atrativoRepository.findAll();
        return lista.stream().map(AtrativoResponseDTO::new).toList();
    }

    public AtrativoResponseDTO buscarPorId(Integer id) {
        Atrativo atrativo = atrativoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atrativo não encontrado"));
        return new AtrativoResponseDTO(atrativo);
    }

    public AtrativoResponseDTO criar(AtrativoRequestDTO dto) {
        Destino destino = destinoRepository.findById(dto.idDestino())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destino não encontrado"));

        Atrativo atrativo = new Atrativo();
        atrativo.setDestino(destino);
        atrativo.setNome(dto.nome().trim());
        atrativo.setTipo(dto.tipo());
        atrativo.setLatitude(dto.latitude());
        atrativo.setLongitude(dto.longitude());
        atrativo.setExigeGuia(dto.exigeGuia() != null ? dto.exigeGuia() : false);
        atrativo.setValorIngresso(dto.valorIngresso() != null ? dto.valorIngresso() : BigDecimal.ZERO);

        return new AtrativoResponseDTO(atrativoRepository.save(atrativo));
    }
}