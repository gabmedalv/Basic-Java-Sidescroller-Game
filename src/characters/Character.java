//Gabriel Medina Alvarez    

package characters;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sprites.Animation;
import sprites.Sprite;
import weapons.Missile;


public class Character extends Sprite {
		
	private BufferedImage spriteChar; 	
	
	private Animation standAnim;
	private Animation runAnim;
	private Animation jumpAnim;
	private Animation fallAnim;
			
	private boolean parado=true;
	private boolean corre=false;
    private boolean salta=false;
    private boolean jumpCount=false;
    private boolean cae=false;
    private boolean noFloor=false;
    
    private boolean collideWorld=false;
    private boolean collideSprite=false;
	
	private int Scale=3;
	private final double GRAVITY=1;
	private int floor;
	private long distPerSec=150;	
	private long timeJump=500; 
	
	private ArrayList<Missile> missiles;
		
	private boolean vaIzquierda=false;
	
	private boolean screenMoveX=false;
	private boolean screenMoveY=false;
	private int screenVelocityX=0;
	private int screenVelocityY=0;
	
	
	private long amountTimeX=0;
	private long currentDistX=0;
	private int velXPerSec=0;
	
	private boolean saltoAltMax=false;
	   	
	private double initialVelY=GRAVITY*timeJump;
	private long amountTimeJump=0;
	private long amountTimeFall=0;	
	private int currentDistJump=0;
	private int currentDistFall=0;
	
	private BufferedImage inaux;
	
	public Character(){	spriteChar=null;	}
	
	public Character(int x, int y){	
	    defineSpritePosition(x,y);
	    missiles=new ArrayList<Missile>();
	    standAnim=new Animation();
	    runAnim=new Animation();
	    jumpAnim=new Animation();
	    fallAnim=new Animation();
	}
	
	public void loadMegamanImg(){
		
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/megamanGameGM.png"));
		}catch(Exception e){	};	
		
		spriteChar=new BufferedImage(inaux.getWidth(), inaux.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = spriteChar.createGraphics();
		g.drawImage(inaux, 0, 0, null);
		g.dispose();       
				
		setImage(spriteChar);	   	   
	   
		standAnim.addFrame(cutSpriteImg(0,0,21,24), 1000);
		standAnim.addFrame(cutSpriteImg(21,0,21,24), 250);   	  	   	   
	   
		runAnim.addFrame(cutSpriteImg(0,30,16,24),150);
		runAnim.addFrame(cutSpriteImg(16,30,21,22),150);
		runAnim.addFrame(cutSpriteImg(16+21,30,16,24),150);
		runAnim.addFrame(cutSpriteImg(16+21+16,30,24,22),150);	   	   
	   
		jumpAnim.addFrame(cutSpriteImg(16+21+16+24,30,26,30),0);
		fallAnim.addFrame(cutSpriteImg(16+21+16+24,30,26,30),0);
	}
	
	public void loadGammaImg(){
		
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/gammaChar.png"));
		}catch(Exception e){	};	
		
		spriteChar=new BufferedImage(inaux.getWidth(), inaux.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = spriteChar.createGraphics();
		g.drawImage(inaux, 0, 0, null);
		g.dispose();       
				
		setImage(spriteChar);	   	   
	   
		standAnim.addFrame(cutSpriteImg(1,0,21,26), 1000);
		standAnim.addFrame(cutSpriteImg(22,0,21,26), 100); 
		standAnim.addFrame(cutSpriteImg(22+21,0,21,26), 100); 
		standAnim.addFrame(cutSpriteImg(22,0,21,26), 100); 
	   
		runAnim.addFrame(cutSpriteImg(0,27,16,26),150);
		runAnim.addFrame(cutSpriteImg(16,27,21,24),150);
		runAnim.addFrame(cutSpriteImg(16+21,27,16,26),150);
		runAnim.addFrame(cutSpriteImg(16+21+16,27,24,24),150);	   	   
	   
		jumpAnim.addFrame(cutSpriteImg(16+21+16+24+22,27,26,32),0);
		fallAnim.addFrame(cutSpriteImg(16+21+16+24,27,22,32),0);
	}
	
	public void loadMarioImg(){
		
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/mario3.png"));
		}catch(Exception e){	};	
		
		spriteChar=new BufferedImage(inaux.getWidth(), inaux.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = spriteChar.createGraphics();
		g.drawImage(inaux, 0, 0, null);
		g.dispose();       
				
		setImage(spriteChar);	   	   
	   
		standAnim.addFrame(cutSpriteImg(0,0,19,27),80);
		standAnim.addFrame(cutSpriteImg(19,0,19,27),80); 
		standAnim.addFrame(cutSpriteImg(19+19,0,19,26),80);		
	}
	
