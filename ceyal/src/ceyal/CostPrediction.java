package ceyal;

//CostPrediction.java
import java.util.List;

public class CostPrediction {
 public double predictFutureCost(List<Double> historicalCosts) {
     return historicalCosts.stream().mapToDouble(Double::doubleValue).average().orElse(0);
 }
}
