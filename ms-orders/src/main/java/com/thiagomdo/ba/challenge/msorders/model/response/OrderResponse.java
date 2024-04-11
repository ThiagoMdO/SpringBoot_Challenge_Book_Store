package com.thiagomdo.ba.challenge.msorders.model.response;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("orders")
@EqualsAndHashCode
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

    private String cancelReason;

    private LocalDate cancelDate;

    public OrderResponse(OrderRequest orderRequest, Double subtotalValue, AddressClientViaCepResponse cepResponse) {
        products = orderRequest.getProducts();
        address.setStreet(orderRequest.getAddress().getStreet());
        address.setNumber(orderRequest.getAddress().getNumber());
        address.setComplement(cepResponse.getComplement());
        address.setCity(cepResponse.getCity());
        address.setState(cepResponse.getState());
        address.setPostalCode(orderRequest.getAddress().getPostalCode());

        paymentMethod = orderRequest.getPaymentMethod();
        if (orderRequest.getPaymentMethod().equals(Payment_method.PIX)) {
            discount = 0.05 * subtotalValue;
            totalValue = subtotalValue - discount;
        } else {
            discount = 0.0;
            totalValue = subtotalValue;
        }

        this.subtotalValue = subtotalValue;

        createdDate = LocalDate.now();
        status = Status.CONFIRMED;
    }

    public OrderResponse(String idInDB, OrderRequest orderRequest, Double subtotalValue, AddressClientViaCepResponse cepResponse) {
        id = idInDB;
        products = orderRequest.getProducts();
        address.setStreet(orderRequest.getAddress().getStreet());
        address.setNumber(orderRequest.getAddress().getNumber());
        address.setComplement(cepResponse.getComplement());
        address.setCity(cepResponse.getCity());
        address.setState(cepResponse.getState());
        address.setPostalCode(orderRequest.getAddress().getPostalCode());

        paymentMethod = orderRequest.getPaymentMethod();
        if (orderRequest.getPaymentMethod().equals(Payment_method.PIX)) {
            discount = 0.05 * subtotalValue;
            totalValue = subtotalValue - discount;
        } else {
            discount = 0.0;
            totalValue = subtotalValue;
        }

        this.subtotalValue = subtotalValue;

        createdDate = LocalDate.now();
        status = Status.CONFIRMED;
    }

    public OrderResponse(String id, List<ProductDTO> productDTOList, AddressClientViaCepResponse address, Payment_method paymentMethod,
                         Double subtotalValue, Double discount, Double totalValue, LocalDate createdDate,  Status status){
        this.id = id;
        this.products = productDTOList;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.subtotalValue = subtotalValue;
        this.discount = discount;
        this.totalValue = totalValue;
        this.createdDate = createdDate;
        this.status = status;
    }

    public OrderResponse(OrderDTO orderDTO){
        id = orderDTO.getId();
        products = orderDTO.getProducts();
        address = orderDTO.getAddress();
        paymentMethod = orderDTO.getPaymentMethod();
        subtotalValue = orderDTO.getSubtotalValue();
        discount = orderDTO.getDiscount();
        totalValue = orderDTO.getTotalValue();
        createdDate = orderDTO.getCreatedDate();
        status = orderDTO.getStatus();

    }

    public OrderResponse (OrderRequestActualization actualization){
        status = actualization.getStatus();
        address = new AddressClientViaCepResponse(actualization.getAddress());
    }

}
