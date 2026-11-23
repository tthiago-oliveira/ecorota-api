package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.TrechoDeslocamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrechoDeslocamentoRepository extends JpaRepository<TrechoDeslocamento, Integer> {
    List<TrechoDeslocamento> findByViagemIdViagem(Integer idViagem);
}