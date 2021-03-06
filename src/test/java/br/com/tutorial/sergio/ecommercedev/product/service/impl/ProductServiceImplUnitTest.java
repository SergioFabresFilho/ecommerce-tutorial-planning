package br.com.tutorial.sergio.ecommercedev.product.service.impl;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.NotFoundException;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.message.ExceptionMessage;
import br.com.tutorial.sergio.ecommercedev.product.domain.mapper.ProductMapper;
import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductUpdateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;
import br.com.tutorial.sergio.ecommercedev.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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

    @Test
    void whenFindAllThenReturnListOfProductListResponse() {
        List<Product> productList = ProductMother.getProductList();
        List<ProductListResponse> productListResponseList = ProductMother.getProductListResponseList();

        given(productRepository.findAll()).willReturn(productList);
        given(productMapper.toProductListResponseList(productList)).willReturn(productListResponseList);

        List<ProductListResponse> response = productService.findAll();

        assertThat(response).isEqualTo(productListResponseList);

        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toProductListResponseList(productList);
    }

    @Test
    void givenValidParametersWhenUpdateThenReturnSuccess() {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();
        Product product = ProductMother.getProduct();

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        productService.update(id, productUpdateRequest);

        verify(productRepository, times(1)).findById(id);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(argumentCaptor.capture());

        Product capturedValue = argumentCaptor.getValue();

        assertThat(capturedValue.getValue()).isEqualTo(productUpdateRequest.getValue());
        assertThat(capturedValue.getName()).isEqualTo(productUpdateRequest.getName());
    }

    @Test
    void givenProductDoesNotExistWhenUpdateThenThrowNotFoundException() {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();

        given(productRepository.findById(id)).willReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> productService.update(id, productUpdateRequest))
                .withMessage(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage());

        verify(productRepository, times(1)).findById(id);
    }
}
