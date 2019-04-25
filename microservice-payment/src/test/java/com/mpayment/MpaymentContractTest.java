package com.mpayment;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MpaymentContractTest {

    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("microservice-orders", "10.126.226.2", 9002, this);

    @Pact(consumer = "microservice-payment")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET /orders")
                .uponReceiving("GET REQUEST")
                .path("/orders")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("[]")
                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {

        // when
        ResponseEntity<String> response = new RestTemplate()
                .getForEntity(mockProvider.getUrl() + "/orders", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
    }
}
