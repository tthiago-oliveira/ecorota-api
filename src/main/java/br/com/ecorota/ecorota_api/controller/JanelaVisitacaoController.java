package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.JanelaVisitacaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.JanelaVisitacaoResponseDTO;
import br.com.ecorota.ecorota_api.service.JanelaVisitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/janelas-visitacao")
@RequiredArgsConstructor
public class JanelaVisitacaoController {

    private final JanelaVisitacaoService service;

    @GetMapping
    public List<JanelaVisitacaoResponseDTO> listar(
            @RequestParam(required = false) Integer idAtrativo,
            @RequestParam(required = false) LocalDate dataVisita
    ) {
        return service.listar(idAtrativo, dataVisita);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JanelaVisitacaoResponseDTO criar(@RequestBody @Valid JanelaVisitacaoRequestDTO dto) {
        return service.criar(dto);
    }
}