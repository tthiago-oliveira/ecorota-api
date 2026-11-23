package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.CapacidadeCargaRequestDTO;
import br.com.ecorota.ecorota_api.dto.CapacidadeCargaResponseDTO;
import br.com.ecorota.ecorota_api.service.CapacidadeCargaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacidades-carga")
@RequiredArgsConstructor
public class CapacidadeCargaController {

    private final CapacidadeCargaService service;

    @GetMapping
    public List<CapacidadeCargaResponseDTO> listar(@RequestParam(required = false) Integer idAtrativo) {
        return service.listar(idAtrativo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CapacidadeCargaResponseDTO criar(@RequestBody @Valid CapacidadeCargaRequestDTO dto) {
        return service.criar(dto);
    }
}