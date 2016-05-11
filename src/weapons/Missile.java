//Gabriel Medina Alvarez

package weapons;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import sprites.Sprite;

public class Missile {
	
    private int x, y;
    private BufferedImage img;
    private boolean visible=true;
    private int width, height;
    private boolean direction; //TRUE=Left 
    
    private final int MISSILE_SPEED = 32;
    
    public Missile(int x,int y,boolean dir){
    	
    	try{
         	  img=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/missile.png"));
        }catch(Exception e){ };
        
        direction=dir;
        if(direction)
        	img=Sprite.horizontalFlip(img);
        	
         height=img.getHeight();
         width=img.getWidth();
         this.x=x;
         this.y=y;        
    }
    
    public void move(int sizeScr) {
    	
    	if(direction)
    		x-=MISSILE_SPEED;
    	else
    		x+=MISSILE_SPEED;
    		
    		if(x > sizeScr||x<0)
    			visible=false;    	
    }
    
    public int getX() { return x; }

    public int getY() { return y; }
    
    public boolean isLeft(){ return direction; }
    
    public BufferedImage getImage(){ return img; }

    public boolean isVisible() { return visible; }

    public void setVisible(Boolean visible) { this.visible = visible; }
    
    public int getWidth(){ return width;}
    
    public int getHeight(){ return height;}  
    
}
