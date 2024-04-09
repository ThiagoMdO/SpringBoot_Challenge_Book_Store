package com.thiagomdo.ba.challenge.msfeedback.services;

import com.thiagomdo.ba.challenge.msfeedback.clients.OrderFeign;
import com.thiagomdo.ba.challenge.msfeedback.clients.models.OrderModel;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedbackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.Feedback;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedbackRequest;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedbackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedbackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedBackRepository;

    @Autowired
    OrderFeign orderFeign;

    public List<FeedbackDTO> getAll() {
        List<Feedback> list = feedBackRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(FeedbackDTO::new).collect(Collectors.toList());
    }

    public FeedbackDTO getById(String id) {
        Feedback feedBack = feedBackRepository.findById(id).orElseThrow(FeedbackNotFoundException::new);
        return new FeedbackDTO(feedBack);
    }

    public FeedbackDTO create(FeedbackRequest feedBackDTO) {
        Feedback feedBack = feedBackRepository.save(createFeedback(feedBackDTO));

        return new FeedbackDTO(feedBack);
    }

    public FeedbackDTO update(String id, FeedbackRequest feedBackRequest){
        Feedback feedBack = feedBackRepository.save(updateFeedback(id, feedBackRequest));

        return new FeedbackDTO(feedBack);
    }

    public void delete(String id){
        FeedbackDTO feedBack = getById(id);
        feedBackRepository.delete(new Feedback(feedBack));
    }

    private Feedback createFeedback(FeedbackRequest feedBackDTO) {
        try {
            OrderModel orderModel = orderFeign.getOrderById(feedBackDTO.getOrderId());
            if (orderModel.getStatus().equals("CANCELED")) throw new NotPossibleToCommentOrderException();
            return new Feedback(feedBackDTO, orderModel.getId());
        } catch (FeignException.FeignClientException e) {
            throw new OrderNotFoundException();
        }
    }

    private Feedback updateFeedback(String id, FeedbackRequest request){
        FeedbackDTO feedBackInDB = getById(id);

        Feedback feedBackCreated = createFeedback(request);

        return new Feedback(feedBackInDB.getId(), feedBackCreated);
    }


}
