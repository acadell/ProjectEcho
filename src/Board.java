import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//CLEANED

public class Board
{
	
	private String levelSelect;
	public Box boxCrateList[] = new Box[20];
	public Enemy enemeyBasicList[] = new Enemy[30];
	public Props itemsList[] = new Props[10];
	private int count = 1;
	
	public Board(String selectLevel)
	{
		levelSelect = selectLevel;
		loadLevel(levelSelect);
	}
	////////////spawn enemy
	public void spawnEnemy(String name, int x, int y, int width, int height, boolean goRight)
	{
		if(name.equals("slime"))
		{
			enemeyBasicList[enemeyBasicList.length-count] = new Slime(x,y,width,height,goRight);
			count= (count%10)+1;
		}
		if(name.equals("teleport"))
		{
			itemsList[itemsList.length-count] = new PropsTeleport(x,y,width,height,name, 25);
			count= (count%10)+1;
		}
			
	}
	//////////////load level
	public void loadLevel(String levelName)
	{
		for (int i = 0; i < enemeyBasicList.length; i++)
		{
			enemeyBasicList[i] = new Slime();
		}
		for (int i = 0; i < boxCrateList.length; i++)
		{
			boxCrateList[i] = new Stone(0,0,0,0);
		}
		for (int i = 0; i < itemsList.length; i++)
		{
			itemsList[i] = new PropsHealthBox(0,0,0,0, "small", 0);
		}
		
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("levels/" + levelName + ".txt"));
			int count = 0;
		    String line = input.readLine();
		    int select = 0;
		    
		    
		    while (line != null)
		    {
		    	if (line.equals("crate"))
		    	{
		    		select = 1;
		    		line = input.readLine();
		    	}
		    	if (line.equals("slime"))
		    	{
		    		select = 3;
		    		if(select != 4) 
		    			count = 0;
		    		line = input.readLine();
		    	}
		    	if (line.equals("slimec"))
		    	{
		    		if(select != 3)
		    			count = 0;
		    		select = 4;
		    		
		    		line = input.readLine();
		    	}
		    	if (line.equals("skeleton"))
		    	{
		    		select = 9;
		    		line = input.readLine();
		    	}
		    	if (line.equals("boss"))
		    	{
		    		select = 2;
		    		line = input.readLine();
		    	}
		    	if (line.equals("health"))
		    	{
		    		select = 5;
		    		count = 0;
		    		line = input.readLine();
		    	}
		    	if (line.equals("breakable"))
		    	{
		    		select =6;
		    		//count = 0;
		    		line = input.readLine();
		    	}
		    	if (line.equals("attackPlus"))
		    	{
		    		select = 7;
		    		//count = 0;
		    		line = input.readLine();
		    	}
		    	if (line.equals("endGame"))
		    	{
		    		select = 8;
		    		count = 0;
		    		line = input.readLine();
		    	}
		    	String[] entry = line.split("\\s+");
		    	if (select == 1)
		    	{
			    	boxCrateList[count] = new Stone(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		    		count++;
		    	}
		    	if (select == 2)
		    	{
			    	enemeyBasicList[count] = new DragonBoss(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		    		count++;
		    	}
		    	if (select == 3)
		    	{
			    	enemeyBasicList[count] = new Slime(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]) );
			    	count++;
		    	}
		    	if (select == 4)
		    	{
			    	enemeyBasicList[count] = new Slime(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]) );
			    	count++;
		    	}
		    	if (select == 5)
		    	{
		    		itemsList[count] = new PropsHealthBox(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]), entry[4], Integer.parseInt(entry[5]));
		    		count++;
		    	}
		    	if (select == 6)
		    	{
		    		boxCrateList[count] = new BoxDestroy(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		    		count++;
		    	}
		    	if (select == 7)
		    	{
		    		itemsList[count] = new PropsIncreaseDamage(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]), entry[4], Integer.parseInt(entry[5]));
		    		count++;
		    	}
		    	if (select == 8)
		    	{
		    		itemsList[count] = new PropsTeleport(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]), entry[4], Integer.parseInt(entry[5]));
		    		count++;
		    	}
		    	if (select == 9)
		    	{
			    	enemeyBasicList[count] = new Skeleton(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]), Integer.parseInt(entry[2]), Integer.parseInt(entry[3]));
		    		count++;
		    	}
		    	line = input.readLine();
		    }
		    input.close();
		}
		catch(IOException e) 
        {
            e.printStackTrace();
			
		}
	}
	
	public void setLevel(String LevelPath)
	{
		levelSelect = LevelPath;
	}
	
	public void MoveAllEnemies() {
		for ( int i = 0; i < enemeyBasicList.length; i++) {
			enemeyBasicList[i].move();
		}
	}
}
