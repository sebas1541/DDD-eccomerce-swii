package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
  private final ProductJpaRepository jpaRepository;

  public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = new ProductEntity(
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        product.getPrice().getAmount(),
        product.getPrice().getCurrency(),
        product.getStockQuantity());
    jpaRepository.save(entity);
    return product;
  }

  @Override
  public Optional<Product> findById(ProductId id) {
    return jpaRepository.findById(id.getValue())
        .map(this::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository.findAll().stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  private Product toDomain(ProductEntity entity) {
    return Product.restore(
        ProductId.of(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        Money.of(entity.getPrice(), entity.getCurrency()),
        entity.getStockQuantity());
  }
}
