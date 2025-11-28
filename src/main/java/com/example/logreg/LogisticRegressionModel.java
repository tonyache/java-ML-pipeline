package com.example.logreg;

public final class LogisticRegressionModel {

    private final double[] weights;
    private final double bias;

    public LogisticRegressionModel(double[] weights, double bias) {
        this.weights = weights.clone();
        this.bias = bias;
    }

    public double predictProb(double[] features) {
        if (features.length != weights.length) {
            throw new IllegalArgumentException("Feature length mismatch");
        }
        double z = bias;
        for (int i = 0; i < weights.length; i++) {
            z += weights[i] * features[i];
        }
        return sigmoid(z);
    }

    public int predictLabel(double[] features) {
        return predictProb(features) >= 0.5 ? 1 : 0;
    }

    private double sigmoid(double z) {
        if (z >= 0) {
            double ez = Math.exp(-z);
            return 1.0 / (1.0 + ez);
        } else {
            double ez = Math.exp(z);
            return ez / (1.0 + ez);
        }
    }

    public int getNumFeatures() {
        return weights.length;
    }

    public double[] getWeights() {
        return weights.clone();
    }

    public double getBias() {
        return bias;
    }
}
