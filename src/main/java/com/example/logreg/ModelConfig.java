package com.example.logreg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    private final ModelLoader modelLoader;

    public ModelConfig(ModelLoader modelLoader) {
        this.modelLoader = modelLoader;
    }
	


    @Bean
    public FeatureScaler featureScaler() {
	return modelLoader.loadScaler("model/means.txt", "model/stds.txt");
    }


    @Bean
    public LogisticRegressionModel logisticRegressionModel() {
        // file: src/main/resources/model/weights.txt
        return modelLoader.loadFromResource("model/weights.txt");
    }
}
