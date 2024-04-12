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
import org.springframework.context.annotation.Description;

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
    @DisplayName("FindAll: ValidData > ReturnsProductDTOList")
    @Description("Tests if the findAll() method of the product service correctly retrieves a list of product DTOs " +
    "when product data is available in the database.")
    void findAll_With_ValidData_ReturnsProductDTOList(){
        when(productRepository.findAll()).thenReturn(PRODUCT_LIST);

        List<ProductDTO> result = productService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyElementsOf(PRODUCT_DTO_LIST);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("FindAll: EmptyList > ThrowsEmptyListException")
    @Description("Tests if the findAll() method of the product service correctly throws an EmptyListException " +
    "when no product data is available in the database.")
    void findAll_With_EmptyList_ThrowsEmptyListException(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> productService.findAll());
        verify(productRepository).findAll();
    }


    @Test
    @DisplayName("FindById: ValidData > ReturnsProductDTO")
    @Description("Tests if the findById() method of the product service correctly retrieves a product DTO " +
    "when a valid product ID is provided.")
    void findById_With_ValidData_ReturnsProductDTO(){
        when(productRepository.findById(PRODUCT.getId())).thenReturn(Optional.of(PRODUCT));

        ProductDTO result = productService.findById(PRODUCT.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PRODUCT_DTO);
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("FindById: ByUnexistingId > ThrowsProductNotFoundException")
    @Description("Tests if the findById() method of the product service correctly throws a ProductNotFoundException " +
    "when an unexisting product ID is provided.")
    void findById_ByUnexistingId_ThrowsProductNotFoundException(){
        final String unexistingId = "Id_invalid";
        when(productRepository.findById(unexistingId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById(unexistingId));
    }

    @Test
    @DisplayName("CreateProduct: ValidData > ReturnsProductDTO")
    @Description("Tests if the createProduct() method of the product service correctly creates and returns a product DTO " +
    "when valid product data is provided.")
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
    @DisplayName("CreateProduct: NameAlreadyExist > ThrowsProductAlreadyExistException")
    @Description("Tests if the createProduct() method of the product service correctly throws a ProductAlreadyExistException " +
    "when attempting to create a product with a name that already exists.")
    void createProduct_With_NameAlreadyExist_ThrowsProductAlreadyExistException(){
        when(productRepository.findByName(O_CORACAO_DO_MUNDO_BOOK_DTO.getName())).thenReturn(O_CORACAO_DO_MUNDO_BOOK);

        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(O_CORACAO_DO_MUNDO_BOOK_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
    }

    @Test
    @DisplayName("CreateProduct: DescriptionLengthLessThanTen > ThrowsMinDescriptionException")
    @Description("Tests if the createProduct() method of the product service correctly throws a MinDescriptionException " +
    "when attempting to create a product with a description length less than ten.")
    void createProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){

        assertThrows(MinDescriptionException.class, () -> productService.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
        verify(productRepository, times(1)).findByName(PRODUCT_DESCRIPTION_LESS_TEEN_DTO.getName());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("CreateProduct: ValueLessThanZero > ThrowsMinValueException")
    @Description("Tests if the createProduct() method of the product service correctly throws a MinValueException " +
    "when attempting to create a product with a value less than zero.")
    void createProduct_With_ValueLessThanZero_ThrowsMinValueException(){

        assertThrows(MinValueException.class, () -> productService.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO));
        verify(productRepository, times(1)).findByName(PRODUCT_VALUE_LESS_ZERO_DTO.getName());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("UpdateProduct: ValidData > ReturnsProductDTO")
    @Description("Tests if the updateProduct() method of the product service correctly updates and returns a product DTO " +
    "when valid product data is provided.")
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
    @DisplayName("UpdateProduct: IdProductNotFound > ThrowsProductNotFoundException")
    @Description("Tests if the updateProduct() method of the product service correctly throws a ProductNotFoundException " +
    "when an invalid product ID is provided.")
    void updateProduct_With_IdProductNotFound_ThrowsProductNotFoundException(){
        when(productRepository.findById("IdNotValidProduct")).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct("IdNotValidProduct", PRODUCT_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("UpdateProduct: NameInUsingForAnotherProduct > ThrowsProductAlreadyExistException")
    @Description("Tests if the updateProduct() method of the product service correctly throws a ProductAlreadyExistException " +
    "when attempting to update a product with a name that is already in use by another product.")
    void updateProduct_With_NameInUsingForAnotherProduct_ThrowsProductAlreadyExistException(){
        when(productRepository.findById(SAPIENS_BOOK_DTO.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(ALERTA_VERMELHO_BOOK.getName())).thenReturn(ALERTA_VERMELHO_BOOK);

        assertThrows(ProductAlreadyExistException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), ALERTA_VERMELHO_BOOK_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
        verify(productRepository, times(1)).findById(any());
        verify(productRepository, times(1)).findByName(any());
    }

    @Test
    @DisplayName("UpdateProduct: DescriptionLengthLessThanTen > ThrowsMinDescriptionException")
    @Description("Tests if the updateProduct() method of the product service correctly throws a MinDescriptionException " +
    "when attempting to update a product with a description length less than ten.")
    void updateProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName("AVAILABLE_NAME_DTO")).thenReturn(null);
        when(productRepository.findByName(PRODUCT_DESCRIPTION_LESS_TEEN.getName())).thenReturn(PRODUCT_DESCRIPTION_LESS_TEEN);

        assertThrows(MinDescriptionException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_DESCRIPTION_LESS_TEEN_AVAILABLE_NAME_DTO));
        assertThrows(MinDescriptionException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
        verify(productRepository, times(2)).findById(any());
        verify(productRepository, times(2)).findByName(any());
    }

    @Test
    @DisplayName("UpdateProduct: ValueIsNull > ThrowsMinValueException")
    @Description("Tests if the updateProduct() method of the product service correctly throws a MinValueException " +
    "when attempting to update a product with a null value.")
    void updateProduct_With_ValueIsNull_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName("AVAILABLE_NAME_DTO")).thenReturn(null);
        when(productRepository.findByName(PRODUCT_VALUE_IS_NULL_DTO.getName())).thenReturn(PRODUCT_VALUE_IS_NULL);

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_IS_NULL_AVAILABLE_NAME_DTO));
        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_IS_NULL_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
        verify(productRepository, times(2)).findById(any());
        verify(productRepository, times(2)).findByName(any());
    }

    @Test
    @DisplayName("UpdateProduct: ValueLessThanZero > ThrowsMinValueException")
    @Description("Tests if the updateProduct() method of the product service correctly throws a MinValueException " +
    "when attempting to update a product with a value less than zero.")
    void updateProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        when(productRepository.findById(SAPIENS_BOOK.getId())).thenReturn(Optional.of(SAPIENS_BOOK));
        when(productRepository.findByName(PRODUCT_VALUE_LESS_ZERO_AVAILABLE_NAME_DTO.getName())).thenReturn(null);
        when(productRepository.findByName(PRODUCT_VALUE_LESS_ZERO_DTO.getName())).thenReturn(PRODUCT_VALUE_LESS_ZERO);

        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_LESS_ZERO_AVAILABLE_NAME_DTO));
        assertThrows(MinValueException.class, () -> productService.updateProduct(SAPIENS_BOOK_DTO.getId(), PRODUCT_VALUE_LESS_ZERO_DTO));
        verify(productRepository, never()).save(O_CORACAO_DO_MUNDO_BOOK);
        verify(productRepository, times(2)).findById(any());
        verify(productRepository, times(2)).findByName(any());
    }

    @Test
    @DisplayName("DeleteProduct: IdProductExistingInDB > ReturnsVoid")
    @Description("Tests if the deleteProduct() method of the product service correctly deletes a product " +
    "when a valid product ID is provided.")
    void deleteProduct_With_IdProductExistingInDB_ReturnsVoid(){
        when(productRepository.findById(PRODUCT.getId())).thenReturn(Optional.of(PRODUCT));

        productService.deleteProduct(PRODUCT.getId());

        verify(productRepository).findById(PRODUCT.getId());
        verify(productRepository).deleteById(PRODUCT.getId());
        verify(productRepository, times(1)).deleteById(any());
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("DeleteProduct: IdProductUnexistingInDB > ThrowsProductNotFoundException")
    @Description("Tests if the deleteProduct() method of the product service correctly throws a ProductNotFoundException when attempting to delete a product with an invalid product ID.")
    void deleteProduct_With_IdProductUnexistingInDB_ThrowsProductNotFoundException(){
        when(productRepository.findById("IncorrectId")).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("IncorrectId"));
        verify(productRepository, never()).deleteById(any(String.class));
        verify(productRepository, times(1)).findById("IncorrectId");
    }
}
