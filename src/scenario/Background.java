//Gabriel Medina Alvarez

package scenario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage backgroundImg;
	private int x,y,height,width;
	
	private BufferedImage inaux;
	
	public Background(String imgBack,double sclX,double sclY){
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/"+imgBack));			
		
		}catch(Exception e){};	
		
		backgroundImg=new BufferedImage(inaux.getWidth(), inaux.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = backgroundImg.createGraphics();
		g.drawImage(inaux, 0, 0, null);
		g.dispose();
		
		x=y=0;
		height=(int)(sclY*backgroundImg.getHeight(null));
		width=(int)(sclX*backgroundImg.getWidth(null));
	}
	
	public void moveX(int offsetX,int mapWidth,int screenWidth){
		
		if(offsetX>=0){ x=0; return;}
		
		if(offsetX<=(screenWidth-mapWidth)){ x=screenWidth-width; return;}
		
		x=offsetX*(screenWidth-width)/(screenWidth-mapWidth);
		
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(backgroundImg,x,y,width,height,null);
		
	}

}
