package com.thiagomdo.ba.challenge.msfeedback.resources;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedBackRequest;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedBackResource {

    @Autowired
    FeedBackService feedBackService;

    @GetMapping
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBack() {
        List<FeedBackDTO> listFeedBack = feedBackService.getAll();
        return ResponseEntity.ok().body(listFeedBack);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedBackDTO> getFeedBackById(@PathVariable String id) {
        FeedBackDTO feedBackDTO = feedBackService.getById(id);
        return ResponseEntity.ok().body(feedBackDTO);
    }

    @PostMapping
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackRequest request) {
        FeedBackDTO feedBackDTO = feedBackService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(feedBackDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(feedBackDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedBackDTO> updateFeedBack(@PathVariable String id, @RequestBody FeedBackRequest request) {
        FeedBackDTO feedBackDTO = feedBackService.update(id, request);

        return ResponseEntity.ok().body(feedBackDTO);
    }
}
