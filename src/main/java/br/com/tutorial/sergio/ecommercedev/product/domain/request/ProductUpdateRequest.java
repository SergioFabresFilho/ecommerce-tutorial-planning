package br.com.tutorial.sergio.ecommercedev.product.domain.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ProductUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double value;

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
