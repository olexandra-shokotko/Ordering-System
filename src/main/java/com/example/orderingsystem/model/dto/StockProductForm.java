package com.example.orderingsystem.model.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockProductForm implements Serializable {

  @NotEmpty
  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotEmpty
  private Integer quantity;

}
