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

    /**
     * Loads a LogisticRegressionModel from a classpath resource.
     *
     * Expected file format:
     *   - First non-empty, non-comment line: bias (double)
     *   - Each subsequent non-empty, non-comment line: a weight (double)
     *
     * Lines starting with '#' and blank lines are ignored.
     */
    public LogisticRegressionModel loadFromResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);

            if (!resource.exists()) {
                throw new IllegalStateException("Weights resource not found on classpath: " + path);
            }

            Double bias = null;
            List<Double> weights = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        // skip comments/blank lines
                        continue;
                    }

                    if (bias == null) {
                        bias = Double.parseDouble(line);
                    } else {
                        weights.add(Double.parseDouble(line));
                    }
                }
            }

            if (bias == null) {
                throw new IllegalStateException("No bias found in weights file: " + path);
            }
            if (weights.isEmpty()) {
                throw new IllegalStateException("No weights found in weights file: " + path);
            }

            double[] w = weights.stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

            return new LogisticRegressionModel(w, bias);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load model from " + path, e);
        }
    }
}
