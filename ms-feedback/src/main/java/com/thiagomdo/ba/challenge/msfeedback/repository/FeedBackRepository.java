package com.thiagomdo.ba.challenge.msfeedback.repository;

import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends MongoRepository<FeedBack, String> {
}
