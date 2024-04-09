package com.thiagomdo.ba.challenge.msfeedback.model.dto;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.Feedback;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedbackRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FeedbackDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Scale scale;

    private String comment;

    private String orderId;

    public FeedbackDTO(Feedback feedBack){
        this.id = feedBack.getId();
        this.scale = feedBack.getScale();
        this.comment = feedBack.getComment();
        this.orderId = feedBack.getOrderId();
    }

    public FeedbackDTO(FeedbackRequest request, String idOrder){
        scale = request.getScale();
        comment = request.getComment();
        orderId = idOrder;
    }

}
