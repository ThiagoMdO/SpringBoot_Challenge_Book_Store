package com.thiagomdo.ba.challenge.msorders.model.response;

import com.thiagomdo.ba.challenge.msorders.client.models.AddressByCep;
import com.thiagomdo.ba.challenge.msorders.model.request.AddressClientViaCepRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

        public AddressClientViaCepResponse(AddressClientViaCepRequest viaCepRequest){
                street = viaCepRequest.getStreet();
                number = viaCepRequest.getNumber();
                postalCode = viaCepRequest.getPostalCode();
        }

}
