package com.thiagomdo.ba.challenge.msfeedback.model.entities;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
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

}
