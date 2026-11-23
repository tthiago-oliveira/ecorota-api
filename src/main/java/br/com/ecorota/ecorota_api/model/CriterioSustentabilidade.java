package br.com.ecorota.ecorota_api.model;

import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "criterio_sustentabilidade")
@Getter
@Setter
public class CriterioSustentabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_criterio")
    private Integer idCriterio;

    @Enumerated(EnumType.STRING)
    @Column(name = "eixo", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private EixoSustentabilidade eixo;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "peso", nullable = false)
    private Integer peso;

    @Column(name = "obrigatorio", nullable = false)
    private Boolean obrigatorio = false;
}