package com.thiagomdo.ba.challenge.msproducts.model.dto;

import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private Double value;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ProductDTO (Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.value = product.getValue();
    }

}
