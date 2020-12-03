import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//CLEAN
/**
 * @author dillion
 *
 */

public abstract class Props {
	
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	protected boolean isActive;
	protected String tag;
	protected int specialValue;
	//static
	static protected Character player = null;
	
	Props(int x, int y, int w, int h, String t, int s) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		isActive = true;
		tag = t;
		specialValue = s;
	}
	
	public abstract void update();
	public abstract BufferedImage getCurrentSprite();
	
	
	public void removeItem() {
		posX = -1000;
		posY = -1000;
		isActive = false;
	}
	
	
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the specialValue
	 */
	public int getSpecialValue() {
		return specialValue;
	}

	/**
	 * @param specialValue the specialValue to set
	 */
	public void setSpecialValue(int specialValue) {
		this.specialValue = specialValue;
	}
	
	/**
	 * @return the player
	 */
	public static Character getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public static void setPlayer(Character player) {
		Props.player = player;
	}
	
	public abstract void applyPropEffect();

	public Rectangle getPropCollisionBox(){
		return new Rectangle(posX, posY, width, height);
	}
	
	

}
