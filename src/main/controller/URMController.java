package main.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import jurm.URM;
import jurm.URMException;
import jurm.util.JurmData;
import jurm.util.LineRange;

import main.controller.StepByStepThread;

/**
 * Controla los eventos realizados en la interfaz gráfica.
 */
public final class URMController{
	@FXML private TextArea messageArea;
	@FXML private TextArea codeArea;
	@FXML private TextArea registerArea;
	@FXML private Slider slider;
	@FXML private ToggleButton runButton;

	private StepByStepThread stepByStep;
	private final URM urm;
	private File file;
	private static Stage mainStage;

	public URMController(){
		urm = new URM();
		stepByStep = new StepByStepThread(this);
		file = null;
	}

	/**
	 * Se ejecuta al cargar la interfaz gráfica
	 */
	@FXML private void initialize() {
		if (messageArea != null)
			messageArea.appendText("");

		if (slider != null){
			slider.valueProperty().addListener(
			(observable, oldValue, newValue) -> {
				stepByStep.setSpeed(newValue.intValue());
			});
		}

		mainStage.setTitle("[Sin nombre]");
	}

	/**********************************************************
	 * Métodos manejadores de eventos
	 **********************************************************/
	@FXML private void handleOpenFile(ActionEvent actionEvent){
		openFile();
	}
	@FXML private void handleSaveFile(ActionEvent actionEvent){
		saveFile();
	}
	@FXML private void handleNewFile(ActionEvent actionEvent){
		codeArea.setText("");
		file = null;
		mainStage.setTitle("[Sin nombre]");
	}
	@FXML private void handleRun(ActionEvent actionEvent){
		run();
	}
	@FXML private void handleRunStep(ActionEvent actionEvent){
		runStep();
	}
	@FXML private void handleToggleRunStop(ActionEvent actionEvent){
		toggleRunStop();
	}
	
	/**
	 * Abre un archivo y lo colóca en el area de código
	 */
	private void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir Programa");
		file = fileChooser.showOpenDialog(new Stage());
		if (file != null) {
			try{
				codeArea.setText(new Scanner(file).useDelimiter("\\Z").next());
				mainStage.setTitle(file.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile(){
		if (file == null){
			file = showSaveFile("Guardar", JurmData.getDirectoryMacros());
			if (file == null) return;
		}
		try{
			FileWriter fw = new FileWriter(file);
			fw.write(codeArea.getText());
			fw.close();
			messageArea.appendText("Archivo guardado\n");
			mainStage.setTitle(file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* Ejecuta una única vez el programa
	*/
	public void run() {
		try {
			preprocess();
			messageArea.setText("Ejecutando el programa...\n");
			urm.run();

			updateRegisters();
			messageArea.setText("Ejecucion correcta!\n");
			messageArea.setText("Resultado :" + urm.result());
		} catch (URMException e) {
			messageArea.appendText(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Ejecuuta la siguiente instrucción del programa.
	 * El método es público para que pueda ser ejecutado
	 * por la clase StepByStepThread.
	 */
	public void runStep() {
		try {
			if (urm.isFinished()) {
				preprocess();
			}
			urm.runStep();
			Platform.runLater(new Runnable() {
				@Override public void run() {
					drawLine();
					updateRegisters();
				}
			});
			
			if (urm.isFinished()) {
				Platform.runLater(new Runnable() {
				   	@Override public void run() {
				   		runButton.setSelected(false);
				   		messageArea.appendText("Ejecucion correcta!\n");
						messageArea.appendText("Resultado :" + urm.result());
				   	}
				});
			}
		} catch (URMException e) {
			messageArea.appendText(e.toString());
		}
	}

	/**
	 * Arranca o pausa la ejecucion del programa
	 * en modo paso por paso.
	 */
	private void toggleRunStop(){
		if (stepByStep.isPaused())
		{
			if (urm.isFinished()){
				try {
					preprocess();
				}catch(URMException e){
					messageArea.appendText(e.toString());
					return;
				}
			}
			stepByStep = new StepByStepThread(this);
			stepByStep.setSpeed((int)slider.getValue());
			messageArea.setText("Ejecutando paso a paso...\n");
			stepByStep.start();
		}
		else 
		{
			stepByStep.pause();
			messageArea.setText("Ejecucion pausada...\n");
		}
	}
	
	/**
	 * Carga la máquina con el contenido del codeArea
	 */
	private void preprocess() throws URMException {
		urm.init(codeArea.getText());
		drawLine();
		updateRegisters();
	}

	/**
	 * Sombrea la linea actual
	 */
	private void drawLine(){
		LineRange range = urm.currentLineRange();
		codeArea.selectRange(range.start, range.end);
	}
	
	/**
	 * Actualiza la vista de registros.
	 */
	public void updateRegisters(){
		registerArea.setText(urm.represent());	
	}

	public boolean isFinished(){
		return urm.isFinished();
	}

	private File showSaveFile(String title, File initial)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		if (initial != null){
			fileChooser.setInitialDirectory(initial);
		}
		return fileChooser.showSaveDialog(new Stage());
	}

	public static void setStage(Stage stage){
		mainStage = stage;
	}
}