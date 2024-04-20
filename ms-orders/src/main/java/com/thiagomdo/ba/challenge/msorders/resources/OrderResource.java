package com.thiagomdo.ba.challenge.msorders.resources;

import com.thiagomdo.ba.challenge.msorders.model.dto.OrderDTO;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequest;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestActualization;
import com.thiagomdo.ba.challenge.msorders.model.request.OrderRequestCancel;
import com.thiagomdo.ba.challenge.msorders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Operation(
        operationId = "getAllOrders",
        summary = "Return all orders.",
        description = "Returns a list of all available orders.",
        tags = { "Orders" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class)))
            }),
            @ApiResponse(responseCode = "200x", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.EmptyListException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> list = orderService.getAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
        operationId = "getOrderById",
        summary = "Return order by Id.",
        description = "Returns a specific order by Id.",
        tags = { "Orders by Id" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            })
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id){
        OrderDTO orderDTO = orderService.getById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

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
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest request){
        OrderDTO orderDTO = orderService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(orderDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(orderDTO);
    }

    @Operation(
        operationId = "updateOrder",
        summary = "Update a order",
        description = "Update Order a specific order by Id.",
        tags = { "Orders by Id" },
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
            @ApiResponse(responseCode = "400x", description = "AddressIncorrectException", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.AddressIncorrectException.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            }),
            @ApiResponse(responseCode = "404x", description = "ProductNotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductNotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            }),
            @ApiResponse(responseCode = "503", description = "Feign Exception", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ERRORWHENSEARCHINGFORFEIGN.class)))
            })
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String id, @RequestBody OrderRequestActualization actualization){
        OrderDTO orderDTO = orderService.update(id, actualization);

        return ResponseEntity.ok().body(orderDTO);
    }

    @Operation(
        operationId = "canceledOrder",
        summary = "Canceled a Order",
        description = "To Cancel a specific order by Id.",
        tags = { "Orders by Id" },
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
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderDTO1.class)))
                }),
                @ApiResponse(responseCode = "400", description = "NotPossibleToChangeStatusException, if order was CANCELED or SENT Status", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotPossibleToChangeDateException.class)))
                }),
                @ApiResponse(responseCode = "400x", description = "AddressIncorrectException", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.AddressIncorrectException.class)))
                }),
                @ApiResponse(responseCode = "404", description = "Not Found", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
                }),
                @ApiResponse(responseCode = "404x", description = "ProductNotFoundException", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductNotFoundException.class)))
                }),
                @ApiResponse(responseCode = "500", description = "SERVICE UNAVAILABLE", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
                }),
                @ApiResponse(responseCode = "503", description = "Feign Exception", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ERRORWHENSEARCHINGFORFEIGN.class)))
                })
        }
    )
    @PostMapping("/{id}")
    public ResponseEntity<OrderDTO> canceledOrder(@PathVariable String id, @RequestBody OrderRequestCancel requestCancel){
        OrderDTO orderDTO = orderService.cancel(id, requestCancel);
        return ResponseEntity.ok().body(orderDTO);
    }
}
