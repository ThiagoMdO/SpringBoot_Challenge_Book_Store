package com.thiagomdo.ba.challenge.msorders.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressClientViaCepRequest {
    private String street;

    private Long number;

    private String postalCode;
}
