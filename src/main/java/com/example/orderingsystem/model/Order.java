package com.example.orderingsystem.model;

import com.example.orderingsystem.model.enums.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Integer quantity;
  private Status status;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "order_catalog",
      joinColumns = @JoinColumn(name = "id"),
      inverseJoinColumns = @JoinColumn(name = "catalog_id")
  )
  private Set<Catalog> catalogs = new HashSet<>();

  public Order(Integer quantity, Status status,
      Set<Catalog> catalogs) {
    this.quantity = quantity;
    this.status = status;
    this.catalogs = catalogs;
  }

  public Order(Integer quantity, Status status) {
    this.quantity = quantity;
    this.status = status;
  }

  public void addCatalog(Catalog catalog) {
    this.catalogs.add(catalog);
    catalog.getOrders().add(this);
  }
}
