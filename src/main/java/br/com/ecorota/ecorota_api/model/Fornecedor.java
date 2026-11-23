package br.com.ecorota.ecorota_api.model;

import br.com.ecorota.ecorota_api.model.enums.OrigemCadastro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "fornecedor")
@Getter
@Setter
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fornecedor")
    private Integer idFornecedor;

    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(name = "nome_fantasia", nullable = false, length = 150)
    private String nomeFantasia;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem_cadastro", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private OrigemCadastro origemCadastro;

    @Column(name = "perfil_reivindicado", nullable = false)
    private Boolean perfilReivindicado = false;

    @Column(name = "data_reivindicacao")
    private LocalDateTime dataReivindicacao;

    @Column(name = "cnpj", unique = true, length = 14, columnDefinition = "CHAR(14)")
    private String cnpj;

    @Column(name = "razao_social", length = 150)
    private String razaoSocial;
}