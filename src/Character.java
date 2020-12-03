import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//NEEDS COMMENTS

//import javax.swing.ImageIcon;
//main character class
@SuppressWarnings("serial")
public class Character extends JPanel implements KeyListener{
	
//===================private data members======================================================================================
	private final double gravity = .5;
	private final double jumpPower = 10;
	
	//movement booleans 
	private boolean right = false;
	private boolean left = false;
	private boolean jumping = false;
	private boolean falling = false; 
	private boolean attacking = true;
	
	
	//player attributes 
	private int health;
	private int maxHealth; 
	private int attackDamage;
	private int attackRange =10;
	
	private boolean isAlive = true;
	private boolean isRight = true;
	private int animationRate;
	
	private boolean canright = true;
	private boolean canleft = true; 
	private boolean grounded = true;
	private boolean touchingCeiling = false;
	protected double invincibilityTime = 0;
	
	//coordinates
	private int x;
	private int y; 
	private int height;
	private int width;
	
	private double attackBuffer = 0;
	private int attackTime = 25;
	private int movespeed = 2; 
	private int dx = 0; 
	private double vy = 0;
	private double vx = 0;
	private double timeTillNextAttack = 0;
	public int pos = 10;
	private int knockback  = 25;
	private boolean rev = false;
	
	public int score = 0;

	//Sprite Maps
	String imgSource = "Bob-Ross.png";
	BufferedImage sheet;
	int step = 0;
	int spriteIdleCoordinates[][] = {
			{0, 6, 14, 29},
			{16, 6, 14, 29},
			{32, 6, 14, 29},
			{48, 6, 14, 29}
	};
	int spriteRunCoordinates[][] = {
			{0, 41, 14, 29},
			{16, 41, 14, 29},
			{32, 41, 14, 29},
			{48, 41, 14, 29},
			{64, 41, 14, 29},
			{80, 41, 14, 29},
			{96, 41, 14, 29},
			{112, 41, 14, 29},
			{128, 41, 14, 29},
			{144, 41, 14, 29}
	};
	int spriteAttackCoordinates[][] = {
			{0, 110, 29, 32},
			{30, 110, 29, 32},
			{62, 110, 30, 32},
			{96, 110, 32, 32},
			{128, 110, 32, 32},
			{163, 110, 34, 32}
	};
	int spriteAttackCoordinates2[][] = {
			{0, 145, 29, 32},
			{30, 145, 29, 32},
			{62, 145, 30, 32},
			{96, 145, 32, 32},
			{128, 145, 32, 32},
			{163, 145, 34, 32}
	};
	int spriteAttackCoordinates3[][] = {
			{0, 180, 29, 32},
			{30, 180, 29, 32},
			{62, 180, 30, 32},
			{96, 180, 32, 32},
			{128, 180, 32, 32},
			{163, 180, 34, 32}
	};
	
//=======================End of Private data members=========================================================================	
	
//jumpSpeed
	public double jumpSpeed = 5;
	public double currentJumpspeed = jumpSpeed;
	
	//falling speed
	public double maxFall = 5;
	public double currentFallspeed = 2;
	
	
	//character consturctor 
	
	/**
	 * Default constructor
	 * sets the default image for the character and the values needed for it to work
	 */
	public Character(){
		x = 100;
		y = 400;
		width = 28;
		height = 58;  
		
		//setting the health and attack 
		health = maxHealth = 100;
		attackDamage = 0;
		attackRange = 40;
		movespeed = 4;
		animationRate = 5;
		
		sheet = null;
		try {
			sheet = ImageIO.read(this.getClass().getClassLoader().getResource(imgSource));//new File(imgSource));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot find picture.");
		}
		
	}
	
	//move function 
		/**
		 * checks all the conditions and inputs of the player and move the position accordingly
		 * is called every frame;
		 */
		public void move(){
			/*
			 * moving to the right and left
			 */
			if (isAlive) {
				if(health <= 0) {
					isAlive = false;
					playerDie();
				}
				if (right && canright && (attackBuffer < 0 || vy != 0)){
					x+=movespeed;
				}
				else if (left && canleft && (attackBuffer < 0 || vy != 0)){
					x -=movespeed;
				}
				//=========================
				/*
				 * checking if player is falling or is on the ground
				 */
				if (grounded){
					falling = false;
				}
				if(jumping && attackBuffer < 0  )
				{
					grounded = false;
					vy = -jumpPower;
					jumping = false;
				}
				if(!grounded)
				{
					if(touchingCeiling)
					{
						vy = 0;
					}
					vy +=gravity;
					y+=vy;
				}
				else
				{
					vy = 0;
				}
				attackBuffer--;
				if(invincibilityTime > -100)
				{
					invincibilityTime--;
				}
				if(x > 700)
				{
					x%=700;
					pos+=1;
					Main.input(pos);
				}
				if(x < -width)
				{
					x+=720;
					pos-=1;
					Main.input(pos);
				}
				if (y > 480)
				{
					y%=480;
					pos-=10;
					Main.input(pos);
				}
				if(y < -height)
				{
					y+=480;
					pos+=10;
					Main.input(pos);
					
					
				}
			}
			if(timeTillNextAttack > 0)
				timeTillNextAttack--;
			
		}

