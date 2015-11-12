package poo.khet.UI;

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

/** 
 * Clase que se ejecuta inicialmente en la aplicación. 
 * Contiene controles para cargar partidas guardadas, o para empezar un juego
 * nuevo desde una configuración default. 
 * @see BoardWindow
 */
public class LoadWindow extends Application implements GraphicDimensions {

    private Canvas background = new Canvas(LOAD_WINDOW_W, LOAD_WINDOW_H);
    private Canvas[] defaultPreviews = new Canvas[3];
    private Button[] defaultButtons = new Button[3];
    private Button loadBtn;
    private CheckBox twoPlayers;
    private CheckBox onePlayer;
    private TextField nameInput;
    private Stage loadStage;


    public void start(final Stage stage) {
        loadStage = stage;
        Group root = new Group();
        loadStage.setWidth(LOAD_WINDOW_W);
        loadStage.setHeight(LOAD_WINDOW_H);
        
        createDefaultGames();
        createLoadButton();
        createNameTextField();
        createPlayerCheckboxes();
        
        // Imagen de fondo
        background.getGraphicsContext2D().drawImage(new Image("file:assets/LoadBack.png"), 0, 0);

        root.getChildren().add(background);
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

    private GameMode getSelectedGameMode() {
        if (onePlayer.isSelected()) {
            return GameMode.PVE;
        }
        if (twoPlayers.isSelected()) {
            return GameMode.PVP;
        }
        throw new IllegalStateException("Unselected GameMode");
    }


    /**
     * Crea el boton para cargar partidas guardadas, y su acción correspondiente. Dentro de la
     * acción se manejan el mostrado de alertas en caso de no poder abrir el archivo con el nombre
     * introducido en <code>nameInput</code>
     */
    private void createLoadButton() {
        loadBtn = new Button("Load Game");
        loadBtn.setTranslateX(175);
        loadBtn.setTranslateY(LOAD_WINDOW_H - 85);
        loadBtn.setPrefSize(BUTTON_W, BUTTON_H);

        // Accion boton de cargar juego guardado
        loadBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    new BoardWindow("savedGames/" + nameInput.getText(), loadStage);
                    loadStage.close();
                } catch (IOException fileNotFound) {
                    throwNotFoundFileAlert();
                } catch (ClassNotFoundException classNotFound) {
                    throwInvalidFileAlert();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    throwUnexpectedErrorAlert();
                }

            }
        });
    }

    /**
     * Crea un campo de texto para introducir el nombre del archivo que se desea guardar. Su
     * información es usada por <code>loadBtn</code>
     */
    private void createNameTextField() {
        nameInput = new TextField();
        nameInput.setPrefSize(350, BUTTON_H);
        nameInput.setTranslateX(275);
        nameInput.setTranslateY(LOAD_WINDOW_H - 85);
    }

    /**
     * Crea dos <code>CheckBox</code> para indicar si se quiere jugar contra la computadora o entre
     * 2 personas. Tambien les da sus acciones correspondientes, para que el seleccionarse una
     * checkbox se deseleccione la otra.
     */
    private void createPlayerCheckboxes() {
        twoPlayers = new CheckBox("Dos Jugadores");
        onePlayer = new CheckBox("Un Jugador");
        twoPlayers.setTranslateX(250);
        onePlayer.setTranslateX(450);
        twoPlayers.setTranslateY(LOAD_WINDOW_H - 85 - 50);
        onePlayer.setTranslateY(LOAD_WINDOW_H - 85 - 50);
        twoPlayers.setSelected(true); // Por default 2 jugadores

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
    }
    
    /**
     * Crea los botones y las previsualizaciones de las tres configuraciones iniciales
     * posibles del Khet, junto con las acciones de cada boton para cargar una
     * configuracion distinta en cada uno. Tambien se encargan de arrojar alertas 
     * en caso de haber algun error en la carga.
     */
    private void createDefaultGames() {
        
        //Imagenes de previsualizacion
        for (int i = 0; i < 3; i++) {
            defaultPreviews[i] = new Canvas(PREVIEW_W, PREVIEW_H);
            defaultPreviews[i].setTranslateX(50 + 50 * i + PREVIEW_W * i);
            defaultPreviews[i].setTranslateY(50);
            defaultPreviews[i].getGraphicsContext2D()
                    .drawImage(new Image("file:assets/DefaultPreview" + (i + 1) + ".png"), 0, 0);
        }
        
        //Botones de carga
        defaultButtons[0] = new Button("Tutankamon");
        defaultButtons[1] = new Button("Ramses");
        defaultButtons[2] = new Button("Cleopatra");
        for (int i = 0; i < 3; i++) {
            defaultButtons[i].setTranslateX(100 + 100 * i + 150 * i);
            defaultButtons[i].setTranslateY(225);
            defaultButtons[i].setPrefSize(BUTTON_W, BUTTON_H);
        }
        
        //Acciones botones de carga
        defaultButtons[0].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                defaultLoad("d1", getSelectedGameMode(), loadStage);
                loadStage.hide();
            }
        });

        defaultButtons[1].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                defaultLoad("d2", getSelectedGameMode(), loadStage);
                loadStage.hide();
            }
        });

        defaultButtons[2].setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                defaultLoad("d3", getSelectedGameMode(), loadStage);
                loadStage.hide();
            }
        });
    }
    
    /**
     * Inicializa el <code>BoardStage</code> con una configuracion inicial dada. Arroja
     * alertas en caso de haber algun error al cargar las configuraciones. 
     * @param name - nombre de la configuracion default a cargar
     * @param selected - <code>GameMode</code> cantidad de jugadores
     * @param loadStage - ventana de carga del juego
     */
    private void defaultLoad(String name, GameMode selected, Stage loadStage) {
        try {
            new BoardWindow("defaultConfigs/" + name, selected, loadStage);
        } catch (IOException fileNotFound) {
            throwNotFoundFileAlert();
        } catch (ClassNotFoundException classNotFound) {
            throwInvalidFileAlert();
        }
    }
    
    /**
     * Se emite una alerta en caso de que el archivo con el nombre dado no haya sido encontrado
     * dentro de la carpeta de archivos guardados.
     */
    private void throwNotFoundFileAlert() {
        Alert notfound = new Alert(Alert.AlertType.ERROR);
        notfound.setTitle("File Not Found");
        notfound.setHeaderText(null);
        notfound.setContentText("File: '" + nameInput.getText()
                + "' was not found.\nPlease check if the filename is correct");
        notfound.showAndWait();
    }

    /**
     * Se emite una alerta en caso de que el archivo que se desea abrir exista pero no corresponda a
     * un archivo valido de Keth Game.
     */
    private void throwInvalidFileAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid File");
        alert.setHeaderText(null);
        alert.setContentText("This file is not a Keth Game valid file.\n Please try again.");
        alert.showAndWait();
    }

    /**
     * Se emite una alerta en caso de que haya ocurrido un error inesperado al intentar abrir
     * configuraciones iniciales u otro archivo.
     *
     */
    private void throwUnexpectedErrorAlert() {
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
