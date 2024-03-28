package com.thiagomdo.ba.challenge.msorders.model.response;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Document("orders")
public class OrderResponse {

    private String id;

    private List<ProductDTO> products = new ArrayList<>();

    private AddressClientViaCepResponse address = new AddressClientViaCepResponse();

    private Payment_method paymentMethod;

    private Double subtotalValue;

    private Double discount;

    private Double totalValue;

    private LocalDate createdDate;

    private Status status;

    public OrderResponse(OrderRequest orderRequest, Double subtotalValue, AddressClientViaCepResponse cepResponse){
        products = orderRequest.getProducts();
        address.setStreet(orderRequest.getAddress().getStreet());
        address.setNumber(orderRequest.getAddress().getNumber());
        address.setComplement(cepResponse.getComplement());
        address.setCity(cepResponse.getCity());
        address.setState(cepResponse.getState());
        address.setPostalCode(orderRequest.getAddress().getPostalCode());

        if (orderRequest.getPaymentMethod().equals(Payment_method.PIX)){
            discount = 0.05 * subtotalValue;
            totalValue = subtotalValue - discount;
        }else {
            discount = 0.0;
            totalValue = subtotalValue;
        }

        createdDate = LocalDate.now();
        status = Status.CONFIRMED;
    }

}
