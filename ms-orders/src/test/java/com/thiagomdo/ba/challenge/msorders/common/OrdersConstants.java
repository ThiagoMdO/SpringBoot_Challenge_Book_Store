package com.thiagomdo.ba.challenge.msorders.common;

import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.response.AddressClientViaCepResponse;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdersConstants {
    public static ProductDTO productDTO1 = new ProductDTO("6605903e1e2d5c55c2017225", 12L);
    public static ProductDTO productDTO2 = new ProductDTO("6605903e1e2d5c55c2017226", 32L);
    public static ProductDTO productDTO3 = new ProductDTO("6605903e1e2d5c55c2017228", 12L);
    public static ProductDTO productDTO4 = new ProductDTO("6605903e1e2d5c55c2017221", 41L);

    public static List<ProductDTO> productDTOList = Arrays.asList(productDTO1, productDTO2);
    public static List<ProductDTO> productDTOList2 = Arrays.asList(productDTO3, productDTO4);


    public static AddressClientViaCepResponse viaCepResponse = new AddressClientViaCepResponse(
    "Praça da Sé", 100L, "lado ímpar", "São Paulo", "SP", "01001-000"
    );

    public static AddressClientViaCepResponse viaCepResponse2 = new AddressClientViaCepResponse(
    "Rua Manoel", 230L, "", "Pintadas", "BA", "44610-000"
    );

    public static final OrderResponse ORDER_RESPONSE = new OrderResponse(
    "6605903e1e2d5c55c2017777", productDTOList, viaCepResponse, Payment_method.PIX, 100.00, 5.00, 95.00, LocalDate.now(), Status.CONFIRMED
    );
    public static final OrderResponse ORDER_RESPONSE2 = new OrderResponse(
    "6605903e1e2d5c55c2017888", productDTOList2, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00, 0.00, 200.00, LocalDate.now(), Status.CONFIRMED
    );

    public static final OrderDTO ORDER_RESPONSE_DTO = new OrderDTO(ORDER_RESPONSE);
    public static final OrderDTO ORDER_RESPONSE2_DTO = new OrderDTO(ORDER_RESPONSE2);


    public static final List<OrderResponse> ORDER_RESPONSE_LIST = Arrays.asList(ORDER_RESPONSE, ORDER_RESPONSE2);

    public static final List<OrderDTO> ORDER_RESPONSE_LIST_DTO = Arrays.asList(ORDER_RESPONSE_DTO, ORDER_RESPONSE2_DTO);



}
