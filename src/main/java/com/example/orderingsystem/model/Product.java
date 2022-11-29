package com.example.orderingsystem.model;

import com.example.orderingsystem.model.enums.Type;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Type type;
  private String name;
  private String description;

  public Product(Type type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }

}
