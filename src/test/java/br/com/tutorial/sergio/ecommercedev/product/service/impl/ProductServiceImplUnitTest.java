package br.com.tutorial.sergio.ecommercedev.product.service.impl;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.mapper.ProductMapper;
import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplUnitTest {

    private final ProductMapper productMapper = Mockito.mock(ProductMapper.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final ProductServiceImpl productService = new ProductServiceImpl(productMapper, productRepository);

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(productMapper, productRepository);
    }

    @Test
    void givenValidProductCreateRequestWhenCreateThenReturnSuccess() {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        final Product product = ProductMother.getProduct();

        given(productMapper.toProduct(productCreateRequest)).willReturn(product);

        productService.create(productCreateRequest);

        verify(productMapper, times(1)).toProduct(productCreateRequest);
        verify(productRepository, times(1)).save(product);
    }
}
