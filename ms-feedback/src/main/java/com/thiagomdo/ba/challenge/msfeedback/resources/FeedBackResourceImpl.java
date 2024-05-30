package com.thiagomdo.ba.challenge.msfeedback.resources;

import com.thiagomdo.ba.challenge.msfeedback.interfaces.FeedbackResource;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedbackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedbackRequest;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedBackResourceImpl implements FeedbackResource {

    @Autowired
    FeedbackService feedBackService;

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedBack() {
        List<FeedbackDTO> listFeedBack = feedBackService.getAll();
        return ResponseEntity.ok().body(listFeedBack);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedBackById(@PathVariable String id) {
        FeedbackDTO feedBackDTO = feedBackService.getById(id);
        return ResponseEntity.ok().body(feedBackDTO);
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedBack(@RequestBody FeedbackRequest request) {
        FeedbackDTO feedBackDTO = feedBackService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(feedBackDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(feedBackDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedBack(@PathVariable String id, @RequestBody FeedbackRequest request) {
        FeedbackDTO feedBackDTO = feedBackService.update(id, request);

        return ResponseEntity.ok().body(feedBackDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable String id) {
        feedBackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
