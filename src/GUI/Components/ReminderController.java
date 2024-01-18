package GUI.Components;

import BE.Remind;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ReminderController {
    @FXML
    private ListView<String> reminderList; // Change String to the type of your data

    // Create an ObservableList to hold your data
    private ObservableList<String> reminderData = FXCollections.observableArrayList();


    private void setInit(ArrayList<Remind> selectedData) {
        // Set the ObservableList as the data source for your ListView
        reminderList.setItems(reminderData);

        for(Remind data: selectedData){
            reminderData.add(
                "MOVE NAME: " + data.getMovieName() + "\n" +
                "MOVE RATING: " + data.getMovieRating() + "\n" +
                "MOVE LAST SEEN: " + data.getMovieLastSeen() + "\n" +
                "CATEGORIES: " + buildStringWithCommas(data.getConnectedCategories())
            );
        }
    }


    private static String buildStringWithCommas(ArrayList<String> dataList) {
        StringBuilder result = new StringBuilder();

        // Iterate through the elements in the ArrayList
        for (String element : dataList) {
            // Append the element to the result string
            result.append(element);

            // Add a comma if it's not the last element
            if (dataList.indexOf(element) < dataList.size() - 1) {
                result.append(", ");
            }
        }

        // Convert StringBuilder to String
        return result.toString();
    }


    public void promptReminder(ArrayList<Remind> selectedData) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReminderPrompt.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Reminder");
        stage.setAlwaysOnTop(true);

        ReminderController controller = loader.getController();
        controller.setInit(selectedData);

        stage.show();
    }
}
