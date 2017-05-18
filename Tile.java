public class Tile {
	private int amount;
	private boolean bomb;
	private boolean disc;
	private boolean flag;
	
	public Tile()
	{
		amount = 0;
		bomb = false;
		disc = false;
		flag = false;
	}
	/**
	 * 
	 * @param a - amount of bombs adjacent
	 * @param b - if this tile is a bomb
	 * @param d - if this tile has been discovered
	 */
	public Tile(int a, boolean b, boolean d)
	{
		amount = a;
		bomb = b;
		disc = d;
	}
	
	/**
	 * Use if the tile should be discovered
	 */
	public void discoverTile()
	{
		disc = true;
	}
	
	/**
	 * set amount of bombs adjacent
	 * @param am
	 */
	public void setAmount(int am)
	{
		amount = am;
	}
	public boolean isDiscovered()
	{
		return disc;
	}
	
	public void setBomb()
	{
		bomb = true;
	}
	public boolean isBomb()
	{
		return bomb;
	}
	
	public int getAmount()
	{
		return amount;
	}
	public void incrementAmount()
	{
		setAmount(amount + 1);
	}
	
	public boolean isFlagged()
	{
		return flag;
	}
	public void setFlagged()
	{
		flag = !flag;
	}
	
	
	
	
	
}