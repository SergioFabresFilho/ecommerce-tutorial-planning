package br.com.tutorial.sergio.ecommercedev.product.service;

import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;

public interface ProductService {
    void create(ProductCreateRequest productCreateRequest);
}
