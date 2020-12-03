import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PropsTeleport extends Props
{

	private String imgSrc = "assets/EndGame.png";
	private BufferedImage sheet;

	PropsTeleport(int x, int y, int w, int h, String t, int s)
	{
		super(x, y, w, h, t, s);
		try
		{
			sheet = ImageIO.read(new File(imgSrc));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public BufferedImage getCurrentSprite()
	{
		return sheet;
	}

	@Override
	public void applyPropEffect()
	{
		player.pos = specialValue;
		Main.input(player.pos);
		Main.title = 25;
		GameDisplay.refresh = true;
	}
	
}
