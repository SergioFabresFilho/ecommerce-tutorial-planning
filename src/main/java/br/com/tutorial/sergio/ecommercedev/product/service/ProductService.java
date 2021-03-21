package br.com.tutorial.sergio.ecommercedev.product.service;

import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;

import java.util.List;

public interface ProductService {
    void create(ProductCreateRequest productCreateRequest);
    ProductFindByIdResponse findById(Long id);
    List<ProductListResponse> findAll();
}
