package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "janela_visitacao")
@Getter
@Setter
public class JanelaVisitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_janela")
    private Integer idJanela;

    @ManyToOne
    @JoinColumn(name = "id_atrativo", nullable = false)
    private Atrativo atrativo;

    @Column(name = "data_visita", nullable = false)
    private LocalDate dataVisita;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "vagas_total", nullable = false)
    private Integer vagasTotal;

    @Column(name = "vagas_ocupadas", nullable = false)
    private Integer vagasOcupadas = 0;
}