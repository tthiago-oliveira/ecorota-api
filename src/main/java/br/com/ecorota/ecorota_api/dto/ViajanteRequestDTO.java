package br.com.ecorota.ecorota_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ViajanteRequestDTO(
        @NotBlank(message = "Nome do viajante é obrigatório")
        @Size(max = 120)
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Size(max = 150)
        String email,

        @Size(min = 11, max = 11, message = "CPF deve conter 11 dígitos numéricos")
        String cpf
) {}