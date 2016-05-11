//Gabriel Medina Alvarez

package scenario;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Iterator;

import sprites.Sprite;

public class TileMap {
	
	private BufferedImage [][] tiles;
	private LinkedList<Sprite> sprites;	
	
	public TileMap(int width, int height) {
	    tiles = new BufferedImage[width][height];
	    sprites = new LinkedList<Sprite>();
	}
	 
	public BufferedImage getTile(int x, int y) {
	    if (x < 0 || x >= getWidth() ||
	        y < 0 || y >= getHeight())
	    {
	        return null;
	    }
	    else {
	        return tiles[x][y];
	    }
	}
	
	public void setTile(int x, int y,BufferedImage tile) { tiles[x][y] = tile; }
	
	public void addSprite(Sprite sprite) {sprites.add(sprite);}
	 
	public void removeSprite(Sprite sprite) {sprites.remove(sprite);}
	
	public Iterator<Sprite> getSprites() {return sprites.iterator(); }
	 
	public int getWidth() {return tiles.length;}
	  
	public int getHeight() {return tiles[0].length;}
	
}
