package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "atrativo")
@Getter
@Setter

public class Atrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atrativo")
    private Integer idAtrativo;

    @ManyToOne
    @JoinColumn(name = "id_destino", nullable = false)
    private Destino destino;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "latitude", precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "exige_guia", nullable = false)
    private Boolean exigeGuia = false;

    @Column(name = "valor_ingresso", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorIngresso = BigDecimal.ZERO;

    // Gere os Getters e Setters aqui (Alt + Insert -> Getter and Setter)
}