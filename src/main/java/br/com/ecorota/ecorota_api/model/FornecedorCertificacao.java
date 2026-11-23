package br.com.ecorota.ecorota_api.model;

import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "fornecedor_certificacao")
@Getter
@Setter
public class FornecedorCertificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forn_cert")
    private Integer idFornCert;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "numero", nullable = false, unique = true, length = 40)
    private String numero;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Column(name = "url_comprovante", length = 255)
    private String urlComprovante;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private StatusCertificacao status = StatusCertificacao.em_analise;
}