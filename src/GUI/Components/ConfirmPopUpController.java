package GUI.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmPopUpController {
    //Selected Object
    private Runnable selectedObject;

    @FXML
    private AnchorPane body;

    @FXML
    private void approve(ActionEvent actionEvent) {
        selectedObject.run();
        closePropt();
    }

    @FXML
    private void cancelBtn(ActionEvent actionEvent) {
        closePropt();
    }

    private void closePropt() {
        Stage stage = (Stage) body.getScene().getWindow();
        stage.close();
    }

    public void init(Runnable runnable) {
        this.selectedObject = runnable;
    }
}
