package com.thiagomdo.ba.challenge.msorders.repository;

import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderResponse, String> {
}
