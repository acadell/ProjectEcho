import java.awt.Rectangle;
import java.awt.image.BufferedImage;
//CLEANED
/**
 * @author dillion
 *
 */

@SuppressWarnings("serial")
public abstract class Box extends Rectangle {
	
	protected String tag;
	protected int health;
	protected boolean isActive;
	
	Box(int tmpX, int tmpY, int tmpW, int tmpH) {
		super.x = tmpX;
		super.y = tmpY;
		super.width = tmpW;
		super.height = tmpH;
		tag = "static";
		health = 1000000;
		isActive = true;
	}
	
	public int getBoxX() {
		return (int) super.x;
	}
	
	public int getBoxY() {
		return (int) super.y;
	}
	
	public int getBoxW() {
		return (int) super.width;
	}
	
	public int getBoxH() {
		return (int) super.height;
	}
	
	public abstract void special(int value);
	
	public abstract BufferedImage getCurrentSprite();

}
