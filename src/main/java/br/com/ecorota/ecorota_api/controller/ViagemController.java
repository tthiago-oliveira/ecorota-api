package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.*;
import br.com.ecorota.ecorota_api.service.ViagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService service;

    @GetMapping
    public List<ViagemResponseDTO> listar(@RequestParam(required = false) Integer idViajante) {
        return service.listar(idViajante);
    }

    @GetMapping("/{id}")
    public ViagemResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViagemResponseDTO criar(@RequestBody @Valid ViagemRequestDTO dto) {
        return service.criar(dto);
    }

    @PatchMapping("/{id}/status")
    public ViagemResponseDTO atualizarStatus(
            @PathVariable Integer id,
            @RequestBody @Valid StatusViagemRequestDTO dto
    ) {
        return service.atualizarStatus(id, dto);
    }

    @PostMapping("/{id}/trechos")
    @ResponseStatus(HttpStatus.CREATED)
    public ViagemResponseDTO adicionarTrecho(
            @PathVariable Integer id,
            @RequestBody @Valid TrechoDeslocamentoRequestDTO dto
    ) {
        return service.adicionarTrecho(id, dto);
    }

    @PostMapping("/{id}/servicos")
    @ResponseStatus(HttpStatus.CREATED)
    public ViagemResponseDTO adicionarServico(
            @PathVariable Integer id,
            @RequestBody @Valid ServicoContratadoRequestDTO dto
    ) {
        return service.adicionarServico(id, dto);
    }
}