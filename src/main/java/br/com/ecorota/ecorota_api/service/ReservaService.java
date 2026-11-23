package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.ItemReservaRequestDTO;
import br.com.ecorota.ecorota_api.dto.ItemReservaResponseDTO;
import br.com.ecorota.ecorota_api.dto.ReservaRequestDTO;
import br.com.ecorota.ecorota_api.dto.ReservaResponseDTO;
import br.com.ecorota.ecorota_api.model.ItemReserva;
import br.com.ecorota.ecorota_api.model.JanelaVisitacao;
import br.com.ecorota.ecorota_api.model.Reserva;
import br.com.ecorota.ecorota_api.model.Viajante;
import br.com.ecorota.ecorota_api.model.enums.StatusReserva;
import br.com.ecorota.ecorota_api.repository.ItemReservaRepository;
import br.com.ecorota.ecorota_api.repository.JanelaVisitacaoRepository;
import br.com.ecorota.ecorota_api.repository.ReservaRepository;
import br.com.ecorota.ecorota_api.repository.ViajanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ItemReservaRepository itemReservaRepository;
    private final ViajanteRepository viajanteRepository;
    private final JanelaVisitacaoRepository janelaRepository;

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listar(Integer idViajante) {
        List<Reserva> reservas = (idViajante != null)
                ? reservaRepository.findByViajanteIdViajante(idViajante)
                : reservaRepository.findAll();

        return reservas.stream().map(this::montarResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarPorId(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));
        return montarResponse(reserva);
    }

    @Transactional
    public ReservaResponseDTO criar(ReservaRequestDTO dto) {
        Viajante viajante = viajanteRepository.findById(dto.idViajante())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viajante não encontrado"));

        Reserva reserva = new Reserva();
        reserva.setViajante(viajante);
        reserva.setDatahora(LocalDateTime.now());
        reserva.setStatus(StatusReserva.confirmada);
        reserva = reservaRepository.save(reserva);

        List<ItemReservaResponseDTO> itensSalvos = new ArrayList<>();

        for (ItemReservaRequestDTO itemDto : dto.itens()) {
            JanelaVisitacao janela = janelaRepository.findByIdWithLock(itemDto.idJanela())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Janela de visitação não encontrada: " + itemDto.idJanela()));

            int vagasRestantes = janela.getVagasTotal() - janela.getVagasOcupadas();
            if (itemDto.qtdPessoas() > vagasRestantes) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Vagas insuficientes na janela " + janela.getIdJanela() + " (" + janela.getAtrativo().getNome() + "). Disponíveis: " + vagasRestantes
                );
            }

            janela.setVagasOcupadas(janela.getVagasOcupadas() + itemDto.qtdPessoas());
            janelaRepository.save(janela);

            BigDecimal valorCalculado = (itemDto.valor() != null)
                    ? itemDto.valor()
                    : janela.getAtrativo().getValorIngresso().multiply(BigDecimal.valueOf(itemDto.qtdPessoas()));

            ItemReserva item = new ItemReserva();
            item.setReserva(reserva);
            item.setJanela(janela);
            item.setQtdPessoas(itemDto.qtdPessoas());
            item.setValor(valorCalculado);

            itensSalvos.add(new ItemReservaResponseDTO(itemReservaRepository.save(item)));
        }

        return new ReservaResponseDTO(reserva, itensSalvos);
    }

    @Transactional
    public ReservaResponseDTO cancelar(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        if (reserva.getStatus() == StatusReserva.cancelada) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta reserva já está cancelada");
        }

        List<ItemReserva> itens = itemReservaRepository.findByReservaIdReserva(id);
        for (ItemReserva item : itens) {
            JanelaVisitacao janela = janelaRepository.findByIdWithLock(item.getJanela().getIdJanela())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Janela não encontrada"));

            int novaOcupacao = Math.max(0, janela.getVagasOcupadas() - item.getQtdPessoas());
            janela.setVagasOcupadas(novaOcupacao);
            janelaRepository.save(janela);
        }

        reserva.setStatus(StatusReserva.cancelada);
        reservaRepository.save(reserva);

        return montarResponse(reserva);
    }

    private ReservaResponseDTO montarResponse(Reserva reserva) {
        List<ItemReservaResponseDTO> itens = itemReservaRepository.findByReservaIdReserva(reserva.getIdReserva())
                .stream()
                .map(ItemReservaResponseDTO::new)
                .toList();
        return new ReservaResponseDTO(reserva, itens);
    }
}