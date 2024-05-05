package com.thiagomdo.ba.challenge.msproducts.interfaces;

import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
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

public interface ProductSwaggerController {
    @Operation(
        operationId = "findAllProducts",
        summary = "Returns all of products.",
        description = "Return a list of all available products.",
        tags = { "Products" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "x-200 ", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.EmptyListException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    ResponseEntity<List<ProductDTO>> findAllProducts();

    @Operation(
        operationId = "findProductById",
        summary = "Returns product by Id.",
        description = "Returns a specific Product by Id.",
        tags = { "Products by Id" },
        parameters = {
            @Parameter(name = "id", description = "Search product by Id.", required = true, example = "6616929eb63057298df31490")
            },
            responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "404", description = "NotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    ResponseEntity<ProductDTO> findProductById(@PathVariable String id);

    @Operation(
        operationId = "createProduct",
        summary = "Create Product",
        description = "Create a new Product with name different.",
        tags = {"Products"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create Product Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductRequestCreate.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "MinDescriptionException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.MinDescriptionException.class)))
            }),
            @ApiResponse(responseCode = "x-400", description = "MinValueException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.MinValueException.class)))
            }),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductAlreadyExistException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO);

    @Operation(
        operationId = "updateProduct",
        summary = "Update Product",
        description = "Update some specifics fields in a product",
        tags = {"Products by Id"},
        parameters = {
            @Parameter(name = "id", description = "Search Product by Id", required = true, example = "6616929eb63057298df31490")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create Product Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductRequestCreate.class)
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductAlreadyExistException.class)))
            }),
            @ApiResponse(responseCode = "400", description = "MinDescriptionException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.MinDescriptionException.class)))
            }),
            @ApiResponse(responseCode = "x-400", description = "MinValueException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.MinValueException.class)))
            }),
            @ApiResponse(responseCode = "404", description = "NotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO);



    @Operation(
        operationId = "deleteProductById",
        summary = "Delete Product",
        description = "Delete a product by Id",
        tags = {"Products by Id"},
        parameters = {
            @Parameter(name = "id", description = "Search Product by Id", required = true, example = "6616929eb63057298df31490")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "NotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    ResponseEntity<Void> deleteProductById(@PathVariable String id);
}
