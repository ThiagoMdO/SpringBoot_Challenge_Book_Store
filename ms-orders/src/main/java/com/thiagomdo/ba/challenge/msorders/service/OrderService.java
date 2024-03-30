package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msorders.service.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getAll(){
        List<OrderResponse> list = orderRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public OrderDTO getById(String id){
        OrderResponse orderResponse = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return new OrderDTO(orderResponse);
    }
}
