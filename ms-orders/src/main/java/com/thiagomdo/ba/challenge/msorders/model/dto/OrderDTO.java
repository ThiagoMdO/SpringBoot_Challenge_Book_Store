package com.thiagomdo.ba.challenge.msorders.model.dto;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.response.AddressClientViaCepResponse;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDTO {
    private String id;

    private List<ProductDTO> products = new ArrayList<>();

    private AddressClientViaCepResponse address = new AddressClientViaCepResponse();

    private Payment_method paymentMethod;

    private Double subtotalValue;

    private Double discount;

    private Double totalValue;

    private LocalDate createdDate;

    private Status status;

    public OrderDTO(OrderResponse orderResponse){
        this.id = orderResponse.getId();
        this.products = orderResponse.getProducts();
        this.address = orderResponse.getAddress();
        this.paymentMethod = orderResponse.getPaymentMethod();
        this.subtotalValue = orderResponse.getSubtotalValue();
        this.discount = orderResponse.getDiscount();
        this.totalValue = orderResponse.getTotalValue();
        this.createdDate = orderResponse.getCreatedDate();
        this.status = orderResponse.getStatus();
    }
}
