package com.thiagomdo.ba.challenge.msorders.model.response;

import com.thiagomdo.ba.challenge.msorders.client.models.AddressByCep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressClientViaCepResponse {
        private String street;

        private Long number;

        private String complement;

        private String city;

        private String state;

        private String postalCode;

        public AddressClientViaCepResponse(AddressByCep addressByCep, Long number){
                street = addressByCep.getLogradouro();
                this.number = number;
                complement = addressByCep.getComplemento();
                city = addressByCep.getLocalidade();
                state = addressByCep.getUf();
                postalCode = addressByCep.getCep();
        }

}
