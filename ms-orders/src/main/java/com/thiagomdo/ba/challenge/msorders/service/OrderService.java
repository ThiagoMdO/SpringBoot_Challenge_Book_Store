package com.thiagomdo.ba.challenge.msorders.service;

import com.thiagomdo.ba.challenge.msorders.client.ProductFeign;
import com.thiagomdo.ba.challenge.msorders.client.ViaCepFeign;
import com.thiagomdo.ba.challenge.msorders.client.models.AddressByCep;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.AddressClientViaCepRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import com.thiagomdo.ba.challenge.msorders.model.request.ProductRequest;
import com.thiagomdo.ba.challenge.msorders.model.response.AddressClientViaCepResponse;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import com.thiagomdo.ba.challenge.msorders.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private ViaCepFeign viaCepFeign;

    public List<OrderDTO> getAll() {
        List<OrderResponse> list = orderRepository.findAll();
        if (list.isEmpty()) throw new EmptyListException();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    public OrderDTO getById(String id) {
        OrderResponse orderResponse = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return new OrderDTO(orderResponse);
    }

    public OrderDTO create(OrderRequest request) {
        OrderResponse orderResponse = orderRepository.save(createOrderResponse(request));
        return new OrderDTO(orderResponse);
    }

    public OrderDTO update(String id, OrderRequestActualization requestActualization) {
        OrderResponse orderResponse = orderRepository.save(createOrderResponseToUpdate(id, requestActualization));
        return new OrderDTO(orderResponse);
    }

    private OrderResponse createOrderResponse(OrderRequest request) {
        testStreetAndNumberAddress(request.getAddress().getStreet(), request.getAddress().getNumber());
        double total = countTotProducts(getListProducts(request.getProducts()), request);
        AddressClientViaCepResponse cepResponse = consultViaCepAddress(request.getAddress(), request.getAddress().getNumber());
        return new OrderResponse(request, total, cepResponse);
    }

    private OrderResponse createOrderResponseToUpdate(String id, OrderRequestActualization request) {
        testStreetAndNumberAddress(request.getAddress().getStreet(), request.getAddress().getNumber());
        var orderResponse = new OrderResponse(getById(id));

        AddressClientViaCepResponse cepResponse = consultViaCepAddress(request.getAddress(), request.getAddress().getNumber());

        testStatusDifferentStatusSENTAndOrderGreaterThan90Days(orderResponse);

        if (request.getStatus().equals(Status.CANCELED)) {
            orderResponse.setCancelReason(request.getCancelReason());
            orderResponse.setCancelDate(LocalDate.now());
        }

        orderResponse.setStatus(request.getStatus());
        orderResponse.setAddress(cepResponse);
        orderResponse.getAddress().setStreet(request.getAddress().getStreet());
        orderResponse.getAddress().setNumber(request.getAddress().getNumber());

        return orderResponse;
    }

    private void testStatusDifferentStatusSENTAndOrderGreaterThan90Days(OrderResponse orderResponse){
        if (orderResponse.getStatus().equals(Status.SENT)) throw new NotPossibleToChangeStatusException();
        if (orderResponse.getCreatedDate().plusDays(90).isBefore(LocalDate.now())) throw new NotPossibleToChangeDateException();
    }

    private void testStreetAndNumberAddress(String street, Long number) {
        if (street == null || street.isBlank() || number == null) throw new AddressIncorrectException();
    }

    private double countTotProducts(List<ProductRequest> productRequestList, OrderRequest request) {
        double sumTotal = 0.0;

        for (ProductRequest request1 : productRequestList) {
            for (ProductDTO pDTO2 : request.getProducts()) {
                if (request1 != null && request1.getId().equals(pDTO2.getId())) {
                    double sum = request1.getValue();
                    Long qtd = pDTO2.getQuantity();
                    sum *= qtd;
                    sumTotal += sum;
                }
            }
        }
        return sumTotal;
    }

    private List<ProductRequest> getListProducts(List<ProductDTO> productDTOList) {
        try {
            List<ProductRequest> list = productDTOList.stream().map(productDTO -> productFeign.findProductById(productDTO.getId())).collect(Collectors.toList());
            if (list.isEmpty()) throw new EmptyListException();
            return list;
        } catch (RuntimeException e) {
            throw new ProductNotFoundException();
        }
    }

    private AddressClientViaCepResponse consultViaCepAddress(AddressClientViaCepRequest viaCepRequest, Long number) {
        try {
            AddressByCep addressByCepFeign = viaCepFeign.searchLocationByCep(viaCepRequest.getPostalCode());
            return new AddressClientViaCepResponse(addressByCepFeign, number);
        } catch (RuntimeException e) {
            throw new AddressIncorrectException();
        }
    }


}
