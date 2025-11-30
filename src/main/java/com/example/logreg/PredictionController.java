package com.example.logreg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/predict")
public class PredictionController {

    private static final Logger log = LoggerFactory.getLogger(PredictionController.class);

    private final LogisticRegressionModel model;
    private final FeatureScaler scaler;

    public PredictionController(LogisticRegressionModel model, FeatureScaler scaler) {
        this.model = model;
        this.scaler = scaler;
    }

    @PostMapping
    public ResponseEntity<PredictionResponse> predict(@RequestBody PredictionRequest request) {
        if (request.getFeatures() == null ||
                request.getFeatures().size() != model.getNumFeatures()) {
            log.warn("Bad request: expected {} features, got {}",
                    model.getNumFeatures(),
                    request.getFeatures() == null ? 0 : request.getFeatures().size());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        double[] features = request.getFeatures().stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        log.debug("Received features: {}", request.getFeatures());

        double[] scaled = scaler.transform(features);
        double prob = model.predictProb(scaled);
        int label = model.predictLabel(scaled);

        log.info("Prediction made: label={}, prob={}", label, prob);

        return ResponseEntity.ok(new PredictionResponse(prob, label));
    }
}

