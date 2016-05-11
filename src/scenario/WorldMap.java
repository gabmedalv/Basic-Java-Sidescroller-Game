//Gabriel Medina Alvarez

package scenario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import physics.ColissionFrames;

import characters.Enemy;
import characters.MegamanUsr;

public class WorldMap {
	
	private TileMap escenario;	
	private BufferedImage water;
	private BufferedImage ground;
	
	private int screenWidth=800;
	private int screenHeight=600;
	private final static int TILE_SIZE=50;	

	private char[][] colissionMap=null;
	
	private int offsetX=0;
	private int offsetY=0;		
	
	private int firstTileX=0;
	private int lastTileX=0;
	private int firstTileY=0;
	private int lastTileY=0;	
	
	private boolean sideScrollerY=false;
	private boolean sideScrollerX=false;
	private boolean prevStateY=false;

	private boolean cornerRight=false;
	private boolean cornerLeft=false;
	private boolean cornerUp=false;
	private boolean cornerDown=false;
	
	//private Background[] backgrnd;	
	private  ArrayList<Enemy> enemies;
	private  ArrayList<MobileSprite> mobileBlocks;
	
	private ColissionFrames colissions;
	
	public WorldMap(String filename){	
		
		colissions=new ColissionFrames();
		enemies=new ArrayList<Enemy>();
		mobileBlocks=new ArrayList<MobileSprite>();
						
		try{
			ground=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/tileMario.png"));
			water=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/tileWater.png"));
			escenario=loadMap(filename);
			
		}catch(Exception e){};	
		
		//backgrnd=new Background[3];
		//backgrnd=new Background[1];	
		
		//backgrnd[0]=new Background("backgrnd1.png",1.2,1);
		//backgrnd[1]=new Background("backgrnd2.png",2,1);
		//backgrnd[2]=new Background("backgrnd3.png",3,1);		
	}
	
	public int tilesToPixels(int numTiles){
		int pixelSize = numTiles * TILE_SIZE;
		return pixelSize;
	}
	
	public int pixelsToTiles(int pixels){
		int numTile = pixels / TILE_SIZE;
		return numTile;
	}
	
	private TileMap loadMap(String filename) throws IOException {

	    ArrayList<String> lines = new ArrayList<String>();
	    int width = 0;
	    int height = 0;	    

	    // read every line in the text file into the list
	    InputStreamReader str=new InputStreamReader(
	    		this.getClass().getClassLoader().getResourceAsStream(filename));
	    
	    BufferedReader reader = new BufferedReader(str);
	    
	    while (true) {
	        String line = reader.readLine();
	        // no more lines to read
	        if (line == null) {
	            reader.close();
	            break;
	        }

	        // add every line except for comments
	        if (!line.startsWith("#")) {
	            lines.add(line);
	            width = Math.max(width, line.length());
	        }
	    }

	    // parse the lines to create a TileEngine
	    height = lines.size();
	    colissionMap=new char[width][height];
	    TileMap newMap = new TileMap(width, height);
	    
	    for (int y=0; y<height; y++) {
	        String line = (String)lines.get(y);	     
	   
	        for (int x=0; x<line.length(); x++) {
	            char ch = line.charAt(x);
	           	 colissionMap[x][y]=ch; 	
	           
	            // check if the char represents tile A, B, C, etc.
	            
	            if (ch == 'A') { newMap.setTile(x, y,ground); }
	            
	            if (ch == 'B') { newMap.setTile(x, y,water); }
	            
	            if (ch == 'Z') {enemies.add( new Enemy(tilesToPixels(x),tilesToPixels(y),2) );}
	            
	            if (ch == 'Y') {enemies.add( new Enemy(tilesToPixels(x),tilesToPixels(y),1) );}
	            
	            if (ch == 'P') {mobileBlocks.add(new MobileSprite(tilesToPixels(x),tilesToPixels(y)) );}
	            
	        }
	    }	
	    
	    if(screenWidth-tilesToPixels(width)<0)
	    	sideScrollerX=true;
	    else{
	    	sideScrollerX=false;	    	
			offsetX = screenWidth - tilesToPixels(width);
	    }
	    
	    if(screenHeight-tilesToPixels(height)<0)
	    	sideScrollerY=true;
	    else{
	    	sideScrollerY=false;	    	
			offsetY = screenHeight - tilesToPixels(height);
	    }
	    
	    return newMap;
	}
	
