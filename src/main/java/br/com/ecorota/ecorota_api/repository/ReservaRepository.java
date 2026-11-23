package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByViajanteIdViajante(Integer idViajante);
}