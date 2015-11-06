package graphics;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaveStage {

    public SaveStage(final GameManager gameManager) {
        final Stage saveWindow = new Stage();
        VBox saveLayout = new VBox(10);
        Button saveBtn = new Button("Save");
        final TextField nameInput = new TextField();

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    gameManager.saveGame("savedGames/" + nameInput.getText());
                    saveWindow.close();
                } catch (IOException e) {
                    Alert notfound = new Alert(Alert.AlertType.ERROR);
                    notfound.setTitle("Could Not Save Game");
                    notfound.setHeaderText(null);
                    notfound.setContentText(nameInput.getText() + " could not be written.");
                    notfound.showAndWait();
                }
            }
        });

        saveLayout.setPadding(new Insets(20));
        saveLayout.getChildren().add(saveBtn);
        saveLayout.getChildren().add(nameInput);
        saveWindow.setScene(new Scene(saveLayout));
        saveWindow.setTitle("Save Game");
        saveWindow.show();
    }
}
