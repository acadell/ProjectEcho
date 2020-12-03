import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//CLEANED
/**
 * @author diltd
 *
 */
@SuppressWarnings("serial")
public class BoxDestroy extends Box {
	
	static private String imgSrc = "assets/CrackedWall.png";
	static private BufferedImage sheet;

	BoxDestroy(int tmpX, int tmpY, int tmpW, int tmpH) {
		super(tmpX, tmpY, tmpW, tmpH);
		health = 50;
		tag = "destructable";
		try {
			sheet = ImageIO.read(new File(imgSrc));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = sheet.getSubimage(0, 0, 16, 16);
	}

	/* (non-Javadoc)
	 * @see Box#special()
	 */
	@Override
	public void special(int damage) {
		if(damage >= 5 )
			health -= damage;
		if (health < 0)
			destroyMe();
	}
	
	public void special(int damage, int breakDamage ) {
		if(damage >= breakDamage )
			health -= damage;
		if (health < 0)
			destroyMe();
	}

	/* (non-Javadoc)
	 * @see Box#getCurrentSprite()
	 */
	@Override
	public BufferedImage getCurrentSprite() {
		return sheet;
	}
	
	public void destroyMe() {
		x = 0;
		y = 0;
		width = 0;
		health = -100;
		height = 0;
		isActive = false;
		
	}

}
