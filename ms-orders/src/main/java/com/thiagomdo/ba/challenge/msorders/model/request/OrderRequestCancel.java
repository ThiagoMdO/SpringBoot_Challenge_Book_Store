package com.thiagomdo.ba.challenge.msorders.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestCancel {

    private String cancelReason;
}
