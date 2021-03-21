package br.com.tutorial.sergio.ecommercedev.product.domain.exception.message;

public enum ExceptionMessage {
    PRODUCT_NOT_FOUND("Produto não encontrado.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
