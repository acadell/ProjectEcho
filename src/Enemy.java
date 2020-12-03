import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class Enemy {
	
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	protected int health;
	protected int attackDamage;
	protected int moveSpeed;
	protected int score;
	protected double invincibilityTime = 0;
	protected double fallSpeed;
	protected boolean facingPlyaer = false; 
	protected boolean isAlive = true; 
	protected boolean isRight;
	protected boolean isGrounded = false;
	protected boolean canRight;
	protected boolean canLeft;
	protected boolean touchingCeiling = false;
	
	//static
	static protected double deltaTime = 1;
	static protected Character player;
	static protected int KNOCK_BACK = 5;
	static public boolean BossAlive = true;
	
	//abstract methods
	public abstract BufferedImage getCurrentSprite();
	public abstract void move();
	public abstract void recieveDamage (int damage, boolean isright);

	
	public void destroyMe() {
		player.score += score;
		posX = -1000;
		posY = -1000;
		width = 0;
		height = 0;
		health = -100;
		attackDamage = 0;
		moveSpeed = 0;
		fallSpeed = 0;
		isRight = false;
		isGrounded =  true;
	}
	
	
	public void invicibleTemporary() {
		invincibilityTime  = 25;
	}
	
	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the attackDamage
	 */
	public int getAttackDamage() {
		return attackDamage;
	}

	/**
	 * @param attackDamage the attackDamage to set
	 */
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
		
		
	}

	public void setTouchingCeiling(boolean touching)
	{
		touchingCeiling = touching;
	}
	
	/**
	 * @return the moveSpeed
	 */
	public int getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * @param moveSpeed the moveSpeed to set
	 */
	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @return the isRight
	 */
	public boolean isRight() {
		return isRight;
	}

	/**
	 * @param isRight the isRight to set
	 */
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	/**
	 * @return the deltaTime
	 */
	public static double getDeltaTime() {
		return deltaTime;
	}

	/**
	 * @param deltaTime the deltaTime to set
	 */
	public static void setDeltaTime(double deltaTime) {
		Enemy.deltaTime = deltaTime;
	}

	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void setFacingPlayer (boolean facingplayer){
		this.facingPlyaer = facingplayer;
	}

	/**
	 * @return the isGrounded
	 */
	public boolean isGrounded() {
		return isGrounded;
	}
	

	/**
	 * @param isGrounded the isGrounded to set
	 */
	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}
	
	public void setCanLeft(boolean localCanLeft)
	{
		this.canLeft = localCanLeft;
	}
	
	public void setCanRight(boolean localCanRight)
	{
		this.canRight = localCanRight;
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
	 * @return the fallSpeed
	 */
	public double getFallSpeed() {
		return fallSpeed;
	}

	/**
	 * @param fallSpeed the fallSpeed to set
	 */
	public void setFallSpeed(double fallSpeed) {
		this.fallSpeed = fallSpeed;
	}

	/**
	 * @return the player
	 */
	public static Character getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public static void setPlayer(Character player) {
		Enemy.player = player;
	}

	/**
	 * @return the canRight
	 */
	public boolean isCanRight() {
		return canRight;
	}

	/**
	 * @return the canLeft
	 */
	public boolean isCanLeft() {
		return canLeft;
	}
	
	public void applyGravity() {
		if (! isGrounded) {
			fallSpeed += (.15);// * deltaTime;
			posY +=  fallSpeed;// *deltaTime;
		}
		else
			fallSpeed = 0;
	}
	
	public void decrementInvincibility() {
		if (invincibilityTime > -100)
		{
			invincibilityTime--;
		}
		
	}
	
	public Rectangle getHitBox() {
		return (new Rectangle(posX, posY, width, height));
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	
	
	public Rectangle getAttackBox() {
		return new Rectangle(0, 0, 0, 0);
	}
	
	public Rectangle getSmallerHitbox() {
		double value = .5;
		double other = 0.25;
		return new Rectangle((int) (posX  + width * other),(int) (posY + height * other), (int)(width *value),(int) (height* value));
	}	
	
}