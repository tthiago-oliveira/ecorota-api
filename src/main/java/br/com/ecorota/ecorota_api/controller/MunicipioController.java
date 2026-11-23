package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.MunicipioRequestDTO;
import br.com.ecorota.ecorota_api.dto.MunicipioResponseDTO;
import br.com.ecorota.ecorota_api.service.MunicipioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService service;

    @GetMapping
    public List<MunicipioResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public MunicipioResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MunicipioResponseDTO criar(@RequestBody @Valid MunicipioRequestDTO dto) {
        return service.criar(dto);
    }
}