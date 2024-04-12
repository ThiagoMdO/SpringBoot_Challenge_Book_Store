package com.thiagomdo.ba.challenge.msorders.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressClientViaCepRequest {
    private String street;

    private Long number;

    private String postalCode;
}
