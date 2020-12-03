import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



/**
 * @author dillion
 *
 */
public class PropsHealthBox extends Props {
	
	static private String imgSrc = "HPBox.png";
	static private BufferedImage sheet;

	PropsHealthBox(int x, int y, int w, int h, String t, int s) {
		super(x, y, w, h, t, s);
		try {
			sheet = ImageIO.read(this.getClass().getClassLoader().getResource(imgSrc));//new File(imgSrc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see Props#getCurrentSprite()
	 */
	@Override
	public BufferedImage getCurrentSprite() {
		return sheet;
	}

	@Override
	public void applyPropEffect() {
		if (player.score >= 100)
		{
			player.setHealth(player.getHealth() + specialValue);
			isActive = false;
			removeItem();
			player.score -= 100;
		}
	}

	@Override
	public void update() {
		
	}


}
