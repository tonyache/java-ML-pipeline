package com.example.logreg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/predict")
public class PredictionController {

    private final LogisticRegressionModel model;

    public PredictionController(LogisticRegressionModel model) {
        this.model = model;
    }

    @PostMapping
    public ResponseEntity<PredictionResponse> predict(@RequestBody PredictionRequest request) {
        if (request.getFeatures() == null ||
            request.getFeatures().size() != model.getNumFeatures()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        double[] features = request.getFeatures().stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        double prob = model.predictProb(features);
        int label = model.predictLabel(features);

        return ResponseEntity.ok(new PredictionResponse(prob, label));
    }
}
