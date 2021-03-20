package br.com.tutorial.sergio.ecommercedev.product.controller;

import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerUnitTest {

    private final String CREATE_PRODUCT_V1 = "/v1/products";

    private final ProductService productService = Mockito.mock(ProductService.class);
    private final ProductController productController = new ProductController(productService);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(productService);
    }

    @Test
    void givenValidRequestWhenCreateThenReturnCreated() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isCreated());

        ArgumentCaptor<ProductCreateRequest> argumentCaptor = ArgumentCaptor.forClass(ProductCreateRequest.class);
        verify(productService, times(1)).create(argumentCaptor.capture());

        final ProductCreateRequest capturedRequest = argumentCaptor.getValue();
        assertThat(productCreateRequest.getName()).isEqualTo(capturedRequest.getName());
        assertThat(productCreateRequest.getValue()).isEqualTo(capturedRequest.getValue());
    }

    @Test
    void givenNullNameWhenCreateThenReturnBadRequest() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        productCreateRequest.setName(null);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenBlankNameWhenCreateThenReturnBadRequest() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        productCreateRequest.setName("");

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNullValueWhenCreateThenReturnBadRequest() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        productCreateRequest.setValue(null);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNegativeValueWhenCreateThenReturnBadRequest() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        productCreateRequest.setValue(-1.0);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenValueZeroWhenCreateThenReturnBadRequest() throws Exception {
        final ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
        productCreateRequest.setValue(0.0);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productCreateRequest);

        mockMvc.perform(
                post(CREATE_PRODUCT_V1)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }
}
