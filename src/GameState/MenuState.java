package GameState;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Main.GamePanel;

/**
 * Class to handle the main menu state and displays it
 * @author TurkiAlharbi
 *
 */

public class MenuState extends GameState{
	//FIELDS
	private int currentChoice;
	private String[] options = {"Start", "Help", "Change Difficulty", "Exit"};
	
	//for title font
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	private Image background;
	
	//for difficulty options
	Object[] possibleDiff = {"Easy", "Normal", "HARD"};
	
	//CONSTRUCTOR
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Century Gothic", Font.PLAIN, 18);
		
		font = new Font("Ariel", Font.PLAIN, 14);
		
		try {
		    background = ImageIO.read(new File("MenuBackground.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		currentChoice = 0;
	}

	//FUNCTIONS
	public void init(){
		
	}
	
	public void update() {
		
	}

	public void draw(Graphics2D g) {
		//draw background
		g.drawImage(background, 0, 0, GamePanel.mainW, GamePanel.mainH, null);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("F I R S T  G A M E", (GamePanel.mainW / 2) - 100, 200);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++){
			if(i == currentChoice){
				g.setColor(Color.BLACK);
			}
			else{
				g.setColor(Color.RED);
			}
			g.drawString(options[i], (GamePanel.mainW / 2) - 80, 250 + i * 15);
		}
	}
	
	public void select(){
		if(currentChoice == 0){
			//start
			gsm.setCurrentState(GameStateManager.PLAYSTATE);
		}
		if(currentChoice == 1){
			//help
			JOptionPane.showMessageDialog(null, "- Use WASD keys to move your player");
		}
		if(currentChoice == 2){
			//change difficulty
			Object chosenOption = JOptionPane.showInputDialog(null, "Choose Difficulty", "Input", JOptionPane.INFORMATION_MESSAGE
					, null, possibleDiff, possibleDiff[0]);
			if(chosenOption == possibleDiff[0]){
				PlayState.setDifficulty(1);
				System.out.println("Difficulty Changed.");
			}
			if(chosenOption == possibleDiff[1]){
				PlayState.setDifficulty(2);
				System.out.println("Difficulty Changed.");
			}
			if(chosenOption == possibleDiff[2]){
				PlayState.setDifficulty(3);
				System.out.println("Difficulty Changed.");
			}
		}
		if(currentChoice == 3){
			//exit
			System.exit(0);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}
	}

	public void keyReleased(int k) {}
	public void mousePressed(int m) {}
	public void mouseReleased(int m) {}
}
