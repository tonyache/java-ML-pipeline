package com.example.logreg;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModelLoader {

    public LogisticRegressionModel loadFromResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                Double bias = null;
                List<Double> weights = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    if (bias == null) {
                        bias = Double.parseDouble(line);
                    } else {
                        weights.add(Double.parseDouble(line));
                    }
                }

                if (bias == null || weights.isEmpty()) {
                    throw new IllegalStateException("Invalid weights file: " + path);
                }

                double[] w = weights.stream().mapToDouble(Double::doubleValue).toArray();
                return new LogisticRegressionModel(w, bias);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load model from " + path, e);
        }
    }
}
