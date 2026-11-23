package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.Viajante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajanteRepository extends JpaRepository<Viajante, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}