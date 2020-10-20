import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
	Game game;
	public Keyboard(Game game){
		this.game = game;
	}
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	}
	
}