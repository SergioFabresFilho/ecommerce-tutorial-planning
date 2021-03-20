package br.com.tutorial.sergio.ecommercedev.product.domain.mapper;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProductMapperUnitTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void givenValidProductCreateRequestWhenToProductThenReturnProduct() {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();

        final Product product = productMapper.toProduct(productCreateRequest);

        assertThat(product.getName()).isEqualTo(productCreateRequest.getName());
        assertThat(product.getValue()).isEqualTo(productCreateRequest.getValue());
        assertThat(product.getId()).isNull();
    }
}