package com.thiagomdo.ba.challenge.msfeedback.config;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    FeedBackRepository feedBackRepository;

//    @Autowired
//    OrderFeign orderFeign;

    @Override
    public void run(String... args) throws Exception {
        feedBackRepository.deleteAll();
        FeedBack feedBack1 = new FeedBack("6605903e1e2d5c55c2011111", Scale.NEUTRAL, "Muito neutro", "6605903e1e2d5c55c2017225");
        FeedBack feedBack2 = new FeedBack("6605903e1e2d5c55c2018888", Scale.SATISFIED, "Very good", "6605903e1e2d5c55c2017225");
        FeedBack feedBack3 = new FeedBack("6605903e1e2d5c55c2019999", Scale.VERY_DISSATISFIED, "Very bad", "6605903e1e2d5c55c2017225");

        feedBackRepository.saveAll(Arrays.asList(feedBack1, feedBack2, feedBack3));

    }
}
