package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class playController{
	@FXML private Pane BG;
	@FXML private Label scrLbl;
	@FXML private Button mmB,gButton,reset;
	@FXML private Label g1,g2,g3,g4,g5;
	@FXML private Label[] guess;
	@FXML private Label aWord,gWord,message;
	@FXML private Rectangle r1,r2,r3,r4,r5;
	@FXML private Rectangle[] r;
	@FXML private Label streakLabel,win1,win2,InvalidLabel;
	@FXML private Label gL1_1,gL1_2,gL1_3,gL1_4,gL1_5;	
	@FXML private Label gL2_1,gL2_2,gL2_3,gL2_4,gL2_5;
	@FXML private Label gL3_1,gL3_2,gL3_3,gL3_4,gL3_5;
	@FXML private Label gL4_1,gL4_2,gL4_3,gL4_4,gL4_5;
	@FXML private Label gL5_1,gL5_2,gL5_3,gL5_4,gL5_5;
	@FXML private Label gL6_1,gL6_2,gL6_3,gL6_4,gL6_5;
	@FXML private Label gO_1,gO_2,gO_3,gO_4,gO_5,gO_6;
	@FXML private Label[] gO;
	@FXML private List<Label> gL;
	@FXML private Button BTG;
	@FXML private Button A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;
	@FXML private Button[] alphaButton;
	@FXML private Pane Pause;
	@FXML private TextField fileName;
	

	
	private Scene scene;
	private Stage stage;
	
	private int scr = 0;
	String guessWord = "";
	String answerWord = "Hello";
	private String wrongLetters = "";
	private int currSize = 0;
	private int val = 0;
	private int streak = 0;
	private int guessCount;
	private boolean win = false;
	private boolean pause = false;
	List<String> wordList = new ArrayList<String> ();
	private int[] guessedOn = {0,0,0,0,0,0};
	FileWriter fileWrite;
	FileWriter saveWrite;
	
	public void initialize() throws Exception {
		 fileWrite = new FileWriter("Guesses.txt");
		 //fileWrite = new FileWriter("saveData.txt");
		 mmB.setFocusTraversable(false);
		 BG.setStyle("-fx-background-color: grey");
		 Pause.setStyle("-fx-background-color: grey");
		 scrLbl.setText("Score: " + scr);
		 guess = new Label[] {g1,g2,g3,g4,g5};
		 r = new Rectangle[] {r1,r2,r3,r4,r5};
		 gO = new Label[] {gO_1,gO_2,gO_3,gO_4,gO_5,gO_6};
		 gL = Arrays.asList(gL1_1,gL1_2,gL1_3,gL1_4,gL1_5, gL2_1,gL2_2,gL2_3,gL2_4,gL2_5, gL3_1,gL3_2,gL3_3,gL3_4,gL3_5
				 , gL4_1,gL4_2,gL4_3,gL4_4,gL4_5, gL5_1,gL5_2,gL5_3,gL5_4, gL5_5, gL6_1,gL6_2,gL6_3,gL6_4,gL6_5);
		 clean();
		 alphaButton = new Button[] {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z};
		 gButton.setStyle("-fx-background-color: green");
		 win1.setStyle("-fx-background-color: green");
		 win2.setStyle("-fx-background-color: green");
		 aWord.setText("Answer: " + answerWord); 
		 
		 Scanner infile = null;
			try
			{
			      infile = new Scanner(new FileReader("word"));
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("File not found");
				e.printStackTrace(); // prints error(s)
				System.exit(0); // Exits entire program
			}
			
			while(infile.hasNextLine()) {
				wordList.add(infile.nextLine());
			}
			infile.close();
			chooseAnswer();
			win2.setText("THE WORD WAS: " + answerWord.toUpperCase());
			
			for(int i = 0 ; i < gO.length; i++) {
				
				gO[i].setText("Games completed on Guess " + (i+1) + ": " + 0);
			}
			
		}
	
	public boolean getWin() {
		return win;
	}
	
	public boolean getPause() {
		return pause;
	}
	
	public void chooseAnswer() {
		Random rand = new Random();
		int randNum = rand.nextInt(wordList.size());
		answerWord = wordList.get(randNum);
		aWord.setText("Answer: " + answerWord);
	}
	
	
	public void addtoGuess(String l) {
		InvalidLabel.setVisible(false);
		if(currSize < 5 && !notValidLetter(l)) {
			String letter = "" + l.toUpperCase();
			guessWord += letter;
			guess[currSize].setText(letter);

 			currSize += 1;
 			gWord.setText(guessWord);
		}
	}
	
	public void deletefromGuess() {
		if(currSize > 0) {
			currSize -= 1;
			guessWord = guessWord.substring(0, currSize);
			guess[currSize].setText("");
			gWord.setText(guessWord);
		}
	}
	
	public void deletefromGuess(ActionEvent e) {
		deletefromGuess();
	}
	
	public void guess() {
		if(guessWord.equals(answerWord.toUpperCase()) && currSize == 5 && !win) {
			message.setVisible(true);
			scr = scr + (6 - guessCount);
			scrLbl.setText("Score: " + scr);
			reset.setVisible(true);
			reset.setDisable(false);
			win = true;
			streak++;
			streakLabel.setText("Streak: " + streak);
			Pause.setVisible(true);
			BTG.setDisable(true);
			pause = true;
			win1.setVisible(true);
			win2.setVisible(true);
			
			guessedOn[guessCount]++;
			for(int i = 0 ; i < gO.length; i++) {
				//System.out.println(i);
				gO[i].setText("Games completed on Guess " + (i+1) + ": " + guessedOn[i]);
			}
			
			for(int i = 0; (i+val) < (val+5); i++) { 
				gL.get(i+val).setText("" + guessWord.charAt(i));
				gL.get(i+val).setStyle("-fx-background-color: green");
			}
			clean();
			
			try {
				fileWrite.write(guessWord +"\n");
				fileWrite.write("----------\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
		else if(!invalidWord(guessWord)) {
			InvalidLabel.setVisible(true);
			System.out.println("INVALID GUESS WORD");
		}
		else if(guessCount == 5 && !win) {
			win1.setVisible(true);
			win2.setVisible(true);
			win1.setStyle("-fx-background-color: red");
			win2.setStyle("-fx-background-color: red");
			win1.setText("YOU DID NOT GUESS THE WORD");
			Pause.setVisible(true);
			pause = true;
			BTG.setDisable(true);
			streak = 0;
		}
		else if(currSize == 5 && !win){
			guessCount += 1; 
			for(int x = 0; x < 5; x++) {
				guess[x].setText("");
				gL.get(x + val).setText("" + guessWord.charAt(x));
				if(isInAns(x)) {
					gL.get(x+val).setStyle("-fx-background-color: yellow");
					if(samecharPos(x)) {
						gL.get(x+val).setStyle("-fx-background-color: green");
					}
				}
				else {
					wrongLetters += (guessWord.charAt(x)+"");
					removeOption(guessWord.charAt(x)+"");
				}
			}
			try {
				fileWrite.write(guessWord +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			val+=5;
			guessWord = "";
			currSize = 0;
		}
	}
	
	public void closeFile() {
		try {
			fileWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSave() {
		Scanner infile = null;
		try
		{
		      infile = new Scanner(new FileReader(fileName.getText() + ".txt"));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
		
		win = infile.nextBoolean();
		scr = infile.nextInt();
		streak = infile.nextInt();
		infile.nextLine();
		wrongLetters = infile.nextLine();
		answerWord = infile.nextLine();
		//System.out.println(win + "," + scr + "," + streak + "," + wrongLetters + "," + answerWord);
		
		for(int i = 0; i < guessedOn.length; i++) {
//			System.out.println("Test:" + i);
			guessedOn[i] = infile.nextInt();
		}
		infile.nextLine();
		for(int i = 0; i < gL.size(); i++) {
			gL.get(i).setText(infile.nextLine());
			gL.get(i).setStyle(infile.nextLine());
			if(gL.get(i).getText() != "-") {
				val++;
			}
			
		}
		guessCount = val/5;
		if(win) {
			BTG.setDisable(true);
		}
	
		
		for(int i = 0; i < 5; i++) {
			guess[i].setText("");
		}
		aWord.setText(answerWord);
		
		infile.close();
		
		for(int i = 0 ; i < gO.length; i++) {
			
			gO[i].setText("Games completed on Guess " + (i+1) + ": " + guessedOn[i]);
		}
		for(int i = 0; i < wrongLetters.length(); i++) {
			removeOption(wrongLetters.charAt(i) + "");
		}
	}
	
	public void saveGame() throws Exception {
		saveWrite = new FileWriter(fileName.getText() + ".txt");
		saveWrite.write(win + "\n");
		saveWrite.write(scr + "\n");
		saveWrite.write(streak + "\n");
		saveWrite.write(wrongLetters+"\n");
		saveWrite.write(answerWord+"\n");
		
		for(int i = 0; i < guessedOn.length; i++) {
			saveWrite.write(guessedOn[i] + "\n");
		}
		for(int i = 0; i < gL.size(); i++) {
			saveWrite.write(gL.get(i).getText()+"\n");
			saveWrite.write(gL.get(i).getStyle()+"\n");
		}
		saveWrite.close();
	}
	
	public boolean isInAns(int x) {
		return (answerWord.toUpperCase().indexOf(guessWord.charAt(x)) > -1);
	}
	
	public boolean samecharPos(int x) {
		return (answerWord.toUpperCase().charAt(x) == guessWord.charAt(x));
	}
	
	public boolean notValidLetter(String l) {
		return wrongLetters.indexOf(l) > -1;
	}
	
	public boolean invalidWord(String l) {
		return wordList.indexOf(l.toLowerCase()) > -1;
	}
	
	public void getpressedLetter(ActionEvent e) {
		Button pressed = (Button) e.getSource();
		String letter = pressed.getId().toUpperCase();
		addtoGuess(letter);
	}
	
	public void reset() {
		if(pause) {
			BTG.setDisable(false);
			unpause();
		}
		
		answerWord = "";
		guessWord = "";
		wrongLetters = "";
		currSize = 0;
		guessCount = 0;
		for(int i = 0; i < 5; i++) {
			guess[i].setText("");
			r[i].setStroke(Color.BLACK);
			r[i].setFill(Color.color(0.4706, 0.4784, 0.4824, 1.0));
		}
		
		clean();
		chooseAnswer();
		reset.setDisable(true);
		reset.setVisible(false);
		message.setVisible(false);
		win = false;
		val = 0;
		for(int i = 0; i < 26; i++){
			alphaButton[i].setDisable(false);
		}
		
		win1.setVisible(false);
		win2.setVisible(false);
		win1.setText("YOU GUESSED THE WORD!!!");
		win2.setText("THE WORD WAS: " + answerWord);
		win1.setStyle("-fx-background-color: green");
		win2.setStyle("-fx-background-color: green");
	}
	
	public void clean() {
		for(int i = 0; i < gL.size(); i++) {
			//System.out.println(i);
			gL.get(i).setStyle(null);
			gL.get(i).setText("-");
		}
	}
	
	public void pause(ActionEvent e) {
		pause();
	}
	
	public void pause() {
		pause = true;
		for(int i = 0; i < 26; i++){
			alphaButton[i].setDisable(true);
		}
		Pause.setVisible(true);
	}
	
	public void unpause(ActionEvent e) {
		unpause();
	}
	
	public void unpause() {
		pause = false;
		for(int i = 0; i < 26; i++){
			alphaButton[i].setDisable(false);
		}
		Pause.setVisible(false);
	}
	
	public void removeOption(String l) {
		switch(l) {
		case "A":
			alphaButton[0].setDisable(true); 
			break;
		case "B":
			alphaButton[1].setDisable(true);
			break;
		case "C":
			alphaButton[2].setDisable(true);
			break;
		case "D":
			alphaButton[3].setDisable(true);
			break;
		case "E":
			alphaButton[4].setDisable(true);
			break;
		case "F":
			alphaButton[5].setDisable(true);
			break;
		case "G":
			alphaButton[6].setDisable(true);
			break;
		case "H":
			alphaButton[7].setDisable(true);
			break;
		case "I":
			alphaButton[8].setDisable(true);
			break;
		case "J":
			alphaButton[9].setDisable(true);
			break;
		case "K":
			alphaButton[10].setDisable(true);
			break;
		case "L":
			alphaButton[11].setDisable(true);
			break;
		case "M":
			alphaButton[12].setDisable(true);
			break;
		case "N":
			alphaButton[13].setDisable(true);
			break;
		case "O":
			alphaButton[14].setDisable(true);
			break;
		case "P":
			alphaButton[15].setDisable(true);
			break;
		case "Q":
			alphaButton[16].setDisable(true);
			break;
		case "R":
			alphaButton[17].setDisable(true);
			break;
		case "S":
			alphaButton[18].setDisable(true);
			break;
		case "T":
			alphaButton[19].setDisable(true);
			break;
		case "U":
			alphaButton[20].setDisable(true);
			break;
		case "V":
			alphaButton[21].setDisable(true);
			break;
		case "W":
			alphaButton[22].setDisable(true);
			break;
		case "X":
			alphaButton[23].setDisable(true);
			break;
		case "Y":
			alphaButton[24].setDisable(true);
			break;
		case "Z":
			alphaButton[25].setDisable(true);
			break;
		}
	}
	
	public void switchScreen(ActionEvent e) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}