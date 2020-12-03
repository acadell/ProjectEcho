import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author SquiggleBottom
 */
public class Slime extends Enemy {
	
	private final int MAX_SPEED = 10;
	
	
	
	private String imgSrc = "assets/slime.png";
	static private BufferedImage sheet; {
		try {
			sheet = ImageIO.read(new File(imgSrc));
		}
		catch (IOException e) {
			
		}
	}
	private int maxX;
	private int minX;
	private int animationRate = 4;
	private int animationStep;
	private int runCoordinates[][] = {
			{ 8, 52, 33, 22},
			{ 58, 52, 33, 22},
			{ 108, 52, 33, 22},
			{ 158, 52, 33, 22},
			{ 208, 52, 33, 22},
			{ 258, 52, 33, 22},
			{ 308, 52, 33, 22},
			{ 358, 52, 33, 22},
			{ 408, 52, 33, 22}
	};
	private int idleCoordinates[][] = {
			{ 8, 13, 33, 22},
			{ 58, 13, 33, 22},
			{ 108, 13, 33, 22},
			{ 158, 13, 33, 22},
			{ 208, 13, 33, 22},
			{ 258, 13, 33, 22}
	};
	
	private double vx = 0;
	
	public Slime() {
		health = -1;
		isAlive = false;
	}
	//two parameter constructor for x and y
	public Slime(int initalX, int intialY) {
		// TODO Auto-generated constructor stub
		//super(initalX, intialY, 50, 20);
		posX = initalX;
		posY = intialY;
		width = 33;
		height = 22;
		health = width/4;
		attackDamage = width/10;
		moveSpeed = 1;
		//animationRate = 10;
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
	
	public Slime(int initalX, int intialY, int initalW, int initalH) {
		//super(initalX, intialY, initalW, initalH);
		posX = initalX;
		posY = intialY;
		width = initalW;
		height = initalH;
		health = width/4;
		attackDamage = width/10;
		moveSpeed = 1;
		//animationRate = 10;
		animationStep = 0;
		isAlive = true;
		isRight = true;
		isGrounded = false;
		maxX = 700;
		minX = 50;
		score = health;
	}
	
	public Slime(int initalX, int intialY, int initalW, int initalH, boolean goRight) {
		//super(initalX, intialY, initalW, initalH);
		posX = initalX;
		posY = intialY;
		width = initalW;
		height = initalH;
		health = width/4;
		attackDamage = width/10;
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
		if(((vx == 0) && (fallSpeed == 0)) || (!(vx == 0) && !(fallSpeed == 0)) )
		{
			if (animationStep >= ((idleCoordinates.length) * animationRate))
				animationStep = 0;
			img = sheet.getSubimage(idleCoordinates[animationStep/animationRate][0], idleCoordinates[animationStep/animationRate][1], idleCoordinates[animationStep/animationRate][2], idleCoordinates[animationStep/animationRate][3]);
			animationStep++;
		}
		if(!(vx == 0) && (fallSpeed == 0))
		{
			if (animationStep >= ((runCoordinates.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(runCoordinates[animationStep/animationRate][0], runCoordinates[animationStep/animationRate][1], runCoordinates[animationStep/animationRate][2], runCoordinates[animationStep/animationRate][3]);
			animationStep++;
		}
		if(vx == 0 && fallSpeed > 0)
		{
			img = sheet.getSubimage(3, 90, 48-3, 35-12);
		}
		if(invincibilityTime > 0)
		{
			if( (int) invincibilityTime%3 == 0)
			img = sheet.getSubimage(3, 10, 1, 1);
		}
		return img;
	}

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

	@Override
	public void move() {
		/*
		 * moves to the right then to the left then repeats
		 */
		if (isAlive) {
			if(health <=0 ) {
				isAlive = false;
				if (width > 50)
				{
					Main.spawnObject("slime", posX+(width/2), posY, width/2, height/2,true);
					Main.spawnObject("slime", posX+(width/2), posY, width/2, height/2,false);
				}
				destroyMe();
				System.out.println("enemy is dead");
			}

			else if( isRight && posX <  maxX && isGrounded && invincibilityTime < 0) {
				if(canRight)			//cant go right? switches direction
					vx = moveSpeed;
				else
					isRight = false;
			}
			else if( isRight && posX >= maxX && invincibilityTime < 0 && isGrounded) {
				isRight = false;
			}
			else if ( !isRight && posX > minX  && isGrounded && invincibilityTime < 0) {
				if(canLeft)				//cant go left? try another direction!!
					vx = -moveSpeed;
				else
					isRight = true;
			}
			else if ( !isRight && posX <= minX && invincibilityTime < 0 && isGrounded) {
				isRight = true;
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
