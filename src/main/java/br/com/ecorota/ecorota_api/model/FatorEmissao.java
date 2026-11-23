package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "fator_emissao")
@Getter
@Setter
public class FatorEmissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fator")
    private Integer idFator;

    @ManyToOne
    @JoinColumn(name = "id_modal", nullable = false)
    private ModalTransporte modal;

    @Column(name = "ano_vigencia", nullable = false)
    private Short anoVigencia;

    @Column(name = "kgco2e_por_km", nullable = false, precision = 8, scale = 4)
    private BigDecimal kgco2ePorKm;

    @Column(name = "fonte", nullable = false, length = 150)
    private String fonte;
}