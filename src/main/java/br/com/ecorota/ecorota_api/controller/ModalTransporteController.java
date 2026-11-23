package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.ModalTransporteRequestDTO;
import br.com.ecorota.ecorota_api.dto.ModalTransporteResponseDTO;
import br.com.ecorota.ecorota_api.service.ModalTransporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modais-transporte")
@RequiredArgsConstructor
public class ModalTransporteController {

    private final ModalTransporteService service;

    @GetMapping
    public List<ModalTransporteResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModalTransporteResponseDTO criar(@RequestBody @Valid ModalTransporteRequestDTO dto) {
        return service.criar(dto);
    }
}