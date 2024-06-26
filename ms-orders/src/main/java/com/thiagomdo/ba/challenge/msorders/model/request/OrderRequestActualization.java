package com.thiagomdo.ba.challenge.msorders.model.request;

import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class OrderRequestActualization {

    private Status status;

    private AddressClientViaCepRequest address;

}
