package com.thiagomdo.ba.challenge.msproducts.model.dto;

import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private Double value;

    public ProductDTO (Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.value = product.getValue();
    }

}
