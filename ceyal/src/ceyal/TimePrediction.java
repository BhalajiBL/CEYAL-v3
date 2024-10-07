package ceyal;

//TimePrediction.java
import java.util.List;

public class TimePrediction {
 public double predictFutureTime(List<Double> historicalTimes) {
     return historicalTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0);
 }
}
