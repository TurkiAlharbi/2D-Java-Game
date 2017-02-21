package Main;

import java.awt.*;

/**
 * Class for the game Bullets
 * @author TurkiAlharbi
 *
 */

public class Bullet {
	//FIELDS
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private Color color1;
	
	//CONSTRUCTOR
	public Bullet(double angle, int x, int y, Color color1){
		this.x = x;
		this.y = y;
		r = 3;
		
		rad = Math.toRadians(angle);
		speed = 10;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		this.color1 = color1;
	}
	
	//FUNCTIONS
	public double getX(){ return x; }
	public double getY(){ return y; }
	public double getR(){ return r; }
	
	public void setColor1(Color c){ color1 = c; }
	public void setR(int r){ this.r = r; }
	
	public boolean update(){
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.mainW + r || y < -r || y > GamePanel.mainH + r){
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color1);
		g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
	}
}
