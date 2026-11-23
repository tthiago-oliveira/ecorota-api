package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findByFornecedorIdFornecedor(Integer idFornecedor);
    List<Avaliacao> findByViajanteIdViajante(Integer idViajante);

    @Query("SELECT COALESCE(AVG(a.nota), 0.0) FROM Avaliacao a WHERE a.fornecedor.idFornecedor = :idFornecedor")
    Double calcularMediaPorFornecedor(@Param("idFornecedor") Integer idFornecedor);
}