	public void setInitialPosition(MegamanUsr megaman){				
		
		for(int i=0;i<escenario.getWidth();i++){
			for(int j=0;j<escenario.getHeight();j++){
				if(colissionMap[i][j]=='M'){
					
					int posX=tilesToPixels(i);
					int posY=tilesToPixels(j);
					
					if(sideScrollerX){
					
						if(posX<screenWidth/2){								
							megaman.setX(posX+megaman.getColissWidth()/2);
							cornerLeft=true;												
						}
						else if(posX>(tilesToPixels(escenario.getWidth())-screenWidth/2)){
								int adjustX=(tilesToPixels(escenario.getWidth())-screenWidth);
								megaman.setX(posX-adjustX+megaman.getColissWidth()/2);
								cornerRight=true;						
							}							
							else{							
								megaman.setX(screenWidth/2);
								offsetX=-(posX-screenWidth/2 + megaman.getColissWidth()/2);					
							}						
					}
					else
						megaman.setX(posX+megaman.getColissWidth()/2);
					
					if(sideScrollerY){
					
						if(posY<screenHeight/2){
							megaman.setY(posY+megaman.getColissHeight());
							cornerUp=true;
						}	
						else if(posY>(tilesToPixels(escenario.getHeight())-screenHeight/2)){
								int adjustY=(tilesToPixels(escenario.getHeight())-screenHeight);
								megaman.setY(posY-adjustY-megaman.getColissHeight());
								cornerDown=true;
							}						
							else{
								megaman.setY(screenHeight/2);
								offsetY=-(posY-screenHeight/2 + megaman.getColissHeight()/2);
							}						
				   }
				   else
					    megaman.setY(posY+megaman.getColissHeight());
				}
			}
		}
		
		updateMapState(megaman);
	}	
	
	public void move(MegamanUsr megaman){
	
		//backgrnd[0].moveX(offsetX,tilesToPixels(escenario.getWidth()),800);
		//backgrnd[1].moveX(offsetX,tilesToPixels(escenario.getWidth()),800);
		//backgrnd[2].moveX(offsetX,tilesToPixels(escenario.getWidth()),800);
		
		offsetX+=megaman.getScreenVelocityX();
		offsetY+=megaman.getScreenVelocityY();	
	}
	
	public void updateMapState(MegamanUsr megaman,long elapsedTime){		
		updateMapState(megaman);
		updateEnemies(elapsedTime);
		updateBlocks(elapsedTime);
	}	
	
	public void updateMapState(MegamanUsr megaman){		
		updateMapStateX(megaman);
		updateMapStateY(megaman);			
	}	
	
	public void updateMapStateX(MegamanUsr megaman){
		
		boolean prevLeft=cornerLeft;
		boolean prevRight=cornerRight;
		
		int mapWidth = tilesToPixels(escenario.getWidth());	
		
		if(sideScrollerX){
		
			if(!megaman.isScreenMoveX()){
				if(cornerLeft){					
					offsetX = screenWidth/2 - megaman.getX();
					offsetX = Math.min(offsetX, 0);					
				}	
				if(cornerRight){					
					offsetX = mapWidth-(screenWidth-megaman.getX())- screenWidth/2;
					offsetX = Math.min(offsetX, mapWidth-screenWidth);
					offsetX = offsetX*(-1);			
				}			
			}		
		}
								
		firstTileX = pixelsToTiles(-offsetX);
		lastTileX = firstTileX +  pixelsToTiles(screenWidth);		
				
		if(lastTileX>=escenario.getWidth()){
			cornerRight=true;
			if(!prevRight){			
				megaman.setX((screenWidth-(mapWidth+offsetX-screenWidth/2)));
				megaman.setVelocityX(-megaman.getScreenVelocityX());	
				megaman.setScreenVelocityX(0);				
			}
			offsetX=-(mapWidth-screenWidth);
		}
		else
			cornerRight=false;		
				
		if(offsetX>=0){
			cornerLeft=true;
			
			if(!prevLeft){
				megaman.setX(screenWidth/2-offsetX);
				megaman.setVelocityX(-megaman.getScreenVelocityX());	
				megaman.setScreenVelocityX(0);				
			}			
			offsetX=0;
		}
		else
			cornerLeft=false;		
		
		if(!cornerLeft && !cornerRight){
			megaman.isScreenMoveX(true);
			megaman.setX(screenWidth/2);
		}
		else
			megaman.isScreenMoveX(false);
		
	}		
		
