package com.thiagomdo.ba.challenge.msorders.interfaces;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestCancel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderSwaggerController {
    @Operation(
        operationId = "getAllOrders",
        summary = "Return all orders.",
        description = "Returns a list of all available orders.",
        tags = { "Orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class)))
            }),
            @ApiResponse(responseCode = "x-200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.EmptyListException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    ResponseEntity<List<OrderDTO>> getAllOrders();

    @Operation(
        operationId = "getOrderById",
        summary = "Return order by Id.",
        description = "Returns a specific order by Id.",
        tags = { "Orders by Id" },
        parameters = {
            @Parameter(name = "id", description = "Search order by Id", required = true, example = "6605903e1e2d5c55c2017225")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    ResponseEntity<OrderDTO> getOrderById(@PathVariable String id);

    @Operation(
        operationId = "createOrder",
        summary = "Create order.",
        description = "It's possible to create a new order.",
        tags = { "Orders" },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create Order Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTORequest1.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class)))
            }),
            @ApiResponse(responseCode = "400", description = "AddressIncorrectException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.AddressIncorrectException.class)))
            }),
            @ApiResponse(responseCode = "404", description = "ProductNotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductNotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            }),
            @ApiResponse(responseCode = "422", description = "Feign Exception", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ERRORWHENSEARCHINGFORFEIGN.class)))
            })
        }
    )
    ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest request);

    @Operation(
        operationId = "updateOrder",
        summary = "Update a order",
        description = "Update Order a specific order by Id.",
        tags = { "Orders by Id" },
        parameters = {
            @Parameter(name = "id", description = "Search order by Id", required = true, example = "6605903e1e2d5c55c2017225")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Update Order Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderRequestActualization.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class)))
            }),
            @ApiResponse(responseCode = "400", description = "NotPossibleToChangeStatusException, if order was CANCELED or SENT Status", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotPossibleToChangeDateException.class)))
            }),
            @ApiResponse(responseCode = "x-400", description = "AddressIncorrectException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.AddressIncorrectException.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            }),
            @ApiResponse(responseCode = "422", description = "Feign Exception", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ERRORWHENSEARCHINGFORFEIGN.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    ResponseEntity<OrderDTO> updateOrder(@PathVariable String id, @RequestBody OrderRequestActualization actualization);

    @Operation(
        operationId = "canceledOrder",
        summary = "Canceled a Order",
        description = "To Cancel a specific order by Id.",
        tags = { "Orders by Id" },
        parameters = {
            @Parameter(name = "id", description = "Search order by Id", required = true, example = "6605903e1e2d5c55c2017225")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Reason to cancel this Order Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderToCancel.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTOCanceled.class)))
            }),
            @ApiResponse(responseCode = "400", description = "NotPossibleToChangeStatusException, if order was CANCELED or SENT Status", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotPossibleToChangeStatusException.class)))
            }),
            @ApiResponse(responseCode = "x-400", description = "NotPossibleToChangeDateException (where cancellation is attempted on an order placed more than 90 days ago).", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotPossibleToChangeDateException.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    ResponseEntity<OrderDTO> canceledOrder(@PathVariable String id, @RequestBody OrderRequestCancel requestCancel);
}
