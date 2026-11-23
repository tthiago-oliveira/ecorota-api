package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "destino")
@Getter
@Setter
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destino")
    private Integer idDestino;

    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "tipo", length = 40)
    private String tipo;

    // Gere os Getters e Setters aqui (Alt + Insert -> Getter and Setter)
}