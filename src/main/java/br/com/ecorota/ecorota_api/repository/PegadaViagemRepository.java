package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.PegadaViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PegadaViagemRepository extends JpaRepository<PegadaViagem, Integer> {
    Optional<PegadaViagem> findByViagemIdViagem(Integer idViagem);
}