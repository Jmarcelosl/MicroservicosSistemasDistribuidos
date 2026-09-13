package com.ecommerce.product_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.product_api.dto.ProductDTO;
import com.ecommerce.product_api.model.Category;
import com.ecommerce.product_api.model.Product;
import com.ecommerce.product_api.repository.CategoryRepository;
import com.ecommerce.product_api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll(); return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId( Long categoryId) {
        List<Product> products = productRepository
                .getProductByCategory(categoryId);
        return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier( String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        if (product != null) {
            return ProductDTO.convert(product);
        }
        return null;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryRepository
                .findById(productDTO.getCategoryDTO().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product =
                productRepository.save(Product.convert(productDTO));
        return ProductDTO.convert(product);
    }

    public void delete(long productId) {
        Optional<Product> product =
                productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.delete(product.get());
        }
    }

    public Page<ProductDTO> getAllPage(Pageable page) {
        Page<Product> users = productRepository.findAll(page);
        return users.map(ProductDTO::convert);
    }

}
