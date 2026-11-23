package br.com.ecorota.ecorota_api.repository;

import br.com.ecorota.ecorota_api.model.JanelaVisitacao;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JanelaVisitacaoRepository extends JpaRepository<JanelaVisitacao, Integer> {
    List<JanelaVisitacao> findByAtrativoIdAtrativo(Integer idAtrativo);

    List<JanelaVisitacao> findByAtrativoIdAtrativoAndDataVisita(Integer idAtrativo, LocalDate dataVisita);

    @Query("""
           SELECT COALESCE(SUM(j.vagasTotal), 0)
           FROM JanelaVisitacao j
           WHERE j.atrativo.idAtrativo = :idAtrativo
             AND j.dataVisita = :dataVisita
           """)
    Integer somarVagasTotalNoDia(
            @Param("idAtrativo") Integer idAtrativo,
            @Param("dataVisita") LocalDate dataVisita
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT j FROM JanelaVisitacao j WHERE j.idJanela = :id")
    Optional<JanelaVisitacao> findByIdWithLock(@Param("id") Integer id);
}