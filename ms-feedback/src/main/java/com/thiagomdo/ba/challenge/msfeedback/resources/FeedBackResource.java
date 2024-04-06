package com.thiagomdo.ba.challenge.msfeedback.resources;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedBackResource {

    @Autowired
    FeedBackService feedBackService;

    @GetMapping
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBack(){
        List<FeedBackDTO> listFeedBack = feedBackService.getAll();
        return ResponseEntity.ok().body(listFeedBack);
    }

}
