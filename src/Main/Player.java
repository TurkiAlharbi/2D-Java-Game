package Main;

import GameState.PlayState;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class for the player
 * @author TurkiAlharbi
 *
 */

public class Player {
	//FIELDS
	private int x;
	private int y;
	private int r;
	
	private int dx;
	private int dy;
	private int speed;
	
	//moving player fields
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	//firing timer fields
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private boolean recovering;
	private long recoveryTimer;
	
	private Image heart;
	private Image sprite;
	
	private int lives;
	private Color color1;
	private Color color2;
	
	//color for player bullet
	private Color color3;
	
	private int score;
	
	private Point mouseP;
	
	//CONSTRUCTOR
	public Player(){
		x = GamePanel.mainW / 2;
		y = GamePanel.mainH / 2;
		r = 5;
		
		dx = 0;
		dy = 0;
		speed = 5;
		
		lives = 3;
		color1 = Color.WHITE;
		color2 = Color.RED;
		
		color3 = Color.YELLOW;
		
		try {
		    sprite = ImageIO.read(new File("Player.png"));
		    heart = ImageIO.read(new File("Heart.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 200;
		
		recovering = false;
		recoveryTimer = 0;
		
		score = 0;
		
		mouseP = new Point();
	}
	
	//FUNCTIONS
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getR(){ return r; }
	
	public int getDx(){ return dx; }
	public int getDy(){ return dy; }
	
	/**
	 * return method to get the player's current lives
	 * @return this Player's lives
	 */
	public int getLives(){ return lives; }
	public Image getHeart(){ return heart; }
	
	/**
	 * method to check if the player is recovering
	 * @return this Player's recovering status
	 */
	public boolean isRecovering(){ return recovering; }
	/**
	 * method to check if the player's health is zero(dead)
	 * @return this Player's life status
	 */
	public boolean isDead(){ return lives <= 0; }
	
	/**
	 * gets the score to post in victory screen
	 * @return the user's score
	 */
	public int getScore(){ return score; }
	
	/**
	 * set methods for moving the player
	 * @param b this Player's new direction of movement
	 */
	public void setLeft(boolean b){ left = b; }
	public void setRight(boolean b){ right = b; }
	public void setUp(boolean b){ up = b; }
	public void setDown(boolean b){ down = b; }
	
	/**
	 * method for bullets
	 * @param b this Player's new Fire status
	 */
	public void setFiring(boolean b){ firing = b; };
	
	public void addScore(int i){ score += i; } 
	
	public void setLives(int i){ lives = i; }
	
	public void loseLife(){
		lives--;
		recovering = true;
		recoveryTimer = System.nanoTime(); 
	}
	
	/**
	 * to get the angle for the bullet
	 * @return this Player's firing angle
	 */
	public double bulletAngle(){
		return Math.atan2(mouseP.getY() - y, mouseP.getX() - x) * (180 / Math.PI);
	}
	
	public void update(){
		if(left){
			dx = -speed;
		}
		if(right){
			dx = speed;
		}
		if(up){
			dy = -speed;
		}
		if(down){
			dy = speed;
		}
		
		x += dx;
		y += dy;
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > GamePanel.mainW - r) x = GamePanel.mainW - r;
		if(y > GamePanel.mainH - r) y = GamePanel.mainH - r;
		
		dx = 0;
		dy = 0;
		
		if(firing){
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;       //time passed since last shot was made
			if(elapsed > firingDelay){
				PlayState.playerBullets.add(new Bullet(bulletAngle(), x, y, color3));
				firingTimer = System.nanoTime();
			}
		}
		
		//to set time for player to recover after being hit
		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000){
			recovering = false;
			recoveryTimer = 0;
		}
	}
	
	public void draw(Graphics2D g){
		Graphics2D g2d = (Graphics2D)g.create();
		mouseP = MouseInfo.getPointerInfo().getLocation();
		double rotation = Math.atan2(mouseP.getY() - y, mouseP.getX() - x);
		g2d.rotate(rotation, x, y);
		
		if(recovering){
			g2d.drawImage(sprite, x - 50, y - 50, null);
			g.setColor(color2);
			g.fillOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(3));
			g.setColor(color2.darker());
			g.drawOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}
		else{
			g2d.drawImage(sprite, x - 50, y - 50, null);
			g.setColor(color1);
			g.fillOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(3));
			g.setColor(color1.darker());
			g.drawOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}
	}
}
