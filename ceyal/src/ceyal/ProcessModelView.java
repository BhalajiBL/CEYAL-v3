package ceyal;

//ProcessModelView.java
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class ProcessModelView {
 private Canvas canvas;
 private static final double TASK_WIDTH = 100;
 private static final double TASK_HEIGHT = 50;

 public ProcessModelView() {
     canvas = new Canvas(600, 400); // Set a size for the process model visualization
 }

 public Canvas getCanvas() {
     return canvas;
 }

 public void drawProcessFlow(List<Event> events) {
     GraphicsContext gc = canvas.getGraphicsContext2D();
     gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

     if (events == null || events.isEmpty()) {
         gc.fillText("No Events to Display", 50, 50);
         return;
     }

     // Example: Draw tasks and flow lines
     int eventCounter = 0;
     double xPosition = 50;
     double yPosition = 50;

     for (Event event : events) {
         // Draw task (rectangle)
         gc.setFill(Color.LIGHTBLUE);
         gc.fillRect(xPosition, yPosition, TASK_WIDTH, TASK_HEIGHT);
         gc.setStroke(Color.BLACK);
         gc.strokeRect(xPosition, yPosition, TASK_WIDTH, TASK_HEIGHT);

         // Label task
         gc.setFill(Color.BLACK);
         gc.setFont(Font.font(14));
         gc.fillText(event.getActivity(), xPosition + 10, yPosition + 25);

         // Draw arrow to the next task
         if (eventCounter < events.size() - 1) {
             drawArrow(gc, xPosition + TASK_WIDTH, yPosition + TASK_HEIGHT / 2, xPosition + TASK_WIDTH + 50, yPosition + TASK_HEIGHT / 2);
         }

         // Update positions
         xPosition += TASK_WIDTH + 70;
         eventCounter++;
     }
 }

 // Method to draw an arrow between tasks
 private void drawArrow(GraphicsContext gc, double startX, double startY, double endX, double endY) {
     gc.setStroke(Color.BLACK);
     gc.strokeLine(startX, startY, endX, endY);

     // Draw arrowhead
     double arrowHeadSize = 10;
     gc.strokeLine(endX, endY, endX - arrowHeadSize, endY - arrowHeadSize);
     gc.strokeLine(endX, endY, endX - arrowHeadSize, endY + arrowHeadSize);
 }
}
