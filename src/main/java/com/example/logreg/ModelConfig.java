package com.example.logreg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    private final ModelLoader modelLoader;
    private final ScalerLoader scalerLoader;

    public ModelConfig(ModelLoader modelLoader, ScalerLoader scalerLoader) {
        this.modelLoader = modelLoader;
        this.scalerLoader = scalerLoader;
    }

    @Bean
    public LogisticRegressionModel logisticRegressionModel() {
        // Loads bias + weights from src/main/resources/model/weights.txt
        return modelLoader.loadFromResource("model/weights.txt");
    }

    @Bean
    public FeatureScaler featureScaler() {
        // Loads means + stds from src/main/resources/model/means.txt and stds.txt
        return scalerLoader.loadScaler("model/means.txt", "model/stds.txt");
    }
}
