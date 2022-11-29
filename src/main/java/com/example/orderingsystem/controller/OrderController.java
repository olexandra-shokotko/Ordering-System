package com.example.orderingsystem.controller;

import com.example.orderingsystem.exception.NotEnoughAvailableItemsException;
import com.example.orderingsystem.model.Catalog;
import com.example.orderingsystem.model.Order;
import com.example.orderingsystem.model.enums.Status;
import com.example.orderingsystem.service.CatalogService;
import com.example.orderingsystem.service.OrderService;
import java.util.List;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

  private final CatalogService catalogService;
  private final OrderService orderService;

  @Autowired
  public OrderController(CatalogService catalogService, OrderService orderService) {
    this.catalogService = catalogService;
    this.orderService = orderService;
  }

  @PostMapping("/{catalogId}")
  public ResponseEntity<Order> addNewOrder(@PathVariable Long catalogId,
      @RequestParam @Min(0) Integer quantity) {
    Catalog catalog = catalogService.getCatalogById(catalogId);
    if (catalog.getQuantity() < quantity) {
      throw new NotEnoughAvailableItemsException();
    }
    Order order = new Order(quantity, Status.NEW);
    order.addCatalog(catalog);
    orderService.addNewOrder(order);
    catalog.decreaseQuantity(quantity);
    catalogService.saveCatalog(catalog);
    return ResponseEntity.ok(order);
  }

  @GetMapping("")
  public ResponseEntity<List<Order>> showAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> showOrderById(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOrderById(orderId));
  }
}
