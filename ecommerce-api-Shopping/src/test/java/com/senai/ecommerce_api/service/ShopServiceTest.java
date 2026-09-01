package com.senai.ecommerce_api.service;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.senai.ecommerce_api.dto.ItemDTO;
import com.senai.ecommerce_api.dto.ShopDTO;
import com.senai.ecommerce_api.model.Item;
import com.senai.ecommerce_api.model.Shop;
import com.senai.ecommerce_api.repository.ShopRepository;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopService shopService;

    @Test
    void shouldSaveShopAndCalculateTotal() {
        ShopDTO input = new ShopDTO();
        input.setUserIdentifier("Filipe");
        input.setDate(LocalDateTime.now());
        input.setItems(List.of(
            item("a1", 100.0f),
            item("a2", 299.0f),
            item("a3", 50.0f)
        ));

        Shop saved = new Shop();
        saved.setUserIdentifier("Filipe");
        saved.setTotal(449.0f);
        saved.setDate(LocalDateTime.now());
        saved.setItems(List.of(
            modelItem("a1", 100.0f),
            modelItem("a2", 299.0f),
            modelItem("a3", 50.0f)
        ));

        when(shopRepository.save(any(Shop.class))).thenReturn(saved);

        ShopDTO result = shopService.save(input);

        assertNotNull(result);
        assertEquals("Filipe", result.getUserIdentifier());
        assertEquals(449.0f, result.getTotal());
    }

    private ItemDTO item(String productIdentifier, Float price) {
        ItemDTO item = new ItemDTO();
        item.setProductIdentifier(productIdentifier);
        item.setPrice(price);
        return item;
    }

    private Item modelItem(String productIdentifier, Float price) {
        Item item = new Item();
        item.setProductIdentifier(productIdentifier);
        item.setPrice(price);
        return item;
    }
}
