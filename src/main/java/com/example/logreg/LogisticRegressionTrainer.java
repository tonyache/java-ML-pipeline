package com.example.logreg;

public class LogisticRegressionTrainer {

    public LogisticRegressionModel train(double[][] X, int[] y, int epochs, double lr) {
        int nSamples = X.length;
        if (nSamples == 0) {
            throw new IllegalArgumentException("Empty dataset");
        }

        int nFeatures = X[0].length;
        double[] weights = new double[nFeatures];
        double bias = 0.0;

        for (int epoch = 0; epoch < epochs; epoch++) {
            double[] gradW = new double[nFeatures];
            double gradB = 0.0;

            for (int i = 0; i < nSamples; i++) {
                double[] xi = X[i];
                double z = dot(weights, xi) + bias;
                double pi = sigmoid(z);
                double error = pi - y[i];

                for (int j = 0; j < nFeatures; j++) {
                    gradW[j] += error * xi[j];
                }
                gradB += error;
            }

            for (int j = 0; j < nFeatures; j++) {
                weights[j] -= lr * gradW[j] / nSamples;
            }
            bias -= lr * gradB / nSamples;
        }

        return new LogisticRegressionModel(weights, bias);
    }

    private double dot(double[] w, double[] x) {
        if (w.length != x.length) {
            throw new IllegalArgumentException("Length mismatch in dot product");
        }
        double sum = 0.0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i] * x[i];
        }
        return sum;
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
}
