package pororo.com.game.maze;

public class Tile {
	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;
	
	public int itemIndex = GameData.ITEM_NONE;
	
	public boolean isItemMake = true;
	
	public boolean isItemHide = false;
}
