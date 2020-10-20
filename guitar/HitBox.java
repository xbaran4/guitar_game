import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class HitBox {

	Dimension dimensions;
	private int positionX;
	private int positionY;
	private Color color;
	private int index;

	public HitBox(Dimension dimensions, int positionX, int positionY, Color color){
		this.dimensions = dimensions;
		this.positionX = positionX;
		this.positionY = positionY;
		this.color = color;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(new Point(positionX, positionY), dimensions);
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
