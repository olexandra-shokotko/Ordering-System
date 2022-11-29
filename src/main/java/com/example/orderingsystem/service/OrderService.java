package com.example.orderingsystem.service;

import com.example.orderingsystem.exception.NonExistingElementAtSpecifiedIdException;
import com.example.orderingsystem.model.Order;
import com.example.orderingsystem.repository.OrderRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepo orderRepo;

  @Autowired
  public OrderService(OrderRepo orderRepo) {
    this.orderRepo = orderRepo;
  }

  public void addNewOrder(Order order) {
    orderRepo.save(order);
  }

  public List<Order> getAllOrders() {
    return orderRepo.findAll();
  }

  public Order getOrderById(Long id) {
    return orderRepo.findById(id).orElseThrow(NonExistingElementAtSpecifiedIdException::new);
  }
}
