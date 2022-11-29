package com.example.orderingsystem.model;

import com.example.orderingsystem.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "catalogs")
public class Catalog implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  private Integer quantity;

  @JsonBackReference
  @ManyToMany(mappedBy = "catalogs")
  private Set<Order> orders;

  public Catalog(Product product, Integer quantity,
      Set<Order> orders) {
    this.product = product;
    this.quantity = quantity;
    this.orders = orders;
  }

  public void setQuantity(Integer quantity) {
    if (quantity == 0) {
      product.setType(Type.UNAVAILABLE);
    }
    this.quantity = quantity;
  }

  public void addQuantity(Integer addendum) {
    this.quantity = this.quantity + addendum;
  }

  public void decreaseQuantity(Integer decreaseNum) {
    this.quantity = this.getQuantity() - decreaseNum;
  }

}
