package br.com.ecorota.ecorota_api.controller;

import br.com.ecorota.ecorota_api.dto.*;
import br.com.ecorota.ecorota_api.model.enums.StatusCertificacao;
import br.com.ecorota.ecorota_api.service.FornecedorCertificacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificacoes")
@RequiredArgsConstructor
public class FornecedorCertificacaoController {

    private final FornecedorCertificacaoService service;

    @GetMapping
    public List<FornecedorCertificacaoResponseDTO> listar(
            @RequestParam(required = false) Integer idFornecedor,
            @RequestParam(required = false) StatusCertificacao status
    ) {
        return service.listar(idFornecedor, status);
    }

    @GetMapping("/{id}")
    public FornecedorCertificacaoResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorCertificacaoResponseDTO criar(@RequestBody @Valid FornecedorCertificacaoRequestDTO dto) {
        return service.criar(dto);
    }

    @PostMapping("/{id}/itens-auditoria")
    @ResponseStatus(HttpStatus.CREATED)
    public FornecedorCertificacaoResponseDTO registrarItemAuditoria(
            @PathVariable Integer id,
            @RequestBody @Valid ItemAuditoriaRequestDTO dto
    ) {
        return service.registrarItemAuditoria(id, dto);
    }

    @PatchMapping("/{id}/status")
    public FornecedorCertificacaoResponseDTO atualizarStatus(
            @PathVariable Integer id,
            @RequestBody @Valid StatusCertificacaoRequestDTO dto
    ) {
        return service.atualizarStatus(id, dto);
    }
}