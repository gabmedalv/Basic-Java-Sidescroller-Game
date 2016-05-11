package physics;

import scenario.WorldMap;
import sprites.Sprite;
import characters.Character;
import java.awt.Rectangle;

public class ColissionFrames {	
	
	public boolean intersect(Sprite sprite,Character megaman,int dX,int dY,int OffsetX,int OffsetY,boolean isX){
		
		int Xini=megaman.getX()- (megaman.getColissWidth()/2) - OffsetX;
		int Yini=megaman.getY()- megaman.getColissHeight() - OffsetY;
				
		int fromX = Math.min(Xini, Xini + dX);
		int fromY = Math.min(Yini, Yini + dY);
		
		int toX = Math.abs(dX);
		int toY = Math.abs(dY);
				
		int fromXSpr,fromYSpr;
		
		int XiniS=sprite.getX()- (sprite.getColissWidth()/2);
		int YiniS=sprite.getY()- sprite.getColissHeight();
		
		if(isX){
			fromXSpr = XiniS;			
			fromYSpr = YiniS;			
		}
		else{
			fromXSpr = XiniS;			
			fromYSpr = YiniS;			
		}
		
		Rectangle charRect=new Rectangle(fromX,fromY,megaman.getColissWidth()+toX
				                                    ,megaman.getColissHeight()+toY);
		Rectangle spriteRect=new Rectangle(fromXSpr,fromYSpr,6*sprite.getImage().getWidth()
                                                   ,3*sprite.getImage().getHeight());
		
		if(charRect.intersects(spriteRect)) {megaman.isColissionS(true); return true;}
		
		megaman.isColissionS(false);
		return false;
		
	}
	
	public void plataformColission(Sprite sprite,Character megaman,WorldMap escenario){		
		
		boolean isCollideY=false;
		
		if(!megaman.isScreenMoveY())		
			isCollideY=intersect(sprite,megaman,0,megaman.getVelocityY(),escenario.getOffsetX(),
					             escenario.getOffsetY(),false);
		else
			isCollideY=intersect(sprite,megaman,0,-megaman.getScreenVelocityY(),escenario.getOffsetX(),
		                         escenario.getOffsetY(),false);
		
		if(isCollideY){
			
			int checkFeet=megaman.getY()-escenario.getOffsetY();
			
			if(checkFeet<=(sprite.getY()-sprite.getColissHeight())+1){
			
				if(megaman.isFalling()){	
					
					megaman.setVelocityY(0);
					megaman.setScreenVelocityY(0);
					megaman.isNotFloor(false);
					if(!megaman.isScreenMoveY())
						megaman.setFloor(sprite.getY()-sprite.getColissHeight()+1);
					//else
						//escenario.setOffsetY(sprite.getY()-megaman.getY()+1);				
				}
				
				if(megaman.isStanding()){					
					if(!megaman.isScreenMoveX())						
							megaman.setX(megaman.getX()+sprite.getVelocityX());
					else
						escenario.setOffsetX(escenario.getOffsetX()-sprite.getVelocityX());
				}
					
				/*if(megaman.isRunning()){
					
					if(!megaman.isScreenMoveX())
						if((megaman.getVelocityX()>0&&sprite.getVelocityX()<0)||
						   (megaman.getVelocityX()<0&&sprite.getVelocityX()>0))
						megaman.setX(megaman.getX()-sprite.getVelocityX());
					else
						//if((megaman.getScreenVelocityX()<0&&sprite.getVelocityX()<0)||
						//   (megaman.getScreenVelocityX()>0&&sprite.getVelocityX()>0))
						//	escenario.setOffsetX(escenario.getOffsetX()+sprite.getVelocityX());
						//else
							//escenario.setOffsetX(escenario.getOffsetX()+sprite.getVelocityX());
							megaman.setScreenVelocityX(megaman.getScreenVelocityX()+sprite.getVelocityX());
				}*/
			}
			
			else{
				megaman.isColissionS(false);
			}
		} 
	}	

}
