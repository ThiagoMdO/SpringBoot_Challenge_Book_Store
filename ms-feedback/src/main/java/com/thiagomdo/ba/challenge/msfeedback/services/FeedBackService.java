package com.thiagomdo.ba.challenge.msfeedback.services;

import com.thiagomdo.ba.challenge.msfeedback.clients.OrderFeign;
import com.thiagomdo.ba.challenge.msfeedback.clients.models.OrderModel;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedBackRequest;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.FeedBackNotFoundException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.NotPossibleToCommentOrderException;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.OrderNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;

    @Autowired
    OrderFeign orderFeign;

    public List<FeedBackDTO> getAll() {
        List<FeedBack> list = feedBackRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(FeedBackDTO::new).collect(Collectors.toList());
    }

    public FeedBackDTO getById(String id) {
        FeedBack feedBack = feedBackRepository.findById(id).orElseThrow(FeedBackNotFoundException::new);
        return new FeedBackDTO(feedBack);
    }

    public FeedBackDTO create(FeedBackRequest feedBackDTO) {
        FeedBack feedBack = feedBackRepository.save(createFeedBack(feedBackDTO));

        return new FeedBackDTO(feedBack);
    }

    public FeedBackDTO update(String id, FeedBackRequest feedBackRequest){
        FeedBack feedBack = feedBackRepository.save(updateFeedBack(id, feedBackRequest));

        return new FeedBackDTO(feedBack);
    }

    private FeedBack createFeedBack(FeedBackRequest feedBackDTO) {
        try {
            OrderModel orderModel = orderFeign.getOrderById(feedBackDTO.getOrderId());
            if (orderModel.getStatus().equals("CANCELED")) throw new NotPossibleToCommentOrderException();
            return new FeedBack(feedBackDTO, orderModel.getId());
        } catch (FeignException.FeignClientException e) {
            throw new OrderNotFoundException();
        }
    }

    private FeedBack updateFeedBack(String id, FeedBackRequest request){
        FeedBackDTO feedBackInDB = getById(id);

        FeedBack feedBackCreated = createFeedBack(request);

        return new FeedBack(feedBackInDB.getId(), feedBackCreated);
    }


}
