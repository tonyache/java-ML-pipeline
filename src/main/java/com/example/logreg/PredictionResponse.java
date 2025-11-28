package com.example.logreg;

public class PredictionResponse {

    private double probability;
    private int label;

    public PredictionResponse(double probability, int label) {
        this.probability = probability;
        this.label = label;
    }

    public double getProbability() {
        return probability;
    }

    public int getLabel() {
        return label;
    }
}
