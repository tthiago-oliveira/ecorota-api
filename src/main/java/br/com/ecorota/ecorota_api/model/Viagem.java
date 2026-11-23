package br.com.ecorota.ecorota_api.model;

import br.com.ecorota.ecorota_api.model.enums.StatusViagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "viagem")
@Getter
@Setter
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viagem")
    private Integer idViagem;

    @ManyToOne
    @JoinColumn(name = "id_viajante", nullable = false)
    private Viajante viajante;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private StatusViagem status = StatusViagem.planejada;
}