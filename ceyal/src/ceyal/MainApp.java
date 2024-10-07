package ceyal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp extends Application {

    private TextArea outputArea;
    private Pane drawingPane;
    private List<String[]> tasks; // Store tasks and gateway information for visualization

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Process Simulation Application");

        // Create BorderPane layout
        BorderPane layout = new BorderPane();

        // Create a TextArea for the output (for debugging purposes)
        outputArea = new TextArea();
        outputArea.setEditable(false); // Make it read-only
        outputArea.setWrapText(true); // Optional: make the text wrap within the area

        // Create a Pane for drawing the process model
        drawingPane = new Pane();
        drawingPane.setStyle("-fx-border-color: black;");

        // Make the output area take the left side and drawing pane the center
        layout.setLeft(outputArea);
        layout.setCenter(drawingPane);

        // Create a button for file upload
        Button uploadButton = new Button("Upload CSV File");
        layout.setTop(uploadButton);

        // Configure file upload action
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                // Handle the file upload and processing
                processFile(file);
            }
        });

        // Create a scene with the layout and make the output box fill the left window
        Scene scene = new Scene(layout, 1000, 600); // Set initial width and height
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processFile(File file) {
        outputArea.clear();
        outputArea.appendText("Processing file: " + file.getName() + "\n");

        // Clear previous drawing
        drawingPane.getChildren().clear();
        tasks = new ArrayList<>();

        // Parse CSV and display contents
        try (Reader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            // Display headers
            outputArea.appendText("Headers: " + csvParser.getHeaderMap().keySet() + "\n");

            // Loop through CSV records and collect tasks and gateway info
            for (CSVRecord record : csvParser) {
                String task = record.get("Task");
                String type = record.get("Type"); // Task, Gateway, End
                String nextTasks = record.get("Next Tasks"); // For branching

                outputArea.appendText("Task: " + task + ", Type: " + type + ", Next Tasks: " + nextTasks + "\n");

                // Store task and next task connections
                tasks.add(new String[]{task, type, nextTasks});
            }

            outputArea.appendText("\nFile processed successfully!");

            // After parsing, draw the process model
            drawProcessModel();

        } catch (IOException e) {
            outputArea.appendText("\nError processing the file: " + e.getMessage());
        }
    }

    // Draw a complex process flow model based on the tasks and gateways
    private void drawProcessModel() {
        Canvas canvas = new Canvas(1000, 600); // Canvas for drawing
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int x = 50;
        int y = 50;
        int nodeWidth = 100;
        int nodeHeight = 50;
        int spacing = 150;
        int verticalSpacing = 100; // For branching

        Map<String, Integer[]> taskPositions = new HashMap<>(); // Store positions of drawn tasks

        for (int i = 0; i < tasks.size(); i++) {
            String task = tasks.get(i)[0];
            String type = tasks.get(i)[1];
            String nextTasks = tasks.get(i)[2];

            if (type.equals("Task")) {
                // Draw task node (rectangle)
                gc.strokeRect(x, y, nodeWidth, nodeHeight);
                gc.fillText(task, x + 10, y + 25); // Display task name inside rectangle

                taskPositions.put(task, new Integer[]{x, y}); // Store position for connecting arrows

                // Move to next position if no branching is happening
                x += spacing;
            } else if (type.equals("Gateway")) {
                // Draw gateway (diamond shape)
                drawDiamond(gc, x, y, nodeWidth, nodeHeight);
                gc.fillText(task, x + 10, y + 25); // Display gateway name inside

                // Branching logic
                String[] branches = nextTasks.split(",");
                int branchY = y; // Start branching from current Y position
                for (String branch : branches) {
                    branchY += verticalSpacing;

                    // Draw arrow from gateway to the next task (branch)
                    gc.strokeLine(x + nodeWidth / 2, y + nodeHeight / 2, x + spacing, branchY + nodeHeight / 2);

                    // Store position for each branch
                    taskPositions.put(branch.trim(), new Integer[]{x + spacing, branchY});
                }

                // Move to next Y position (for subsequent tasks)
                y += branches.length * verticalSpacing;
            }
        }

        drawingPane.getChildren().add(canvas); // Add canvas to the drawing pane
    }

    // Helper method to draw a diamond shape (gateway)
    private void drawDiamond(GraphicsContext gc, int x, int y, int width, int height) {
        gc.strokePolygon(
            new double[]{x + width / 2, x + width, x + width / 2, x},
            new double[]{y, y + height / 2, y + height, y + height / 2},
            4
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
