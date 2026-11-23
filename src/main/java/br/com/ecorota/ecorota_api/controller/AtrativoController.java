package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.AtrativoRequestDTO;
import br.com.ecorota.ecorota_api.dto.AtrativoResponseDTO;
import br.com.ecorota.ecorota_api.service.AtrativoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atrativos")
@RequiredArgsConstructor
public class AtrativoController {

    private final AtrativoService service;

    @GetMapping
    public List<AtrativoResponseDTO> listar(@RequestParam(required = false) Integer idDestino) {
        return service.listar(idDestino);
    }

    @GetMapping("/{id}")
    public AtrativoResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtrativoResponseDTO criar(@RequestBody @Valid AtrativoRequestDTO dto) {
        return service.criar(dto);
    }
}