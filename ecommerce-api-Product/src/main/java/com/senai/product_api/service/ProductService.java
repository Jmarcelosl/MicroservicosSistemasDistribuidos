package com.senai.product_api.service;

import com.senai.product_api.dto.ProductDTO;
import com.senai.product_api.model.Category;
import com.senai.product_api.model.Product;
import com.senai.product_api.repository.CategoryRepository;
import com.senai.product_api.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(ProductDTO::convert).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        return productRepository.getProductByCategory(categoryId).stream().map(ProductDTO::convert).collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);
        return product != null ? ProductDTO.convert(product) : null;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = Product.convert(productDTO);
        product.setCategory(category);
        return ProductDTO.convert(productRepository.save(product));
    }

    public void delete(long productId) {
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(productRepository::delete);
    }

    public ProductDTO editProduct(long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getNome() != null && !dto.getNome().isEmpty()) {
            product.setNome(dto.getNome());
        }
        if (dto.getPreco() != null) {
            product.setPreco(dto.getPreco());
        }
        if (dto.getDescricao() != null && !dto.getDescricao().isEmpty()) {
            product.setDescricao(dto.getDescricao());
        }
        if (dto.getProductIdentifier() != null && !dto.getProductIdentifier().isEmpty()) {
            product.setProductIdentifier(dto.getProductIdentifier());
        }

        return ProductDTO.convert(productRepository.save(product));
    }

    public Page<ProductDTO> getAllPage(Pageable page) {
        return productRepository.findAll(page).map(ProductDTO::convert);
    }
}
