package com.thiagomdo.ba.challenge.msproducts.services;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return new ProductDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        builderCreateProduct(productDTO);

        return new ProductDTO(productRepository.save(new Product(productDTO)));
    }

    public ProductDTO updateProduct(String id, ProductDTO dtoRequest) {
        testsUpdateProduct(id, dtoRequest);

        Product product = productRepository.save(new Product(id, dtoRequest));

        return new ProductDTO(product);
    }

    public void deleteProduct(String id) {
        findById(id);
        productRepository.deleteById(id);
    }

    private void builderCreateProduct(ProductDTO productDTO) {
        testProductAlreadyExistingInCreate(productDTO);

        testFieldsRequestCorrect(productDTO);
    }

    private void testFieldsRequestCorrect(ProductDTO productDTO) {
        if (productDTO.getDescription().length() < 10) throw new MinDescriptionException();
        if (productDTO.getValue() == null || productDTO.getValue() < 0) throw new MinValueException();
    }

    private void testProductAlreadyExistingInCreate(ProductDTO productDTO) {
        Product search = productRepository.findByName(productDTO.getName());

        if (search != null) throw new ProductAlreadyExistException();
    }

    private void testsUpdateProduct(String id, ProductDTO productDTO) {
        testProductAlreadyExistingInUpdate(id, productDTO);

        testFieldsRequestCorrect(productDTO);
    }

    private void testProductAlreadyExistingInUpdate(String id, ProductDTO productDTO) {
        ProductDTO testProductId = findById(id);
        Product testProductExist = productRepository.findByName(productDTO.getName());

        if (testProductExist != null && !Objects.equals(testProductId.getName(), testProductExist.getName())) {
            throw new ProductAlreadyExistException();
        }
    }

}

