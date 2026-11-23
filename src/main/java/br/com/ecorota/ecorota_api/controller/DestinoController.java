package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.DestinoRequestDTO;
import br.com.ecorota.ecorota_api.dto.DestinoResponseDTO;
import br.com.ecorota.ecorota_api.service.DestinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoService service;

    @GetMapping
    public List<DestinoResponseDTO> listar(@RequestParam(required = false) Integer idMunicipio) {
        return service.listar(idMunicipio);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DestinoResponseDTO criar(@RequestBody @Valid DestinoRequestDTO dto) {
        return service.criar(dto);
    }
}