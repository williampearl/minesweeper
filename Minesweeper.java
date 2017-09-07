import java.util.*;
import java.awt.*;
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
	
	/**
	 * Works the game clock and input
	 */
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
	
	/**
	 * Sets up a new game
	 * @param sizeX - the amount of columns
	 * @param sizeY - the amount of rows
	 */
	public void startGame(int sizeX, int sizeY)
	{
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
	
	/**
	 * Receives input from stdin and sends it to parser
	 */
	public void getInput()
	{
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter command (\"h\") for help :: ");
		String line = in.nextLine();
		parseString(line);
		//in.close();
	}
	
	/**
	 * Parses and runs commands
	 * @param l
	 */
	public void parseString(String l){
		String[] s = l.split(" ");
		if(s.length == 1){
			switch(l)
			{
			/*help screen*/
			case "h": printHelp(); break;
			/*quit game*/
			case "q": endGame(false); break;
			/*print the map*/
			case "p": showMap(); break;
			/*prints the whole map*/
			case "sp": System.out.println(printMap(true)); break;
			/*toggles debug mode*/
			case "d": debug = !debug; break;
			}
		} else {
			//these require additional input so they are separated
			switch(s[0])
			{
			/*place flag*/
			case "f": parseFlag(l); if(!runGame)break; showMap(); break;
			/*click spot*/
			case "c": parseClick(l); if(!runGame)break; showMap(); break;
			}
		}
		
	}
	public void parseClick(String line)
	{
		String[] l = line.split(" ");
		//if they didnt input 2 integers then it fails
		try {
			click(Integer.parseInt(l[1]), Integer.parseInt(l[2]));
		} catch (NumberFormatException e) {
			System.err.println("You didn't input 2 numbers.");
		}
	}
	public void parseFlag(String line)
	{
		
		String[] l = line.split(" ");
		//if they didn't input 2 integers then it fails
		try {
			flag(Integer.parseInt(l[1]), Integer.parseInt(l[2]));
		} catch (NumberFormatException e) {
			System.err.println("You didn't input 2 numbers.");
		}
		
		
	}
	
	/**
	 * increments amount of bombs surrounding the tile
	 * @param r the row
	 * @param c the column
	 */
	private void incrementTileAmount(int r, int c)
	{
		//checks if location is out of bounds
		if((r < 0 || r >= map.length) || (c < 0 || c >= map[r].length))
			return;
		map[r][c].incrementAmount();
	}
	/**
	 * increments the area around a bomb
	 * @param r the row
	 * @param c the column
	 */
	private void incrementArea(int r, int c)
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
	
	/**
	 * area of the board
	 * X * Y
	 * @return area
	 */
	public int getArea() {
		return map.length * map[0].length;
	}
	
	/**
	 * places a bomb in a random spot
	 */
	private void setBomb()
	{
		//pick a random row
		int r = ((int)(Math.random()*getArea()))%map.length;
		//pick a random column
		int c = ((int)(Math.random()*getArea()))%map[0].length;
		//check if picked spot already has a bomb
		if(map[r][c].isBomb()) {
			setBomb();
			return;
		}
		map[r][c].setBomb();
		
		incrementArea(r,c);
		
		
	}
	
	/**
	 * places bombs
	 * add "int numOfBombs"
	 */
	public void setBombs()
	{
		//change the 25 to numOfBombs
		for(int i=0; i<25; i++)
			setBomb();
	}
	
	/**
	 * flag given spot
	 * @param r the row
	 * @param c the column
	 */
	public void flag(int r, int c)
	{
		map[r][c].setFlagged();
		if(checkWin())
			endGame(true);
	}
	
	/**
	 * clicks spot
	 * @param r the row
	 * @param c the column
	 */
	public void click(int r, int c)
	{
		//diagnostic printing
		if(debug)
		{
			//shows the spot clicked and stats about it
			System.out.printf("Click @ %d : %d\n",r,c);
			System.out.printf("%d, %d is " + (map[r][c].isBomb()?"":"not")+" a bomb.\n", r, c);
		}
		//check if clicked a bomb
		if(map[r][c].isBomb())
		{
			//clicked bomb, lost game
			endGame(false);
			return;
		}
		//check if spot is already discovered
		if(map[r][c].isDiscovered())
		{
			//give error
			System.err.println("Clicked on spot already discovered.");
			return;
		}
		
		autoClick(r,c);
			
	}
	/**
	 * discovers blank tiles
	 * @param r
	 * @param c
	 */
	private void autoClick(int r, int c)
	{
		//checks if out of bounds
		if(((r < 0 || r >= map.length) || (c < 0 || c >= map[r].length)) || map[r][c].isBomb() || map[r][c].isDiscovered())
			return;
		//diagnostic stuff
		if(debug)
			System.out.printf("Autoclick @ %d : %d\n", r, c);
		
		map[r][c].discoverTile();
		
		//if amount != 0 then the spot is adjacent to a bomb, so dont continue
		if(map[r][c].getAmount() != 0)
			return;
		autoClick(r-1, c);
		autoClick(r+1, c);
		autoClick(r, c-1);
		autoClick(r, c+1);
	}
	
	/**
	 * checks if all the bombs have been flagged
	 * @return
	 */
	public boolean checkWin()
	{
		boolean d = true;
		for(Tile[] t1 : map)
			for(Tile t2 : t1)
				if(t2.isBomb() && !t2.isFlagged())
					d = false;
		return d;
	}
	/**
	 * ends the game
	 * @param win true = won, false = lost
	 */
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
			showMap();
			
			System.out.println("\n\nThe whole map.");
			System.out.println(printMap(true));
		}
		runGame = false;
	}
	
	/**
	 * prints map to stdout
	 */
	public void showMap() {
		System.out.println(printMap(false));
	}
	
	/**
	 * gives a nice formatted view of the map
	 * @param showAll
	 * @return formatted map
	 */
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
	
	/**
	 * gives map for use with gui
	 * @return
	 */
	public Tile[][] getMap() {
		return map;
	}
	
	/**
	 * prints the help screen
	 */
	public void printHelp()
	{
		try {
			//access help screen file
			Scanner f = new Scanner(new File("HelpScreen.txt"));
			//print the file
			while(f.hasNextLine())
				System.out.println(f.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("Your help screen file is missing.");
		}
		
		
	}
	
}
