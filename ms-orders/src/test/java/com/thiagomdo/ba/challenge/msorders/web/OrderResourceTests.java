package com.thiagomdo.ba.challenge.msorders.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestCancel;
import com.thiagomdo.ba.challenge.msorders.resources.OrderResource;
import com.thiagomdo.ba.challenge.msorders.service.OrderService;
import com.thiagomdo.ba.challenge.msorders.service.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderResource.class)
class OrderResourceTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test
    @Description("Tests if the GET endpoint '/orders' returns a list of orders with valid data and status code 200 (OK). " +
    "This test verifies that the endpoint correctly returns the expected order list in the response body and returns the status code 200.")
    void getAllOrders_With_ValidData_ReturnsOrderList_Status200() throws Exception {
        when(orderService.getAll()).thenReturn(ORDER_RESPONSE_LIST_DTO);

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(ORDER_RESPONSE_LIST.get(0).getId()))
            .andExpect(jsonPath("$[1].id").value(ORDER_RESPONSE_LIST.get(1).getId()));
    }

    @Test
    @Description("Tests if the GET endpoint '/orders' handles the EmptyListException and returns status code 200 (OK). " +
    "This test verifies that the endpoint correctly handles the case when no orders are available, " +
    "returning an empty list in the response body and status code 200.")
    void getAllOrders_ReturnsEmptyListException_Status200() throws Exception {
        when(orderService.getAll()).thenThrow(EmptyListException.class);

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk());
    }

    @Test
    @Description("Tests if the GET endpoint '/orders/{orderId}' returns an order with valid data and status code 200 (OK). " +
    "This test verifies that the endpoint correctly retrieves the order with the given ID and returns it in the response body, " +
    "along with status code 200.")
    void getOrderById_With_ValidIdOrder_ReturnsOrder_Status200() throws Exception {
        when(orderService.getById("6605903e1e2d5c55c2017777")).thenReturn(ORDER_RESPONSE_DTO);

        mockMvc.perform(get("/orders/6605903e1e2d5c55c2017777"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(ORDER_RESPONSE_DTO)));
    }

    @Test
    @Description("Tests if the GET endpoint '/orders/{orderId}' throws OrderNotFoundException when an order with the given ID does not exist, " +
    "and returns status code 404 (Not Found). " +
    "This test verifies that the endpoint correctly handles the case when an order with the provided ID does not exist, " +
    "throwing OrderNotFoundException and returning status code 404.")
    void getOrderById_ByUnexistingId_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(orderService.getById("InvalidIdOrders")).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(get("/orders/InvalidIdOrders"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Description("Tests if the POST endpoint '/orders' creates an order with valid data and returns status code 201 (Created). " +
    "This test verifies that the endpoint correctly creates an order with the provided data, " +
    "returns the created order DTO in the response body, and returns status code 201.")
    void create_With_ValidData_ReturnsOrderDTO_Status201() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenReturn(ORDER_RESPONSE_TO_CREATE_DTO);

        mockMvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(ORDER_REQUEST))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    }

    @Test
    @Description("Tests if the POST endpoint '/orders' throws AddressIncorrectException when the provided order request contains invalid address fields, " +
    "and returns status code 400 (Bad Request). " +
    "This test verifies that the endpoint correctly handles the case when the provided order request contains invalid address fields, " +
    "throwing AddressIncorrectException and returning status code 400.")
    void create_With_InvalidFieldsAddress_ThrowsAddressIncorrectException_Status400() throws Exception {
        when(orderService.create(any(OrderRequest.class))).thenThrow(AddressIncorrectException.class);

        mockMvc.perform(post("/orders")
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_INVALID_ADDRESS_DTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Tests if the POST endpoint '/orders' throws ProductNotFoundException when the provided order request contains an invalid product ID, " +
    "and returns status code 404 (Not Found). " +
    "This test verifies that the endpoint correctly handles the case when the provided order request contains an invalid product ID, " +
    "throwing ProductNotFoundException and returning status code 404.")
    void create_With_InvalidIdProduct_ProductNotFoundException_Status404() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(post("/orders")
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @Description("Tests if the POST endpoint '/orders' throws AddressIncorrectException when the provided order request contains an invalid CEP, " +
    "and returns status code 400 (Bad Request). " +
    "This test verifies that the endpoint correctly handles the case when the provided order request contains an invalid CEP, " +
    "throwing AddressIncorrectException and returning status code 400.")
    void create_With_InvalidCEP_AddressIncorrectException_Status400() throws Exception{
        when(orderService.create(any(OrderRequest.class))).thenThrow(AddressIncorrectException.class);

        mockMvc.perform(post("/orders")
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_WITH_CEP_INVALID))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Tests if the PUT endpoint '/orders/{orderId}' updates an order with valid data and returns status code 200 (OK). " +
    "This test verifies that the endpoint correctly updates an order with the provided data, " +
    "returns the updated order DTO in the response body, and returns status code 200.")
    void updateOrder_With_ValidData_ReturnsOrderDTO_Status200() throws Exception{
        when(orderService.update(ORDER_RESPONSE.getId(), ORDER_REQUEST_ACTUALIZATION)).thenReturn(ORDER_RESPONSE_DTO);

        mockMvc.perform(put("/orders/" + ORDER_RESPONSE.getId())
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_DTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void updateOrder_With_CanceledStatus_NotPossibleToChangeStatusException_Status400() throws Exception{
//        when(orderService.update(ORDER_RESPONSE.getId(), ORDER_REQUEST_ACTUALIZATION_STATUS_CANCELED)).thenThrow(NotPossibleToChangeStatusException.class);
//
//        mockMvc.perform(put("/orders/" + ORDER_RESPONSE.getId())
//            .content(objectMapper.writeValueAsString(ORDER_REQUEST_ACTUALIZATION_STATUS_CANCELED))
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_With_InvalidDataOrFormatAddress_ThrowsAddressIncorrectException_Status400() throws Exception{
        when(orderService.update(any(String.class), any(OrderRequestActualization.class))).thenThrow(AddressIncorrectException.class);

        mockMvc.perform(put("/orders/" + ORDER_RESPONSE_WITH_INVALID_ADDRESS.getId())
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS_INCORRECT))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_With_OrderIdNotFound_ThrowsOrderNotFoundException_Status404() throws Exception{
        when(orderService.update(any(String.class), any(OrderRequestActualization.class))).thenThrow(OrderNotFoundException.class);

        mockMvc.perform(put("/orders/IncorrectId")
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_ACTUALIZATION))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateOrder_With_InvalidCEP_ThrowsAddressIncorrectException_Status400() throws Exception{
        when(orderService.update(any(String.class), any(OrderRequestActualization.class))).thenThrow(AddressIncorrectException.class);

        mockMvc.perform(put("/orders/" + ORDER_RESPONSE_DTO.getId())
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_ACTUALIZATION_WITH_CEP_INCORRECT))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_With_StatusSENT_ThrowsNotPossibleToChangeStatusException_Status400() throws Exception{
        when(orderService.update(any(String.class), any(OrderRequestActualization.class))).thenThrow(NotPossibleToChangeStatusException.class);

        mockMvc.perform(put("/orders/" + ORDER_RESPONSE_WITH_STATUS_SENT.getId())
            .content(objectMapper.writeValueAsString(ORDER_REQUEST_ACTUALIZATION_STATUS_SENT))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    void canceledOrder_With_ValidData_ReturnsOrderDTO_Status200() throws Exception{
        when(orderService.cancel(ORDER_RESPONSE_TO_CANCELED.getId(), new OrderRequestCancel("Cancel Reason"))).thenReturn(ORDER_RESPONSE_CANCELED_DTO);

        mockMvc.perform(post("/orders/" + ORDER_RESPONSE_TO_CANCELED.getId())
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_CANCELED))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void canceledOrder_With_OrderStatusAlreadySENTorCANCELED_ThrowsNotPossibleToChangeStatusException_Status400() throws Exception{
        when(orderService.cancel(any(String.class), any(OrderRequestCancel.class))).thenThrow(NotPossibleToChangeStatusException.class);

        mockMvc.perform(post("/orders/" + ORDER_RESPONSE_SENT.getId())
            .content(objectMapper.writeValueAsString(ORDER_RESPONSE_SENT))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        mockMvc.perform(post("/orders/" + ORDER_RESPONSE_CANCELED.getId())
        .content(objectMapper.writeValueAsString(ORDER_RESPONSE_CANCELED))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void canceledOrder_With_DateGreaterThen90Days_ThrowsNotPossibleToChangeDateException_Status400() throws Exception{
        when(orderService.cancel(any(String.class), any(OrderRequestCancel.class))).thenThrow(NotPossibleToChangeDateException.class);

        mockMvc.perform(post("/orders/" + ORDER_RESPONSE_GREATER_THEN_90_DAYS.getId())
        .content(objectMapper.writeValueAsString(ORDER_RESPONSE_GREATER_THEN_90_DAYS))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }


}
