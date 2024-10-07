package ceyal;

//CostEstimation.java
import java.util.List;

public class CostEstimation {

 public double estimateTotalCost(List<Event> events) {
     double totalCost = 0;
     for (Event event : events) {
         // For example, set a fixed cost for each activity
         totalCost += 100; // Replace with actual cost estimation logic
     }
     return totalCost;
 }

 public void setActivityCost(String activity, double cost) {
     // Logic to set cost per activity if needed
 }
}

