package com.example.orderingsystem.repository;

import com.example.orderingsystem.model.Catalog;
import com.example.orderingsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepo extends JpaRepository<Catalog, Long> {

  Catalog findByProduct(Product product);

}