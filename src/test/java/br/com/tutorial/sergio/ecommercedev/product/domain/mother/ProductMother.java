package br.com.tutorial.sergio.ecommercedev.product.domain.mother;

import br.com.tutorial.sergio.ecommercedev.product.domain.entity.Product;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;

public class ProductMother {

    private ProductMother() {
    }

    public static Product getProduct() {
        return new Product(1L, "product name", 10.99);
    }

    public static ProductCreateRequest getProductCreateRequest() {
        return new ProductCreateRequest("product name", 10.99);
    }
}
