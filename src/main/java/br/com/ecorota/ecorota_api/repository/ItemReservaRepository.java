package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.ItemReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemReservaRepository extends JpaRepository<ItemReserva, Integer> {
    List<ItemReserva> findByReservaIdReserva(Integer idReserva);
}