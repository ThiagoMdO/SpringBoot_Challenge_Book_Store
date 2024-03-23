package com.thiagomdo.ba.challenge.msproducts.services;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAll(){
        List<Product> list = productRepository.findAll();
        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
    }
}
