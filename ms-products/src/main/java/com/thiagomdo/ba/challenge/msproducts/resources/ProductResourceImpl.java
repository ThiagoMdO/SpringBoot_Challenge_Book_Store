package com.thiagomdo.ba.challenge.msproducts.resources;

import com.thiagomdo.ba.challenge.msproducts.interfaces.ProductResource;
import com.thiagomdo.ba.challenge.msproducts.model.dto.ProductDTO;
import com.thiagomdo.ba.challenge.msproducts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResourceImpl implements ProductResource {

    @Autowired
    private ProductService productService;

    @Override
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProducts(){
        List<ProductDTO> list = productService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable String id){
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        ProductDTO pDTO = productService.createProduct(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(pDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(pDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO){
        ProductDTO productDTO1 = productService.updateProduct(id, productDTO);

        return ResponseEntity.ok().body(productDTO1);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
