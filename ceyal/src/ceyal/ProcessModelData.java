package ceyal;

public class ProcessModelData {
    private String caseId;
    private String task;
    private String resource;
    private String startTimestamp;
    private String endTimestamp;
    private String outcome;

    public ProcessModelData(String caseId, String task, String resource, String startTimestamp, String endTimestamp, String outcome) {
        this.caseId = caseId;
        this.task = task;
        this.resource = resource;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "Case ID: " + caseId + ", Task: " + task + ", Resource: " + resource
                + ", Start Time: " + startTimestamp + ", End Time: " + endTimestamp + ", Outcome: " + outcome;
    }
}
