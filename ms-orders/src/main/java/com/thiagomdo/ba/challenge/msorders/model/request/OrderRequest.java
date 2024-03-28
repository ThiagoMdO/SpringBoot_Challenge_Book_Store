package com.thiagomdo.ba.challenge.msorders.model.request;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<ProductDTO> products = new ArrayList<>();

    private AddressClientViaCepRequest address;

    private Payment_method paymentMethod;

}
