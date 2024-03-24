package com.thiagomdo.ba.challenge.msproducts.services;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.exception.EmptyListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAll(){
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
    }
}
