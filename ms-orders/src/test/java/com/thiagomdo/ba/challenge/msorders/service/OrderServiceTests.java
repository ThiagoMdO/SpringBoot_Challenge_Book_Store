package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msorders.service.exception.OrderNotFoundException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    void getAll_With_ValidData_ReturnsOrderResponseList(){
        when(orderRepository.findAll()).thenReturn(ORDER_RESPONSE_LIST);

        List<OrderDTO> result = orderService.getAll();

        assertThat(result).isNotNull();

        assertThat(result).containsExactlyElementsOf(ORDER_RESPONSE_LIST_DTO);
    }

    @Test
    void getAll_ThrowsEmptyListException(){
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyListException.class, () -> orderService.getAll());
    }

    @Test
    void getById_With_ValidIdOrder_ReturnsOrder(){
        when(orderRepository.findById("6605903e1e2d5c55c2017777")).thenReturn(Optional.of(ORDER_RESPONSE));

        OrderDTO orderResponse = orderService.getById("6605903e1e2d5c55c2017777");

        assertThat(orderResponse).isNotNull();
        assertThat(orderResponse.getId()).isEqualTo(ORDER_RESPONSE.getId());
    }

    @Test
    void getById_WithInvalidIdOrder_ThrowsOrderNotFoundException(){
        when(orderRepository.findById("InvalidIdOrder")).thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () -> orderService.getById("InvalidIdOrder"));
    }


}
