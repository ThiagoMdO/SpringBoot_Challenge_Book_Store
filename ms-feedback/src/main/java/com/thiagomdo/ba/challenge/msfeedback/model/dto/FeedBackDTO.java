package com.thiagomdo.ba.challenge.msfeedback.model.dto;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
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

}
