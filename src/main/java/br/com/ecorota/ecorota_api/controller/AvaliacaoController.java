package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.AvaliacaoRequestDTO;
import br.com.ecorota.ecorota_api.dto.AvaliacaoResponseDTO;
import br.com.ecorota.ecorota_api.dto.RespostaAvaliacaoRequestDTO;
import br.com.ecorota.ecorota_api.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService service;

    @GetMapping
    public List<AvaliacaoResponseDTO> listar(
            @RequestParam(required = false) Integer idFornecedor,
            @RequestParam(required = false) Integer idViajante
    ) {
        return service.listar(idFornecedor, idViajante);
    }

    @GetMapping("/fornecedor/{idFornecedor}/resumo")
    public Map<String, Object> obterResumoFornecedor(@PathVariable Integer idFornecedor) {
        return service.obterResumoFornecedor(idFornecedor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponseDTO criar(@RequestBody @Valid AvaliacaoRequestDTO dto) {
        return service.criar(dto);
    }

    @PatchMapping("/{id}/responder")
    public AvaliacaoResponseDTO responder(
            @PathVariable Integer id,
            @RequestBody @Valid RespostaAvaliacaoRequestDTO dto
    ) {
        return service.responder(id, dto);
    }
}