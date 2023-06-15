package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.product.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT NEW com.cg.model.dto.product.ProductDTO (" +
                "p.id, " +
                "p.title, " +
                "p.price, " +
                "p.quantity, " +
                "p.description, " +
                "p.unit, " +
                "p.category, " +
                "p.productAvatar " +
                ") " +
            "FROM Product p")
    List<ProductDTO> findAllProductDTO();
}
