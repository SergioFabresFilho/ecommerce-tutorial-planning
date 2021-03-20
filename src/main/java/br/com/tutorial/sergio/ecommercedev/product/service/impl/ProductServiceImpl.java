package br.com.tutorial.sergio.ecommercedev.product.service.impl;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.mapper.ProductMapper;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.repository.ProductRepository;
import br.com.tutorial.sergio.ecommercedev.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void create(ProductCreateRequest productCreateRequest) {
        Product product = productMapper.toProduct(productCreateRequest);
        productRepository.save(product);
    }
}
