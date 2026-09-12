package com.ecommerce.user_api.dto;

import java.time.LocalDateTime;

import com.ecommerce.user_api.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Schema(example = "Marina Oliveira")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @Schema(example = "12345678909", description = "CPF sem pontuação")
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    @Schema(example = "Rua das Palmeiras, 42 - Curitiba")
    private String endereco;
    @Schema(example = "marina.oliveira@example.com")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;
    @Schema(example = "+5541998765432")
    private String telefone;
    @Schema(example = "2026-09-11T18:15:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCadastro;

    public static UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNome(user.getNome());
        userDTO.setEndereco(user.getEndereco());
        userDTO.setCpf(user.getCpf());
        userDTO.setEmail(user.getEmail());
        userDTO.setTelefone(user.getTelefone());
        userDTO.setDataCadastro(user.getDataCadastro());
        return userDTO;
    }


}
