package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.CriterioSustentabilidade;
import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CriterioSustentabilidadeRepository extends JpaRepository<CriterioSustentabilidade, Integer> {
    List<CriterioSustentabilidade> findByEixo(EixoSustentabilidade eixo);
}