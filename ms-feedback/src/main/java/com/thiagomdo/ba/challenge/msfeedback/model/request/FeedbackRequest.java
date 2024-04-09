package com.thiagomdo.ba.challenge.msfeedback.model.request;

import com.thiagomdo.ba.challenge.msfeedback.enuns.Scale;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FeedbackRequest {

    private Scale scale;

    private String comment;

    private String orderId;
}
