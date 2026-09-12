package com.ecommerce.shopping_api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.shopping_api.model.Shop;

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
public class ShopDTO {

    @Schema(example = "12345678909", description = "CPF do cliente cadastrado no User API")
    @NotBlank
    private String userIdentifier;

    @Schema(example = "69.80", accessMode = Schema.AccessMode.READ_ONLY)
    private Float total;

    @Schema(example = "2026-09-11T18:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime date;

    @NotNull
    private List<ItemDTO> items;

    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        shopDTO.setItems(
                shop.getItems()
                        .stream()
                        .map(ItemDTO::convert)
                        .toList()
        );
        return shopDTO;
    }
}

