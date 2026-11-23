package br.com.ecorota.ecorota_api.model;

import br.com.ecorota.ecorota_api.model.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;

    @ManyToOne
    @JoinColumn(name = "id_viajante", nullable = false)
    private Viajante viajante;

    @Column(name = "datahora", nullable = false)
    private LocalDateTime datahora = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private StatusReserva status = StatusReserva.confirmada;
}