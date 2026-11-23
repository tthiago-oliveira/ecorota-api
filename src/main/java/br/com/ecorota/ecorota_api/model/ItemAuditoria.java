package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_auditoria")
@Getter
@Setter
public class ItemAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_auditoria")
    private Integer idItemAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_forn_cert", nullable = false)
    private FornecedorCertificacao fornecedorCertificacao;

    @ManyToOne
    @JoinColumn(name = "id_criterio", nullable = false)
    private CriterioSustentabilidade criterio;

    @Column(name = "atendido", nullable = false)
    private Boolean atendido;

    @Column(name = "evidencia_url", length = 255)
    private String evidenciaUrl;

    @Column(name = "observacao", length = 500)
    private String observacao;
}