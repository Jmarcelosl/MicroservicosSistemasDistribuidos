package com.ecommerce.product_api.dto;

import com.ecommerce.product_api.model.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @Schema(example = "cafe-origem-serra-250g")
    @NotBlank
    private String productIdentifier;
    @Schema(example = "Cafe especial da Serra")
    @NotBlank
    private String nome;
    @Schema(example = "Graos 100% arabica, torra media e notas de chocolate.")
    @NotBlank
    private String descricao;
    @Schema(example = "34.90")
    @NotNull
    private Float preco;
    @Schema(example = "{\"id\": 1, \"nome\": \"Eletrônico\"}")
    @NotNull
    private CategoryDTO categoryDTO;

    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNome(product.getNome());
        productDTO.setPreco(product.getPreco());
        productDTO.setProductIdentifier(product.getProductIdentifier());
        productDTO.setDescricao(product.getDescricao());

        if (product.getCategory() != null) {
            productDTO.setCategoryDTO(
                    CategoryDTO.convert(product.getCategory()));
        }
        return productDTO;
    }
}

