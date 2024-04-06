package com.thiagomdo.ba.challenge.msfeedback.services;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import com.thiagomdo.ba.challenge.msfeedback.repository.FeedBackRepository;
import com.thiagomdo.ba.challenge.msfeedback.services.exception.EmptyListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;

//    @Autowired
//    OrderFeign orderFeign;

    public List<FeedBackDTO> getAll(){
        List<FeedBack> list = feedBackRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(FeedBackDTO::new).collect(Collectors.toList());
    }


}
