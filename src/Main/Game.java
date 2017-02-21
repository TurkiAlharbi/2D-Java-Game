package Main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * this will be the main frame for the 
 * 2D shooter game
 * @author TurkiAlharbi
 *
 */
@SuppressWarnings("serial")
public class Game extends JFrame {
	
	public Game(){
		super("First 2D Shooter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new GamePanel());
		setResizable(false);
		pack();
		//to set the Icon
		try {
			setIconImage(ImageIO.read(new File("Icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.setVisible(true);
	}
}