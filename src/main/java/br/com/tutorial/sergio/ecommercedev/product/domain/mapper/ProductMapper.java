package br.com.tutorial.sergio.ecommercedev.product.domain.mapper;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreateRequest productCreateRequest);
    ProductFindByIdResponse toProductFindByIdResponse(Product product);
    List<ProductListResponse> toProductListResponseList(List<Product> productList);
}
