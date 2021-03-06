package br.com.tutorial.sergio.ecommercedev.product.controller;

import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductUpdateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;
import br.com.tutorial.sergio.ecommercedev.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        productService.create(productCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductListResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFindByIdResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        productService.update(id, productUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
