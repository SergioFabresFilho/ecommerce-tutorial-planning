package br.com.tutorial.sergio.ecommercedev.product.service.impl;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.NotFoundException;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.message.ExceptionMessage;
import br.com.tutorial.sergio.ecommercedev.product.domain.mapper.ProductMapper;
import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        Product product = ProductMother.getProduct();

        given(productMapper.toProduct(productCreateRequest)).willReturn(product);

        productService.create(productCreateRequest);

        verify(productMapper, times(1)).toProduct(productCreateRequest);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void givenValidIdWhenFindByIdThenReturnSuccess() {
        Long id = 1L;
        Product product = ProductMother.getProduct();
        ProductFindByIdResponse productFindByIdResponse = ProductMother.getProductFindByIdResponse();

        given(productRepository.findById(id)).willReturn(Optional.of(product));
        given(productMapper.toProductFindByIdResponse(product)).willReturn(productFindByIdResponse);

        ProductFindByIdResponse response = productService.findById(id);

        assertThat(response).isEqualTo(productFindByIdResponse);

        verify(productRepository, times(1)).findById(id);
        verify(productMapper, times(1)).toProductFindByIdResponse(product);
    }

    @Test
    void givenInvalidIdWhenFindByIdThenThrowNotFoundException() {
        Long id = 1L;

        given(productRepository.findById(id)).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> productService.findById(id))
                .withMessage(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage());

        verify(productRepository, times(1)).findById(id);
    }
}
