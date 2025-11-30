package com.example.logreg;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScalerLoader {

    public FeatureScaler loadScaler(String meansPath, String stdsPath) {
        double[] means = loadVector(meansPath);
        double[] stds = loadVector(stdsPath);

        if (means.length != stds.length) {
            throw new IllegalStateException("Means and stds length mismatch");
        }

        return new FeatureScaler(means, stds);
    }

    private double[] loadVector(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);

            List<Double> values = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    values.add(Double.parseDouble(line));
                }
            }

            double[] arr = new double[values.size()];
            for (int i = 0; i < values.size(); i++) {
                arr[i] = values.get(i);
            }
            return arr;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load vector: " + path, e);
        }
    }
}
