package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.FornecedorRequestDTO;
import br.com.ecorota.ecorota_api.dto.FornecedorResponseDTO;
import br.com.ecorota.ecorota_api.dto.ReivindicarFornecedorDTO;
import br.com.ecorota.ecorota_api.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

    @GetMapping
    public List<FornecedorResponseDTO> listar(@RequestParam(required = false) Integer idMunicipio) {
        return service.listar(idMunicipio);
    }

    @GetMapping("/{id}")
    public FornecedorResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorResponseDTO criar(@RequestBody @Valid FornecedorRequestDTO dto) {
        return service.criar(dto);
    }

    @PatchMapping("/{id}/reivindicar")
    public FornecedorResponseDTO reivindicarPerfil(
            @PathVariable Integer id,
            @RequestBody @Valid ReivindicarFornecedorDTO dto
    ) {
        return service.reivindicarPerfil(id, dto);
    }
}