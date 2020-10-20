import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;


public class PlayingBox {
	Dimension dimensions;
	private int positionX;
	private int positionY;
	private Color color;
	private int index;
	private int velocity;

	public PlayingBox(Dimension dimensions, int positionX, int positionY, Color color, int index){
		this.dimensions = dimensions;
		this.positionX = positionX;
		this.positionY = positionY;
		this.color = color;
		this.index = index;
		velocity = 5;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(new Point(positionX, positionY), dimensions);
	}
	
	public void refresh(Game game){
		positionY += velocity; 
		if(positionY > game.getGameHeight() )
		{
			game.getPlayingBoxes().remove(this);
			game.getPlayer().setHealth(game.getPlayer().getHealth() - 1);
		}
	}

	public Dimension getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


}
