package application;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javafx.scene.Node;
import javafx.scene.Parent;

public class SampleController {
	@FXML private Label nametag;  //MAIN MENU OBJECTS
	@FXML private Label title;
	@FXML private Pane bot;
	@FXML private Pane left;
	@FXML private Pane right;
	@FXML private Button playButton;
	
	private Stage stage;
	
	private FadeTransition fadeTitle = new FadeTransition(Duration.millis(3000));
	private FadeTransition fadeName = new FadeTransition(Duration.millis(3000));
	private FadeTransition fadeButton = new FadeTransition(Duration.millis(3000));
	
	public void initialize() {	
		fadeName.setNode(nametag);
		fadeTitle.setNode(title);
		fadeButton.setNode(playButton);

	    fadeName.setFromValue(0.0); //Set Fade Ins
	    fadeName.setToValue(1.0);
	    fadeName.setCycleCount(1);
	    fadeTitle.setFromValue(0.0);
	    fadeTitle.setToValue(1.0);
	    fadeTitle.setCycleCount(1);
	    fadeButton.setFromValue(0.0);
	    fadeButton.setToValue(1.0);
	    fadeButton.setCycleCount(1);

	    fadeTitle.playFromStart(); //Play Fade Ins
	    fadeName.playFromStart();
	    fadeButton.playFromStart();
	    
	    
	    bot.setStyle("-fx-background-color: grey"); //Set Colors
	    left.setStyle("-fx-background-color: yellow");
	    right.setStyle("-fx-background-color: green");
	    playButton.setStyle("-fx-background-color: green");
	}
	
	public void switchScreen(ActionEvent e) {
		try {	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Parent root = loader.load();	
			playController pC = loader.getController();
			Scene scene = new Scene(root);

			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() { 
				public void handle(KeyEvent e) {
					if(!pC.getWin() && !pC.getPause()) {
						if(e.getCode() == e.getCode().BACK_SPACE) {
							pC.deletefromGuess();
						}
						else if(e.getText().toUpperCase().matches("[A-Z]")) {
							String l = e.getText().toUpperCase();
							pC.addtoGuess(l);
						}
						
					}
					if(e.getCode() == e.getCode().ENTER) {
						if(!pC.getWin()) {
							pC.guess();
						}
						else {
							pC.reset();
						}
					}
					if(e.getCode() == e.getCode().ESCAPE) {
						if(!pC.getPause()){
							pC.pause();
						}
						else {
							pC.unpause();
						}
					}
				}
			});
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent e) {
					pC.closeFile();
				}
			});
			
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
}
