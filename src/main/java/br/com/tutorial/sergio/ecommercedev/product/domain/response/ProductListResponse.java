package br.com.tutorial.sergio.ecommercedev.product.domain.response;

public class ProductListResponse {
    private Long id;
    private String name;
    private Double value;

    public ProductListResponse() {
    }

    public ProductListResponse(Long id, String name, Double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
