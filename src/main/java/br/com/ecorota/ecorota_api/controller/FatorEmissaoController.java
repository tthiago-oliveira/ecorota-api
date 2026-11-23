package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.FatorEmissaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.FatorEmissaoResponseDTO;
import br.com.ecorota.ecorota_api.service.FatorEmissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fatores-emissao")
@RequiredArgsConstructor
public class FatorEmissaoController {

    private final FatorEmissaoService service;

    @GetMapping
    public List<FatorEmissaoResponseDTO> listar(@RequestParam(required = false) Integer idModal) {
        return service.listar(idModal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FatorEmissaoResponseDTO criar(@RequestBody @Valid FatorEmissaoRequestDTO dto) {
        return service.criar(dto);
    }
}