package ceyal;

import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventLogView {

    private List<ProcessModelData> processModelDataList = new ArrayList<>();

    // New method to parse CRM-style CSV
    public void parseCSV(File file) throws IOException {
        try (CSVParser csvParser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : csvParser) {
                // Adjusting field names to CRM standard event log
                String caseId = record.get("Case ID");
                String task = record.get("Task");
                String resource = record.get("Resource");
                String startTimestamp = record.get("Start Timestamp");
                String endTimestamp = record.get("End Timestamp");
                String outcome = record.get("Outcome");

                ProcessModelData processModelData = new ProcessModelData(caseId, task, resource, startTimestamp, endTimestamp, outcome);
                processModelDataList.add(processModelData);
            }
        }
    }

    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                parseCSV(file);
                System.out.println("Processing file: " + file.getName());
                displayProcessModel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Display logic for process model
    private void displayProcessModel() {
        // Code to display the process model visually
        // Here, you can create more complex flows with gateways and branches
        for (ProcessModelData data : processModelDataList) {
            System.out.println(data);
        }
    }
}
