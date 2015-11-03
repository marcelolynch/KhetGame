package graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class LoadMain extends Application {
	
	Canvas[] graphicDefaults = new Canvas[3];
	//Canvas BackGround;
	Button[] defaultButtons = new Button[3];
	Button loadBtn;
	
	//TODO: Exception
	public void start(final Stage loadStage) throws Exception {
		Group root = new Group();
		loadStage.setWidth(800);
		loadStage.setHeight(400);
		
		//Que hago con todos los magic numbers, son todas cosas par aue este alineado, es imposible ponerle nombre a todo
		//BackGround = new Canvas(800, 400);
		//BackGround.getGraphicsContext2D().drawImage(new Image("file:assets/BackGround.png"),0,0);
		
		for (int i=0; i<3; i++) {
			graphicDefaults[i] = new Canvas(200, 160);
			graphicDefaults[i].setTranslateX(50*(i+1) + 200*i);
			graphicDefaults[i].setTranslateY(50);
			graphicDefaults[i].getGraphicsContext2D().drawImage(new Image("file:assets/TableroGeneric.png"),0,0);
		}
		
		defaultButtons[0] = new Button("Tutankamon");
		defaultButtons[1] = new Button("Ramses");
		defaultButtons[2] = new Button("Cleopatra");
		for (int i=0; i<3; i++) {
			defaultButtons[i].setTranslateX(100*(i+1) + 150*i);
			defaultButtons[i].setTranslateY(225);
			defaultButtons[i].setPrefSize(100, 35);
		}
		
		loadBtn = new Button("Load Game");
		loadBtn.setTranslateX(175); loadBtn.setTranslateY(315);
		loadBtn.setPrefSize(100, 35);
		final TextField nameInput = new TextField();
		nameInput.setPrefSize(350, 35);
		nameInput.setTranslateX(275); nameInput.setTranslateY(315);
		
//		for (int i = 0; i < 3; i++) {
//			defaultButtons[i].setOnAction(
//					new EventHandler<ActionEvent>() {
//						public void handle(ActionEvent e) {
//							try {
//								Main.start("default" + i);// no me deja poner el for por esto
//							} catch (Exception e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							loadStage.close();
//						}
//					});
//		}
		
		defaultButtons[0].setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							new Main("default1", loadStage); // TODO: esto no me gusta
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						loadStage.hide();
					}
				});
		
		defaultButtons[1].setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							new Main("default2", loadStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						loadStage.hide();
					}
				});
		
		defaultButtons[2].setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							new Main("default3", loadStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						loadStage.hide();
					}
				});
		
		loadBtn.setOnAction(
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						try {
							new Main(nameInput.getText(), loadStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						loadStage.close();
					}
				});
		
		//root.getChildren().add(BackGround);
		root.getChildren().addAll(graphicDefaults);
		root.getChildren().addAll(defaultButtons);
		root.getChildren().add(loadBtn);
		root.getChildren().add(nameInput);
		
		loadStage.setResizable(false);
		loadStage.setTitle("Khet - Board Selection");
		loadStage.setScene(new Scene(root));
		loadStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}
