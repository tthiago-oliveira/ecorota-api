package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "capacidade_carga")
@Getter
@Setter

public class CapacidadeCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capacidade")
    private Integer idCapacidade;

    @ManyToOne
    @JoinColumn(name = "id_atrativo", nullable = false)
    private Atrativo atrativo;

    @Column(name = "vigencia_inicio", nullable = false)
    private LocalDate vigenciaInicio;

    @Column(name = "vigencia_fim", nullable = false)
    private LocalDate vigenciaFim;

    @Column(name = "cota_diaria", nullable = false)
    private Integer cotaDiaria;

    @Column(name = "cota_por_janela", nullable = false)
    private Integer cotaPorJanela;
}