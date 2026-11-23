package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.ServicoContratado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoContratadoRepository extends JpaRepository<ServicoContratado, Integer> {
    List<ServicoContratado> findByViagemIdViagem(Integer idViagem);
    List<ServicoContratado> findByFornecedorIdFornecedor(Integer idFornecedor);
}