package com.thiagomdo.ba.challenge.msfeedback.repository;

import com.thiagomdo.ba.challenge.msfeedback.model.entities.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
}
