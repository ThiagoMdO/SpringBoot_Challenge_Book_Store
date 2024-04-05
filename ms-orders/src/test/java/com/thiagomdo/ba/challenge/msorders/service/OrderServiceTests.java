package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.client.ProductFeign;
import com.thiagomdo.ba.challenge.msorders.client.ViaCepFeign;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
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

import javax.management.DescriptorKey;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        verify(orderRepository).save(any(OrderResponse.class));
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

        verify(orderRepository).save(any(OrderResponse.class));
    }

    @Test
    @DisplayName("Update: OrderResponse with CANCELED status should set cancelReason and cancelDate")
    void update_With_CanceledStatus_ReturnsOrderDTO(){
        when(orderRepository.findById(ORDER_RESPONSE_WITH_STATUS_CANCELED.getId())).thenReturn(Optional.of(ORDER_RESPONSE_WITH_STATUS_CANCELED));
        when(viaCepFeign.searchLocationByCep(any(String.class))).thenReturn(ADDRESS_BY_CEP_44610000);
        when(orderRepository.save(any(OrderResponse.class))).thenReturn(ORDER_RESPONSE_WITH_STATUS_CANCELED);

        OrderDTO orderResponse = orderService.update(ORDER_RESPONSE_WITH_STATUS_CANCELED.getId(), ORDER_REQUEST_ACTUALIZATION_STATUS_CANCELED);

        assertEquals(Status.CANCELED, orderResponse.getStatus());
        assertEquals("Cancel Reason", orderResponse.getCancelReason());
        assertEquals(LocalDate.now(), orderResponse.getCancelDate());
        verify(orderRepository).save(any(OrderResponse.class));
    }

    @Test
    void update_With_InvalidDataOrFormatAddress_ThrowsAddressIncorrectException(){

        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS_INCORRECT));
        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS2_INCORRECT));
        assertThrows(AddressIncorrectException.class, () -> orderService.update(ORDER_RESPONSE_DTO.getId(), ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS3_INCORRECT));

        verify(orderRepository, never()).save(any(OrderResponse.class));
        verify(orderRepository, never()).findById(any(String.class));
        verify(viaCepFeign, never()).searchLocationByCep(any(String.class));

//        //teste não unitário, apenas testar testStreetAndNumberAddress
//        OrderService orderServiceMock = mock(OrderService.class);
//
//        doThrow(AddressIncorrectException.class)
//        .when(orderServiceMock)
//        .testStreetAndNumberAddress(ORDER_REQUEST_WITH_ADDRESS_INVALID.getAddress().getStreet(), ORDER_REQUEST_WITH_ADDRESS_INVALID.getAddress().getNumber());
//
//        assertThrows(AddressIncorrectException.class, () -> orderServiceMock.testStreetAndNumberAddress(null, null));
//        verify(orderRepository, never()).save(any(OrderResponse.class));
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
    @DisplayName("Not possible to change Status if Order Status is SENT, throws NotPossibleToChangeStatusException")
    void update_With_StatusSENT_ThrowsNotPossibleToChangeStatusException(){
        when(orderRepository.findById(ORDER_RESPONSE_WITH_STATUS_SENT.getId())).thenReturn(Optional.of(ORDER_RESPONSE_WITH_STATUS_SENT));
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST_ACTUALIZATION_STATUS_SENT.getAddress().getPostalCode())).thenReturn(ADDRESS_BY_CEP);

        assertThrows(NotPossibleToChangeStatusException.class, () -> orderService.update(ORDER_RESPONSE_WITH_STATUS_SENT.getId(), ORDER_REQUEST_ACTUALIZATION_STATUS_SENT));
        verify(orderRepository, never()).save(any(OrderResponse.class));

//        OrderService orderServiceMock = mock(OrderService.class);
//
//        doThrow(NotPossibleToChangeStatusException.class)
//        .when(orderServiceMock)
//        .testStatusDifferentStatusSENTAndOrderGreaterThan90Days(ORDER_RESPONSE_WITH_STATUS_SENT);
//
//        assertThrows(NotPossibleToChangeStatusException.class, () -> orderServiceMock.testStatusDifferentStatusSENTAndOrderGreaterThan90Days(ORDER_RESPONSE_WITH_STATUS_SENT));
//        verify(orderRepository, never()).save(any(OrderResponse.class));
    }

    @Test
    @DisplayName("Update: Not possible to change Date if Date is Greater then 90 after que Created date order, throws NotPossibleToChangeDateException")
    void update_With_DateGreaterThen90Days_ThrowsNotPossibleToChangeDateException(){
        when(orderRepository.findById(ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS.getId())).thenReturn(Optional.of(ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS));
        when(viaCepFeign.searchLocationByCep(any(String.class))).thenReturn(ADDRESS_BY_CEP2);


        assertThrows(NotPossibleToChangeDateException.class, () -> orderService.update(ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS.getId(), ORDER_REQUEST_ACTUALIZATION));
        verify(orderRepository, never()).save(any(OrderResponse.class));

//        OrderService orderServiceMock = mock(OrderService.class);
//
//        doThrow(NotPossibleToChangeDateException.class)
//        .when(orderServiceMock)
//        .testStatusDifferentStatusSENTAndOrderGreaterThan90Days(ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS);
//
//        assertThrows(NotPossibleToChangeDateException.class, () -> orderServiceMock.testStatusDifferentStatusSENTAndOrderGreaterThan90Days(ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS));
//        verify(orderRepository, never()).save(any(OrderResponse.class));
    }






}
