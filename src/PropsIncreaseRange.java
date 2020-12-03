import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//cleaned

/**
 * @author dillion
 *
 */
public class PropsIncreaseRange extends Props {
	
	private String imgSrc = "assets/health.png";
	private BufferedImage sheet;

	PropsIncreaseRange(int x, int y, int w, int h, String t, int s) {
		super(x, y, w, h, t, s);
		try {
			sheet = ImageIO.read(new File(imgSrc));
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
		player.setAttackRange(45);
		isActive = false;
		removeItem();
	}

	@Override
	public void update() {
		
	}

}
