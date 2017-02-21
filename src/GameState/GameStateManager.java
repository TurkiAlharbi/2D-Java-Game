package GameState;

import java.util.ArrayList;

/**
 * Class that handles all of the game states
 * @author TurkiAlharbi
 *
 */

public class GameStateManager {
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int YOUWINSTATE = 3;
	
	public GameStateManager(){
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new YouWinState(this));
	}
	
	public void setCurrentState(int state){ 
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
	public void mousePressed(int m) {
		gameStates.get(currentState).mousePressed(m);
	}
	
	public void mouseReleased(int m) {
		gameStates.get(currentState).mouseReleased(m);
	}
}
