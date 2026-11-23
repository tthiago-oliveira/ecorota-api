package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.FatorEmissao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FatorEmissaoRepository extends JpaRepository<FatorEmissao, Integer> {
    List<FatorEmissao> findByModalIdModal(Integer idModal);
    boolean existsByModalIdModalAndAnoVigencia(Integer idModal, Short anoVigencia);
}