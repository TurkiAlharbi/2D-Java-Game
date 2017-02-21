package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import GameState.GameStateManager;

/**
 * this class will be the main, 
 * it passes all the states, 
 * and contains the game loop
 * @author TurkiAlharbi
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener{
	//FIELDS
	public static final int mainW = 960;
	public static final int mainH = 720;
	
	private BufferedImage image;
	private Graphics2D g;
	
	//game thread
	private Thread thread;
	public static boolean running;
	private int FPS = 60;
	private double avgFPS;
	
	//game state manager
	private GameStateManager gsm;
	
	//CONSTRUCTOR
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(mainW, mainH));
		setFocusable(true);
		requestFocus();
	}
	
	//FUNCTIONS
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		addMouseListener(this);
	}
	
	/**
	 * to initialize the objects
	 */
	public void init(){
		running = true;
	
		image = new BufferedImage(mainW, mainH, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		//to make the graphics more smooth
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		gsm = new GameStateManager();
	}
	
	public void run(){
		init();
		
		long startTime;
		long URDTimeMillis;     //Update Render Draw(UPD)
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 60;
		
		long targetTime = 1000 / FPS;
		
		//GAME LOOP
		while(running){
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;        //the FPS desired wait time in milliseconds
			
			try {
				if(waitTime < 0) waitTime = 0;
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				System.out.println("Unexpected interrupt");
    	        System.exit(0);
			}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount){
				avgFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
		
	}
	
	private void gameUpdate(){
		/**
		 * pass the update method to game state manager
		 */
		gsm.update();
	}
	
	
	
	private void gameRender(){
		/**
		 * pass the draw method to game state manager
		 */
		gsm.draw(g);
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	public void keyTyped(KeyEvent arg0) {}

	public void mousePressed(MouseEvent key) {
		gsm.mousePressed(key.getButton());
	}
	public void mouseReleased(MouseEvent key) {
		gsm.mouseReleased(key.getButton());
	}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent key) {}
	public void mouseExited(MouseEvent arg0) {}
}
