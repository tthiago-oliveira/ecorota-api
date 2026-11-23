package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.CriterioSustentabilidadeRequestDTO;
import br.com.ecorota.ecorota_api.dto.CriterioSustentabilidadeResponseDTO;
import br.com.ecorota.ecorota_api.model.CriterioSustentabilidade;
import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;
import br.com.ecorota.ecorota_api.repository.CriterioSustentabilidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CriterioSustentabilidadeService {

    private final CriterioSustentabilidadeRepository repository;

    public List<CriterioSustentabilidadeResponseDTO> listar(EixoSustentabilidade eixo) {
        List<CriterioSustentabilidade> lista = (eixo != null)
                ? repository.findByEixo(eixo)
                : repository.findAll();
        return lista.stream().map(CriterioSustentabilidadeResponseDTO::new).toList();
    }

    public CriterioSustentabilidadeResponseDTO criar(CriterioSustentabilidadeRequestDTO dto) {
        CriterioSustentabilidade criterio = new CriterioSustentabilidade();
        criterio.setEixo(dto.eixo());
        criterio.setDescricao(dto.descricao().trim());
        criterio.setPeso(dto.peso());
        criterio.setObrigatorio(dto.obrigatorio() != null ? dto.obrigatorio() : false);

        return new CriterioSustentabilidadeResponseDTO(repository.save(criterio));
    }
}