package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipio")
@Getter
@Setter
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "uf", nullable = false, length = 2, columnDefinition = "CHAR(2)")
    private String uf;

    @Column(name = "codigo_ibge", unique = true, length = 7, columnDefinition = "CHAR(7)")
    private String codigoIbge;

    // Gere os Getters e Setters aqui (Alt + Insert -> Getter and Setter)
}