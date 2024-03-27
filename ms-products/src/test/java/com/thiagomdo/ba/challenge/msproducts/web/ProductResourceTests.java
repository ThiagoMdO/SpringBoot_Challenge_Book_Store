package com.thiagomdo.ba.challenge.msproducts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.resources.ProductResource;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msproducts.common.ProductConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductResource.class)
class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void findAllProducts_With_ValidData_ReturnsProductList_Status200() throws Exception {

        when(productService.findAll()).thenReturn(PRODUCT_DTO_LIST);

        mockMvc.perform(
                get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(PRODUCT_DTO_LIST.get(0).getId()))
                    .andExpect(jsonPath("$[0].name").value(PRODUCT_DTO_LIST.get(0).getName()))
                    .andExpect(jsonPath("$[0].description").value(PRODUCT_DTO_LIST.get(0).getDescription()))
                    .andExpect(jsonPath("$[0].value").value(PRODUCT_DTO_LIST.get(0).getValue()))
                    .andExpect(jsonPath("$[1].id").value(PRODUCT_DTO_LIST.get(1).getId()))
                    .andExpect(jsonPath("$[1].name").value(PRODUCT_DTO_LIST.get(1).getName()))
                    .andExpect(jsonPath("$[1].description").value(PRODUCT_DTO_LIST.get(1).getDescription()))
                    .andExpect(jsonPath("$[1].value").value(PRODUCT_DTO_LIST.get(1).getValue()));
    }

    @Test
    void findAllProducts_ReturnsNoProduct_Status200() throws Exception{
        when(productService.findAll()).thenThrow(new EmptyListException());

        mockMvc.perform(
            get("/products"))
        .andExpect(status().isOk());
    }

    @Test
    void findProductById_With_ValidData_ReturnsProductDTO_Status200() throws Exception{
        when(productService.findById("asdaf2")).thenReturn(PRODUCT_DTO);

        mockMvc
            .perform(
                get("/products/asdaf2"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(PRODUCT_DTO)));

    }

    @Test
    void findProductById_ByUnexistingId_ThrowsProductNotFoundException_Status404(){
        when(productService.findById("IncorrectIdProduct")).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.findById("IncorrectIdProduct"));
    }

    @Test
    void createProduct_With_ValidData_ReturnsProductDTO_Status201() throws Exception{
        when(productService.createProduct(PRODUCT_DTO)).thenReturn(PRODUCT_DTO);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(PRODUCT_DTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void createProduct_With_NameAlreadyExist_ThrowsProductAlreadyExistException_Status409(){
        when(productService.createProduct(PRODUCT_DTO)).thenThrow(ProductAlreadyExistException.class);

        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(PRODUCT_DTO));
    }

    @Test
    void createProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException_Status400(){
        when(productService.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO)).thenThrow(MinDescriptionException.class);

        assertThrows(MinDescriptionException.class, () -> productService.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
    }
    @Test
    void createProduct_With_ValueLessThanZero_ThrowsMinValueException_Status400(){
        when(productService.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO)).thenThrow(MinValueException.class);

        assertThrows(MinValueException.class, () -> productService.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO));
    }

    @Test
    void updateProduct_With_ValidData_ReturnsProductDTO_Status200() throws Exception{
        when(productService.updateProduct(PRODUCT.getId(), PRODUCT_DTO)).thenReturn(PRODUCT_DTO);

        mockMvc.perform(put("/products/asdaf2")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(PRODUCT_DTO)))
        .andExpect(status().isOk());
    }

    @Test
    void updateProduct_With_IdProductNotFound_ThrowsProductNotFoundException_Status404() throws Exception{
        when(productService.updateProduct("IncorrectIdProduct", PRODUCT_DTO)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, ()-> productService.updateProduct("IncorrectIdProduct", PRODUCT_DTO));
    }

    @Test
    void updateProduct_With_NameInUsingForAnotherProduct_ThrowsProductAlreadyExistException_Status409(){
        when(productService.updateProduct("dasfx3", PRODUCT_DTO)).thenThrow(ProductAlreadyExistException.class);

        assertThrows(ProductAlreadyExistException.class, () -> productService.updateProduct("dasfx3", PRODUCT_DTO));
    }

    @Test
    void updateProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException_Status400() {
        when(productService.updateProduct("dasfx3", PRODUCT_DESCRIPTION_LESS_TEEN_DTO)).thenThrow(MinDescriptionException.class);

        assertThrows(MinDescriptionException.class, () -> productService.updateProduct("dasfx3", PRODUCT_DESCRIPTION_LESS_TEEN_DTO));
    }

    @Test
    void updateProduct_With_ValueIsNull_ThrowsMinDescriptionException_Status400(){
        when(productService.updateProduct("dasfx3", PRODUCT_VALUE_IS_NULL_DTO)).thenThrow(MinValueException.class);

        assertThrows(MinValueException.class, () -> productService.updateProduct("dasfx3", PRODUCT_VALUE_IS_NULL_DTO));
    }

    @Test
    void updateProduct_With_ValueLessThanZero_ThrowsMinValueException(){
        when(productService.updateProduct("dasfx3", PRODUCT_VALUE_LESS_ZERO_DTO)).thenThrow(MinValueException.class);

        assertThrows(MinValueException.class, () -> productService.updateProduct("dasfx3", PRODUCT_VALUE_LESS_ZERO_DTO));
    }

}
