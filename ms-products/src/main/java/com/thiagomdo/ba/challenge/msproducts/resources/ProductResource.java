package com.thiagomdo.ba.challenge.msproducts.resources;

import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {


    @Autowired
    private ProductService productService;

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
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts(){
        List<ProductDTO> list = productService.findAll();
        return ResponseEntity.ok().body(list);
    }



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
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable String id){
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

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
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        ProductDTO pDTO = productService.createProduct(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(pDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(pDTO);
    }

    @Operation(
        operationId = "updateProduct",
        summary = "Update Product",
        description = "Update some specifics fields in a product",
        tags = {"/products/{id}"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create Product Request",
            required = true,
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductRequestCreate.class)
        )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Updated", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductDTO.class)))
            }),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.ProductAlreadyExistException.class)))
            }),
            @ApiResponse(responseCode = "400", description = "MinDescriptionException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.MinDescriptionException.class)))
            }),
            @ApiResponse(responseCode = " 400 ", description = "MinValueException", content = {
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
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO){
        ProductDTO productDTO1 = productService.updateProduct(id, productDTO);

        return ResponseEntity.ok().body(productDTO1);
    }

    @Operation(
        operationId = "deleteProductById",
        summary = "Delete Product",
        description = "Delete a product by Id",
        tags = {"/products/{id}"},
        responses = {
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "NotFoundException", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotFoundException.class)))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
            })
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
