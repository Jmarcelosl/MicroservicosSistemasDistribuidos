package com.ecommerce.product_api.converter;

import com.ecommerce.product_api.dto.CategoryDTO;
import com.ecommerce.product_api.dto.ProductDTO;
import com.ecommerce.product_api.model.Category;
import com.ecommerce.product_api.model.Product;

public class DTOConverter {

    public static CategoryDTO convert(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setNome(category.getNome());
        return categoryDTO;
    }

    public static ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNome(product.getNome());
        productDTO.setPreco(product.getPreco());
        if (product.getCategory() != null) {
            productDTO.setCategoryDTO(DTOConverter.convert(product.getCategory()));
        }
        return productDTO;
    }

}
