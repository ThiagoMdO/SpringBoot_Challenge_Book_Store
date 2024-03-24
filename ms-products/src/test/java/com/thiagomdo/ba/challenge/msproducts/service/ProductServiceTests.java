package com.thiagomdo.ba.challenge.msproducts.service;


import com.thiagomdo.ba.challenge.msproducts.common.ProductConstants;
import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.EmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;


    @Test
    void getAllProducts_With_ValidData_ReturnsProductList(){
        when(productRepository.findAll()).thenReturn(ProductConstants.PRODUCT_LIST);

        List<ProductDTO> result = productService.findAll();

        assertThat(result).isNotNull();

        assertThat(result).containsExactlyElementsOf(ProductConstants.PRODUCT_DTO_LIST);
    }

    @Test
    void getListProducts_ReturnsNoProducts(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> productService.findAll());
    }


}
