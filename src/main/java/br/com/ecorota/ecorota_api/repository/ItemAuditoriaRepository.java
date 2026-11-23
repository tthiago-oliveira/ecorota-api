package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.ItemAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemAuditoriaRepository extends JpaRepository<ItemAuditoria, Integer> {
    List<ItemAuditoria> findByFornecedorCertificacaoIdFornCert(Integer idFornCert);
    boolean existsByFornecedorCertificacaoIdFornCertAndCriterioIdCriterio(Integer idFornCert, Integer idCriterio);
}