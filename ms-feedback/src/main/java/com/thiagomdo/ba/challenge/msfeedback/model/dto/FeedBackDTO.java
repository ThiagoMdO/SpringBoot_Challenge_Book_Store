package com.thiagomdo.ba.challenge.msfeedback.model.dto;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import com.thiagomdo.ba.challenge.msfeedback.model.entities.FeedBack;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FeedBackDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Scale scale;

    private String comment;

    private String orderId;

    public FeedBackDTO(FeedBack feedBack){
        this.id = feedBack.getId();
        this.scale = feedBack.getScale();
        this.comment = feedBack.getComment();
        this.orderId = feedBack.getOrderId();
    }

}
