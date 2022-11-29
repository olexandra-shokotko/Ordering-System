package com.example.orderingsystem.service;

import com.example.orderingsystem.exception.NonExistingElementAtSpecifiedIdException;
import com.example.orderingsystem.exception.ProductAlreadyExistsException;
import com.example.orderingsystem.model.Catalog;
import com.example.orderingsystem.model.Product;
import com.example.orderingsystem.model.dto.StockProductForm;
import com.example.orderingsystem.repository.CatalogRepo;
import com.example.orderingsystem.repository.ProductRepo;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  public final ModelMapper mapper;
  private final ProductRepo productRepo;
  private final CatalogRepo catalogRepo;

  @Autowired
  public ProductService(ModelMapper modelMapper, ProductRepo productRepo, CatalogRepo catalogRepo) {
    this.mapper = modelMapper;
    this.productRepo = productRepo;
    this.catalogRepo = catalogRepo;
  }

  public Product getProductById(Long id) {
    return productRepo.findById(id).orElseThrow(NonExistingElementAtSpecifiedIdException::new);
  }

  public List<Product> getAllProducts() {
    return productRepo.findAll();
  }

  public void addNewProduct(Product product) {
    Optional<Product> duplicateProduct = productRepo.findAll().stream()
        .filter(p -> p.getName().equals(product.getName())).findAny();
    if (duplicateProduct.isPresent()) {
      throw new ProductAlreadyExistsException();
    }

    productRepo.save(product);
  }

  public void addNewProductToCatalog(Long id, Integer quantity) {
    Catalog catalog = new Catalog();
    catalog.setProduct(getProductById(id));
    catalog.setQuantity(quantity);

    Optional<Catalog> duplicateCatalog = catalogRepo.findAll().stream()
        .filter(c -> c.getProduct().getId().equals(id)).findAny();
    if (duplicateCatalog.isPresent()) {
      throw new ProductAlreadyExistsException();
    }
    catalogRepo.save(catalog);
  }

  public void deleteProductFromCatalog(Long id) {
    Catalog catalog = catalogRepo.findByProduct(
        productRepo.findById(id).orElseThrow(NonExistingElementAtSpecifiedIdException::new));
    catalogRepo.deleteById(catalog.getId());
  }

  public StockProductForm getProductStockInfo(Long id) {
    Catalog catalog = catalogRepo.findById(id)
        .orElseThrow(NonExistingElementAtSpecifiedIdException::new);
    StockProductForm stockProductForm = mapper.map(catalog, StockProductForm.class);
    Product productByCatalog = catalog.getProduct();
    stockProductForm.setId(productByCatalog.getId());
    stockProductForm.setName(productByCatalog.getName());
    stockProductForm.setDescription(productByCatalog.getDescription());

    return stockProductForm;
  }

  public StockProductForm increaseAvailableItems(Long id, Integer availableItems) {
    Catalog catalog = catalogRepo.findById(id)
        .orElseThrow(NonExistingElementAtSpecifiedIdException::new);
    Product productByCatalog = catalog.getProduct();
    catalog.addQuantity(availableItems);
    StockProductForm stockProductForm = mapper.map(productByCatalog, StockProductForm.class);
    catalogRepo.save(catalog);
    stockProductForm.setQuantity(catalog.getQuantity());

    return stockProductForm;
  }

}
