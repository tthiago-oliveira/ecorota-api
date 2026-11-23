package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.CapacidadeCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CapacidadeCargaRepository extends JpaRepository<CapacidadeCarga, Integer> {
    List<CapacidadeCarga> findByAtrativoIdAtrativo(Integer idAtrativo);

    @Query("""
           SELECT c FROM CapacidadeCarga c
           WHERE c.atrativo.idAtrativo = :idAtrativo
             AND :data BETWEEN c.vigenciaInicio AND c.vigenciaFim
           """)
    Optional<CapacidadeCarga> buscarVigenciaNaData(
            @Param("idAtrativo") Integer idAtrativo,
            @Param("data") LocalDate data
    );
}