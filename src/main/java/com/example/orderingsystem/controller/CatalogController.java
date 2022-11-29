package com.example.orderingsystem.controller;

import com.example.orderingsystem.model.Catalog;
import com.example.orderingsystem.model.Product;
import com.example.orderingsystem.model.dto.StockProductForm;
import com.example.orderingsystem.service.CatalogService;
import com.example.orderingsystem.service.ProductService;
import java.util.List;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/catalog")
public class CatalogController {

  private final ProductService productService;
  private final CatalogService catalogService;

  @Autowired
  public CatalogController(ProductService productService,
      CatalogService catalogService) {
    this.productService = productService;
    this.catalogService = catalogService;
  }

  @PostMapping("/{id}")
  public ResponseEntity<Product> addProductToCatalog(@PathVariable Long id,
      @RequestParam @Min(0) Integer quantity) {

    productService.addNewProductToCatalog(id, quantity);
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @GetMapping("/products")
  public ResponseEntity<List<Catalog>> getProductsFromCatalog() {
    return ResponseEntity.ok(catalogService.getAllProductsInCatalog());
  }

  @DeleteMapping("/{catalogId}")
  public ResponseEntity<Product> deleteProductFromCatalog(@PathVariable Long catalogId) {
    Product product = catalogService.getCatalogById(catalogId).getProduct();
    productService.deleteProductFromCatalog(product.getId());
    return ResponseEntity.ok(product);
  }

  @GetMapping("/stock-info/{catalogId}")
  public ResponseEntity<StockProductForm> productStockInfo(@PathVariable Long catalogId) {
    return ResponseEntity.ok(productService.getProductStockInfo(catalogId));
  }

  @PostMapping("/increase-available-items/{catalogId}")
  public ResponseEntity<StockProductForm> increaseAvailableItems(@PathVariable Long catalogId,
      @RequestParam @Min(0) Integer availableItems) {

    StockProductForm stockProductForm = productService
        .increaseAvailableItems(catalogId, availableItems);
    return ResponseEntity.ok(stockProductForm);
  }
}
