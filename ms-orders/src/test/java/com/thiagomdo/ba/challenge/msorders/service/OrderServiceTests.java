package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.client.ProductFeign;
import com.thiagomdo.ba.challenge.msorders.client.ViaCepFeign;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestCancel;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.*;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductFeign productFeign;

    @Mock
    ViaCepFeign viaCepFeign;

    @Test
    void getAll_With_OrdersInDB_ReturnsOrderResponseList() {
        when(orderRepository.findAll()).thenReturn(ORDER_RESPONSE_LIST);

        List<OrderDTO> result = orderService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyElementsOf(ORDER_RESPONSE_LIST_DTO);
    }

    @Test
    void getAll_Without_OrdersInDB_ThrowsEmptyListException() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> orderService.getAll());
    }

    @Test
    void getById_With_ValidIdOrder_ReturnsOrder() {
        when(orderRepository.findById("6605903e1e2d5c55c2017777")).thenReturn(Optional.of(ORDER_RESPONSE));

        OrderDTO orderResponse = orderService.getById("6605903e1e2d5c55c2017777");

        assertThat(orderResponse).isNotNull();
        assertThat(orderResponse.getId()).isEqualTo(ORDER_RESPONSE.getId());
    }

    @Test
    void getById_WithInvalidIdOrder_ThrowsOrderNotFoundException() {
        when(orderRepository.findById("InvalidIdOrder")).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> orderService.getById("InvalidIdOrder"));
    }

    @Test
    void create_With_ValidData_ReturnsOrderDTO() {
        when(productFeign.findProductById(ORDER_REQUEST.getProducts().get(0).getId())).thenReturn(PRODUCT_REQUEST1);
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST.getAddress().getPostalCode())).thenReturn(ADDRESS_BY_CEP);
        when(orderRepository.save(any(OrderResponse.class))).thenReturn(ORDER_RESPONSE);

        OrderDTO result = orderService.create(ORDER_REQUEST);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(new OrderDTO(ORDER_RESPONSE));
        verify(orderRepository, times(1)).save(any(OrderResponse.class));
    }

    @Test
    void create_With_InvalidFieldsAddress_ThrowsAddressIncorrectException() {

        assertThrows(AddressIncorrectException.class, () -> orderService.create(ORDER_REQUEST_WITH_ADDRESS_INVALID));
        assertThrows(AddressIncorrectException.class, () -> orderService.create(ORDER_REQUEST_WITH_ADDRESS2_INVALID));
        assertThrows(AddressIncorrectException.class, () -> orderService.create(ORDER_REQUEST_WITH_ADDRESS3_INVALID));
        verify(orderRepository, never()).save(any(OrderResponse.class));
        verify(orderRepository, never()).findById(any(String.class));
        verify(productFeign, never()).findProductById(any(String.class));
        verify(viaCepFeign, never()).searchLocationByCep(any(String.class));
    }

    @Test
    void create_With_InvalidIdProduct_ProductNotFoundException() {
        when(productFeign.findProductById(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID.getProducts().get(0).getId())).thenThrow(FeignException.class);

        assertThrows(ProductNotFoundException.class, () -> orderService.create(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    void create_With_InvalidCEP_AddressIncorrectException() {
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST_WITH_CEP_INVALID.getAddress().getPostalCode())).thenThrow(AddressIncorrectException.class);

        assertThrows(AddressIncorrectException.class, () -> orderService.create(ORDER_REQUEST_WITH_CEP_INVALID));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }


    @Test
    void update_With_ValidData_ReturnsOrderDTO(){
        when(orderRepository.findById(ORDER_RESPONSE.getId())).thenReturn(Optional.of(ORDER_RESPONSE));
        when(viaCepFeign.searchLocationByCep(ORDER_RESPONSE.getAddress().getPostalCode())).thenReturn(ADDRESS_BY_CEP);
        when(orderRepository.save(any(OrderResponse.class))).thenReturn(ORDER_RESPONSE);

        OrderDTO result = orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(new OrderDTO(ORDER_RESPONSE));

        verify(orderRepository, times(1)).save(any(OrderResponse.class));
    }

    @Test
    void update_With_InvalidDataOrFormatAddress_ThrowsAddressIncorrectException(){

        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS_INCORRECT));
        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS2_INCORRECT));
        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS3_INCORRECT));

        verify(orderRepository, never()).save(any(OrderResponse.class));
        verify(orderRepository, never()).findById(any(String.class));
        verify(viaCepFeign, never()).searchLocationByCep(any(String.class));

    }

    @Test
    void update_With_OrderIdNotFound_ThrowsOrderNotFoundException(){
        when(orderRepository.findById("InvalidId")).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> orderService.update("InvalidId", ORDER_REQUEST_ACTUALIZATION));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    void update_With_InvalidCEP_ThrowsAddressIncorrectException(){
        when(orderRepository.findById(ORDER_RESPONSE_DTO.getId())).thenReturn(Optional.of(ORDER_RESPONSE));
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST_ACTUALIZATION_WITH_CEP_INCORRECT.getAddress().getPostalCode())).thenThrow(AddressIncorrectException.class);

        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_CEP_INCORRECT));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    @DisplayName("Not possible to change Status if Order Status is SENT or Canceled, throws NotPossibleToChangeStatusException")
    void update_With_OrderStatusAlreadySENT_ThrowsNotPossibleToChangeStatusException(){
        when(orderRepository.findById(ORDER_RESPONSE_SENT.getId())).thenReturn(Optional.of(ORDER_RESPONSE_SENT));
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST_ACTUALIZATION_STATUS_SENT.getAddress().getPostalCode())).thenReturn(ADDRESS_BY_CEP);

        assertThrows(NotPossibleToChangeStatusException.class, () -> orderService.update(ORDER_RESPONSE_SENT.getId(), ORDER_REQUEST_ACTUALIZATION_STATUS_SENT));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    void update_With_OrderStatusAlreadyCANCELED_ThrowsNotPossibleToChangeStatusException() {

        assertThrows(NotPossibleToChangeStatusException.class, () -> orderService.update(ORDER_RESPONSE_CANCELED.getId(), ORDER_REQUEST_ACTUALIZATION_STATUS_CANCELED));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    void cancel_With_ValidData_ReturnsOrderDTO(){
        when(orderRepository.save(ORDER_RESPONSE_CANCELED)).thenReturn(ORDER_RESPONSE_CANCELED);
        when(orderRepository.findById(ORDER_RESPONSE_TO_CANCELED.getId())).thenReturn(Optional.of(ORDER_RESPONSE_TO_CANCELED));

        OrderDTO result = orderService.cancel(ORDER_RESPONSE_CANCELED.getId(), new OrderRequestCancel("Cancel Reason"));

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ORDER_RESPONSE_CANCELED_DTO);
        verify(orderRepository, times(1)).save(ORDER_RESPONSE_CANCELED);
    }

    @Test
    void cancel_With_OrderStatusAlreadySENTorCANCELED_ThrowsNotPossibleToChangeStatusException(){
        when(orderRepository.findById(ORDER_RESPONSE_SENT.getId())).thenReturn(Optional.of(ORDER_RESPONSE_SENT));
        when(orderRepository.findById(ORDER_RESPONSE_CANCELED.getId())).thenReturn(Optional.of(ORDER_RESPONSE_CANCELED));

        assertThrows(NotPossibleToChangeStatusException.class, () -> orderService.cancel(ORDER_RESPONSE_SENT.getId(), new OrderRequestCancel("Cancel Reason")));
        assertThrows(NotPossibleToChangeStatusException.class, () -> orderService.cancel(ORDER_RESPONSE_CANCELED.getId(), new OrderRequestCancel("Cancel Reason")));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    void cancel_With_DateGreaterThen90Days_ThrowsNotPossibleToChangeDateException(){
        when(orderRepository.findById(ORDER_RESPONSE_GREATER_THEN_90_DAYS.getId())).thenReturn(Optional.of(ORDER_RESPONSE_GREATER_THEN_90_DAYS));

        assertThrows(NotPossibleToChangeDateException.class, () -> orderService.cancel(ORDER_RESPONSE_GREATER_THEN_90_DAYS.getId(), new OrderRequestCancel("Cancel Reason")));
        verify(orderRepository, never()).save(any(OrderResponse.class));
    }



}
