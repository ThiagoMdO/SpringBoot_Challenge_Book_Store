package com.thiagomdo.ba.challenge.msorders.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.resources.OrderResource;
import com.thiagomdo.ba.challenge.msorders.service.OrderService;
import com.thiagomdo.ba.challenge.msorders.service.exception.AddressIncorrectException;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msorders.service.exception.OrderNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderResource.class)
class OrderResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void getAllOrders_With_ValidData_ReturnsOrderList_Status200() throws Exception {
        when(orderService.getAll()).thenReturn(ORDER_RESPONSE_LIST_DTO);

        mockMvc.perform(get("/orders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(ORDER_RESPONSE_LIST.get(0).getId()))
        .andExpect(jsonPath("$[1].id").value(ORDER_RESPONSE_LIST.get(1).getId()));
    }

    @Test
    void getAllOrders_ReturnsEmptyListException_Status200() throws Exception {
        when(orderService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/orders"))
        .andExpect(status().isOk());
    }

    @Test
    void getOrderById_With_ValidIdOrder_ReturnsOrder_Status200() throws Exception {
        when(orderService.getById("6605903e1e2d5c55c2017777")).thenReturn(ORDER_RESPONSE_DTO);

        mockMvc.perform(get("/orders/6605903e1e2d5c55c2017777"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(ORDER_RESPONSE_DTO)));
    }

    @Test
    void getOrderById_ByUnexistingId_ThrowsOrderNotFoundException_Status404() {
        when(orderService.getById("InvalidIdOrders")).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> orderService.getById("InvalidIdOrders"));
    }

    @Test
    void create_With_ValidData_ReturnsOrderDTO_Status201() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenReturn(ORDER_RESPONSE_DTO);

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_DTO)))
            .andExpect(status().isCreated());
    }

    @Test
    void create_With_InvalidFieldsAddress_ThrowsAddressIncorrectException_Status400() throws Exception {
        when(orderService.create(any(OrderRequest.class))).thenThrow(AddressIncorrectException.class);

        mockMvc.perform(post("/orders")
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_INVALID_ADDRESS_DTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void create_With_InvalidIdProduct_ThrowsFeignException() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenThrow(FeignException.class);

        mockMvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void create_With_InvalidCEP_ThrowsFeignException() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenThrow(FeignException.class);

        mockMvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(ORDER_REQUEST_WITH_CEP_INVALID))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

}
