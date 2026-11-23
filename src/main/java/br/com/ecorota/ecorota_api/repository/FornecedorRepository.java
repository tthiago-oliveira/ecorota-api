package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    List<Fornecedor> findByMunicipioIdMunicipio(Integer idMunicipio);
    boolean existsByCnpj(String cnpj);
}