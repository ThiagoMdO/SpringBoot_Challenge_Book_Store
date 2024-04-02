package com.thiagomdo.ba.challenge.msorders.client;

import com.thiagomdo.ba.challenge.msorders.model.request.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080/products",name = "MsProduct")
public interface ProductFeign {
    @GetMapping("/{id}")
    ProductRequest findProductById(@PathVariable String id);


}
