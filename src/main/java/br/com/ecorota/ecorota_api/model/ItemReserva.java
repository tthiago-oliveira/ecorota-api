package br.com.ecorota.ecorota_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "item_reserva")
@Getter
@Setter
public class ItemReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_janela", nullable = false)
    private JanelaVisitacao janela;

    @Column(name = "qtd_pessoas", nullable = false)
    private Integer qtdPessoas;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
}