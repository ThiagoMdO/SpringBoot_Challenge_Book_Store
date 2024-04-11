package com.thiagomdo.ba.challenge.msorders.model.request;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderRequest {

    private List<ProductDTO> products = new ArrayList<>();

    private AddressClientViaCepRequest address;

    private Payment_method paymentMethod;

}
