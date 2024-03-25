package com.thiagomdo.ba.challenge.msproducts.repository;

import com.thiagomdo.ba.challenge.msproducts.model.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
}
