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
	/**
	 * has the tile been discovered
	 * @return
	 */
	public boolean isDiscovered()
	{
		return disc;
	}
	/**
	 * makes the tile a bomb
	 */
	public void setBomb()
	{
		bomb = true;
	}
	/**
	 * is the tile a bomb
	 * @return
	 */
	public boolean isBomb()
	{
		return bomb;
	}
	/**
	 * return amount of bombs nearby
	 * @return
	 */
	public int getAmount()
	{
		return amount;
	}
	/**
	 * adds 1 to amount of nearby bombs
	 */
	public void incrementAmount()
	{
		setAmount(amount + 1);
	}
	/**
	 * is the tile flagged
	 * @return
	 */
	public boolean isFlagged()
	{
		return flag;
	}
	/**
	 * toggles flagged state
	 */
	public void setFlagged()
	{
		flag = !flag;
	}
	
	
	
	
	
}