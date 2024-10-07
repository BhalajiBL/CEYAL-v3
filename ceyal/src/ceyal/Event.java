package ceyal;
//Event.java
public class Event {
 private String caseId;
 private String activity;
 private long timestamp;
 private double duration;

 public Event(String caseId, String activity, long timestamp, double duration) {
     this.caseId = caseId;
     this.activity = activity;
     this.timestamp = timestamp;
     this.duration = duration;
 }

 public String getCaseId() {
     return caseId;
 }

 public String getActivity() {
     return activity;
 }

 public long getTimestamp() {
     return timestamp;
 }

 public double getDuration() {
     return duration;
 }
}
