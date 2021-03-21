package br.com.tutorial.sergio.ecommercedev.product.controller;

import br.com.tutorial.sergio.ecommercedev.product.domain.exception.NotFoundException;
import br.com.tutorial.sergio.ecommercedev.product.domain.exception.message.ExceptionMessage;
import br.com.tutorial.sergio.ecommercedev.product.domain.mother.ProductMother;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductCreateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.request.ProductUpdateRequest;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductFindByIdResponse;
import br.com.tutorial.sergio.ecommercedev.product.domain.response.ProductListResponse;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerUnitTest {

    private final String CREATE_PRODUCT_V1 = "/v1/products";
    private final String FIND_BY_ID_V1 = "/v1/products/{id}";
    private final String FIND_ALL_V1 = "/v1/products";
    private final String UPDATE_V1 = "/v1/products/{id}";

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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();

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

        ProductCreateRequest capturedRequest = argumentCaptor.getValue();
        assertThat(productCreateRequest.getName()).isEqualTo(capturedRequest.getName());
        assertThat(productCreateRequest.getValue()).isEqualTo(capturedRequest.getValue());
    }

    @Test
    void givenNullNameWhenCreateThenReturnBadRequest() throws Exception {
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
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
        ProductCreateRequest productCreateRequest = ProductMother.getProductCreateRequest();
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

    @Test
    void givenValidIdIdWhenFindByIdThenReturnSuccess() throws Exception {
        Long id = 1L;
        ProductFindByIdResponse response = ProductMother.getProductFindByIdResponse();

        given(productService.findById(id)).willReturn(response);

        mockMvc.perform(
                get(FIND_BY_ID_V1, id)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.value").value(response.getValue()));

        verify(productService, times(1)).findById(id);
    }

    @Test
    void givenServiceThrowsNotFoundExceptionWhenFindByIdThenReturnNotFound() throws Exception {
        Long id = 1L;

        given(productService.findById(id)).willThrow(new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));

        mockMvc.perform(
                get(FIND_BY_ID_V1, id)
        ).andExpect(status().isNotFound());

        verify(productService, times(1)).findById(id);
    }

    @Test
    void whenFindAllThenReturnSuccess() throws Exception {
        List<ProductListResponse> productListResponseList = ProductMother.getProductListResponseList();

        given(productService.findAll()).willReturn(productListResponseList);

        mockMvc.perform(get(FIND_ALL_V1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(productListResponseList.size())));

        verify(productService, times(1)).findAll();
    }

    @Test
    void givenValidParametersWhenUpdateThenReturnNoContent() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isNoContent());

        ArgumentCaptor<ProductUpdateRequest> argumentCaptor = ArgumentCaptor.forClass(ProductUpdateRequest.class);
        verify(productService, times(1)).update(eq(id), argumentCaptor.capture());

        ProductUpdateRequest capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue.getName()).isEqualTo(productUpdateRequest.getName());
        assertThat(capturedValue.getValue()).isEqualTo(productUpdateRequest.getValue());
    }

    @Test
    void givenNullNameWhenUpdateThenReturnBadRequest() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();
        productUpdateRequest.setName(null);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenBlankNameWhenUpdateThenReturnBadRequest() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();
        productUpdateRequest.setName("");

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNullValueWhenUpdateThenReturnBadRequest() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();
        productUpdateRequest.setValue(null);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenNegativeValueWhenUpdateThenReturnBadRequest() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();
        productUpdateRequest.setValue(-1D);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void givenServiceThrowsNotFoundExceptionWhenUpdateThenReturnNotFound() throws Exception {
        Long id = 1L;
        ProductUpdateRequest productUpdateRequest = ProductMother.getProductUpdateRequest();

        willThrow(new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND)).
                given(productService).update(eq(id), any(ProductUpdateRequest.class));

        Gson gson = new Gson();
        String jsonBody = gson.toJson(productUpdateRequest);

        mockMvc.perform(patch(UPDATE_V1, id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andExpect(status().isNotFound());

        ArgumentCaptor<ProductUpdateRequest> argumentCaptor = ArgumentCaptor.forClass(ProductUpdateRequest.class);
        verify(productService, times(1)).update(eq(id), argumentCaptor.capture());

        ProductUpdateRequest capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue.getValue()).isEqualTo(productUpdateRequest.getValue());
        assertThat(capturedValue.getName()).isEqualTo(productUpdateRequest.getName());
    }
}
