package com.thiagomdo.ba.challenge.msfeedback.resources;

import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedBackResource {

    @Autowired
    FeedBackService feedBackService;


}
