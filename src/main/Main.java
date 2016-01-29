package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

import main.controller.URMController;

/**
 * Clase principal de la aplicación
 */
public final class Main extends Application {
	private Stage stage;

	/**
	 * Inicia la clase Vista y Controlador, e inicia
	 * el entorno gráfico.
 	 */
	public void start(Stage stage){
		this.stage = stage;
		initRootLayout();
	}

	public void initRootLayout()
	{
		try {
			URMController.setStage(stage);
			Scene scene = new Scene(FXMLLoader.load(
				Main.class.getResource("/res/gui/main.fxml")
			));
			stage.setScene(scene);

       		stage.getIcons().add(new Image("/res/Jurm.png"));

			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
        launch(args);

    }
}