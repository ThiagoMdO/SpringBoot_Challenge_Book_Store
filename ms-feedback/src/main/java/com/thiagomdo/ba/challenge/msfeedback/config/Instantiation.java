package com.thiagomdo.ba.challenge.msfeedback.config;

import com.thiagomdo.ba.challenge.msfeedback.clients.OrderFeign;
import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    FeedBackRepository feedBackRepository;

    @Autowired
    OrderFeign orderFeign;

    @Override
    public void run(String... args) throws Exception {
//        feedBackRepository.deleteAll();
//        FeedBack feedBack1 = new FeedBack("123456789", Scale.NEUTRAL, "Muito neutro", "Order12345");
//
//        feedBackRepository.save(feedBack1);

    }
}
