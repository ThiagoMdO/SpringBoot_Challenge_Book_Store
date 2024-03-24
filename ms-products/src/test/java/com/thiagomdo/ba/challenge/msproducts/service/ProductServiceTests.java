package com.thiagomdo.ba.challenge.msproducts.service;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msproducts.services.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.thiagomdo.ba.challenge.msproducts.common.ProductConstants.*;
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
    void findAll_With_ValidData_ReturnsProductDTOList(){
        when(productRepository.findAll()).thenReturn(PRODUCT_LIST);

        List<ProductDTO> result = productService.findAll();

        assertThat(result).isNotNull();

        assertThat(result).containsExactlyElementsOf(PRODUCT_DTO_LIST);
    }

    @Test
    void findAll_ThrowsEmptyListException(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> productService.findAll());
    }


    @Test
    void findById_With_ValidData_ReturnsProductDTO(){
        when(productRepository.findById(PRODUCT.getId())).thenReturn(Optional.of(PRODUCT));

        ProductDTO result = productService.findById(PRODUCT.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PRODUCT_DTO);
    }

    @Test
    void findById_ByUnexistingId_ThrowsProductNotFoundException(){
        final String unexistingId = "1234qwer";
        when(productRepository.findById(unexistingId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(unexistingId));
    }
}