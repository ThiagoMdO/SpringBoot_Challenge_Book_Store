package com.thiagomdo.ba.challenge.msfeedback.clients;

import com.thiagomdo.ba.challenge.msfeedback.clients.models.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8081/orders",name = "MsOrders")
public interface OrderFeign {
    @GetMapping("/{id}")
    OrderModel getOrderById(@PathVariable String id);
}
