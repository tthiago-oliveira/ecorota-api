package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.ReservaRequestDTO;
import br.com.ecorota.ecorota_api.dto.ReservaResponseDTO;
import br.com.ecorota.ecorota_api.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @GetMapping
    public List<ReservaResponseDTO> listar(@RequestParam(required = false) Integer idViajante) {
        return service.listar(idViajante);
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO criar(@RequestBody @Valid ReservaRequestDTO dto) {
        return service.criar(dto);
    }

    @PatchMapping("/{id}/cancelar")
    public ReservaResponseDTO cancelar(@PathVariable Integer id) {
        return service.cancelar(id);
    }
}