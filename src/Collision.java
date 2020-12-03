
//CLEANED
public class Collision 
{
	private Board currentBoard;
	private Character player;
	private boolean canRight = true;
	private boolean canLeft = true;
	private boolean grounded = true;
	private boolean hitCeiling = false;
	
	
	//Set up for later if i want to use these points on the player for detecting collision
	private int top;
	private int right;
	private int bottom;
	private int left;
	
	//CONSTRUCTORS
	public Collision(Board localBoard, Character localPlayer  )
	{
		currentBoard = localBoard;
		player = localPlayer;
	}
	
	//ACTUAL COLLISION DETECTION
	public void detectCollisions()
	{
		
		playerCollision();
		EnemyCollision();
		
		//attack collisions
		if(player.getAttackBox() != null)
		{
			//checks the player attack against all the enemies and deal damage to the enemy
			for( int i  = 0; i < currentBoard.enemeyBasicList.length; i++)
			{
				if(player.getAttackBox().intersects(currentBoard.enemeyBasicList[i].getHitBox()))
				{
					currentBoard.enemeyBasicList[i].recieveDamage(player.getAttackDamage(), player.isRight());
				}
			}
			
			//collision of attacks to special blocks so that when hit, the special method of the block is called.
			for( int i = 0; i < currentBoard.boxCrateList.length; i++)
			{
				if(player.getAttackBox().intersects(currentBoard.boxCrateList[i].getBounds() ) )
				{
					currentBoard.boxCrateList[i].special(player.getAttackDamage());
				}
			}
			
		}
		//attack collision end
		
		
		
		//apply prop effects such as health and upgrades
		for( int i = 0; i < currentBoard.itemsList.length; i++)
		{
			if(currentBoard.itemsList[i].getPropCollisionBox().intersects(player.getHitbox() ))
			{
				currentBoard.itemsList[i].applyPropEffect();
			}
		}
		
	}
	
	//////////////////////////////////////////////PLAYER COLLISION METHOD////////////////////////////////////
	private void playerCollision()
	{
		right = player.getX() + player.getWidth();  //finds the right side of the player
		bottom = player.getY()+player.getHeight();	//gets the bottom side of the player
		top = player.getY();						//gets the top side of the player
		left = player.getX();						//gets the left side of the player
		
		
		//checks every rectangle from box crate list in the board and sees if it is about to hit any of them
		//The .25 multiplier is there to add a buffer so that the right side touching a wall wont tell the player that they are on the ground
		//and when the ground is touching it wont register the player as touching a wall
		for( int i  = 0; i < currentBoard.boxCrateList.length; i++)
		{
			//checks the bottom of the player to see if it is on the ground, if so, move the player to the top of the rectangle
			if(currentBoard.boxCrateList[i].contains(left+player.getWidth()*.25 , bottom) || currentBoard.boxCrateList[i].contains(right-player.getWidth()*.25 ,bottom))
			{
				grounded = true;
				player.setY((int)currentBoard.boxCrateList[i].getY()-player.getHeight());
			}
			else //check the right side of the player and see if it can move right, if not, then set the player to the left side of the box
			if(currentBoard.boxCrateList[i].contains(right,bottom-player.getHeight()*.25) || currentBoard.boxCrateList[i].contains( right,top + player.getHeight()*.25 )) //checks bottom and top right corners of the player
			{
				canRight = false;
				player.setX( (int)currentBoard.boxCrateList[i].getX() - player.getWidth() );
			}
			else//checks the left side of the player and see if it can move, if not, then set the player to the right side of the box
			if(currentBoard.boxCrateList[i].contains(left , bottom - player.getHeight()*.25) || currentBoard.boxCrateList[i].contains(left-1 , top + player.getHeight()*.25))
			{
				canLeft = false;
				player.setX( (int)currentBoard.boxCrateList[i].getX() + (int)currentBoard.boxCrateList[i].getWidth() );
			}
			else// checks the top of the player, if it's hitting something, set the position below the ceiling and say it's hitting the ceiling
			if(currentBoard.boxCrateList[i].contains(left+player.getWidth()*.25 , top ) || currentBoard.boxCrateList[i].contains(right-player.getWidth()*.25 ,top))
			{
				hitCeiling = true;
				player.setY((int)currentBoard.boxCrateList[i].getY()+(int)currentBoard.boxCrateList[i].getHeight());
			}
		}
		
		//sets all of the variables required for the character class to make the correct choices such as being able to move right
		//or being able to jump
		player.setGrounded(grounded);
		player.setTouchingCeiling(hitCeiling);
		player.setCanRight(canRight);
		player.setCanLeft(canLeft);
		canRight = true;
		canLeft = true;
		grounded = false;
		hitCeiling = false;
		
	}
	