		/**
		 * method automatically called every frame by KeyListener
		 */
		//@Override
		public void keyTyped(KeyEvent arg0) {
		}

		//@Override
		/**
		 * method automatically called every frame by KeyListener
		 * checks for any keys pressed down and moves the player accordingly
		 * right key moves player right
		 * left key moves player left 
		 * up key moves player up and jumps
		 * spacebar makes the player attack
		 */
		public void keyPressed(KeyEvent e) {
			int button = e.getKeyCode();
			
			if (button == KeyEvent.VK_SPACE){
				
				
				if (attacking && timeTillNextAttack <= 0)
				{
					attackBuffer = attackTime;
					timeTillNextAttack = 30;
				}
				//System.out.println("attack was pressed" + ":: " + attackBuffer);
				attacking = false;
			}
			else
			if (button == KeyEvent.VK_RIGHT) {
				right = true; 
				left = false;
				if (canright && attackBuffer < 0) {
					dx = 1;
					isRight =true;
				}
			}
			else
			if (button == KeyEvent.VK_LEFT ) {
				left = true; 
				right = false;
					if (canleft && attackBuffer < 0) {
						dx =-1;
						isRight =false;
					}
			}
			else
			if (button == KeyEvent.VK_UP && grounded) {
				jumping = true;
			}
		}

