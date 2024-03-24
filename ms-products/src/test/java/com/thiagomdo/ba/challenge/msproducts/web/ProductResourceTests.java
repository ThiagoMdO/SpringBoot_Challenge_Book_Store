package com.thiagomdo.ba.challenge.msproducts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msproducts.resources.ProductResource;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msproducts.services.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msproducts.common.ProductConstants.PRODUCT_DTO;
import static com.thiagomdo.ba.challenge.msproducts.common.ProductConstants.PRODUCT_DTO_LIST;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void findAllProducts_With_ValidData_ReturnsProductList() throws Exception {

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
                    .andExpect(jsonPath("$[1].value").value(PRODUCT_DTO_LIST.get(1).getValue()));;
    }

    @Test
    void findAllProducts_ReturnsNoProduct() throws Exception{
        when(productService.findAll()).thenThrow(new EmptyListException());

        mockMvc.perform(
            get("/products"))
        .andExpect(status().isOk());
    }

    @Test
    void findProductById_With_ValidData_ReturnsProductDTO() throws Exception{
        when(productService.findById("asdaf2")).thenReturn(PRODUCT_DTO);

        mockMvc
            .perform(
                get("/products/asdaf2"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(PRODUCT_DTO)));

    }

    @Test
    void findProductById_ByUnexistingId_ThrowsProductNotFoundException(){
        when(productService.findById("1234sdd")).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.findById("1234sdd"));
    }
}
