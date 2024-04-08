package com.thiagomdo.ba.challenge.msfeedback.model.entities;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedBackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedBackRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Document("feedback")
public class FeedBack implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Scale scale;

    private String comment;

    private String orderId;

    public FeedBack(FeedBackDTO feedBackDTO){
        id = feedBackDTO.getId();
        scale = feedBackDTO.getScale();
        comment = feedBackDTO.getComment();
        orderId = feedBackDTO.getOrderId();
    }

    public FeedBack(FeedBackRequest request, String idOrder){
        scale = request.getScale();
        comment = request.getComment();
        orderId = idOrder;
    }

    public FeedBack(String idInBD, FeedBack feedBackCreated){
        id = idInBD;
        scale = feedBackCreated.getScale();
        comment = feedBackCreated.getComment();
        orderId = feedBackCreated.getOrderId();
    }

}
