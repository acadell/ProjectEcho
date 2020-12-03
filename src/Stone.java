import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author dillion
 *
 */
@SuppressWarnings("serial")
public class Stone extends Box {
	
	private String imgSrc = "Brick.png";
	static private BufferedImage sheet;

	Stone(int tmpX, int tmpY, int tmpW, int tmpH) {
		super(tmpX, tmpY, tmpW, tmpH);
		tag = "static";
		try {
			sheet = ImageIO.read(this.getClass().getClassLoader().getResource(imgSrc));//new File(imgSrc));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = sheet.getSubimage(0, 0, 16, 16);
	}

	/* (non-Javadoc)
	 * @see Box#getCurrentSprite()
	 */
	@Override
	public BufferedImage getCurrentSprite() {
		return sheet;

	}

	@Override
	public void special( int value) {
		
	}

}
