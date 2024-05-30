package com.thiagomdo.ba.challenge.msproducts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msproducts.resources.ProductResourceImpl;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import com.thiagomdo.ba.challenge.msproducts.services.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msproducts.common.ProductConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductResourceImpl.class)
class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("FindAllProducts: ValidData > ReturnsProductList :: Status200")
    @Description("Tests if the GET endpoint '/products' returns a list of products with valid data, " +
            "verifying that it returns status code 200 (OK).")
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
    @DisplayName("FindAllProducts: ReturnsNoProduct > ThrowsEmptyListException :: Status200")
    @Description("Tests if the GET endpoint '/products' throws EmptyListException when there are no products, " +
            "verifying that it returns status code 200 (OK).")
    void findAllProducts_ReturnsNoProduct_ThrowsEmptyListException_Status200() throws Exception{
        when(productService.findAll()).thenThrow(new EmptyListException());

        mockMvc.perform(
            get("/products"))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("FindProductById: ValidData > ReturnsProductDTO :: Status200")
    @Description("Tests if the GET endpoint '/products/{id}' returns a product DTO with valid data when a valid ID is provided, " +
            "verifying that it returns status code 200 (OK).")
    void findProductById_With_ValidData_ReturnsProductDTO_Status200() throws Exception{
        when(productService.findById("asdaf2")).thenReturn(PRODUCT_DTO);

        mockMvc
            .perform(
                get("/products/asdaf2"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(PRODUCT_DTO)));
    }

    @Test
    @DisplayName("FindProductById: ByUnexistingId > ThrowsProductNotFoundException :: Status404")
    @Description("Tests if the GET endpoint '/products/{id}' throws ProductNotFoundException when an invalid ID is provided, " +
            "verifying that it returns status code 404 (Not Found).")
    void findProductById_ByUnexistingId_ThrowsProductNotFoundException_Status404() throws Exception{
        when(productService.findById("IncorrectIdProduct")).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get("/products/IncorrectIdProduct"))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("CreateProduct: ValidData > ReturnsProductDTO :: Status201")
    @Description("Tests if the POST endpoint '/products' creates a product with valid data and returns the created product DTO, " +
            "verifying that it returns status code 201 (Created).")
    void createProduct_With_ValidData_ReturnsProductDTO_Status201() throws Exception{
        when(productService.createProduct(PRODUCT_DTO)).thenReturn(PRODUCT_DTO);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PRODUCT_DTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("CreateProduct: NameAlreadyExist > ThrowsProductAlreadyExistException :: Status409")
    @Description("Tests if the POST endpoint '/products' throws ProductAlreadyExistException " +
            "when attempting to create a product with a name that already exists, " +
            "verifying that it returns status code 409 (Conflict).")
    void createProduct_With_NameAlreadyExist_ThrowsProductAlreadyExistException_Status409() throws Exception{
        when(productService.createProduct(PRODUCT_DTO)).thenThrow(ProductAlreadyExistException.class);

        mockMvc.perform(post("/products")
        .content(objectMapper.writeValueAsString(PRODUCT_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("CreateProduct: DescriptionLengthLessThanTen > ThrowsMinDescriptionException :: Status400")
    @Description("Tests if the POST endpoint '/products' throws MinDescriptionException " +
            "when attempting to create a product with a description length less than ten characters, " +
            "verifying that it returns status code 400 (Bad Request).")
    void createProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException_Status400() throws Exception{
        when(productService.createProduct(PRODUCT_DESCRIPTION_LESS_TEEN_DTO)).thenThrow(MinDescriptionException.class);

        mockMvc.perform(post("/products")
        .content(objectMapper.writeValueAsString(PRODUCT_DESCRIPTION_LESS_TEEN_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CreateProduct: ValueLessThanZero > ThrowsMinValueException :: Status400")
    @Description("Tests if the POST endpoint '/products' throws MinValueException " +
            "when attempting to create a product with a value less than zero, " +
            "verifying that it returns status code 400 (Bad Request).")
    void createProduct_With_ValueLessThanZero_ThrowsMinValueException_Status400() throws Exception{
        when(productService.createProduct(PRODUCT_VALUE_LESS_ZERO_DTO)).thenThrow(MinValueException.class);

        mockMvc.perform(post("/products")
        .content(objectMapper.writeValueAsString(PRODUCT_VALUE_LESS_ZERO_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("UpdateProduct: ValidData > ReturnsProductDTO :: Status200")
    @Description("Tests if the PUT endpoint '/products/{id}' updates a product with valid data and returns the updated product DTO, " +
            "verifying that it returns status code 200 (OK).")
    void updateProduct_With_ValidData_ReturnsProductDTO_Status200() throws Exception{
        when(productService.updateProduct(PRODUCT.getId(), PRODUCT_DTO)).thenReturn(PRODUCT_DTO);

        mockMvc.perform(put("/products/asdaf2")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(PRODUCT_DTO)))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("UpdateProduct: IdProductNotFound > ThrowsProductNotFoundException :: Status404")
    @Description("Tests if the PUT endpoint '/products/{id}' throws ProductNotFoundException " +
            "when attempting to update a product with an invalid ID, " +
            "verifying that it returns status code 404 (Not Found).")
    void updateProduct_With_IdProductNotFound_ThrowsProductNotFoundException_Status404() throws Exception{
        when(productService.updateProduct("IncorrectIdProduct", PRODUCT_DTO)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(put("/products/IncorrectIdProduct")
        .content(objectMapper.writeValueAsString(PRODUCT_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("UpdateProduct: NameIsUsingForAnotherProduct > ThrowsProductAlreadyExistException :: Status409")
    @Description("Tests if the PUT endpoint '/products/{id}' throws ProductAlreadyExistException " +
            "when attempting to update a product with a name that is already being used by another product, " +
            "verifying that it returns status code 409 (Conflict).")
    void updateProduct_With_NameIsUsingForAnotherProduct_ThrowsProductAlreadyExistException_Status409() throws Exception{
        when(productService.updateProduct("dasfx3", PRODUCT_DTO)).thenThrow(ProductAlreadyExistException.class);

        mockMvc.perform(put("/products/dasfx3")
        .content(objectMapper.writeValueAsString(PRODUCT_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("UpdateProduct: DescriptionLengthLessThanTen > ThrowsMinDescriptionException :: Status400")
    @Description("Tests if the PUT endpoint '/products/{id}' throws MinDescriptionException " +
            "when attempting to update a product with a description length less than ten characters, " +
            "verifying that it returns status code 400 (Bad Request).")
    void updateProduct_With_DescriptionLengthLessThanTen_ThrowsMinDescriptionException_Status400() throws Exception{
        when(productService.updateProduct("dasfx3", PRODUCT_DESCRIPTION_LESS_TEEN_DTO)).thenThrow(MinDescriptionException.class);

        mockMvc.perform(put("/products/dasfx3")
        .content(objectMapper.writeValueAsString(PRODUCT_DESCRIPTION_LESS_TEEN_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("UpdateProduct: ValueIsNull > ThrowsMinDescriptionException :: Status400")
    @Description("Tests if the PUT endpoint '/products/{id}' throws MinValueException " +
            "when attempting to update a product with a null value, " +
            "verifying that it returns status code 400 (Bad Request).")
    void updateProduct_With_ValueIsNull_ThrowsMinDescriptionException_Status400() throws Exception{
        when(productService.updateProduct("dasfx3", PRODUCT_VALUE_IS_NULL_DTO)).thenThrow(MinValueException.class);

        mockMvc.perform(put("/products/dasfx3")
        .content(objectMapper.writeValueAsString(PRODUCT_VALUE_IS_NULL_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("UpdateProduct: ValueLessThanZero > ThrowsMinValueException :: Status400")
    @Description("Tests if the PUT endpoint '/products/{id}' throws MinValueException " +
            "when attempting to update a product with a value less than zero, " +
            "verifying that it returns status code 400 (Bad Request).")
    void updateProduct_With_ValueLessThanZero_ThrowsMinValueException_Status400() throws Exception{
        when(productService.updateProduct("dasfx3", PRODUCT_VALUE_LESS_ZERO_DTO)).thenThrow(MinValueException.class);

        mockMvc.perform(put("/products/dasfx3")
        .content(objectMapper.writeValueAsString(PRODUCT_VALUE_LESS_ZERO_DTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DeleteProductById: IdProductExistingInDB > ReturnsVoid :: Status204")
    @Description("Tests if the DELETE endpoint '/products/{id}' deletes a product with a valid ID and returns status code 204 (No Content).")
    void deleteProductById_With_IdProductExistingInDB_ReturnsVoid_Status204() throws Exception{
        String productId = "dasfx3";

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/products/{id}", productId))
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DeleteProductById: IdProductUnexistingInDB > ThrowsProductNotFoundException :: Status404")
    @Description("Tests if the DELETE endpoint '/products/{id}' throws ProductNotFoundException " +
            "when attempting to delete a product with an invalid ID, " +
            "verifying that it returns status code 404 (Not Found).")
    void deleteProductById_With_IdProductUnexistingInDB_ThrowsProductNotFoundException_Status404() throws Exception{
        doThrow(ProductNotFoundException.class).when(productService).deleteProduct("InvalidId");

        mockMvc.perform(delete("/products/InvalidId"))
        .andExpect(status().isNotFound());
    }

}
