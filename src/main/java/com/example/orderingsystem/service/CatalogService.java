package com.example.orderingsystem.service;

import com.example.orderingsystem.exception.NonExistingElementAtSpecifiedIdException;
import com.example.orderingsystem.model.Catalog;
import com.example.orderingsystem.repository.CatalogRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {

  private final CatalogRepo catalogRepo;

  @Autowired
  public CatalogService(CatalogRepo catalogRepo) {
    this.catalogRepo = catalogRepo;
  }

  public List<Catalog> getAllProductsInCatalog() {
    return catalogRepo.findAll();
  }

  public Catalog getCatalogById(Long id) {
    return catalogRepo.findById(id).orElseThrow(NonExistingElementAtSpecifiedIdException::new);
  }

  public void saveCatalog(Catalog catalog) {
    catalogRepo.saveAndFlush(catalog);
  }

}
