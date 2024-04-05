package com.thiagomdo.ba.challenge.msproducts.service;


import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import com.thiagomdo.ba.challenge.msproducts.repository.ProductRepository;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

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
    @DisplayName("findAll")
    void findAll_With_EmptyList_ThrowsEmptyListException(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> productService.findAll());
        verify(productRepository).findAll();
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

        assertThat(result).isNotNull();
        assertThat(result.getDescription().length()).isGreaterThan(10);
        assertThat(result.getValue()).isNotNegative();
        assertThat(result).isEqualTo(O_CORACAO_DO_MUNDO_BOOK_DTO);
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void createProduct_With_NameAlreadyExist_ThrowsProductAlreadyExistException(){
        when(productRepository.findByName(O_CORACAO_DO_MUNDO_BOOK_DTO.getName())).thenReturn(O_CORACAO_DO_MUNDO_BOOK);

        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(O_CORACAO_DO_MUNDO_BOOK_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void createProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){
        ProductService productServiceMock = mock(ProductService.class);
        when(productServiceMock.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO)).thenThrow(MinDescriptionException.class);

        assertThat(PRODUCT_DESCRIPTION_LESS_TEEN.getDescription().length()).isLessThan(10);
        assertThrows(MinDescriptionException.class, () -> productServiceMock.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void createProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        ProductService productServiceMock = mock(ProductService.class);
        when(productServiceMock.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO)).thenThrow(MinValueException.class);

        assertThat(PRODUCT_VALUE_LESS_ZERO_DTO.getValue()).isNegative();
        assertThrows(MinValueException.class, () -> productServiceMock.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void updateProduct_With_ValidData_ReturnsProductDTO(){
        when(productRepository.findById(ALERTA_VERMELHO_BOOK_DTO.getId())).thenReturn(Optional.of(ALERTA_VERMELHO_BOOK));
        when(productRepository.findByName("AvailableName")).thenReturn(null);
        when(productRepository.findByName(SAPIENS_BOOK_DTO.getName())).thenReturn(SAPIENS_BOOK);
        when(productRepository.save(any(Product.class))).thenReturn(ALERTA_VERMELHO_BOOK);

        Product testAvailableName = productRepository.findByName("AvailableName");
        Product testDifferentName = productRepository.findByName(SAPIENS_BOOK_DTO.getName());
        ProductDTO result = productService.updateProduct(ALERTA_VERMELHO_BOOK.getId(), ALERTA_VERMELHO_BOOK_DTO);

        assertThat(testAvailableName).isNull();

        assertThat(testDifferentName).isNotNull();
        assertThat(testDifferentName.getName()).isNotEqualTo(result.getName());

        assertThat(result).isNotNull();
        assertThat(result.getDescription().length()).isGreaterThan(10);
        assertThat(result.getValue()).isNotNegative();
        assertThat(result).isEqualTo(ALERTA_VERMELHO_BOOK_DTO);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_With_IdProductNotFound_ThrowsProductNotFoundException(){
        when(productRepository.findById("IdNotValidProduct")).thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct("IdNotValidProduct", PRODUCT_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void updateProduct_With_NameInUsingForAnotherProduct_ThrowsProductAlreadyExistException(){
        when(productRepository.findById(SAPIENS_BOOK_DTO.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(ALERTA_VERMELHO_BOOK.getName())).thenReturn(ALERTA_VERMELHO_BOOK);

        assertThrows(ProductAlreadyExistException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), ALERTA_VERMELHO_BOOK_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void updateProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(any(String.class))).thenReturn(null);
        when(productRepository.findByName(PRODUCT_DESCRIPTION_LESS_TEEN.getName())).thenReturn(PRODUCT_DESCRIPTION_LESS_TEEN);

        assertThrows(MinDescriptionException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_DESCRIPTION_LESS_TEEN_AVAILABLE_NAME_DTO));
        assertThrows(MinDescriptionException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void updateProduct_With_ValueIsNull_Throws_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(any(String.class))).thenReturn(null);
        when(productRepository.findByName(PRODUCT_VALUE_IS_NULL_DTO.getName())).thenReturn(PRODUCT_VALUE_IS_NULL);

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_IS_NULL_AVAILABLE_NAME_DTO));
        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_IS_NULL_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void updateProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(any(String.class))).thenReturn(null);
        when(productRepository.findByName(PRODUCT_VALUE_LESS_ZERO_DTO.getName())).thenReturn(PRODUCT_VALUE_LESS_ZERO);

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_LESS_ZERO_AVAILABLE_NAME_DTO));
        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_LESS_ZERO_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    void deleteProduct_With_IdProductExistingInDB_ReturnsVoid(){
        when(productRepository.findById("asdaf2")).thenReturn(Optional.of(PRODUCT));

        productService.deleteProduct("asdaf2");

        verify(productRepository).findById("asdaf2");
        verify(productRepository).deleteById("asdaf2");
    }

    @Test
    void deleteProduct_With_IdProductUnexistingInDB_ThrowsProductNotFoundException(){
        when(productRepository.findById("IncorrectId")).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("IncorrectId"));
        verify(productRepository, never()).deleteById(any(String.class));
    }
}
