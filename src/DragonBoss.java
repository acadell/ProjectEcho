import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * @author diltd
 *
 */
public class DragonBoss extends Enemy {
	
	static private BufferedImage sheet;
	private int animationRate = 15;
	private int animationStep = 0;
	private int wHard;
	private int hHard;
	private Rectangle currentAttackBox = new Rectangle(0, 0, 0, 0);
	private double dx = 0;
	private double dy = 0;
	final private String[] AI = {"thinking", "idle", "attack", "fly", "jump"};
	private int aiState = 0;
	private int aiStep = 0;
	private int aiRate = 15;
	private String imgSrc = "assets/dragon2.png";
	
	int spriteIdle [][] ={
			{0, 260, 121 - 0, 350 - 260},
			{130, 260, 251 - 130, 350 - 260},
			{260, 260, 381 - 260, 350 - 260}
	};
	
	int spriteAttack [][] ={
			{5, 474, 185 - 5, 590 - 474},
			{190, 474, 370 - 190, 590 - 474},
			{380, 474, 560 - 380, 590 - 474}
	};
	
	int spriteFly [][] ={
			{118, 138, 264 - 118, 254 - 138},
			{274, 138, 440 - 274, 254 - 138},
			{444, 138, 618 - 444, 254 - 138},
			{635, 138, 800 - 635, 254 - 138},
			{813, 138, 986 - 813, 254 - 138},
			{998, 138, 1135 - 998, 254 - 138},
			{1142, 138, 1270 - 1142, 254 - 138}
	};
	
	/**
	 * 
	 */
	public DragonBoss() {
		health = -1;
		isAlive = false;
	}
	
	/**
	 * 
	 * @param initX
	 * @param initY
	 * @param initW
	 * @param initH
	 */
	public DragonBoss(int initX, int initY, int initW, int initH) {
		posX = initX;
		posY = initY;
		width = initW;
		height = initH;
		wHard = initW;
		hHard = initH;
		health = 150;
		attackDamage = 15;
		isAlive = true;
		isGrounded = false;
		fallSpeed =  -3;
		isRight = true;
		moveSpeed =10;
		try {
			sheet = ImageIO.read(new File(imgSrc));
		}
		catch (IOException e) {
			
		}
	}

	/* (non-Javadoc)
	 * @see Enemy#recieveDamage(int, boolean)
	 */
	@Override
	public void recieveDamage(int damage, boolean isright) {
		
		damage/=2;
		if (invincibilityTime <= 0 && damage > 0) //can only take damage if damage is greater than 0
		{
			health -= damage;
			invicibleTemporary();
			Main.play("assets/hit.wav" , false);
		}
	}
	
	public void bossLogic() {
		
		int targetX = player.getX();
		int maxStep = 10 * aiRate * 3;
		switch (aiState) {
			case 1: {	// Idle
				break;
			}
			case 2: {	// Attack
				if (aiStep <= 0) { // aiState has ended
				aiState = 3;
				aiStep = maxStep;
				
				}
				else if(aiStep% 100 == 0)
				{
					Main.spawnObject("slime", posX + 20, posY + 20, 33, 22, true);
					Main.spawnObject("slime", posX + 20, posY + 20, 33, 22, false);
				}
				aiStep--;
				break;
			}
			case 3: {	// Fly
				if (aiStep <= 0) { // aiState has ended
					aiStep = maxStep;
					aiState = 0;
				}
				else {
					if( targetX +100 < posX) {
						isRight = false;
						dx -= Main.randomNumber.nextInt(2);//-distRatio;
					}
					else if(targetX -100 > posX) {
						isRight = true;
						dx += Main.randomNumber.nextInt(2);
					}
					if(isGrounded)
					{
						isGrounded = !isGrounded;
						posY -= 5;
					}
					//fallSpeed= -(aiStep%5 + 7);
					aiStep--;
				}
				if( posY+height > 420)
				{
					fallSpeed = -Main.randomNumber.nextInt(10);
				}
				break;
			}
			case 4: {	// intital jump animataion before flying
				if (aiStep <= 0) {
					aiStep = maxStep;
					aiState = 3;	//sets the state to flying
				}
				else {
					
					//posY-=(aiStep%(maxStep/9));
					aiStep--;
				}
				break;
			}
			default:{	// Thinking
				dx = 0;
				if (aiStep <= 0) {	// aiState has ended
					aiStep = maxStep;
					if (Math.abs(targetX - posX) < 150) {
						aiState = 2;	// attack
						aiStep = maxStep/2;
					}
					else {
						aiState = 3;	// Jump before flying
						aiStep = maxStep;
					}
				}
				else {	// ai State has not ended
					aiStep = 0;
					
				}
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see Enemy#getCurrentSprite()
	 */
	@Override
	public BufferedImage getCurrentSprite() {
		BufferedImage img = null;
		switch (aiState) {
		case 1: {	// Idle Animation
			if (animationStep >= ((spriteIdle.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(spriteIdle[animationStep/animationRate][0], spriteIdle[animationStep/animationRate][1], spriteIdle[animationStep/animationRate][2], spriteIdle[animationStep/animationRate][3]);
			break;
		}
		case 2: {	// Attack Animation
			if (animationStep >= ((spriteAttack.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(spriteAttack[animationStep/animationRate][0], spriteAttack[animationStep/animationRate][1], spriteAttack[animationStep/animationRate][2], spriteAttack[animationStep/animationRate][3]);
			
			break;
		}
		case 3: {	// Fly Animation
			if (animationStep >= ((spriteFly.length) * animationRate))
			{
				animationStep = 1;
			}
			img = sheet.getSubimage(spriteFly[animationStep/animationRate][0], spriteFly[animationStep/animationRate][1], spriteFly[animationStep/animationRate][2], spriteFly[animationStep/animationRate][3]);
			break;
		}
		case 4: {	// intital jump animataion before flying
			if (animationStep >= ((spriteIdle.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(spriteIdle[animationStep/animationRate][0], spriteIdle[animationStep/animationRate][1], spriteIdle[animationStep/animationRate][2], spriteIdle[animationStep/animationRate][3]);
			break;
		}
		default:{ 	// Idle Animation
			if (animationStep >= ((spriteIdle.length) * animationRate))
			{
				animationStep = 0;
			}
			img = sheet.getSubimage(spriteIdle[animationStep/animationRate][0], spriteIdle[animationStep/animationRate][1], spriteIdle[animationStep/animationRate][2], spriteIdle[animationStep/animationRate][3]);
			break;
		}
	}
		animationStep++;
		return img;
	}

	/* (non-Javadoc)
	 * @see Enemy#move()
	 */
	@Override
	public void move() {
		/*
		 * moves to the right then to the left then repeats
		 */
		if (true){//isAlive) {
			if(health <=0 ) {
				if(isAlive)
					Main.spawnObject("teleport", posX, posY, 32, 32, false);
				
				isAlive = false;
				
				destroyMe();
				
				
			}
			else {
			resetAttackBox();
			bossLogic();
			if(dx > moveSpeed)
				dx = moveSpeed;
			if(dx < -moveSpeed)
				dx = -moveSpeed;
			//posY -= dy;
			posX += dx;
			applyGravity();
			decrementInvincibility();
			}
		}
	}
	
	public Rectangle getAttackBox() {
		return null;
	}
	
	private void resetAttackBox() {
		currentAttackBox.x = 0;
		currentAttackBox.y = 0;
		currentAttackBox.height = 0;
		currentAttackBox.width = 0;
	}
	
	
	
}
