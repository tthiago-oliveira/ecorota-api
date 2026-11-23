package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modal_transporte")
@Getter
@Setter
public class ModalTransporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modal")
    private Integer idModal;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "categoria", length = 40)
    private String categoria;
}