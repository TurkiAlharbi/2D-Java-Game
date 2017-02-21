package GameState;

import Main.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Class that handles the main play state, 
 * it handles also the collisions and movements and 
 * drawing and updating the player and the enemies, 
 * it is practically the Game Engine
 * @author TurkiAlharbi
 *
 */

public class PlayState extends GameState {
	//FIELDS
	private Image background;
	
	public static Player player;
	public static ArrayList<Bullet> playerBullets;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Bullet> enemyBullets;
	
	public static int difficulty;
	
	//CONSTRUCTOR
	public PlayState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	//FUNCTIONS
	public static void setDifficulty(int i){ difficulty = i; }
	
	/**
	 * initialize Objects
	 */
	public void init(){
		try {
		    background = ImageIO.read(new File("Background.jpg"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		player = new Player();
		playerBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>(); 
		enemyBullets = new ArrayList<Bullet>();
		
		if(difficulty == 0){
			difficulty = 1;
		}
		
		createNewEnemies();
	}
	
	/**
	 * initialize enemies
	 */
	private void createNewEnemies(){
		enemies.clear();
		
		if(difficulty == 1){
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(1, 1));
			}
			for(int i = 0; i < 1; i++){
				enemies.add(new Enemy(3, 1));
			}
		}
		if(difficulty == 2){
			for(int i = 0; i < 3; i++){
				enemies.add(new Enemy(1, 1));
			}
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(3, 1));
			}
		}
		if(difficulty == 3){
			for(int i = 0; i < 1; i++){
				enemies.add(new Enemy(1, 1));
			}
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(2, 1));
			}
			for(int i = 0; i < 3; i++){
				enemies.add(new Enemy(3, 1));
			}
		}
	}
	
	public void update() {
		//player update
		player.update();
		
		//player bullet update
		for(int i = 0; i < playerBullets.size(); i++){
			boolean remove = playerBullets.get(i).update();
			if(remove){
				playerBullets.remove(i);
				i--;
			}
		}
		
		//enemy bullet update
		for(int i = 0; i < enemyBullets.size(); i++){
			boolean remove = enemyBullets.get(i).update();
			if(remove){
				enemyBullets.remove(i);
				i--;
			}
		}
		
		//enemy update
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
		
		//COLLISIONS
		//bullet-enemy collision
		for(int i = 0; i < playerBullets.size(); i++){
			Bullet b = playerBullets.get(i);
			double bx = b.getX();
			double by = b.getY();
			double br = b.getR();
			
			for(int j = 0; j < enemies.size(); j++){
				Enemy e = enemies.get(j);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getR();
				
				double dx = bx - ex;
				double dy = by - ey;
				double distance = Math.sqrt(dx * dx + dy * dy);
				
				if(distance < br + er){
					e.hit();
					playerBullets.remove(i);
					i--;
					break;
				}
			}
		}
		
		//bullet-player collision
		if(!player.isRecovering()){
			for(int i = 0; i < enemyBullets.size(); i++){
				Bullet b = enemyBullets.get(i);
				double bx = b.getX();
				double by = b.getY();
				double br = b.getR();
				
				double px = player.getX();
				double py = player.getY();
				double pr = player.getR();
				
				double dx = bx - px;
				double dy = by - py;
				double distance = Math.sqrt(dx * dx + dy * dy);
				
				if(distance < br + pr){
					player.loseLife();
					enemyBullets.remove(i);
					i--;
					break;
				}
			}
		}
		
		//player-enemy collision
		if(!player.isRecovering()){
			int px = player.getX();
			int py = player.getY();
			int pr = player.getR();
			for(int i = 0; i < enemies.size(); i++){
				Enemy e = enemies.get(i);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getR();
						
				double dx = px - ex;
				double dy = py - ey;
				double distance = Math.sqrt(dx * dx + dy * dy);
						
				if(distance < pr + er){
					player.loseLife();
					enemies.get(i).hit();
				}
			}
		}
		
		//check dead enemies
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).isDead()){
				Enemy e = enemies.get(i);
				player.addScore(e.getType() + e.getRank());
				enemies.remove(i);
				i--;
				if(enemies.size() == 0){
					gsm.setCurrentState(GameStateManager.YOUWINSTATE);
				}
			}
		}
		
		//check dead player
		if(player.isDead()){
			gsm.setCurrentState(GameStateManager.GAMEOVERSTATE);
			player.setLives(3);
		}
	}

	public void draw(Graphics2D g) {
		//draw background
		g.drawImage(background, 0, 0, GamePanel.mainW, GamePanel.mainH, null);
		
		//draw player
		player.draw(g);
		
		//draw player bullet
		for(int i = 0; i < playerBullets.size(); i++){
			playerBullets.get(i).draw(g);
		}
		
		//draw enemy bullet
		for(int i = 0; i < enemyBullets.size(); i++){
			enemyBullets.get(i).draw(g);
		}
		
		//draw enemy
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		//draw player lives
		for(int i = 0; i < player.getLives(); i++){
			g.drawImage(player.getHeart(), 10 + (22 * i), 5, 20, 20, null);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A){
			player.setLeft(true);
		}
		if(k == KeyEvent.VK_D){
			player.setRight(true);
		}
		if(k == KeyEvent.VK_W){
			player.setUp(true);
		}
		if(k == KeyEvent.VK_S){
			player.setDown(true);
		}
	}

	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A){
			player.setLeft(false);
		}
		if(k == KeyEvent.VK_D){
			player.setRight(false);
		}
		if(k == KeyEvent.VK_W){
			player.setUp(false);
		}
		if(k == KeyEvent.VK_S){
			player.setDown(false);
		}
	}
	
	public void mousePressed(int m) {
		if(m == MouseEvent.BUTTON1){
			player.setFiring(true);
		}
	}
	
	public void mouseReleased(int m) {
		if(m == MouseEvent.BUTTON1){
			player.setFiring(false);
		}
	}
}
