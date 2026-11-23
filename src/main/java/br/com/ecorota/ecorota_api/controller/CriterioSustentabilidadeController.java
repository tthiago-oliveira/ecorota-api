package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.CriterioSustentabilidadeRequestDTO;
import br.com.ecorota.ecorota_api.dto.CriterioSustentabilidadeResponseDTO;
import br.com.ecorota.ecorota_api.model.enums.EixoSustentabilidade;
import br.com.ecorota.ecorota_api.service.CriterioSustentabilidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criterios-sustentabilidade")
@RequiredArgsConstructor
public class CriterioSustentabilidadeController {

    private final CriterioSustentabilidadeService service;

    @GetMapping
    public List<CriterioSustentabilidadeResponseDTO> listar(@RequestParam(required = false) EixoSustentabilidade eixo) {
        return service.listar(eixo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CriterioSustentabilidadeResponseDTO criar(@RequestBody @Valid CriterioSustentabilidadeRequestDTO dto) {
        return service.criar(dto);
    }
}