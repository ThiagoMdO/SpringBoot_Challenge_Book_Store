package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.client.ProductFeign;
import com.thiagomdo.ba.challenge.msorders.client.ViaCepFeign;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.AddressIncorrectException;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msorders.service.exception.OrderNotFoundException;
import feign.FeignException;
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

//        ProductRequest productRequest = productFeign.findProductById(ORDER_REQUEST.getProducts().get(0).getId());
//        AddressByCep byCep = viaCepFeign.searchLocationByCep(ORDER_REQUEST.getAddress().getPostalCode());
//        OrderResponse orderResponse = orderRepository.save(ORDER_RESPONSE);

        OrderDTO result = orderService.create(ORDER_REQUEST);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ORDER_RESPONSE.getId());

//        assertThat(productRequest).isNotNull();
//        assertThat(byCep).isNotNull();
//        assertThat(orderResponse).isNotNull();
//
//        assertThat(productRequest).isEqualTo(PRODUCT_REQUEST1);
//        assertThat(byCep).isEqualTo(ADDRESS_BY_CEP);
//        assertThat(orderResponse).isEqualTo(ORDER_RESPONSE);
    }

    @Test
    void create_With_InvalidFieldsAddress_ThrowsAddressIncorrectException() {
        OrderService orderService = mock(OrderService.class);

        orderService.create(ORDER_REQUEST_WITH_ADDRESS_INVALID);

        doThrow(AddressIncorrectException.class)
        .when(orderService)
        .testStreetAndNumberAddress(null, null);

        assertThrows(AddressIncorrectException.class, () -> orderService.testStreetAndNumberAddress(null, null));
    }

    @Test
    void create_With_InvalidIdProduct_ThrowsFeignException() {
        when(productFeign.findProductById(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID.getProducts().get(0).getId())).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> orderService.create(ORDER_REQUEST_WITH_ID_PRODUCT_INVALID));
    }

    @Test
    void create_With_InvalidCEP_ThrowsFeignException() {
        when(viaCepFeign.searchLocationByCep(ORDER_REQUEST_WITH_CEP_INVALID.getAddress().getPostalCode())).thenThrow(FeignException.class);

        assertThrows(FeignException.class, () -> orderService.create(ORDER_REQUEST_WITH_CEP_INVALID));
    }


}