	public void loadEnemyImg(){
		
		try{
			inaux=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/enemy1.png"));
		}catch(Exception e){	};	
		
		spriteChar=new BufferedImage(inaux.getWidth(), inaux.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = spriteChar.createGraphics();
		g.drawImage(inaux, 0, 0, null);
		g.dispose();       
				
		setImage(spriteChar);	   	   
	   
		standAnim.addFrame(cutSpriteImg(0,0,25,29),1000);
		standAnim.addFrame(cutSpriteImg(25,0,25,29),100); 
		standAnim.addFrame(cutSpriteImg(25+25,0,25,29),100); 
		standAnim.addFrame(cutSpriteImg(25,0,25,29),100);		
	}
	
    public void Update(){		
    	move();		
		UpdateMissiles();
	}
	
	public void JumpAndMoveX(long elapsedTime){
		if(salta)
			moveJump(elapsedTime);
		if(!salta && cae)
			caidaLibre(elapsedTime);
		
		animCharacter(elapsedTime);
		if(!parado)
			velXPerSec=getAmountVel(elapsedTime);
	}
	
	public void UpdateMissiles(){
		
		for (int i=0; i < missiles.size(); i++) {
            Missile m = (Missile) missiles.get(i);
            if (m.isVisible()) 
                m.move(800);
            else missiles.remove(i);
        }
	}
	
	 public void fire() {
		 	if(vaIzquierda)
		 		missiles.add(new Missile(getX()-(Scale*18)/2, getY()-(Scale*18),true));
		 	else
		 		missiles.add(new Missile(getX()+(Scale*18)/2, getY()-(Scale*18),false));
	    }
	
	public int getAmountVel(long elapsedTime){
			
	    amountTimeX+=elapsedTime;			
		long prevDistX=currentDistX;			
		currentDistX=amountTimeX/(1000/distPerSec);		
					
		return (int)(currentDistX-prevDistX);			
	}
	
	public void resetValuesX(){
		amountTimeX=currentDistX=0;		
	}
	
	public void setVelocityXFull(){
			
		if(!isScreenMoveX()){  
			if(vaIzquierda)
				setVelocityX(-getVelXPerSec());	
			else
				setVelocityX(getVelXPerSec());
    		setScreenVelocityX(0);
    	}
    	else{
    		setVelocityX(0);
    		if(vaIzquierda)
    			setScreenVelocityX(getVelXPerSec());
    		else
    			setScreenVelocityX(-getVelXPerSec());
    	}	
	}		
		
	public void moveJump(long elapsedTime){			
		
		if(salta){
			if(!saltoAltMax){
				amountTimeJump+=elapsedTime;
												
				int prevDistY=currentDistJump;
				currentDistJump=(int)((initialVelY*amountTimeJump)-(0.5*GRAVITY*amountTimeJump*amountTimeJump))/(1000);
				
				int currentVelY=-(prevDistY-currentDistJump);
				
				
				if(amountTimeJump>=timeJump){
					currentDistJump=0;
					amountTimeJump=0;
					saltoAltMax=true;			
				}
				
				if(!isScreenMoveY()){
					setVelocityY(-currentVelY);
					setScreenVelocityY(0);
				}
				else{
					setScreenVelocityY(currentVelY);
					setVelocityY(0);
				}
				noFloor=true;
			}
			else{
				amountTimeJump=0;
				currentDistJump=0;
				setVelocityY(0);
				setScreenVelocityY(0);
				cae=true;				
				caidaLibre(elapsedTime);
				
			}			
		}								
	}	
	
	public void caidaLibre(long elapsedTime){
		
		if(cae){	
			amountTimeFall+=elapsedTime;
			int prevDistY=currentDistFall;
						
			currentDistFall=(int) ((0.5*GRAVITY*amountTimeFall*amountTimeFall)/1000);			
			int currentVelY=-(prevDistY-currentDistFall);						
			
			if(noFloor){
				if(!isScreenMoveY()){
					setVelocityY(currentVelY);
					setScreenVelocityY(0);
				}
				else{
					setVelocityY(0);
					setScreenVelocityY(-currentVelY);
				}
			}
			
			else{
					amountTimeFall=0;
					currentDistFall=0;
					setVelocityY(0);
					setScreenVelocityY(0);
										
					if(!isScreenMoveY())
						setY(floor);
																				
					if(isJumping()){
						saltoAltMax=false;
						salta=false;
						jumpCount=true;
					}
					if(getVelocityX()==0 && getScreenVelocityX()==0){
						corre=false;
						parado=true;
					}
					else{
						parado=false;
						if(getVelocityX()<0)
							vaIzquierda=true;
						corre=true;
					}
				cae=false;	
			}
		}		
	}			
	
	public void drawCharacter(Graphics2D g2d){
		
		if(salta){					
			if(vaIzquierda)
				setDrawImage(g2d,horizontalFlip(jumpAnim.getImage())); 			
			else
				setDrawImage(g2d,jumpAnim.getImage());
			return;
		}	
		
		if(cae){					
			if(vaIzquierda)
				setDrawImage(g2d,horizontalFlip(fallAnim.getImage())); 			
			else
				setDrawImage(g2d,fallAnim.getImage());
			return;
		}	
						
		if(corre){		
			if(vaIzquierda)
				setDrawImage(g2d,horizontalFlip(runAnim.getImage()));			
			else
				setDrawImage(g2d,runAnim.getImage()); 	
			return;
		}
				
		if(parado){				
			if(vaIzquierda)				
				setDrawImage(g2d,horizontalFlip(standAnim.getImage())); 			
			else
				setDrawImage(g2d,standAnim.getImage()); 
			return;
		}	    					  	
	}
			
	public void setDrawImage(Graphics2D g2d,BufferedImage img){
		
		g2d.drawImage(img,getX()-(Scale*img.getWidth())/2,getY()-Scale*img.getHeight(),
	                  Scale*img.getWidth(),Scale*img.getHeight(),null);		
		
		drawMissiles(g2d);
	}
	
////FOR ENEMY PRINTING///////////////////////////////////////////////////////////////7	
	
	public void drawCharacter(Graphics2D g2d,int X,int Y){
		
		if(salta|| cae){					
			if(vaIzquierda)
				setDrawImage(g2d,horizontalFlip(jumpAnim.getImage()),X,Y); 			
			else
				setDrawImage(g2d,jumpAnim.getImage(),X,Y);
			return;
		}		
						
		if(corre){		
			if(vaIzquierda)
				setDrawImage(g2d,horizontalFlip(runAnim.getImage()),X,Y);			
			else
				setDrawImage(g2d,runAnim.getImage(),X,Y); 	
			return;
		}
				
		if(parado){				
			if(vaIzquierda)				
				setDrawImage(g2d,horizontalFlip(standAnim.getImage()),X,Y); 			
			else
				setDrawImage(g2d,standAnim.getImage(),X,Y); 
			return;
		}	    					  	
	}
	
	public void setDrawImage(Graphics2D g2d,BufferedImage img,int X,int Y){
		
		g2d.drawImage(img,X-(Scale*img.getWidth())/2,Y-Scale*img.getHeight(),
	                  Scale*img.getWidth(),Scale*img.getHeight(),null);			
		
	}
	
//*******************************************************************+	
		
	public void drawMissiles(Graphics2D g2d){
		int Scale=this.Scale-1;
		for(int i=0; i<missiles.size(); i++){
			Missile m = (Missile) missiles.get(i);
			if(m.isVisible())	
				if(m.isLeft())
					g2d.drawImage(m.getImage(),m.getX()- Scale*m.getWidth(),m.getY(),
							  Scale*m.getWidth(),Scale*m.getHeight(),null);	
				else
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),
						  Scale*m.getWidth(),Scale*m.getHeight(),null);	
		}		
	}	
	
	public void animCharacter(long elapsedTime){
						 		 		    	
		if(parado)
			standAnim.update(elapsedTime);		
		
		if(corre)	    	
	    	runAnim.update(elapsedTime);    	
		
		if(salta)
			jumpAnim.update(elapsedTime);		
	} 	
	
	public void resetAnimation(String anim){
		
		if(anim=="Jump"){ jumpAnim.start(); }
		
		if(anim=="Stand"){ standAnim.start(); }
		
		if(anim=="Run"){ runAnim.start(); }
	}
	
	public int getScale(){return Scale;};
	
	public int getColissWidth(){ return Scale*20; }
	
	public int getColissHeight(){ return Scale*24; }
	
	public int getJumpOffSize(){ return Scale*30; }
	
	public int getVelXPerSec(){ return velXPerSec; }
	
	public int getScreenVelocityX(){return screenVelocityX;}	
	
	public int getScreenVelocityY(){return screenVelocityY;}	
 
	public void setScale(int Scale){ this.Scale=Scale; }
	
	public void setFloor(int state){ floor=state; }
	
	public void setScreenVelocityX(int state){screenVelocityX=state;}	
	
	public void setScreenVelocityY(int state){screenVelocityY=state;}	
	
	public void isNotFloor(boolean state){ noFloor=state; }
    	
	public void isRunning(boolean state){ corre=state; }
    
    public void isStanding(boolean state){ parado=state; } 
    
    public void isJumping(boolean state){ salta=state; } 
    
    public void goLeft(boolean state){ vaIzquierda=state; } 
    
    public void isFalling(boolean state){ cae=state; }
    
    public void reachJump(boolean state){ saltoAltMax=state; }
    
    public void isScreenMoveX(boolean state){ screenMoveX=state; }
    
    public void isScreenMoveY(boolean state){ screenMoveY=state; }
    
    public void isColissionW(boolean state){ collideWorld=state; }

    public void isColissionS(boolean state){ collideSprite=state; }
    
    public boolean isColissionW(){ return collideWorld; }
    
    public boolean isColissionS(){ return collideSprite; }
    
    public boolean isFalling(){ return cae; }
    
    public boolean isNotFloor(){ return noFloor; }
    
    public boolean isRunning(){ return corre; }
    
    public boolean isStanding(){ return parado; } 
    
    public boolean isJumping(){ return salta; } 
    
    public boolean goLeft(){ return vaIzquierda; }   
        	
	public boolean isScreenMoveX(){ return screenMoveX; }
	
	public boolean isScreenMoveY(){ return screenMoveY; }
	
	public boolean jumpCount(){return jumpCount; }
	
	public void jumpCount(boolean state){jumpCount=state; }

}
