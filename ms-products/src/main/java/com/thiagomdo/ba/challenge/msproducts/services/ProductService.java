package com.thiagomdo.ba.challenge.msproducts.services;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAll() {
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
    }

    public ProductDTO findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ProductNotFoundException();
        return new ProductDTO(product.get());
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product result = productRepository.findByName(productDTO.getName());

        if (result != null) throw new ProductAlreadyExistException();
        if (productDTO.getDescription().length() < 10) throw new MinDescriptionException();
        if (productDTO.getValue() < 0) throw new MinValueException();

        result = productRepository.save(new Product(productDTO));

        return new ProductDTO(result);
    }
}
