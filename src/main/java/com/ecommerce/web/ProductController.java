// web/ProductController.java
package com.ecommerce.web;

import com.ecommerce.product.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        try {
            Money price = Money.usd(request.getPrice());
            Product product = Product.create(
                request.getName(),
                request.getDescription(),
                price,
                request.getStockQuantity()
            );
            Product saved = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ProductResponse.from(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId) {
        return productRepository.findById(ProductId.of(productId))
            .map(product -> ResponseEntity.ok(ProductResponse.from(product)))
            .orElse(ResponseEntity.notFound().build());
    }

    public static class CreateProductRequest {
        private String name;
        private String description;
        private BigDecimal price;
        private int stockQuantity;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public int getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    }

    public static class ProductResponse {
        private String id;
        private String name;
        private String description;
        private BigDecimal price;
        private int stockQuantity;

        public static ProductResponse from(Product product) {
            ProductResponse response = new ProductResponse();
            response.id = product.getId().getValue();
            response.name = product.getName();
            response.description = product.getDescription();
            response.price = product.getPrice().getAmount();
            response.stockQuantity = product.getStockQuantity();
            return response;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public BigDecimal getPrice() { return price; }
        public int getStockQuantity() { return stockQuantity; }
    }
}