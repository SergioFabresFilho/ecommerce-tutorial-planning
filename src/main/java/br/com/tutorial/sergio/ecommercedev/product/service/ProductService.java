package br.com.tutorial.sergio.ecommercedev.product.service;

import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;

public interface ProductService {
    void create(ProductCreateRequest productCreateRequest);
    ProductFindByIdResponse findById(Long id);
}
