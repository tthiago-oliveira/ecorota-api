package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.ViajanteRequestDTO;
import br.com.ecorota.ecorota_api.dto.ViajanteResponseDTO;
import br.com.ecorota.ecorota_api.service.ViajanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajantes")
@RequiredArgsConstructor
public class ViajanteController {

    private final ViajanteService service;

    @GetMapping
    public List<ViajanteResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ViajanteResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViajanteResponseDTO criar(@RequestBody @Valid ViajanteRequestDTO dto) {
        return service.criar(dto);
    }
}