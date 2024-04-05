package com.thiagomdo.ba.challenge.msorders.resources;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestCancel;
import com.thiagomdo.ba.challenge.msorders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> list = orderService.getAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id){
        OrderDTO orderDTO = orderService.getById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest request){
        OrderDTO orderDTO = orderService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(orderDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String id, @RequestBody OrderRequestActualization actualization){
        OrderDTO orderDTO = orderService.update(id, actualization);

        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderDTO> canceledOrder(@PathVariable String id, @RequestBody OrderRequestCancel requestCancel){
        OrderDTO orderDTO = orderService.cancel(id, requestCancel);
        return ResponseEntity.ok().body(orderDTO);
    }
}
