package com.example.logreg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public LogisticRegressionModel logisticRegressionModel() {
        // Tiny OR dataset
        double[][] X = {
                {0.0, 0.0},
                {0.0, 1.0},
                {1.0, 0.0},
                {1.0, 1.0}
        };
        int[] y = {0, 1, 1, 1};

        LogisticRegressionTrainer trainer = new LogisticRegressionTrainer();
        return trainer.train(X, y, 5000, 0.1);
    }
}
