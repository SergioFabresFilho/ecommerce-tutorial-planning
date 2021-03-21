package br.com.tutorial.sergio.ecommercedev.product.domain.mother;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductUpdateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductMother {

    private ProductMother() {
    }

    public static Product getProduct() {
        return new Product(1L, "product", 10.99);
    }

    public static ProductCreateRequest getProductCreateRequest() {
        return new ProductCreateRequest("product create request", 20.99);
    }

    public static ProductFindByIdResponse getProductFindByIdResponse() {
        return new ProductFindByIdResponse(1L, "product find by id response", 30.99);
    }

    public static List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();

        productList.add(getProduct());
        productList.add(getProduct());
        productList.add(getProduct());

        return productList;
    }

    public static ProductListResponse getProductListResponse() {
        return new ProductListResponse(1L, "product list response", 40.99);
    }

    public static List<ProductListResponse> getProductListResponseList() {
        List<ProductListResponse> productListResponseList = new ArrayList<>();

        productListResponseList.add(getProductListResponse());
        productListResponseList.add(getProductListResponse());
        productListResponseList.add(getProductListResponse());

        return productListResponseList;
    }

    public static ProductUpdateRequest getProductUpdateRequest() {
        return new ProductUpdateRequest("product update request", 50.99);
    }
}
