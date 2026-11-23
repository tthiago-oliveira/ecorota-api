package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_viajante", nullable = false)
    private Viajante viajante;

    @Column(name = "nota", nullable = false)
    private Integer nota;

    @Column(name = "comentario", nullable = false, length = 1000)
    private String comentario;

    @Column(name = "data_avaliacao", nullable = false)
    private LocalDateTime dataAvaliacao = LocalDateTime.now();

    @Column(name = "resposta", length = 1000)
    private String resposta;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;
}