package GameState;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;

import Main.GamePanel;

/**
 * Class that handles the game over state and displays it
 * @author TurkiAlharbi
 *
 */

public class GameOverState extends GameState{
	//FIELDS
	private int currentChoice;
	private String[] options = {"Return to Main Menu", "Quit"};
	
	//for font
	private Font font;
	
	private Image background;
	
	//CONSTRUCTOR
	public GameOverState(GameStateManager gsm){
		this.gsm = gsm;
		
		font = new Font("Ariel", Font.PLAIN, 14);
		
		try {
		    background = ImageIO.read(new File("GameOver.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		currentChoice = 0;
	}
	
	//FUNCTIONS
	public void init(){
		
	}
	
	public void update(){
		
	}

	public void draw(Graphics2D g) {
		//draw background
		g.drawImage(background, 0, 0, GamePanel.mainW, GamePanel.mainH, null);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++){
			if(i == currentChoice){
				g.setColor(Color.RED);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], (GamePanel.mainW / 2) - 80, 200 + i * 15);
		}
	}

	public void select(){
		if(currentChoice == 0){
			//return to main menu
			gsm.setCurrentState(GameStateManager.MENUSTATE);
		}
		if(currentChoice == 1){
			//exit
			System.out.println("Thanks for playing!");
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
