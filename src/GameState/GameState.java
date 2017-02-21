package GameState;

/**
 * a Super abstract class to pass certain methods all over the Game States
 * @author TurkiAlharbi
 *
 */

public abstract class GameState {
	protected GameStateManager gsm;    //why protected?
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mousePressed(int m);
	public abstract void mouseReleased(int m);
}
