package GUI.Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ReminderController {
    @FXML
    private ListView<String> reminderList; // Change String to the type of your data

    // Create an ObservableList to hold your data
    private ObservableList<String> reminderData = FXCollections.observableArrayList();

    // Initialize method (you can use @FXML annotation for initialization as well)
    public void initialize() {
        // Set the ObservableList as the data source for your ListView
        reminderList.setItems(reminderData);

        // You can add some initial data if needed
        reminderData.add("Reminder 1");
        reminderData.add("Reminder 2");
    }

    // Method to add data to the ListView
    public void addReminder(String reminder) {
        reminderData.add(reminder);
    }

    // Example of how to use the addReminder method
    public void someMethod() {
        addReminder("New Reminder");
    }
}
