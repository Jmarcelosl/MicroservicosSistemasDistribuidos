package com.ecommerce.product_api.model;

import com.ecommerce.product_api.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="category")
@Table(name = "category", schema = "products")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    public static Category convert(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setNome(categoryDTO.getNome());
        return category;
    }
}
