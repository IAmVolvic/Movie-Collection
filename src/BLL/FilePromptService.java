package BLL;

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


        return getData.getPath();
    }

    public void playFile(String filePath){
        try (FileChannel channel = FileChannel.open(new File(filePath).toPath(), StandardOpenOption.WRITE);
             FileLock lock = channel.tryLock()) {

            if (lock != null) {
                // The file is not already open; you have acquired a lock
                Desktop.getDesktop().open(new File(filePath));
            } else {
                System.out.println("The video is already open.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
