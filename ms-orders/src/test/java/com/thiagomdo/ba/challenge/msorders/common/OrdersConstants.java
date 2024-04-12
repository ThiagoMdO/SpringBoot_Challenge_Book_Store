package com.thiagomdo.ba.challenge.msorders.common;

import com.thiagomdo.ba.challenge.msorders.client.models.AddressByCep;
import com.thiagomdo.ba.challenge.msorders.enuns.Payment_method;
import com.thiagomdo.ba.challenge.msorders.enuns.Status;
import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.*;
import com.thiagomdo.ba.challenge.msorders.model.response.AddressClientViaCepResponse;
import com.thiagomdo.ba.challenge.msorders.model.response.OrderResponse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OrdersConstants {
    public static ProductDTO productDTO1 = new ProductDTO("6616929eb63057298df31491", 10L);
    public static ProductDTO productDTO2 = new ProductDTO("6616929eb63057298df31490", 10L);
    public static ProductDTO productDTO3 = new ProductDTO("6605903e1e2d5c55c2017228", 12L);
    public static ProductDTO productDTO4 = new ProductDTO("6605903e1e2d5c55c2017221", 41L);
    public static ProductDTO productDTO_WITH_ID_INVALID = new ProductDTO("InvalidIdProduct", 41L);

    public static List<ProductDTO> productDTOList = Arrays.asList(productDTO1, productDTO2);
    public static List<ProductDTO> productDTOList2 = Arrays.asList(productDTO3, productDTO4);
    public static List<ProductDTO> productDTOList_WITH_ID_INVALID = Arrays.asList(productDTO_WITH_ID_INVALID, productDTO1);


    public static AddressClientViaCepResponse viaCepResponse = new AddressClientViaCepResponse(
    "Praça da Sé", 100L, "lado ímpar", "São Paulo", "SP", "01001000"
    );

    public static AddressClientViaCepResponse viaCepResponse2 = new AddressClientViaCepResponse(
    "Rua Manoel", 230L, "", "Pintadas", "BA", "44610000"
    );
    public static AddressClientViaCepResponse viaCepResponseInvalidFields = new AddressClientViaCepResponse(
    null, null, "", "Pintadas", "BA", "44610000"
    );

    public static final OrderResponse ORDER_RESPONSE = new OrderResponse(
    "6605903e1e2d5c55c2017777", productDTOList, viaCepResponse, Payment_method.PIX, 100.00,
    5.00, 95.00, LocalDate.now(), Status.CONFIRMED
    );
    public static final OrderResponse ORDER_RESPONSE2 = new OrderResponse(
    "6605903e1e2d5c55c2017888", productDTOList2, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00,
    0.00, 200.00, LocalDate.now(), Status.CONFIRMED
    );

    public static final OrderResponse ORDER_RESPONSE_WITH_INVALID_ADDRESS = new OrderResponse(
    "6605903e1e2d5c55c2017840", productDTOList2, viaCepResponseInvalidFields, Payment_method.BANK_TRANSFER, 200.00,
    0.00, 200.00, LocalDate.now(), Status.CANCELED,"Cancel Reason" , LocalDate.now()
    );

    public static final OrderResponse ORDER_RESPONSE_WITH_ID_PRODUCT_INCORRECT = new OrderResponse(
    "6605903e1e2d5c55c2017888", productDTOList_WITH_ID_INVALID, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00,
    0.00, 200.00, LocalDate.now(), Status.CONFIRMED
    );
    public static final OrderResponse ORDER_RESPONSE_WITH_STATUS_SENT = new OrderResponse(
    "6605903e1e2d5c55c2017888", productDTOList2, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00,
    0.00,200.00, LocalDate.now(), Status.SENT
    );
    public static final OrderResponse ORDER_RESPONSE_WITH_DATE_GREATER_THEN_90_DAYS = new OrderResponse(
    "6605903e1e2d5c55c2017888", productDTOList2, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00,
    0.00,200.00, LocalDate.now().minusDays(91), Status.CONFIRMED
    );
    public static final OrderResponse ORDER_RESPONSE_WITH_STATUS_CANCELED = new OrderResponse(
    "6605903e1e2d5c55c2017840", productDTOList2, viaCepResponse2, Payment_method.BANK_TRANSFER, 200.00,
    0.00,200.00, LocalDate.now(), Status.CANCELED,"Cancel Reason" , LocalDate.now()
    );




    public static final OrderDTO ORDER_RESPONSE_DTO = new OrderDTO(ORDER_RESPONSE);
    public static final OrderDTO ORDER_RESPONSE2_DTO = new OrderDTO(ORDER_RESPONSE2);


    public static final List<OrderResponse> ORDER_RESPONSE_LIST = Arrays.asList(
    ORDER_RESPONSE, ORDER_RESPONSE2);

    public static final List<OrderDTO> ORDER_RESPONSE_LIST_DTO = Arrays.asList(
    ORDER_RESPONSE_DTO, ORDER_RESPONSE2_DTO);


    // Management OrderRequest
    public static ProductRequest PRODUCT_REQUEST1 = new ProductRequest("6605903e1e2d5c55c2017778", 100.00);
    public static ProductDTO PRODUCT_DT01 = new ProductDTO("6605903e1e2d5c55c2017778", 10L);
    public static ProductDTO PRODUCT_DT02 = new ProductDTO("6605903e1e2d5c55c2017779", 10L);

    public static List<ProductDTO> productDTO_LIST = Arrays.asList(PRODUCT_DT01, PRODUCT_DT02);

    public static AddressClientViaCepRequest VIA_CEP_REQUEST = new AddressClientViaCepRequest(
    "Rua 1", 121L, "01001000");
    public static AddressClientViaCepRequest VIA_CEP_REQUEST_44610000 = new AddressClientViaCepRequest(
    "Rua pintadas", 10L, "44610000");
    public static AddressClientViaCepRequest VIA_CEP_REQUEST_FIELDS_INVALID = new AddressClientViaCepRequest(
    null, null, "01001000");
    public static AddressClientViaCepRequest VIA_CEP_REQUEST_FIELDS2_INVALID = new AddressClientViaCepRequest(
    "", null, "01001000");
    public static AddressClientViaCepRequest VIA_CEP_REQUEST_FIELDS3_INVALID = new AddressClientViaCepRequest(
    " ", 15L, "01001000");
    public static AddressClientViaCepRequest VIA_CEP_REQUEST_CEP_INVALID = new AddressClientViaCepRequest(
    "Rua 1", 121L, "123");

    public static AddressByCep ADDRESS_BY_CEP = new AddressByCep("Praça da Sé", "lado ímpar",
    "Sé", "São Paulo", "SP", "01001000");
    public static AddressByCep ADDRESS_BY_CEP2 = new AddressByCep("Rua Manoel", "",
    "", "Pintadas", "BA", "44610000");
    public static AddressByCep ADDRESS_BY_CEP_44610000 = new AddressByCep("Rua principal", "lado ímpar",
    "Centro", "Pintadas", "BA", "44610000");
    public static AddressByCep ADDRESS_BY_CEP_INCORRECT_FIELDS = new AddressByCep("Praça da Sé", "lado ímpar",
    "Sé", "São Paulo", "SP", "01001000");

    public static OrderRequest ORDER_REQUEST = new OrderRequest(productDTOList, VIA_CEP_REQUEST, Payment_method.PIX);
    public static OrderRequest ORDER_REQUEST_WITH_ADDRESS_INVALID = new OrderRequest(
    productDTOList, VIA_CEP_REQUEST_FIELDS_INVALID, Payment_method.PIX);
    public static OrderRequest ORDER_REQUEST_WITH_ADDRESS2_INVALID = new OrderRequest(
    productDTOList, VIA_CEP_REQUEST_FIELDS2_INVALID, Payment_method.PIX);
    public static OrderRequest ORDER_REQUEST_WITH_ADDRESS3_INVALID = new OrderRequest(
    productDTOList, VIA_CEP_REQUEST_FIELDS3_INVALID, Payment_method.PIX);
    public static OrderRequest ORDER_REQUEST_WITH_ID_PRODUCT_INVALID = new OrderRequest(
    productDTOList_WITH_ID_INVALID, VIA_CEP_REQUEST, Payment_method.PIX);
    public static OrderRequest ORDER_REQUEST_WITH_CEP_INVALID = new OrderRequest(
    productDTOList, VIA_CEP_REQUEST_CEP_INVALID, Payment_method.PIX);
    public static AddressClientViaCepResponse VIA_CEP_RESPONSE = new AddressClientViaCepResponse(
    ADDRESS_BY_CEP, ORDER_REQUEST.getAddress().getNumber());
    public static AddressClientViaCepResponse VIA_CEP_RESPONSE_ADDRESS_INVALID = new AddressClientViaCepResponse(
    ADDRESS_BY_CEP_INCORRECT_FIELDS, ORDER_REQUEST_WITH_ADDRESS_INVALID.getAddress().getNumber());
    public static AddressClientViaCepResponse VIA_CEP_RESPONSE_TO_CREATE = new AddressClientViaCepResponse(
    ADDRESS_BY_CEP_INCORRECT_FIELDS, ORDER_REQUEST.getAddress().getNumber());


    public static final OrderResponse ORDER_RESPONSE_INVALID_ADDRESS = new OrderResponse(
    "6605903e1e2d5c55c2017889", productDTOList, VIA_CEP_RESPONSE_ADDRESS_INVALID, Payment_method.BANK_TRANSFER, 200.00, 0.00, 200.00, LocalDate.now(), Status.CONFIRMED
    );

    public static final OrderResponse ORDER_RESPONSE_TO_CREATE = new OrderResponse("6605903e1e2d5c55c2017890", ORDER_REQUEST, 1500.00, VIA_CEP_RESPONSE_TO_CREATE);

    public static final OrderDTO ORDER_RESPONSE_INVALID_ADDRESS_DTO = new OrderDTO(ORDER_RESPONSE_INVALID_ADDRESS);
    public static final OrderDTO ORDER_RESPONSE_TO_CREATE_DTO = new OrderDTO(ORDER_RESPONSE_TO_CREATE);


    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION = new OrderRequestActualization(
    Status.CONFIRMED, VIA_CEP_REQUEST);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_STATUS_CANCELED = new OrderRequestActualization(
    Status.CANCELED, VIA_CEP_REQUEST);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_STATUS_SENT = new OrderRequestActualization(
    Status.SENT, VIA_CEP_REQUEST);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_WITH_CEP_INCORRECT = new OrderRequestActualization(
    Status.CONFIRMED, VIA_CEP_REQUEST_CEP_INVALID);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS_INCORRECT = new OrderRequestActualization(
    Status.CONFIRMED, VIA_CEP_REQUEST_FIELDS_INVALID);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS2_INCORRECT = new OrderRequestActualization(
    Status.CONFIRMED, VIA_CEP_REQUEST_FIELDS2_INVALID);
    public static final OrderRequestActualization ORDER_REQUEST_ACTUALIZATION_WITH_FIELDS3_INCORRECT = new OrderRequestActualization(
    Status.CONFIRMED, VIA_CEP_REQUEST_FIELDS3_INVALID);


    public static final OrderResponse ORDER_RESPONSE_TO_CANCELED = new OrderResponse(
    "6605903e1e2d5c55c2017777", productDTOList, viaCepResponse, Payment_method.PIX, 100.00,
    5.00, 95.00, LocalDate.now(), Status.CONFIRMED
    );

    public static final OrderResponse ORDER_RESPONSE_SENT = new OrderResponse(
    "6605903e1e2d5c55c2017988", productDTOList, viaCepResponse, Payment_method.PIX, 100.00,
    5.00, 95.00, LocalDate.now(), Status.SENT
    );

    public static final OrderResponse ORDER_RESPONSE_GREATER_THEN_90_DAYS = new OrderResponse(
    "6605903e1e2d5c55c2017988", productDTOList, viaCepResponse, Payment_method.PIX, 100.00,
    5.00, 95.00, LocalDate.now().minusDays(91), Status.CONFIRMED
    );

    public static final OrderResponse ORDER_RESPONSE_CANCELED = new OrderResponse(
    "6605903e1e2d5c55c2017777", productDTOList, viaCepResponse, Payment_method.PIX, 100.00,
    5.00, 95.00, LocalDate.now(), Status.CANCELED, "Cancel Reason", LocalDate.now()
    );

    public static final OrderDTO ORDER_RESPONSE_CANCELED_DTO = new OrderDTO(ORDER_RESPONSE_CANCELED);
    public static final OrderDTO ORDER_RESPONSE_SENT_DTO = new OrderDTO(ORDER_RESPONSE_SENT);

    public static final OrderRequestCancel ORDER_REQUEST_CANCEL = new OrderRequestCancel("Cancel Reason");
}
