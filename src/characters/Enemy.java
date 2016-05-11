package characters;

import java.awt.Graphics2D;

public class Enemy extends Character{	
	
	private boolean visible=false;	
	
	public Enemy(int x,int y,int img){
		super(x,y);
		
		if(img==1)
			loadMegamanImg();
		else
			loadMarioImg();			
		
		setX((int)(getX()+0.5*getColissWidth()));
		setY(getY()+getColissHeight()-22);		
	}	
	
	public void drawEnemy(Graphics2D g2d,int offsetX,int offsetY,int xx,int yy){
		
		if(Math.abs(getX()+offsetX)<=820 && Math.abs(getY()-getColissHeight()+offsetY)<=600){	
			visible=true;
			
			if(xx-offsetX<=getX())
				goLeft(true);
			else
				goLeft(false);
				
			drawCharacter(g2d,getX()+offsetX,getY()+offsetY);
		}
		else visible=false;		
	}
	
	public boolean isVisible(){return visible;}

}
