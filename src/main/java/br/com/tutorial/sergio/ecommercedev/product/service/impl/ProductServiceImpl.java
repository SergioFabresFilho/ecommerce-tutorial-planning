package br.com.tutorial.sergio.ecommercedev.product.service.impl;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.NotFoundException;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.message.ExceptionMessage;
import br.com.tutorial.sergio.ecommercedev.product.domain.mapper.ProductMapper;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductUpdateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;
import br.com.tutorial.sergio.ecommercedev.product.repository.ProductRepository;
import br.com.tutorial.sergio.ecommercedev.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public ProductFindByIdResponse findById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return productMapper.toProductFindByIdResponse(
                product.orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND)));
    }

    @Override
    public List<ProductListResponse> findAll() {
        List<Product> productList = productRepository.findAll();

        return productMapper.toProductListResponseList(productList);
    }

    @Override
    public void update(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));

        product.setName(productUpdateRequest.getName());
        product.setValue(productUpdateRequest.getValue());

        productRepository.save(product);
    }
}
