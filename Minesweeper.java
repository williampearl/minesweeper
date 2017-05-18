import java.util.*;
import java.awt.Graphics;
import java.io.*;
public class Minesweeper {

	public static void main(String[] args)
	{
		System.out.println("Welcome to Minesweeper by Will Pearl");
		System.out.println("Made May, 2017");
		System.out.println("\n\n\n");
		System.out.println("Please enter the size of the map ( must be square ) :: ");
		Scanner in = new Scanner(System.in);
		int x = 0;
		int y = 0;
		boolean done = true;
		while(done){
			String l = in.nextLine();
			String[] li = l.split(" ");
			try{
				x = Integer.parseInt(li[0]);
				y = Integer.parseInt(li[1]);
				if(x != y){
					System.out.println("Please enter 2 equal integers :: ");
					continue;
				}
				done = false;
			} catch(NumberFormatException e){
				System.out.println("Please enter 2 valid integers. :: ");
			}
		}
		
		Minesweeper m = new Minesweeper(x,y);
	}
	
	
	private Tile[][] map;
	private boolean runGame;
	private boolean debug;
	private int tileSize;
	
	
	public Minesweeper(int sizeX, int sizeY)
	{
		startGame(sizeX, sizeY);
		
	}
	
	public void runIt()
    {
    	while(runGame)
    	{
    		getInput();
    		try {
				Thread.sleep((long) 100);
			} catch (InterruptedException e) {
	
				e.printStackTrace();
			}
    	}
    }
	
	public void startGame(int sizeX, int sizeY)
	{
//		mapD = new boolean[sizeY][sizeX];
//		map = new boolean[sizeY][sizeX];
//		am = new int[sizeY][sizeX];
		map = new Tile[sizeX][sizeY];
		runGame = true;
		debug = false;
		for(int r=0; r<sizeY; r++)
			for(int c=0; c<sizeX; c++)
				map[r][c] = new Tile();
		tileSize = (450)/(sizeX);
		//System.out.println(tileSize);
		setBombs();
		runIt();
	}
	public void getInput()
	{
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter command (\"h\") for help :: ");
		String line = in.nextLine();
		parseString(line);
		//in.close();
	}

	public void parseString(String l){
		String[] s = l.split(" ");
		if(s.length == 1){
			switch(l)
			{
			case "h": printHelp(); break;
			case "q": endGame(false); break;
			case "p": System.out.println(printMap(false)); break;
			case "sp": System.out.println(printMap(true)); break;
			case "d": debug = !debug; break;
			}
		} else {
			switch(s[0])
			{
			case "f": parseFlag(l); if(!runGame)break; System.out.println(printMap(false)); break;
			case "c": parseClick(l); if(!runGame)break; System.out.println(printMap(false)); break;
			}
		}
		
	}
	public void parseClick(String line)
	{
		String[] l = line.split(" ");
		click(Integer.parseInt(l[1]), Integer.parseInt(l[2]));
	}
	public void parseFlag(String line)
	{
		String[] l = line.split(" ");
		//long ct = System.currentTimeMillis();
		flag(Integer.parseInt(l[1]), Integer.parseInt(l[2]));
		
	}
	
	
	public void incrementTileAmount(int r, int c)
	{
		if((r < 0 || r >= map.length) || (c < 0 || c >= map[r].length))
			return;
		map[r][c].incrementAmount();
	}
	public void incrementArea(int r, int c)
	{
		incrementTileAmount(r-1, c-1);
		incrementTileAmount(r-1, c);
		incrementTileAmount(r-1, c+1);
		incrementTileAmount(r, c-1);
		incrementTileAmount(r, c+1);
		incrementTileAmount(r+1, c-1);
		incrementTileAmount(r+1, c);
		incrementTileAmount(r+1, c+1);
	}
	
	
	public void setBomb(int r, int c)
	{
		if((r < 0 || r >= map.length) || (c < 0 || c >= map[r].length) || map[r][c].isBomb())
			return;
		map[r][c].setBomb();
		incrementArea(r,c);
		
		
	}
	public void setBombs()
	{
		for(int r = 0; r<map.length; r++){
			for(int c=0; c<map.length; c++){
				if((int)(Math.random() * 10) == 0){
					//System.out.println("bomb @ " + r +" " + c);
					setBomb(r,c);
				}
			}
		}
	}
	
	
	public void flag(int r, int c)
	{
		map[r][c].setFlagged();
		if(checkWin())
			endGame(true);
	}
	
	
	public void click(int r, int c)
	{
		if(debug)
		{
			System.out.printf("Click @ %d : %d\n",r,c);
			System.out.printf("%d, %d is " + (map[r][c].isBomb()?"":"not")+" a bomb.\n", r, c);
		}
		if(map[r][c].isBomb())
		{
			endGame(false);
			return;
		}
		if(map[r][c].isDiscovered())
		{
			System.err.println("Clicked on spot already discovered.");
			return;
		}
		autoClick(r,c);
			
	}
	public void autoClick(int r, int c)
	{
		if(((r < 0 || r >= map.length) || (c < 0 || c >= map[r].length)) || map[r][c].isBomb() || map[r][c].isDiscovered())
			return;
		if(debug)
			System.out.printf("Autoclick @ %d : %d\n", r, c);
		map[r][c].discoverTile();
		if(map[r][c].getAmount() != 0)
			return;
		autoClick(r-1, c);
		autoClick(r+1, c);
		autoClick(r, c-1);
		autoClick(r, c+1);
	}
	
	
	public boolean checkWin()
	{
		boolean d = true;
		for(Tile[] t1 : map)
			for(Tile t2 : t1)
				if(t2.isBomb() && !t2.isFlagged())
					d = false;
		return d;
	}
	public void endGame(boolean win)
	{
		if(win)
		{
			System.out.println("\n\nYou have flagged all the bombs!");
			System.out.println("You win!");
			
		} else {
			System.out.println("You clicked on a bomb!");
			System.out.println("Game over!");
			
			System.out.println("What you discovered.");
			System.out.println(printMap(false));
			
			System.out.println("\n\nThe whole map.");
			System.out.println(printMap(true));
		}
		runGame = false;
	}
	
	
	public String printMap(boolean showAll)
	{
		String o = "  ";
		for(int c=0; c<map[0].length; c++)
			o += c + " ";
		o+="\n";
		for(int r=0; r<map.length; r++)
		{
			o += r + " ";
			for(int c=0; c<map[r].length; c++)
			{
				if(map[r][c].isFlagged())
				{
					o += "^ ";
					continue;
				}
				if(!showAll && !map[r][c].isDiscovered())
				{
					o += "# ";
					continue;
				}
				if(map[r][c].isBomb())
					o += "X";
				else
					o += map[r][c].getAmount() != 0 ? map[r][c].getAmount() : ".";
					
				o+= " ";
			}

			
			
			o += "\n";
		}
		return o;
	}
	public void printHelp()
	{
		try {
			Scanner f = new Scanner(new File("HelpScreen.txt"));
			while(f.hasNextLine())
				System.out.println(f.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Your help screen file is missing.");
			return;
		}
		
		
	}
	
}
