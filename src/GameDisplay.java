import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author dillion
 *
 */
public class GameDisplay extends JPanel {
	
	private Board genericBoard;
	private Character genericCharacter;
	private BufferedImage backDrop;
	public static boolean refresh = true;
	public boolean win = false;
	
	//private Area currentArea;
	
	public GameDisplay() {
		// TODO Auto-generated constructor stub
		try {
			backDrop= ImageIO.read(new File("assets/creepyCave.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//gamedisplay.displayarea(x,y,w,h); // display a box at x,y with width w and height h.
	public void setListOfBoards(Board tmpBoard) {
		genericBoard = tmpBoard;
	}
	
	public void setGenericCharacter(Character tmpCharacter) {
		genericCharacter = tmpCharacter;
	}
	
	
	
	/*
	 * public void setArea(Area tmpArea) {
	 * currentArea = tmpArea;
	 * 
	 }
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	//public void displayBoard(Board tmpBoard) {
		//BufferedImage nextImg = null;									
		
		//first display background
		
	//}

	@Override
	public void paint(Graphics g) {
		
		if(refresh)
		{
			try
			{
				if(Main.title == -10083)
				{
					backDrop= ImageIO.read(new File("assets/TitleHolder.jpg"));
				}
				else if(Main.title == 25)
				{
					backDrop = ImageIO.read(new File("assets/endScreen.jpg"));
					win = true;
					refresh = false;
				}
				else
				{
					backDrop= ImageIO.read(new File("assets/creepyCave.jpg"));
					refresh = false;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		super.paint(g);										//refresh
		Graphics2D g2d = (Graphics2D) g;					// creates another graphics thing i guess
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//.setColor(Color.orange);
		//g2d.fillOval(52, 100, 100, 100);
		
		g.drawImage(backDrop, 0, 0, 720, 480, null);
		if(win)
		{
			g2d.setColor(Color.WHITE);
			g2d.fillRect(330, 310, 85, 30);
			g2d.setColor(Color.RED);
			g.drawString("You win!", 350, 330);
		}
		g2d.setColor(Color.orange);                         //not needed original BOXES
		
		//================Board Items===========Display==================================================================================================================================
		/*
		for (int i = 0; i < genericBoard.boxCrateList.length; i++) {
		
			g.drawImage(genericBoard.boxCrateList[i].getCurrentSprite(), genericBoard.boxCrateList[i].x, genericBoard.boxCrateList[i].y, genericBoard.boxCrateList[i].width, genericBoard.boxCrateList[i].height, null);
		}
		for (int i = 0; i < genericBoard.enemeyBasicList.length; i++) {
		*/
		for (int i = 0; i < genericBoard.boxCrateList.length; i++) 
		{
			for ( int k = genericBoard.boxCrateList[i].x; k < genericBoard.boxCrateList[i].width + genericBoard.boxCrateList[i].x; k += 16)
			{
				for ( int j = genericBoard.boxCrateList[i].y; j < genericBoard.boxCrateList[i].height + genericBoard.boxCrateList[i].y; j += 16)
				{
					g.drawImage(genericBoard.boxCrateList[i].getCurrentSprite(), k, j, 16, 16, null);
				}
			}
		}
		for (int i = 0; i < genericBoard.enemeyBasicList.length; i++) {
/*//*test draw enemy hitboxes
			g2d.setColor(Color.BLUE);			
			g.fillRect(genericBoard.enemeyBasicList[i].posX, genericBoard.enemeyBasicList[i].posY, genericBoard.enemeyBasicList[i].width, genericBoard.enemeyBasicList[i].height);
			g2d.setColor(Color.white);
			g.fillRect(genericBoard.enemeyBasicList[i].getSmallerHitbox().x, genericBoard.enemeyBasicList[i].getSmallerHitbox().y, genericBoard.enemeyBasicList[i].getSmallerHitbox().width, genericBoard.enemeyBasicList[i].getSmallerHitbox().height);
//*/		
			if(genericBoard.enemeyBasicList[i].getHealth() > 0){
				if(genericBoard.enemeyBasicList[i].isRight) {
					//g.fillRect(genericBoard.enemeyBasicList[i].getPosX(), genericBoard.enemeyBasicList[i].getPosY(),( (genericBoard.enemeyBasicList[i].getWidth() / genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()  ),( (genericBoard.enemeyBasicList[i].getHeight()/genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight() ));
					//g.drawImage(genericBoard.enemeyBasicList[i].getCurrentSprite(), genericBoard.enemeyBasicList[i].getPosX(), genericBoard.enemeyBasicList[i].getPosY(),( (genericBoard.enemeyBasicList[i].getWidth() / genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()  ),( (genericBoard.enemeyBasicList[i].getHeight()/genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight() ), null);
					//g.drawImage(genericBoard.enemeyBasicList[i].getCurrentSprite(), genericBoard.enemeyBasicList[i].getPosX(), genericBoard.enemeyBasicList[i].getPosY(), genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()*genericBoard.enemeyBasicList[i].getWidth(), genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight()*genericBoard.enemeyBasicList[i].getWidth(), null);
					g.drawImage(genericBoard.enemeyBasicList[i].getCurrentSprite(), genericBoard.enemeyBasicList[i].getPosX(), genericBoard.enemeyBasicList[i].getPosY(), genericBoard.enemeyBasicList[i].getWidth() , genericBoard.enemeyBasicList[i].getHeight() , null);

				}
				else {
				//	g.setColor(Color.orange);
				//	g.fillRect(genericBoard.enemeyBasicList[i].getPosX(), genericBoard.enemeyBasicList[i].getPosY(),  ( (genericBoard.enemeyBasicList[i].getWidth() / genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()  ),( (genericBoard.enemeyBasicList[i].getHeight()/genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight() ));
				//	g.drawImage(genericBoard.enemeyBasicList[i].getCurrentSprite(), genericBoard.enemeyBasicList[i].getPosX() + genericBoard.enemeyBasicList[i].getWidth(), genericBoard.enemeyBasicList[i].getPosY(),( -1 * (genericBoard.enemeyBasicList[i].getWidth() / genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getWidth()  ),((genericBoard.enemeyBasicList[i].getHeight()/genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight()) * genericBoard.enemeyBasicList[i].getCurrentSprite().getHeight() ), null);
					g.drawImage(genericBoard.enemeyBasicList[i].getCurrentSprite(), genericBoard.enemeyBasicList[i].getPosX() + genericBoard.enemeyBasicList[i].getWidth() , genericBoard.enemeyBasicList[i].getPosY(), -1 * genericBoard.enemeyBasicList[i].getWidth() , genericBoard.enemeyBasicList[i].getHeight() , null);

				}
			}
		}
		for (int i = 0; i < genericBoard.itemsList.length; i++) {
			if ( genericBoard.itemsList[i].isActive())
				g.drawImage(genericBoard.itemsList[i].getCurrentSprite(), genericBoard.itemsList[i].getPosX(), genericBoard.itemsList[i].getPosY(), genericBoard.itemsList[i].getWidth(), genericBoard.itemsList[i].getHeight(), null);
		}
		//===============================================================================================================================================================================
		//g2d.setColor(Color.BLUE);
		//g2d.fillRect(genericCharacter.getX()/*+(genericCharacter.getWidth()/2)*/, genericCharacter.getY()/*+(genericCharacter.getHeight()/2)*/, genericCharacter.getWidth(), genericCharacter.getHeight());
		//System.out.println("x: " + genericCharacter.getX() +  " Y; " + genericCharacter.getY());
		
		if (genericCharacter.isAlive()) {
//PLAYER HITBOX
/*	
			g2d.setColor(Color.BLUE);
			g2d.fillRect( genericCharacter.getX(), genericCharacter.getY(), genericCharacter.getWidth(), genericCharacter.getHeight());
			*/
			if(genericCharacter.isRight() ){
				g.drawImage(genericCharacter.getCurrentSprite(), genericCharacter.getX(), genericCharacter.getY(), genericCharacter.getCurrentSprite().getWidth()*2, genericCharacter.getCurrentSprite().getHeight()*2, null);
				
			}
			else {
				g.drawImage(genericCharacter.getCurrentSprite(), genericCharacter.getX()+genericCharacter.getWidth(), genericCharacter.getY(), -2 *genericCharacter.getCurrentSprite().getWidth(), genericCharacter.getCurrentSprite().getHeight()*2, null);
			}
		}
/*		g2d.setColor(Color.RED);
		if  (genericCharacter.getAttackBox() != null)
			g2d.fillRect(genericCharacter.getAttackBox().x, genericCharacter.getAttackBox().y, genericCharacter.getAttackBox().width, genericCharacter.getAttackBox().height);
*/	 //System.out.println(genericCharacter.getAttackBox().x + " -- " + genericCharacter.getAttackBox().y);
		//g.drawImage(genEnemy.getCurrentSprite() ,genEnemy.getPosX(), genEnemy.getPosY(), genEnemy.getWidth(), genEnemy.getHeight(), null);
		
		//g.drawImage(genBox.getCurrentSprite(), genBox.x, genBox.y, genBox.width, genBox.height, null);
		
		//HEALTH BOXXXXXXXXXX PRO ($14.99)																											//+1 Mtn dew
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 100, 20);
		g2d.setColor(Color.RED);
		g2d.fillRect(0, 0, genericCharacter.getHealth(), 20);
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, 100, 20);
		g.setColor(Color.white);
		g.drawString("Health", 30, 15);
		g.drawString("Score: " + genericCharacter.score, 120, 15);
		g.drawString("Attack power: " + genericCharacter.getAttackDamage(), 610 , 15);
	}

}
 