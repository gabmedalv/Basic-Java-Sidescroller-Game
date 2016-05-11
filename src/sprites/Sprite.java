//Gabriel Medina Alvarez 
//This class controls the image, print and cut in sub images, and control
//his position and velocity for the animation and move

package sprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Sprite {
	
	  private BufferedImage imagen;	 //Image containing all the frames for animation
	  
	  private int x;                // position of images (pixels) in the screen
	  private int y;
	  private int dx;               // velocity of images (pixels per millisecond) in the screen
	  private int dy;	  
	  	  	  
	  public Sprite(){ imagen=null;}
	  
	  public Sprite(BufferedImage imagen) {this.imagen = imagen;}
	  
	  /**
	  Put the sprite in the (x,y) screen position 
	  */	 	  
	  public void defineSpritePosition(int x,int y) {		    		  	
		    this.x = x;
	        this.y = y;	     
	  } 	  	  	  	  	  
	  
	  /**
	  Move the sprite acording to the velocity in x and y respectively. 
	  */
	  public void move() {
	       x += dx;
	       y += dy;	   
	  }	   
		 	  
	  /**
	  Cut one frame from the main image and return in Buffered image's object.
	  */	 	
	  public BufferedImage cutSpriteImg(int frameX,int frameY,int imgWidth,int imgHeight){
		  
		 BufferedImage img=imagen.getSubimage(frameX, frameY, imgWidth, imgHeight);		  
		 return img;
	  }
	  
	  /**
	  This useful static method turns out an image around the x axis, very useful for
	  character turn out ( right or left )
	  */		  
	  public static BufferedImage horizontalFlip(BufferedImage img) {  
	        int w = img.getWidth();  
	        int h = img.getHeight();  
	        //BufferedImage dimg = new BufferedImage(w, h, 2); 
	        BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	        
	        Graphics2D g = dimg.createGraphics();  
	        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);  
	        g.dispose();  
	        return dimg;  
	    }  	 
	  
	  /**
	  Return and get important private variables: position, velocity and mainframes image
	  */
	  
	  public abstract int getColissWidth();
	  
	  public abstract int getColissHeight();
		
	  public int getX() { return x; }

	  public int getY() { return y; }

	  public void setX(int x) { this.x = x; }

	  public void setY(int y) { this.y = y; }
	  
	  public int getVelocityX() { return dx;  }
	  
	  public int getVelocityY() { return dy;  }

	  public void setVelocityX(int dx) { this.dx = dx; }

	  public void setVelocityY(int dy) { this.dy = dy; }

	  public BufferedImage getImage() { return imagen; }
	  
	  public void setImage(BufferedImage imagen) { this.imagen=imagen; }	 
	  
	  public int getImgWidth() { return imagen.getWidth(); }
	  
	  public int getImgHeight() { return imagen.getHeight(); }

}
