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

        ProductDTO p1 = new ProductDTO("6605903e1e2d5c55c2017277", 132L);
        ProductDTO p2 = new ProductDTO("6605903e1e2d5c55c2017278", 100L);

        ProductDTO pDTO1 = productFeign.findProductById("6605cf4356de767ab57abb6f");
        ProductDTO pDTO2 = productFeign.findProductById("6605cf4356de767ab57abb71");

        pDTO1.setQuantity(10L);
        pDTO2.setQuantity(20L);

        List<ProductDTO> listProducts1 = new ArrayList<>() {
            {
                addAll(Arrays.asList(pDTO1, pDTO2));
            }
        };



        AddressClientViaCepRequest newAddress = new AddressClientViaCepRequest();
        newAddress.setStreet("Street");
        newAddress.setNumber(10L);
        newAddress.setPostalCode("01001000");

        AddressByCep addressByCepFeign = viaCepFeign.searchLocationByCep("01001000");


        System.out.println(addressByCepFeign);

        AddressClientViaCepResponse cepResponse = new AddressClientViaCepResponse(addressByCepFeign, newAddress.getNumber());


        OrderRequest oR1 = new OrderRequest();

        oR1.setProducts(listProducts1);
        oR1.setAddress(newAddress);
        oR1.setPaymentMethod(Payment_method.PIX);

        Double productValue = 100.0;

        OrderResponse o1 = new OrderResponse(oR1, productValue, cepResponse);
        o1.setSubtotalValue(productValue);
        o1.setId("6605903e1e2d5c55c2017225");
//        o1.getAddress().setCity("City");
//        o1.getAddress().setState("State");
//        o1.getAddress().setComplement("Complement");

        orderRepository.save(o1);


        ProductDTO p3 = new ProductDTO("6605903e1e2d5c55c2017279", 12L);
        ProductDTO p4 = new ProductDTO("6605903e1e2d5c55c2017280", 10L);
        ProductDTO p5 = new ProductDTO("6605903e1e2d5c55c2017290", 10L);

        List<ProductDTO> listProducts2 = new ArrayList<>() {
            {
                addAll(Arrays.asList(p3, p4, p5));
            }
        };

        AddressClientViaCepRequest newAddress2 = new AddressClientViaCepRequest();
        newAddress2.setStreet("Street2");
        newAddress2.setNumber(156L);
        newAddress2.setPostalCode("31333334");

        OrderRequest oR2 = new OrderRequest();

        oR2.setProducts(listProducts2);
        oR2.setAddress(newAddress2);
        oR2.setPaymentMethod(Payment_method.PIX);

        Double productValue2 = 300.0;

        OrderResponse o2 = new OrderResponse(oR2, productValue2, cepResponse);
        o2.setSubtotalValue(productValue);
        o2.setId("6605903e1e2d5c55c2017245");
        o2.getAddress().setCity("City2");
        o2.getAddress().setState("State2");
        o2.getAddress().setComplement("Complement2");

        orderRepository.save(o2);
    }

}
