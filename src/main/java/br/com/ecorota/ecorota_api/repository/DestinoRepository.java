package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Integer> {
    List<Destino> findByMunicipioIdMunicipio(Integer idMunicipio);
}