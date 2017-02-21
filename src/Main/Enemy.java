package Main;

import GameState.PlayState;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class for the Enemies
 * @author TurkiAlharbi
 *
 */

public class Enemy {
	//FIELDS
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private int health;
	private int type;
	private int rank;
	
	//firing timer fields
	private boolean enemyFiring;
	private long firingTimer;
	private long firingDelay;
	
	private Color color1;
	
	//color for enemy bullet
	private Color color2;
	
	private Image enemy1;
	private Image enemy2;
	private Image enemyBoss;
	
	private boolean ready;
	private boolean dead;
	
	//CONSTRUCTOR
	public Enemy(int type, int rank){
		this.type = type;
		this.rank = rank;
		color2 = Color.BLACK;
		
		try {
		    enemy1 = ImageIO.read(new File("Enemy1.png"));
		    enemy2 = ImageIO.read(new File("Enemy2.png"));
		    enemyBoss = ImageIO.read(new File("EnemyBoss.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		//stalker enemy
		if(type == 1){
			color1 = Color.BLUE;
			if(rank == 1){
				speed = 2;
				r = 10;
				health = 1;
				enemyFiring = false;
			}
		}
		//stronger, faster, can shoot stalker enemy
		if(type == 2){
			color1 = Color.RED;
			if(rank == 1){
				speed = 3;
				r = 10; 
				health = 2;
				enemyFiring = true;
				firingDelay = 1500;
			}
		}
		//can shoot, unmoving enemy boss
		if(type == 3){
			color1 = Color.BLACK;
			if(rank == 1){
				speed = 0;
				r = 10;
				health = 3;
				enemyFiring = true;
				firingDelay = 1000;
			}
		}
		x = Math.random() * GamePanel.mainW / 2 + GamePanel.mainW / 4;
		y = 50;
		
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
		
		firingTimer = System.nanoTime();
	}
	
	//FUNCTIONS
	public double getX(){ return x; }
	public double getY(){ return y; }
	public double getR(){ return r; }
	
	public int getType(){ return type; }
	public int getRank(){ return rank; }

	/**
	 * return method to check if the enemy is dead
	 * @return this Enemy's life status
	 */
	public boolean isDead(){ return dead; }
	
	/**
	 * method for bullets
	 * @param b this Enemy's new Fire status
	 */
	public void setEnemyFiring(boolean b){ enemyFiring = b; };
	
	public void hit(){
		health--;
		if(health <= 0){
			dead = true;
		}
	}
	
	public void followPlayer(){
		double a = PlayState.player.getX() - x;
		double b = PlayState.player.getY() - y;
		double h = Math.sqrt(a * a + b * b);
		dx = (a / h) * speed;
		dy = (b / h) * speed;
	}
	
	/**
	 * method to get the player angle for bullet
	 * @return this Enemy's firing angle
	 */
	public double bulletAngle(){
		return Math.atan2(PlayState.player.getY() - y, PlayState.player.getX() - x) * (180 / Math.PI);
	}
	
	public void update(){
		followPlayer();
		x += dx;
		y += dy;
		
		if(!ready){
			if(x > r && x < GamePanel.mainW - r && y > r && y < GamePanel.mainH - r){
				ready = true;
			}
		}
		
		//to make the enemy bounce off the walls
		if(x < r && dx < 0) dx = -dx;
		if(y < r && dy < 0) dy = -dy;
		if(x > GamePanel.mainW - r && dx > 0) dx = -dx;
		if(y > GamePanel.mainH - r && dy > 0) dy = -dy;
		
		if(enemyFiring){
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;       //time passed since last shot was made
			if(elapsed > firingDelay){
				PlayState.enemyBullets.add(new Bullet(bulletAngle(), (int) x,(int) y, color2));
				firingTimer = System.nanoTime();
			}
		}
	}
	
	public void draw(Graphics2D g){
		if(type == 1){
			g.drawImage(enemy1, (int) (x - 50), (int) (y - 50), null);
		}
		if(type == 2){
			g.drawImage(enemy2, (int) (x - 90), (int) (y - 60), null);
		}
		if(type == 3){
			g.drawImage(enemyBoss, (int) (x - 60), (int) (y - 50), null);
		}
		g.setColor(color1);
		g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.darker());
		g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
	}	
}
