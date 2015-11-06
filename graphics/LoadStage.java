package graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.IOException;

import poo.khet.gameutils.GameMode;

public class LoadStage extends Application {

    Canvas[] defaultPreviews = new Canvas[3];
    Button[] defaultButtons = new Button[3];
    Button loadBtn;
    CheckBox twoPlayers;
    CheckBox onePlayer;
    TextField nameInput;
    
    final int BUTTON_WIDTH = 100;
    final int BUTTON_HEIGHT = 35;
    final int PREVIEW_WIDTH = 200;
    final int PREVIEW_HEIGHT = 160;

    // TODO: Exception
    public void start(final Stage loadStage) throws Exception {
        Group root = new Group();
        loadStage.setWidth(800);
        loadStage.setHeight(450);

        //Botones configuraciones iniciales
        for (int i = 0; i < 3; i++) {
            defaultPreviews[i] = new Canvas(PREVIEW_WIDTH, PREVIEW_HEIGHT);
            defaultPreviews[i].setTranslateX(50 + 50 * i + PREVIEW_WIDTH * i);
            defaultPreviews[i].setTranslateY(50);
            defaultPreviews[i].getGraphicsContext2D()
                    .drawImage(new Image("file:assets/DefaultPreview" + (i + 1) + ".png"), 0, 0);
        }
        
        defaultButtons[0] = new Button("Tutankamon");
        defaultButtons[1] = new Button("Ramses");
        defaultButtons[2] = new Button("Cleopatra");
        for (int i = 0; i < 3; i++) {
            defaultButtons[i].setTranslateX(100 + 100 * i + 150 * i);
            defaultButtons[i].setTranslateY(225);
            defaultButtons[i].setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        
        //Boton de carga partida guardada
        loadBtn = new Button("Load Game");
        loadBtn.setTranslateX(175);
        loadBtn.setTranslateY(450-85);
        loadBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        
        //Texto para el nombre de archivo a cargar
        nameInput = new TextField();
        nameInput.setPrefSize(350, BUTTON_HEIGHT);
        nameInput.setTranslateX(275);
        nameInput.setTranslateY(450-85);
        
        //Checkboxes cantidad de jugadores
        twoPlayers = new CheckBox("Dos Jugadores");
        onePlayer = new CheckBox("Un Jugador");
        twoPlayers.setTranslateX(250); onePlayer.setTranslateX(450);
        twoPlayers.setTranslateY(450-85-50); onePlayer.setTranslateY(450-85-50);
        twoPlayers.setSelected(true); // Por default 2 jugadores
        
        //Para que al seleccionar uno se deseleccione el otro
        onePlayer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                onePlayer.setSelected(true);
                twoPlayers.setSelected(false);
            }  
        });
        
        twoPlayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                twoPlayers.setSelected(true);
                onePlayer.setSelected(false);
            }            
        });
        
        // Ver el tema de un for{}
        //Acciones Botones Config Default
        defaultButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    new BoardStage("defaultConfigs/def1", getSelectedGameMode(), loadStage);
                } catch (Exception e1) {
                    throwUnexpectedErrorAlert();
                }
                loadStage.hide();
            }
        });

        defaultButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    new BoardStage("defaultConfigs/def2", getSelectedGameMode(), loadStage);
                } catch (Exception e1) {
                    throwUnexpectedErrorAlert();
                }
                loadStage.hide();
            }
        });

        defaultButtons[2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    new BoardStage("defaultConfigs/def3", getSelectedGameMode(), loadStage);
                } catch (Exception e1) {
                    throwUnexpectedErrorAlert();
                }
                loadStage.hide();
            }
        });
        
        //Accion boton de cargar juego guardado
        loadBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    new BoardStage("savedGames/" + nameInput.getText(), loadStage);
                    loadStage.close();
                } catch (IOException fileNotFound) {
                    throwNotFoundFileAlert();
                } catch (ClassNotFoundException classNotFound) {
                    throwInvalidFileAlert();
                } catch (Exception e1){
                    throwUnexpectedErrorAlert();
                }

            }
        });

        root.getChildren().addAll(defaultPreviews);
        root.getChildren().addAll(defaultButtons);
        root.getChildren().add(loadBtn);
        root.getChildren().add(nameInput);
        root.getChildren().add(onePlayer);
        root.getChildren().add(twoPlayers);

        loadStage.setResizable(false);
        loadStage.setTitle("Khet - Board Selection");
        loadStage.setScene(new Scene(root));
        loadStage.show();
    }

    private GameMode getSelectedGameMode(){
        if (onePlayer.isSelected()) {
            return GameMode.PVE;
        }
        if (twoPlayers.isSelected()) {
            return GameMode.PVP;
        }
        throw new IllegalStateException("Unselected GameMode");
    }
    
    private void throwNotFoundFileAlert() {
        Alert notfound = new Alert(Alert.AlertType.ERROR);
        notfound.setTitle("File Not Found");
        notfound.setHeaderText(null);
        notfound.setContentText("File: '" + nameInput.getText()
        + "' was not found.\nPlease check if the filename is correct");
        notfound.showAndWait();                    
    }
    
    /**
     * Se emite una alerta en caso de que el archivo que se desea abrir exista pero no corresponda a un
     * archivo valido de Keth Game
     */
    private void throwInvalidFileAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid File");
        alert.setHeaderText(null);
        alert.setContentText("This file is not a Keth Game valid file.\n Please try again.");
        alert.showAndWait();
    }

    /**Se emite una alerta en caso de que haya ocurrido un error inesperado al intentar abrir
     * configuraciones iniciales u otro archivo.
     *
     */
    private void throwUnexpectedErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unexpected Error");
        alert.setHeaderText(null);
        alert.setContentText("There was an unexpected error loading the file.\nPlease try again");
        alert.showAndWait();
    }
   
    public static void main(String[] args) {
        launch(args);
    }

}