	////////////////////////ENEMY COLLISION METHOD/////////////////////////////////////////////////////////
	//almost exactly the same as the player collision
	private void EnemyCollision()
	{
		// Enemy Box collisions
		for( int i = 0; i < currentBoard.enemeyBasicList.length; i++) 
		{
			Enemy enemy = currentBoard.enemeyBasicList[i];
			
			//gets the top, bottom, left and right sides of the enemy
			bottom = enemy.getPosY()+enemy.getHeight();
			right = enemy.getPosX() + enemy.getWidth(); 
			top = enemy.getPosY();
			left = enemy.getPosX();
			
			for(int j = 0; j < currentBoard.boxCrateList.length; j++)
			{
				
				if(currentBoard.boxCrateList[j].contains(left+enemy.getWidth()*.25 , bottom) || currentBoard.boxCrateList[j].contains(right-enemy.getWidth()*.25 ,bottom))
				{
					grounded = true;
					enemy.setPosY((int)currentBoard.boxCrateList[j].getY()-enemy.getHeight());
				}
				else //check the right side of the enemy and see if it can move right, if not, then set the enemy to the left side of the box
				if(currentBoard.boxCrateList[j].contains(right,bottom-enemy.getHeight()*.25) || currentBoard.boxCrateList[j].contains( right,top + enemy.getHeight()*.25 )) //checks bottom and top right corners of the enemy
				{
					canRight = false;
					enemy.setPosX( (int)currentBoard.boxCrateList[j].getX() - enemy.getWidth() );
				}
				else//checks the left side of the enemy and see if it can move, if not, then set the enemy to the right side of the box
				if(currentBoard.boxCrateList[j].contains(left , bottom - enemy.getHeight()*.25) || currentBoard.boxCrateList[j].contains(left-1 , top + enemy.getHeight()*.25))
				{
					canLeft = false;
					enemy.setPosX( (int)currentBoard.boxCrateList[j].getX() + (int)currentBoard.boxCrateList[j].getWidth() );
				}
				else// checks the top of the enemy, if it's hitting something, set the position below the ceiling and say it's hitting the ceiling
				if(currentBoard.boxCrateList[j].contains(left+enemy.getWidth()*.25 , top ) || currentBoard.boxCrateList[j].contains(right-enemy.getWidth()*.25 ,top))
				{
					hitCeiling = true;
					enemy.setPosY((int)currentBoard.boxCrateList[j].getY()+(int)currentBoard.boxCrateList[j].getHeight());
				}
				
			}
			
			
			enemy.setGrounded(grounded);
			enemy.setCanRight(canRight);
			enemy.setCanLeft(canLeft);
			enemy.setTouchingCeiling(hitCeiling);
			canRight = true;
			canLeft = true;
			grounded = false;
			hitCeiling = false;
			
			
			//enemy attacks
			if(enemy.getHitBox().intersects(player.getHitbox()) )
			{
				player.recieveDamage(enemy.getAttackDamage());
			}
			if(enemy.getAttackBox() != null && enemy.getAttackBox().contains(player.getHitbox()) )
			{
				player.recieveDamage(enemy.getAttackDamage());
			}
			
		}
	}
	//MUTATORS
		public void setCurrentBoard(Board nextBoard)
		{
			currentBoard = nextBoard;	
		}
	
}
