package ceyal;

//TimeEstimation.java
import java.util.List;

public class TimeEstimation {

 public double estimateTotalTime(List<Event> events) {
     double totalTime = 0;
     for (Event event : events) {
         // For example, set a fixed duration for each activity
         totalTime += 1; // Replace with actual time estimation logic
     }
     return totalTime;
 }
}

