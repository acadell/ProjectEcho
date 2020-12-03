import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//cleaned
/**
 * 
 */

/**
 * @author dillion
 *
 */
public class PropsIncreaseDamage extends Props {
	
	private String imgSrc;
	private BufferedImage sheet;

	PropsIncreaseDamage(int x, int y, int w, int h, String t, int s) {
		super(x, y, w, h, t, s);
		if(t.equals("light") )
		{
			imgSrc = "assets/Brush1.png";
		}
		if(t.equals( "medium" ))
		{
			imgSrc = "assets/Brush2.png";
		}
		if(t.equals( "heavy") )
		{
			imgSrc = "assets/Brush3.png";
		}
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
		
		if(player.getAttackDamage() < specialValue)
			player.score += specialValue*25;
		
		player.setAttackDamage( specialValue );
		player.setKnockback( specialValue * 15 );
		isActive = false;
		removeItem();
		Main.play("assets/itemGet.wav", false);
	}

	@Override
	public void update() {
		
	}

}
