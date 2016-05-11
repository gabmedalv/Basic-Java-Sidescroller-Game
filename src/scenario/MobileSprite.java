package scenario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import sprites.Sprite;

public class MobileSprite extends Sprite {
	
	private int ScaleX=6;
	private int ScaleY=3;
	private boolean visible=false;
	private int width,height;
	
	private long amountTimeX=0;
	private long currentDistX=0;
	private int velPerSecX=0;
	private long distPerSecX=100;	
	
	private BufferedImage inaux;
	boolean goLeft=false;
		
	public MobileSprite(int x,int y){
		
		setX(x);
		setY(y);
		
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/tilePlat.png"));
		}catch(Exception e){	};	
		
		width=inaux.getWidth();
		height=inaux.getHeight();
		
		setImage(inaux);		
	}
	
	public int getAmountVelX(long elapsedTime){
		
	    amountTimeX+=elapsedTime;			
		long prevDistX=currentDistX;			
		currentDistX=amountTimeX/(1000/distPerSecX);
					
		return (int)(currentDistX-prevDistX);			
	}
	
	public void resetValuesX(){
		amountTimeX=currentDistX=0;		
	}
	
	public void TrayX(long elapsedTime,long distX){
		
		velPerSecX=getAmountVelX(elapsedTime);
		if(amountTimeX>=distX){  goLeft=!goLeft; resetValuesX();}
		
		if(goLeft)
			setVelocityX(-velPerSecX);
		else
			setVelocityX(velPerSecX);
		move();
	}	
	
	public void drawPlatform(Graphics2D g2d,int offsetX,int offsetY){
		
		if(Math.abs(getX()+offsetX)<=820 && Math.abs(getY()-height+offsetY)<=600){	
			visible=true;				
			drawSprite(g2d,getX()+offsetX,getY()+offsetY);
		}
		else visible=false;		
	}
	
	public void drawSprite(Graphics2D g2d,int X,int Y){
		
		g2d.drawImage(getImage(),X-(ScaleX*width)/2,Y-ScaleY*height,
	                  ScaleX*width,ScaleY*height,null);		
	}
	
	public boolean isVisible(){return visible;}
	
	public int getColissWidth(){ return ScaleX*height; }
	
	public int getColissHeight(){ return ScaleY*width; } 

}
