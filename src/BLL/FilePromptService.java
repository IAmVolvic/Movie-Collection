package BLL;

import COMMON.ApplicationException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

import java.awt.Desktop;


public class FilePromptService {
    public String promptFilerChooser(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Find A Movie");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("VIDEO FILES", "*.mp4"));

        File getData = fileChooser.showOpenDialog(stage);

        if(getData == null){
            return null;
        }

        return getData.getPath();
    }


    public void playFile(String filePath) throws ApplicationException {
        try {
            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {
                // Throw an ApplicationException if the file does not exist or is not a regular file
                throw new ApplicationException("The file either does not exist or is not a regular file.");
            }

            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new ApplicationException("Error in BLL", e);
        }
    }
}
