import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author SquiggleBottom
 */
public class Skeleton extends Enemy {
	//private speed int
	private final int MAX_SPEED = 25;
	
	
	//handiling of importing the sprites 
	private String imgSrc = "assets/skeleton2.png";
	static private BufferedImage sheet; {
		try {
			sheet = ImageIO.read(new File(imgSrc));
		}
		catch (IOException e) {
			
		}
	}
	
	//integers used for the sprites 
	private int maxX;
	private int minX;
	private int animationRate;
	private int animationStep;
	private int walkCoordinates[][] = {
			{1, 0, 57 - 1, 50},
			{60, 0, 115 - 60, 50},
			{116, 0, 172 - 116, 50},
			{172, 0, 223 - 172, 50},
			{223, 0, 271 - 223, 50},
			{272, 0, 317 - 272, 50}
	};
	private int attackCoordinates[][] = {
			{32, 68, 62 - 32, 130 - 68},
			{70, 68, 100 - 70, 130 - 68},
			{104, 68, 138 - 104, 130 - 68},
			{190, 68, 224 - 190, 130 - 68}
	};
	//velcity x for x direction 
	private double vx = 0;

	
	public Skeleton() {
		// TODO Auto-generated constructor stub
	
		health = -1;
		isAlive = false;
		
		
		
		
	}
	//two parameter constructor for x and y
	public Skeleton(int initalX, int intialY) {
		// TODO Auto-generated constructor stub
		
		
		
		//variables used from enemy and slime 
		posX = initalX;
		posY = intialY;
		width = 50;
		height = 20;
		health = 25;
		attackDamage = 10;
		moveSpeed = 1;
		animationRate = 8;
		animationStep = 0;
		isAlive = true;
		isRight = true;
		isGrounded = false;
		canLeft = true;
		canRight = true;
		maxX = 700;
		minX = 50;
		score = health;
		
	}
	//constructor 
	public Skeleton(int initalX, int intialY, int initalW, int initalH) {
		//super(initalX, intialY, initalW, initalH);
		posX = initalX;
		posY = intialY;
		width = initalW;
		height = initalH;
		if(width > 70)
		{
			health = 50;
			attackDamage = 20;
		}
		else
		{
			health = 25;
			attackDamage = 15;
		}
		moveSpeed = 1;
		animationRate = 8;
		animationStep = 0;
		isAlive = true;
		isRight = true;
		isGrounded = false;
		maxX = 700;
		minX = 50;
		score = health;
	}
	
	public Skeleton(int initalX, int intialY, int initalW, int initalH, boolean goRight) {
		//super(initalX, intialY, initalW, initalH);
		posX = initalX;
		posY = intialY;
		width = initalW;
		height = initalH;
		health = 50;
		attackDamage = 20;
		moveSpeed = 1;
		animationRate = 8;
		animationStep = 0;
		isAlive = true;
		isGrounded = false;
		maxX = 600;
		minX = 50;
		
		posY-=2;
		isGrounded = false;
		fallSpeed =  -3;
		//calling knock back function for the enemies 
		if(goRight)
			vx = Main.randomNumber.nextDouble()*KNOCK_BACK;
		else
			vx = -Main.randomNumber.nextDouble()*KNOCK_BACK; 
		
		isRight = goRight;
		
		invicibleTemporary();
		score = health;
	}
	

	/* (non-Javadoc)
	 * @see Enemy#getCurrentSprite()
	 */
	@Override
	public BufferedImage getCurrentSprite() {
		BufferedImage img = null;
		if(!(vx == 0) && (fallSpeed == 0))
		{
			if (animationStep >= ((walkCoordinates.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(walkCoordinates[animationStep/animationRate][0], walkCoordinates[animationStep/animationRate][1], walkCoordinates[animationStep/animationRate][2], walkCoordinates[animationStep/animationRate][3]);
			animationStep++;
		}
		else if (!(fallSpeed == 0))
		{
			if (animationStep >= ((walkCoordinates.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(walkCoordinates[animationStep/animationRate][0], walkCoordinates[animationStep/animationRate][1], walkCoordinates[animationStep/animationRate][2], walkCoordinates[animationStep/animationRate][3]);
			animationStep++;
		}
		return img;
	}
	//recieve damage function
	@Override
	public void recieveDamage(int damage , boolean ISsright) {
		// TODO Auto-generated method stub
		if (invincibilityTime <= 0 && damage > 0) { //can only take damage if damage is greater than 0
			health -= damage;
			System.out.println("enemy takes damage: " + damage);
			Main.play("assets/hit.wav" , false);
			
		
			if(ISsright)
				vx = player.getKnockBack()/width;
			else
				vx = -player.getKnockBack()/width;
			invicibleTemporary();
			isGrounded = false;
			posY-=1;
			fallSpeed = -player.getKnockBack()/(width/1.5);
			invicibleTemporary(); 
		}
		
		
	}
	
	//move function so it can always follow the player
	@Override
	public void move() {
		int px = player.getX();
		int py = player.getY();
		
		
		if (isAlive) {
			if(health <=0 ) {
				isAlive = false;
				if (width > 50)
				{
					Main.spawnObject("skeleton", posX+(width/2), posY, width/2, height/2,true);
					Main.spawnObject("skeleton", posX+(width/2), posY, width/2, height/2,false);
				}
				destroyMe();
				System.out.println("enemy is dead");
			}
			
			
			else if( isRight && posX <  px + 35 && isGrounded && invincibilityTime < 0) {
				if(canRight){			//cant go right? switches direction
					isRight = true;
					vx = moveSpeed +1;
				}
				
				else
					isGrounded=false;
					fallSpeed = -5;
				}
				
			else if( isRight && posX > px +35  &&invincibilityTime < 0 && isGrounded) {
				if(canLeft){
					
					isRight = false;
					vx = -moveSpeed - 1;
			}
				else
					
						isGrounded = false;
						fallSpeed = -5;
				
			}
			else if ( !isRight && posX > px - 35 && isGrounded && invincibilityTime < 0) {
				if(canLeft)	{	
					//cant go left? try another direction!!
					isRight = false;
					vx = -moveSpeed-1;
				}
				else
						isGrounded = false;
						fallSpeed = -5;
					
				
					
			}
			else if ( !isRight && posX < px - 35 && invincibilityTime < 0 && isGrounded) {
				if(canRight){
					isRight = true;
					vx = moveSpeed +1;
				}
				else
					isGrounded = false;
					fallSpeed = -5;
					
			}
			else if (isRight && posX <= px+15 && posX >= px+15 && posY < py && invincibilityTime < 0 && isGrounded){
				if(canRight){
					isRight = true;
					vx = moveSpeed +1;
				}
				else if (!canRight && posY < py){
					isGrounded = false;
					fallSpeed = -5;
				
				}
				
					
			}
			else if (!isRight && posX <= px+15 && posX >= px+15 && posY < py && invincibilityTime < 0 && isGrounded){
				if(canLeft){
					isRight = false;
					vx = -moveSpeed - 1;
					
				}
				else if (!canLeft && posY < py){
					isGrounded = false;
					fallSpeed = -5;
				}
			}
			if(touchingCeiling)
			{
				fallSpeed = .1;
			}
			
			if( vx > MAX_SPEED && invincibilityTime < 0)
				vx = MAX_SPEED;
			
			if( vx < -MAX_SPEED && invincibilityTime < 0)
				vx = -MAX_SPEED;
			
			posX += vx;
			
			applyGravity();
			decrementInvincibility();
		}
		
		
		
	}

}
	