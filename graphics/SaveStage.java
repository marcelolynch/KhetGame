package graphics;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaveStage {
		
	public SaveStage (GameManager gameManager) {
		Stage saveWindow = new Stage();
		VBox saveLayout = new VBox(10);
		Button saveBtn = new Button("Save");
		final TextField nameInput = new TextField();
		
		saveBtn.setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							gameManager.saveGame(nameInput.getText());
							System.out.println("Saved!");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						saveWindow.close();
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