	public void updateMapStateY(MegamanUsr megaman){		
		
		boolean prevUp=cornerUp;
		boolean prevDown=cornerDown;
				
		int mapHeight= tilesToPixels(escenario.getHeight());
		prevStateY=megaman.isScreenMoveY();
				
		if(sideScrollerY){
		 
			if(!megaman.isScreenMoveY()){
				if(cornerUp){
					offsetY=screenHeight/2 - megaman.getY();
					offsetY=Math.min(offsetY,0);
				}
				if(cornerDown){					
					offsetY=mapHeight-(screenHeight-megaman.getY())-screenHeight/2;					
					offsetY=Math.min(offsetY, mapHeight-screenHeight);
					offsetY=offsetY*(-1);
				}
			}
		}		
				 
		firstTileY = pixelsToTiles(-offsetY);
		lastTileY = firstTileY + pixelsToTiles(screenHeight);
		 
		if(lastTileY>=escenario.getHeight()){
			cornerDown=true;
			
			if(!prevDown){			
				megaman.setY((screenHeight-(mapHeight+offsetY-screenHeight/2)));
				megaman.setVelocityY(-megaman.getScreenVelocityY());	
				megaman.setScreenVelocityY(0);				
			}
			offsetY=-(mapHeight-screenHeight);
		}
		else{
			cornerDown=false;
			if(prevDown){		
				megaman.setScreenVelocityY(-megaman.getVelocityY());
				megaman.setVelocityY(0);
			}
		}		
		
		if(offsetY>=0){		
			cornerUp=true;				
			
			if(!prevUp){
				megaman.setY(screenHeight/2-offsetY);
				megaman.setVelocityY(-megaman.getScreenVelocityY());	
				megaman.setScreenVelocityY(0);
				
			}
			offsetY=0;
	    }
		else{
			cornerUp=false;	
			if(prevUp){				
				megaman.setScreenVelocityY(-megaman.getVelocityY());
				megaman.setVelocityY(0);
			}
		}
		
		if(!cornerUp && !cornerDown){			
			megaman.isScreenMoveY(true);			
			megaman.setY(screenHeight/2);
		}
		else
		    megaman.isScreenMoveY(false);
			
	}
	
	public void collideFrames(MegamanUsr megaman){
		for(int i=0;i<mobileBlocks.size();i++){
			MobileSprite m = (MobileSprite) mobileBlocks.get(i);
			if(m.isVisible())
				colissions.plataformColission(m, megaman,this);
		}	
	}
	
	public void updateEnemies(long elapsedTime){
		for(int i=0;i<enemies.size();i++){
			Enemy m = (Enemy) enemies.get(i);
			if(m.isVisible())
				m.animCharacter(elapsedTime);
		}		
	}
	
	public void updateBlocks(long elapsedTime){
		for(int i=0;i<mobileBlocks.size();i++){
			MobileSprite m = (MobileSprite) mobileBlocks.get(i);
			m.TrayX(elapsedTime,2000);			
		}		
	}
	
	public void drawMap(Graphics2D g2d,MegamanUsr megaman){		
		
		//backgrnd[0].draw(g2d);
		//backgrnd[1].draw(g2d);			
		
		for (int y=firstTileY; y<=lastTileY; y++) {
		    for (int x=firstTileX; x <= lastTileX; x++) {
		    	BufferedImage image = escenario.getTile(x, y);
		        if (image != null) {
		            g2d.drawImage(image,tilesToPixels(x) + offsetX,tilesToPixels(y) + offsetY,
		            		TILE_SIZE,TILE_SIZE,null);				            	
		        }
		    }
		}	
		
		drawEnemies(g2d,megaman.getX(),megaman.getY());	
		drawBlocks(g2d);	
		megaman.drawCharacter(g2d);	
		
		//backgrnd[2].draw(g2d);
	}
	
	public void drawEnemies(Graphics2D g2d,int xx,int yy){
		for(int i=0;i<enemies.size();i++){
			Enemy m = (Enemy) enemies.get(i);			
			m.drawEnemy(g2d,offsetX,offsetY,xx,yy );
		}
	}
	
	public void drawBlocks(Graphics2D g2d){
		for(int i=0;i<mobileBlocks.size();i++){
			MobileSprite m = (MobileSprite) mobileBlocks.get(i);
			m.drawPlatform(g2d,offsetX,offsetY);			
		}		
	}
	
	public char getColissionTile(int x,int y){ return colissionMap[x][y]; }
	
	public int getTileSize(){ return TILE_SIZE;}	
	
	public int getOffsetX(){return offsetX;}
	
	public void setOffsetX(int value){offsetX=value;}
	
	public int getOffsetY(){return offsetY;}
	
	public void setOffsetY(int value){offsetY=value;}
	
	public int getWidth(){return escenario.getWidth();}
	
	public int getHeight(){return escenario.getHeight();}
	
	public BufferedImage getTile(int x,int y){ return escenario.getTile(x,y);}	
	
	public boolean getPrevStateY(){return prevStateY;}
	
	public void isCornerUp(boolean state){ cornerUp=state; }
	
	public void isCornerDown(boolean state){ cornerDown=state; }
	
	public boolean isSideScrollingY(){ return sideScrollerY; }

}