		//@Override
		/**
		 * method automatically called  everyframe by KeyListenr
		 * resets the states of player to what they were before the keys were pressed
		 */
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int k = e.getKeyCode();
					if (k == KeyEvent.VK_RIGHT) {
						right =false;
						dx =0;
					}
					if (k == KeyEvent.VK_LEFT) {
						left = false; 
						dx =0;
					}
					if (k == KeyEvent.VK_UP) {
						
					}
					if (k == KeyEvent.VK_SPACE){
						//attack = false; 
						attacking = true;
					}
		}

		
	
	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * @return the jumping
	 */
	public boolean isJumping() {
		return jumping;
	}

	/**
	 * @param jumping the jumping to set
	 */
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	/**
	 * @return the falling
	 */
	public boolean isFalling() {
		return falling;
	}

	/**
	 * @param falling the falling to set
	 */
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	/**
	 * @return the attacking
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * @param attacking the attacking to set
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	/**
	 * @return the attackRange
	 */
	public int getAttackRange() {
		return attackRange;
	}

	/**
	 * @param attackRange the attackRange to set
	 */
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}



	

	/**
	 * @return the canleft
	 */
	public boolean isCanleft() {
		return canleft;
	}
	
	/**
	 * @return the canright
	 */
	public boolean isCanright() {
		return canright;
	}

	/**
	 * @param canleft the canleft to set
	 */
	public void setCanleft(boolean canleft) {
		this.canleft = canleft;
	}
	
	/**
	 * @param canright the canright to set
	 */
	public void setCanright(boolean canright) {
		this.canright = canright;
	}

	/**
	 * @return the invincibilityTime
	 */
	public double getInvincibilityTime() {
		return invincibilityTime;
	}

	/**
	 * @param invincibilityTime the invincibilityTime to set
	 */
	public void setInvincibilityTime(double invincibilityTime) {
		this.invincibilityTime = invincibilityTime;
	}

	/**
	 * @return the attackBuffer
	 */
	public double getAttackBuffer() {
		return attackBuffer;
	}

	/**
	 * @param attackBuffer the attackBuffer to set
	 */
	public void setAttackBuffer(double attackBuffer) {
		this.attackBuffer = attackBuffer;
	}

	/**
	 * @return the attackTime
	 */
	public int getAttackTime() {
		return attackTime;
	}

	/**
	 * @param attackTime the attackTime to set
	 */
	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}

	/**
	 * @return the movespeed
	 */
	public int getMovespeed() {
		return movespeed;
	}

	/**
	 * @param movespeed the movespeed to set
	 */
	public void setMovespeed(int movespeed) {
		this.movespeed = movespeed;
	}

	/**
	 * @return the dx
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * @param dx the dx to set
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}

	/**
	 * @return the vy
	 */
	public double getVy() {
		return vy;
	}

	/**
	 * @param vy the vy to set
	 */
	public void setVy(double vy) {
		this.vy = vy;
	}

	/**
	 * @return the vx
	 */
	public double getVx() {
		return vx;
	}

	/**
	 * @param vx the vx to set
	 */
	public void setVx(double vx) {
		this.vx = vx;
	}

	/**
	 * @return the timeTillNextAttack
	 */
	public double getTimeTillNextAttack() {
		return timeTillNextAttack;
	}

	/**
	 * @param timeTillNextAttack the timeTillNextAttack to set
	 */
	public void setTimeTillNextAttack(double timeTillNextAttack) {
		this.timeTillNextAttack = timeTillNextAttack;
	}

	/**
	 * @return the pos
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * @return the knockback
	 */
	public int getKnockback() {
		return knockback;
	}

	/**
	 * @param knockback the knockback to set
	 */
	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}

	/**
	 * @return the sheet
	 */
	public BufferedImage getSheet() {
		return sheet;
	}

	/**
	 * @param sheet the sheet to set
	 */
	public void setSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * @return the jumpSpeed
	 */
	public double getJumpSpeed() {
		return jumpSpeed;
	}

	/**
	 * @param jumpSpeed the jumpSpeed to set
	 */
	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	/**
	 * @return the currentJumpspeed
	 */
	public double getCurrentJumpspeed() {
		return currentJumpspeed;
	}

	/**
	 * @param currentJumpspeed the currentJumpspeed to set
	 */
	public void setCurrentJumpspeed(double currentJumpspeed) {
		this.currentJumpspeed = currentJumpspeed;
	}

	/**
	 * @return the maxFall
	 */
	public double getMaxFall() {
		return maxFall;
	}

	/**
	 * @param maxFall the maxFall to set
	 */
	public void setMaxFall(double maxFall) {
		this.maxFall = maxFall;
	}

	/**
	 * @return the currentFallspeed
	 */
	public double getCurrentFallspeed() {
		return currentFallspeed;
	}

	/**
	 * @param currentFallspeed the currentFallspeed to set
	 */
	public void setCurrentFallspeed(double currentFallspeed) {
		this.currentFallspeed = currentFallspeed;
	}

	/**
	 * @return the gravity
	 */
	public double getGravity() {
		return gravity;
	}

	/**
	 * @return the jumpPower
	 */
	public double getJumpPower() {
		return jumpPower;
	}

	/**
	 * @return the animationRate
	 */
	public int getAnimationRate() {
		return animationRate;
	}

	/**
	 * @return the grounded
	 */
	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * @return the touchingCeiling
	 */
	public boolean isTouchingCeiling() {
		return touchingCeiling;
	}

	/**
	 * @param maxHealth the maxHealth to set
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * @param attackDamage the attackDamage to set
	 */
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	/**
	 * @param isRight the isRight to set
	 */
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * returns the current health of the player
	 * @return health
	 */
	public int getHealth(){
		return health;
	}
	
	/**
	 * returns the maximum helath that the player can have
	 * @return maxHealth
	 */
	public int getMaxHealth(){
		return maxHealth;
	}
	
	
	/**
	 * returns the width of the player for display and collision purposes
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of the player for display and collision purposes
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * returns the x coordinate of the player
	 * note that this is the top left corner of where the player is displayed
	 * @return x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * returns the y coordinate of the player
	 * note that this is the  top left corner of the where the player is displayed
	 */
	public int getY(){
		return y; 
	}
	
	
	/**
	 * sets the players x position to  xpos
	 * note x is the top left corner
	 * @param xpos
	 */
	public void setX(int xpos){
		x = xpos; 
	}
	
	/**
	 * sets the players y position to  ypos
	 * note y is the top left corner
	 * @param ypos
	 */
	public void setY(int ypos){
		y  = ypos;
		
	}
	
	/**
	 * sets the ability of the player to move to the right 
	 * note this value is related to collision 
	 * @param localCanRight
	 */
	public void setCanRight(boolean localCanRight)
	{
		canright = localCanRight;
	}
	
	/**
	 * sets the ability of the player to move to the left 
	 * note this value is related to collision 
	 * @param localCanLeft
	 */
	public void setCanLeft(boolean localCanLeft)
	{
		canleft = localCanLeft;
	}
	
	//determines if the ceiling is hit
	/**
	 * sets the condition is if the player is directly beneath a ceiling
	 * note this is for collision and jumping purposes
	 * @param isTouchingCeiling
	 */
	public void setTouchingCeiling(boolean isTouchingCeiling)
	{
		touchingCeiling = isTouchingCeiling;
	}
	
	/**
	 * returns the state of player of weather it is facing towards the right of the screen or not
	 * @return the  isRight
	 */
	public boolean isRight() {
		return isRight;
	}
	
	
	/**
	 * sets the state of the player's grounded to tempGrounded
	 * @param tempGrounded
	 */
	public void setGrounded(boolean tempGrounded) {
		// TODO Auto-generated method stub
		grounded = tempGrounded;
	}
	
	public void playerDie() {
		System.out.println("404 player is dead");
		pos = 11;
		Main.input(pos);
		x = 300;
		y = 50;
		health = 100;
		isAlive = true;
		score = 0;
		/*x = -1000;
		y = -1000;*/
		//grounded = true;
		//System.exit(0);
	}
	
	/**
	 * 
	 * @param damage
	 */
	public void recieveDamage( int damage) {
		if(invincibilityTime < 0) {
			health -= damage;
			invincibilityTime = 25;
			if( isRight) 
			{
				
			}
			else
			{
				
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAttackDamage() {
		return attackDamage;
	}
	
	/**
	 * 
	 * @return
	 */
	public Rectangle getAttackBox() {
		Rectangle attackBox = null;
		if (attackBuffer > 0) {
			if (isRight) {
				attackBox = new Rectangle(x + width, y, attackRange, height);
			}
			else {
				attackBox = new Rectangle(x - attackRange, y, attackRange, height);
			}
		}
		return attackBox;
	}
	
	/**
	 * @param animationRate the animationRate to set
	 */
	public void setAnimationRate(int animationRate) {
		this.animationRate = animationRate;
	}

	/**
	 * takes the dx and determines whether the character is moving right or not
	 * @return returns a buffered image of the current sprite to be displayed
	 */
	public BufferedImage getCurrentSprite() {
		BufferedImage currentImg = null;
		/*if (rev)
		{
			x+=32;
			rev = false;
		}
		width = 28;*/
		if(attackBuffer < 0)
		{
			rev = false;
		}
		if((jumping || vy < 0 ) && attackBuffer < 0) {
			currentImg = sheet.getSubimage(0, 76, 14-0, 29-0);
		}
		else if((falling || vy > 0 ) && attackBuffer < 0)
		{
			currentImg = sheet.getSubimage(16, 76, 14-0, 29-0);
		}
		else if((right || left) && (canleft && canright) && attackBuffer < 0){
			setAnimationRate(16);
			if (step >= (animationRate * (spriteRunCoordinates.length)))
				step = 0;
			currentImg = sheet.getSubimage(spriteRunCoordinates[step/animationRate][0], spriteRunCoordinates[step/animationRate][1], spriteRunCoordinates[step/animationRate][2], spriteRunCoordinates[step/animationRate][3]);
			step++;
		}
		else if(attackBuffer >= 0 && attackDamage > 0){
			/*width = 64;
			if(!isRight)
			{
				x-=32;
				rev = true;
			}*/
			if(!rev)
			{
				rev = true;
				step = 0;
			}
			setAnimationRate(12);
			if (step >= (animationRate * (spriteAttackCoordinates.length))) {
				step = 70;
			}
			if (attackDamage == 5)
			{
				currentImg = sheet.getSubimage(spriteAttackCoordinates[step/animationRate][0], spriteAttackCoordinates[step/animationRate][1], spriteAttackCoordinates[step/animationRate][2], spriteAttackCoordinates[step/animationRate][3]);
			}
			if (attackDamage == 10)
			{
				currentImg = sheet.getSubimage(spriteAttackCoordinates2[step/animationRate][0], spriteAttackCoordinates2[step/animationRate][1], spriteAttackCoordinates2[step/animationRate][2], spriteAttackCoordinates2[step/animationRate][3]);
			}
			if (attackDamage > 10)
			{
				currentImg = sheet.getSubimage(spriteAttackCoordinates3[step/animationRate][0], spriteAttackCoordinates3[step/animationRate][1], spriteAttackCoordinates3[step/animationRate][2], spriteAttackCoordinates3[step/animationRate][3]);
			}
			step++;
		}
		else {
			setAnimationRate(16);
			if (step >= (animationRate * (spriteIdleCoordinates.length)))
				step = 0;
			currentImg = sheet.getSubimage(spriteIdleCoordinates[step/animationRate][0], spriteIdleCoordinates[step/animationRate][1], spriteIdleCoordinates[step/animationRate][2], spriteIdleCoordinates[step/animationRate][3]);
			step++;
		}
		if (invincibilityTime > 0)
		{
			if((int)invincibilityTime%2 == 0)
			currentImg = sheet.getSubimage(0, 0, 1, 1);
		}
		return currentImg;
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
		//prevent health from going above 100
		if(this.health > 100)
			this.health = 100;
	}	
	
	public Rectangle getHitbox() {
		return new Rectangle (x , y, (int)(width),(int) (height));
	}
	
	public int getKnockBack()
	{
		return knockback;
	}
	
}