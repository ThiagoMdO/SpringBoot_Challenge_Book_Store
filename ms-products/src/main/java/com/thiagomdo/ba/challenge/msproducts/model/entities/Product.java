package com.thiagomdo.ba.challenge.msproducts.model.entities;

import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Document
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    @NotBlank
    @Size(min = 10, message = "The field must have at least 10 characters")
    private String description;

    @NotNull
    @Min(0)
    private Double value;

    public Product(ProductDTO productDTO){
        this.name = productDTO.getName();
        this.description = productDTO.getDescription();
        this.value = productDTO.getValue();
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
}
