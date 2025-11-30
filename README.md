# 🚀 Java ML Pipeline — Logistic Regression + Feature Scaling + Spring Boot

This project demonstrates a **production-ready Java machine-learning inference service**, backed by:

- A **Logistic Regression** model  
- Feature scaling (standardization)  
- Model + scaler loading from resource files  
- A **Spring Boot** REST API  
- Optional **Docker / Docker Compose** packaging  

The system uses a Python-trained model (weights, bias, means, stds) and loads them in Java to provide fast, thread-safe, scalable inference.

---

## 📁 Project Structure

```
java-ml-pipeline/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/example/logreg/
│   │   │   ├── FeatureScaler.java
│   │   │   ├── LogisticRegressionModel.java
│   │   │   ├── LogisticRegressionTrainer.java
│   │   │   ├── ModelConfig.java
│   │   │   ├── ModelLoader.java
│   │   │   ├── PredictionController.java
│   │   │   ├── PredictionRequest.java
│   │   │   ├── PredictionResponse.java
│   │   │   └── ScalerLoader.java
│   │   └── resources/model/
│   │       ├── weights.txt
│   │       ├── means.txt
│   │       └── stds.txt
│   └── test/java/com/example/logreg/
│       └── LogregServiceApplicationTests.java
└── README.md
```

---

## 🧠 How It Works

The model is a logistic regression:

```
p = σ(w · x' + b)
```

With standardized features:

```
x'_i = (x_i - μ_i) / σ_i
```

- **w** = weights  
- **b** = bias  
- **μ** = means  
- **σ** = standard deviations  

Files loaded from:

```
src/main/resources/model/
```

---

## 🐍 Exporting Model Files from Python

```python
import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import make_classification

X, y = make_classification(n_samples=200, n_features=3, random_state=42)

scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

model = LogisticRegression()
model.fit(X_scaled, y)

np.savetxt("weights.txt", np.hstack([model.intercept_, model.coef_.ravel()]))
np.savetxt("means.txt", scaler.mean_)
np.savetxt("stds.txt", scaler.scale_)
```

Copy to:

```
src/main/resources/model/
```

---

## ▶️ Running Locally

### Maven

```
mvn clean package
mvn spring-boot:run
```

Runs at:

```
http://localhost:8080
```

---

## 🧪 Prediction Request

```
curl -X POST http://localhost:8080/predict   -H "Content-Type: application/json"   -d '{"features":[1.0,0.0,2.0]}'
```

---

## 🐳 Run with Docker

```
docker compose up --build
```

---

## ✔️ Testing

```
mvn test
```

---

## 🛠 Troubleshooting

- **400 BAD_REQUEST** → feature length mismatch  
- **Resource not found** → ensure correct file paths  
- **Compose error** → ensure Dockerfile ≠ docker-compose.yml  

---

## 📌 Future Improvements

- Swagger/OpenAPI  
- Prometheus metrics  
- Model versioning  
- Real-time hot-swapping  
- Kubernetes deployment  
