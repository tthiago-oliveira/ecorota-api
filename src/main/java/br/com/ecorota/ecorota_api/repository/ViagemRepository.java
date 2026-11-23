package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Integer> {
    List<Viagem> findByViajanteIdViajante(Integer idViajante);
}