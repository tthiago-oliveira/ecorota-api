package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.FornecedorCertificacao;
import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FornecedorCertificacaoRepository extends JpaRepository<FornecedorCertificacao, Integer> {
    List<FornecedorCertificacao> findByFornecedorIdFornecedor(Integer idFornecedor);
    List<FornecedorCertificacao> findByStatus(StatusCertificacao status);
    boolean existsByNumero(String numero);
}