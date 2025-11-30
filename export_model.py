import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import make_classification

# 1. Create a small dataset for demonstration
X, y = make_classification(
    n_samples=200,
    n_features=3,
    n_informative=3,
    n_redundant=0,
    random_state=42
)

# 2. Fit a scaler
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# 3. Fit logistic regression
clf = LogisticRegression()
clf.fit(X_scaled, y)

# 4. Extract weights
bias = clf.intercept_[0]
weights = clf.coef_[0]

means = scaler.mean_
stds = scaler.scale_

print("bias =", bias)
print("weights =", weights)
print("means =", means)
print("stds =", stds)

# 5. Write to files in Java-friendly format
np.savetxt("weights.txt", np.hstack([[bias], weights]))
np.savetxt("means.txt", means)
np.savetxt("stds.txt", stds)
