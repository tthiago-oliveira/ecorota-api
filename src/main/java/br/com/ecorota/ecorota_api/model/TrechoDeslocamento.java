package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "trecho_deslocamento")
@Getter
@Setter
public class TrechoDeslocamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trecho")
    private Integer idTrecho;

    @ManyToOne
    @JoinColumn(name = "id_viagem", nullable = false)
    private Viagem viagem;

    @ManyToOne
    @JoinColumn(name = "id_fator", nullable = false)
    private FatorEmissao fatorEmissao;

    @Column(name = "origem", nullable = false, length = 120)
    private String origem;

    @Column(name = "destino", nullable = false, length = 120)
    private String destino;

    @Column(name = "distancia_km", nullable = false, precision = 8, scale = 2)
    private BigDecimal distanciaKm;
}