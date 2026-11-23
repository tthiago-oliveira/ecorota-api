package br.com.ecorota.ecorota_api.service;

import br.com.ecorota.ecorota_api.dto.FornecedorRequestDTO;
import br.com.ecorota.ecorota_api.dto.FornecedorResponseDTO;
import br.com.ecorota.ecorota_api.dto.ReivindicarFornecedorDTO;
import br.com.ecorota.ecorota_api.model.Fornecedor;
import br.com.ecorota.ecorota_api.model.Municipio;
import br.com.ecorota.ecorota_api.model.enums.OrigemCadastro;
import br.com.ecorota.ecorota_api.repository.FornecedorRepository;
import br.com.ecorota.ecorota_api.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final MunicipioRepository municipioRepository;

    public List<FornecedorResponseDTO> listar(Integer idMunicipio) {
        List<Fornecedor> lista = (idMunicipio != null)
                ? fornecedorRepository.findByMunicipioIdMunicipio(idMunicipio)
                : fornecedorRepository.findAll();
        return lista.stream().map(FornecedorResponseDTO::new).toList();
    }

    public FornecedorResponseDTO buscarPorId(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
        return new FornecedorResponseDTO(fornecedor);
    }

    public FornecedorResponseDTO criar(FornecedorRequestDTO dto) {
        Municipio municipio = municipioRepository.findById(dto.idMunicipio())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Município não encontrado"));

        String cnpj = (dto.cnpj() == null || dto.cnpj().isBlank()) ? null : dto.cnpj().trim();
        if (cnpj != null && fornecedorRepository.existsByCnpj(cnpj)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já cadastrado");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setMunicipio(municipio);
        fornecedor.setNomeFantasia(dto.nomeFantasia().trim());
        fornecedor.setTipo(dto.tipo());
        fornecedor.setOrigemCadastro(dto.origemCadastro());
        fornecedor.setCnpj(cnpj);
        fornecedor.setRazaoSocial(dto.razaoSocial() != null ? dto.razaoSocial().trim() : null);

        if (dto.origemCadastro() == OrigemCadastro.proprio) {
            fornecedor.setPerfilReivindicado(true);
            fornecedor.setDataReivindicacao(LocalDateTime.now());
        } else {
            fornecedor.setPerfilReivindicado(false);
        }

        return new FornecedorResponseDTO(fornecedorRepository.save(fornecedor));
    }

    public FornecedorResponseDTO reivindicarPerfil(Integer id, ReivindicarFornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));

        if (Boolean.TRUE.equals(fornecedor.getPerfilReivindicado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este perfil já foi reivindicado");
        }

        String cnpj = dto.cnpj().trim();
        if (!cnpj.equals(fornecedor.getCnpj()) && fornecedorRepository.existsByCnpj(cnpj)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ já vinculado a outro fornecedor");
        }

        fornecedor.setCnpj(cnpj);
        fornecedor.setRazaoSocial(dto.razaoSocial().trim());
        fornecedor.setPerfilReivindicado(true);
        fornecedor.setDataReivindicacao(LocalDateTime.now());

        return new FornecedorResponseDTO(fornecedorRepository.save(fornecedor));
    }
}