package com.thiagomdo.ba.challenge.msfeedback.config;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.Feedback;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    FeedbackRepository feedBackRepository;

//    @Autowired
//    OrderFeign orderFeign;

    @Override
    public void run(String... args) throws Exception {
        feedBackRepository.deleteAll();

        Feedback feedBack1 = new Feedback("6605903e1e2d5c55c2017111", Scale.NEUTRAL, "Muito neutro", "6605903e1e2d5c55c2017225");
        Feedback feedBack2 = new Feedback("6605903e1e2d5c55c2017222", Scale.DISSATISFIED, "Muito ruim", "6605903e1e2d5c55c2017225");
        Feedback feedBack3 = new Feedback("6605903e1e2d5c55c2017333", Scale.VERY_SATISFIED, "Muito bom", "6605903e1e2d5c55c2017225");

        feedBackRepository.saveAll(Arrays.asList(feedBack1, feedBack2, feedBack3));

    }
}
