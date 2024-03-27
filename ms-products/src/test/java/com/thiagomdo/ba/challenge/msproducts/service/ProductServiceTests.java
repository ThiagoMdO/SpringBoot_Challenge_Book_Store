package com.thiagomdo.ba.challenge.msproducts.service;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
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
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void createProduct_With_ValidData_ReturnsProductDTO(){
        when(productRepository.findByName(O_CORACAO_DO_MUNDO_BOOK_DTO.getName())).thenReturn(null);

        when(productRepository.save(any(Product.class))).thenReturn(O_CORACAO_DO_MUNDO_BOOK);

        ProductDTO result = productService.createProduct(O_CORACAO_DO_MUNDO_BOOK_DTO);

        assertThat(result).isEqualTo(O_CORACAO_DO_MUNDO_BOOK_DTO);
    }

    @Test
    void createProduct_With_NameAlreadyExist_ThrowsProductAlreadyExistException(){
        when(productRepository.findByName(O_CORACAO_DO_MUNDO_BOOK_DTO.getName())).thenReturn(O_CORACAO_DO_MUNDO_BOOK);

        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(O_CORACAO_DO_MUNDO_BOOK_DTO));
    }

    @Test
    void createProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){
        assertThrows(MinDescriptionException.class, () -> productService.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO));

    }

    @Test
    void createProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        assertThrows(MinValueException.class, () -> productService.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO));

    }

    @Test
    void updateProduct_With_ValidData_ReturnsProductDTO(){
        when(productRepository.findById(ALERTA_VERMELHO_BOOK_DTO.getId())).thenReturn(Optional.of(ALERTA_VERMELHO_BOOK));

        when(productRepository.save(any(Product.class))).thenReturn(ALERTA_VERMELHO_BOOK);

        ProductDTO result = productService.updateProduct(ALERTA_VERMELHO_BOOK.getId(), ALERTA_VERMELHO_BOOK_DTO);

        assertThat(result).isNotNull();

        assertThat(result).isEqualTo(ALERTA_VERMELHO_BOOK_DTO);

    }

    @Test
    void updateProduct_With_IdProductNotFound_ThrowsProductNotFoundException(){
        when(productRepository.findById("IdNotValidProduct")).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> productRepository.findById("IdNotValidProduct"));
    }

    @Test
    void updateProduct_With_NameInUsingForAnotherProduct_ThrowsProductAlreadyExistException(){
        when(productRepository.findById(ALERTA_VERMELHO_BOOK.getId())).thenReturn(Optional.of(ALERTA_VERMELHO_BOOK));

        when(productRepository.findByName("Sapiens, Uma breve história da humanidade")).thenReturn(SAPIENS_BOOK);

        assertThat(SAPIENS_BOOK.getName()).isNotNull();

        assertThat(SAPIENS_BOOK.getName()).isNotEqualTo(ALERTA_VERMELHO_BOOK.getName());

        assertThrows(ProductAlreadyExistException.class, () -> productService.updateProduct(ALERTA_VERMELHO_BOOK.getId(), SAPIENS_BOOK_DTO));
    }

    @Test
    void updateProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));

        assertThrows(MinDescriptionException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
    }

    @Test
    void updateProduct_With_ValueIsNull_Throws_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_IS_NULL_DTO));
    }

    @Test
    void updateProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_LESS_ZERO_DTO));
    }

}
