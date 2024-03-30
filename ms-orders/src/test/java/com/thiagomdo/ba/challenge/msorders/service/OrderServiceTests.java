package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.ORDER_RESPONSE_LIST;
import static com.thiagomdo.ba.challenge.msorders.common.OrdersConstants.ORDER_RESPONSE_LIST_DTO;
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
}
