package com.ecommerce.shopping_api.dto;

import com.ecommerce.shopping_api.model.Item;

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
public class ItemDTO {

    @Schema(example = "cafe-origem-serra-250g")
    @NotBlank
    private String productIdentifier;
    @Schema(example = "34.90", accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    private Float price;

    public static ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductIdentifier(
                item.getProductIdentifier());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}

