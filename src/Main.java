import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
//CLEAN
/**
 *	main execution class !!!!RUN THIS CLASS!!!
 */

public class Main
{
	static double deltaTime = 0;
	static Board level1;
	static GameDisplay gameDisplay;
	static Collision collisionDetection;
	static Character player;
	static boolean load = false;
	static Random randomNumber = new Random(System.currentTimeMillis());
	static public int title = -10083;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException 
	{
		//declaration of essential objects
		player = new Character();
		level1 = new Board("level" + title);
		collisionDetection = new Collision(level1,player);

		//EVERYTHING WE NEED TO GET THE DISPLAY WORKING
		JFrame frame = new JFrame("Echo");					//window with name of game
		gameDisplay = new GameDisplay();					// note that paint is called from this class using repaint method

		frame.add(gameDisplay);								//need this to call paint() method
		frame.addKeyListener(player);
		frame.setSize(720, 480);							//size of the game window
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		gameDisplay.setListOfBoards(level1);
		gameDisplay.setGenericCharacter(player);
		Props.setPlayer(player);
		
		//deltatime is used to make the game update 60 times a second
		deltaTime = 0;
		double then = System.nanoTime();
		double ticks = 60;
		double nanoSecs = 1000000000 / ticks;								//gives everything in terms of nanoseconds
		double now;
		
		play("assets/Unreal.wav" , true);
		while (!load) 															//only play while the level is not loading, onld 
		{	
			//System.out.println(player.getY());
			now = System.nanoTime();
			deltaTime = deltaTime + (now - then)/nanoSecs;
			then = now;
			
			if(deltaTime > 1)
			{			
				Enemy.setPlayer(player);					//gives the player to the enemy
				gameDisplay.setGenericCharacter(player);	//gives gamedisplay the player
				gameDisplay.setListOfBoards(level1);		//gives gamedisplay the board
				//should be last stuff in loop
				player.move();								//moves the player
				level1.MoveAllEnemies();					//moves the enemies
				collisionDetection.detectCollisions();		//performs collision detection
				gameDisplay.repaint();						// IMPORTANT calls the paint function
				deltaTime = 0;
			}

		}
		
	}
	
	public static void input(int b)
	{
		level1.loadLevel("level" + b);
		gameDisplay.setListOfBoards(level1);
		collisionDetection.setCurrentBoard(level1);
		player.setGrounded(false);
		title = 0;
	}
	
	//spawns in enemies 
	public static void spawnObject(String name, int x, int y, int width, int height, boolean goRight)
	{
		 level1.spawnEnemy(name, x, y, width, height, goRight);
	}
	
	//plays the sound files
	public static void play(String filename, boolean continuous)
	{
	    try
	    {
	        Clip sound = AudioSystem.getClip();
	        sound.open(AudioSystem.getAudioInputStream(new File(filename)));
	        sound.start();
	        
	        if(continuous)
	        	sound.loop(Clip.LOOP_CONTINUOUSLY);
	    }
	    catch (Exception e)
	    {
	        System.out.println("can't find file or some other error.");
	    }
	}
}
