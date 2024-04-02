package com.thiagomdo.ba.challenge.msorders.config;

import com.thiagomdo.ba.challenge.msorders.client.ProductFeign;
import com.thiagomdo.ba.challenge.msorders.client.ViaCepFeign;
import com.thiagomdo.ba.challenge.msorders.client.models.AddressByCep;
import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.AddressClientViaCepRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.response.AddressClientViaCepResponse;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;
import com.thiagomdo.ba.challenge.msorders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ViaCepFeign viaCepFeign;

    @Autowired
    private ProductFeign productFeign;

    @Override
    public void run(String... args) throws Exception {

        orderRepository.deleteAll();

        ProductDTO p1 = new ProductDTO("6605903e1e2d5c55c2017277", 10L);
        ProductDTO p2 = new ProductDTO("6605903e1e2d5c55c2017278", 12L);

//        ProductDTO pDTO1 = productFeign.findProductById("6605cf4356de767ab57abb6f");
//        ProductDTO pDTO2 = productFeign.findProductById("6605cf4356de767ab57abb71");
//
//        pDTO1.setQuantity(10L);
//        pDTO2.setQuantity(20L);

        List<ProductDTO> listProducts1 = Arrays.asList(p1, p2);

        AddressClientViaCepRequest newAddress = new AddressClientViaCepRequest();
        newAddress.setStreet("Street");
        newAddress.setNumber(10L);
        newAddress.setPostalCode("01001000");

        AddressByCep addressByCepFeign = viaCepFeign.searchLocationByCep("01001000");


        AddressClientViaCepResponse cepResponse = new AddressClientViaCepResponse(addressByCepFeign, newAddress.getNumber());


        OrderRequest oR1 = new OrderRequest();

        oR1.setProducts(listProducts1);
        oR1.setAddress(newAddress);
        oR1.setPaymentMethod(Payment_method.PIX);

        Double productValue = 100.0;

        OrderResponse o1 = new OrderResponse(oR1, productValue, cepResponse);
        o1.setSubtotalValue(productValue);
        o1.setId("6605903e1e2d5c55c2017225");

        orderRepository.save(o1);


        ProductDTO p3 = new ProductDTO("6605903e1e2d5c55c2017219", 112L);
        ProductDTO p4 = new ProductDTO("6605903e1e2d5c55c2017154", 60L);

//        ProductDTO pDTO1 = productFeign.findProductById("6605cf4356de767ab57abb6f");
//        ProductDTO pDTO2 = productFeign.findProductById("6605cf4356de767ab57abb71");
//
//        pDTO1.setQuantity(10L);
//        pDTO2.setQuantity(20L);

        List<ProductDTO> listProducts2 = Arrays.asList(p3, p4);

        AddressClientViaCepRequest newAddress2 = new AddressClientViaCepRequest();
        newAddress2.setStreet("Street 2");
        newAddress2.setNumber(120L);
        newAddress2.setPostalCode("44610000");

        AddressByCep addressByCepFeign2 = viaCepFeign.searchLocationByCep(newAddress2.getPostalCode());


        AddressClientViaCepResponse cepResponse2 = new AddressClientViaCepResponse(addressByCepFeign2, newAddress2.getNumber());


        OrderRequest oR2 = new OrderRequest();

        oR2.setProducts(listProducts2);
        oR2.setAddress(newAddress2);
        oR2.setPaymentMethod(Payment_method.BANK_TRANSFER);

        Double productValue2 = 200.0;

        OrderResponse o2 = new OrderResponse(oR2, productValue2, cepResponse2);
        o2.setSubtotalValue(productValue2);
        o2.setId("6605903e1e2d5c55c2017223");

        orderRepository.save(o2);
    }

}
