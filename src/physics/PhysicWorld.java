//Gabriel Medina Alvarez & Heplful of Book: Developing games in Java

package physics;

import java.awt.Point;
import scenario.WorldMap;
import characters.MegamanUsr;

public class PhysicWorld {	
	
//***********************Escenario-Character Colissions**************************************	

	public Point getTileCollision(MegamanUsr megaman,WorldMap escenario,int dX,int dY){
			
		Point pointCache=new Point();
				
		int Xini=megaman.getX()- (megaman.getColissWidth()/2) - escenario.getOffsetX();
		int Yini=megaman.getY()- megaman.getColissHeight() - escenario.getOffsetY();
			
		int fromX = Math.min(Xini, Xini + dX);
		int fromY = Math.min(Yini, Yini + dY);
		int toX = Math.max(Xini, Xini + dX);
		int toY = Math.max(Yini, Yini + dY);
				
		int fromTileX = escenario.pixelsToTiles(fromX);
		int fromTileY = escenario.pixelsToTiles(fromY);
		int toTileX = escenario.pixelsToTiles(toX + megaman.getColissWidth()-1);
		int toTileY;
		
		if(dY==0 && dX!=0)              //rest 1 for not interfere the x & y colissions
			toTileY = escenario.pixelsToTiles(toY + megaman.getColissHeight()-2);	
		else				            //because the char. always collide with the floor.  	
			toTileY = escenario.pixelsToTiles(toY + megaman.getColissHeight()-1);			
			
		for (int x=fromTileX; x<=toTileX; x++) {
	        for (int y=fromTileY; y<=toTileY; y++) {		           
		    
	        	if (escenario.getTile(x, y) != null){	            
	                //collision found, return the tile	        		
	       			pointCache.setLocation(x, y);
	       			if(escenario.getColissionTile(x, y)=='A'){
	       				megaman.isColissionW(true);
	       			    return pointCache;		      			
	       			}
	            }		               
	        }
	    }
		megaman.isColissionW(false);
	    return null;		
	}

	public void WorldColissions(MegamanUsr megaman,WorldMap escenario){
		WorldColissionX(megaman,escenario);
		WorldColissionY(megaman,escenario);
		checkAbyss(megaman,escenario);
	}

	public void WorldColissionX(MegamanUsr megaman, WorldMap escenario){
		
		int dx=0;
		Point tile=new Point();		
		
		if(!megaman.isScreenMoveX()){
			
			dx = megaman.getVelocityX();
			tile =getTileCollision(megaman,escenario, dx, 0);
			
			 if(tile!=null){			
				 if(escenario.getColissionTile(tile.x, tile.y) == 'A'){
					 if (dx>0) 
						megaman.setX(escenario.tilesToPixels(tile.x)- 1 -(megaman.getColissWidth()/2)+escenario.getOffsetX());				     
				     if (dx<0) 
				      	megaman.setX(escenario.tilesToPixels(tile.x) +escenario.getTileSize()  + (megaman.getColissWidth()/2)+escenario.getOffsetX());
				     megaman.setVelocityX(0);
				     return;
				 }
			}
		}
		else{
			dx=megaman.getScreenVelocityX();			
			tile =getTileCollision(megaman,escenario, -dx, 0);				
						
			if(tile!=null){					
				
				if(escenario.getColissionTile(tile.x,tile.y) == 'A'){					
					if(dx<0)						
						escenario.setOffsetX(-(escenario.tilesToPixels(tile.x)-megaman.getX()-(megaman.getColissWidth()/2)-1));										
					if(dx>0)
						escenario.setOffsetX(-(escenario.tilesToPixels(tile.x) +escenario.getTileSize() -megaman.getX()+(megaman.getColissWidth()/2)));			
					megaman.setScreenVelocityX(0);
					return;
				}				
			}
			
		}
		
	}	

	public void WorldColissionY(MegamanUsr megaman, WorldMap escenario){
		
		int dy=0;
		Point tile=new Point();
				
		if(!megaman.isScreenMoveY()){
		
			dy = megaman.getVelocityY();
			tile =getTileCollision(megaman,escenario, 0, dy);
			
			if(tile!=null){				
				
				 if(escenario.getColissionTile(tile.x, tile.y) == 'A'){		
					if(dy>0){ 
						megaman.isNotFloor(false); 
						megaman.setFloor(escenario.tilesToPixels(tile.y)+escenario.getOffsetY()+1);	
						megaman.setVelocityY(0);
					}
					if(dy<=0){	
					// Roof coordinates	
						int roof=escenario.tilesToPixels(tile.y)+escenario.getTileSize()+1+escenario.getOffsetY();
					//Check if the roof is really a roof and not floor.
						if(roof<=megaman.getY()){  							
							megaman.reachJump(true);                                           
							megaman.setY(roof+1+ megaman.getColissHeight());
							megaman.setVelocityY(0);
						}
					}
					return;
				 }
			}
			else if(!megaman.isJumping()&& !megaman.isColissionS()){    //if not collide, remember that character ALWAYS collide with floor
					megaman.isNotFloor(true);    //if only run without jumping and lost the floor
					megaman.isFalling(true);     //we fall 
				}			
		}
		
		else{
			
			dy=megaman.getScreenVelocityY();			
			tile =getTileCollision(megaman,escenario, 0, -dy);
			
			if(tile!=null){				
				
				if(escenario.getColissionTile(tile.x,tile.y) == 'A'){	
										
					if(dy<=0){
						megaman.isNotFloor(false); 
						escenario.setOffsetY(-(escenario.tilesToPixels(tile.y)-megaman.getY()+1));						
						megaman.setScreenVelocityY(0);						
					}										
					if(dy>0){					
					// Roof coordinates											
						int roof=escenario.tilesToPixels(tile.y)+escenario.getTileSize()+1+escenario.getOffsetY();						
						//Check if the roof is really a roof and not floor.						
						if(roof<=megaman.getY()){ 
							megaman.reachJump(true);                                           
							escenario.setOffsetY(-(escenario.tilesToPixels(tile.y)+escenario.getTileSize()+1
									             -megaman.getY()+megaman.getColissHeight() ));							
							megaman.setScreenVelocityY(0);
						}						
					}				
					
					return;
				}
			}
			
			else if(!megaman.isJumping()&&!megaman.isColissionS()){
					megaman.isNotFloor(true);    //if only run without jumping and lost the floor
					megaman.isFalling(true);     //we fall 					
				}
			
		}
		
	}

//********************************************************************************************	
	
	public void checkAbyss(MegamanUsr megaman, WorldMap escenario){
		if((megaman.getY()-(megaman.getJumpOffSize()))>
		   ((escenario.getTileSize()*escenario.getHeight())+escenario.getOffsetY())){
			
			megaman.setY(0);
			if(escenario.isSideScrollingY()){
				escenario.setOffsetY(0);
				megaman.isScreenMoveY(false);
				escenario.isCornerDown(false);
				escenario.isCornerUp(true);
			}
			
		}		
	}
	
}
