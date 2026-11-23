package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pegada_viagem")
@Getter
@Setter
public class PegadaViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pegada")
    private Integer idPegada;

    @OneToOne
    @JoinColumn(name = "id_viagem", nullable = false, unique = true)
    private Viagem viagem;

    @Column(name = "total_kgco2e", nullable = false, precision = 10, scale = 3)
    private BigDecimal totalKgco2e;

    @Column(name = "calculado_em", nullable = false)
    private LocalDateTime calculadoEm = LocalDateTime.now();

    @Column(name = "versao_metodologia", nullable = false, length = 20)
    private String versaoMetodologia;
}