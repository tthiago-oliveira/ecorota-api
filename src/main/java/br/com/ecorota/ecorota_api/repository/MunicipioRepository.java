package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    boolean existsByCodigoIbge(String codigoIbge);
}