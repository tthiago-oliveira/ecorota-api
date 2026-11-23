package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "servico_contratado")
@Getter
@Setter
public class ServicoContratado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer idServico;

    @ManyToOne
    @JoinColumn(name = "id_viagem", nullable = false)
    private Viagem viagem;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "tipo_servico", nullable = false, length = 40)
    private String tipoServico;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
}