package GUI.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPopUpController {
    @FXML
    private Text errorMessage;



    private void setInit(String errorTxt) {
        this.errorMessage.setText(errorTxt);
    }


    public void prompError(String errorText) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ErrorPopUp.fxml"));
        Parent root = loader.load();

        ErrorPopUpController controller = loader.getController();
        controller.setInit(errorText);

        stage.setScene(new Scene(root));
        stage.setTitle("Oops Error!");
        stage.show();
    }


    @FXML
    private void closeBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) this.errorMessage.getScene().getWindow();
        stage.close();
    }
}
