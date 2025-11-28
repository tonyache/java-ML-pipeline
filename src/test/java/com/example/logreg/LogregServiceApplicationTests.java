package com.example.logreg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogregServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testPredictEndpoint() {
        // Build request JSON
        PredictionRequest req = new PredictionRequest(List.of(1.0, 0.0));

        // Send HTTP request
        ResponseEntity<PredictionResponse> res = rest.postForEntity(
                "http://localhost:" + port + "/predict",
                req,
                PredictionResponse.class);

        // Assert HTTP status
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert response content
        PredictionResponse body = res.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getLabel()).isEqualTo(1);
        assertThat(body.getProbability()).isGreaterThan(0.5);
    }
}
