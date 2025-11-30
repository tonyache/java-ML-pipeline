package com.example.logreg;

import java.util.Arrays;

public class FeatureScaler {

    private final double[] means;
    private final double[] stds;

    public FeatureScaler(double[] means, double[] stds) {
        if (means.length != stds.length) {
            throw new IllegalArgumentException("Means and stds length mismatch");
        }
        this.means = means.clone();
        this.stds = stds.clone();
    }

    public double[] transform(double[] x) {
        if (x.length != means.length) {
            throw new IllegalArgumentException("Feature length mismatch");
        }
        double[] out = Arrays.copyOf(x, x.length);
        for (int i = 0; i < out.length; i++) {
            if (stds[i] == 0.0) {
                // avoid division by zero, treat as no scaling
                out[i] = out[i] - means[i];
            } else {
                out[i] = (out[i] - means[i]) / stds[i];
            }
        }
        return out;
    }

    public int getNumFeatures() {
        return means.length;
    }
}
