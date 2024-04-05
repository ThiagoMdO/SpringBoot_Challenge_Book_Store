package com.thiagomdo.ba.challenge.msproducts.services;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public ProductDTO updateProduct(String id, ProductDTO dtoRequest){

        ProductDTO testProductId = findById(id); //NOME = SAPIENS

        Product testProductExist = productRepository.findByName(dtoRequest.getName()); // Alerta vermelho
        if (dtoRequest.getDescription().length() < 10) throw new MinDescriptionException();

        if (testProductExist != null && !Objects.equals(testProductId.getName(), testProductExist.getName())){
            throw new ProductAlreadyExistException();
        }

        if (dtoRequest.getValue() == null || dtoRequest.getValue() < 0) throw new MinValueException();

        Product productInDB = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        productInDB = ConvertDTORequestToRepository(productInDB, dtoRequest);

        productRepository.save(productInDB);

        return new ProductDTO(productInDB);
    }

    public static Product ConvertDTORequestToRepository(Product dtoRepository, ProductDTO dtoRequest){
        dtoRepository.setName(dtoRequest.getName());
        dtoRepository.setDescription(dtoRequest.getDescription());
        dtoRepository.setValue(dtoRequest.getValue());
        return dtoRepository;
    }

    public void deleteProduct(String id){
        findById(id);
        productRepository.deleteById(id);
    }

}

