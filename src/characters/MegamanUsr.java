//Gabriel Medina Alvarez

package characters;

import java.awt.event.KeyEvent;

public class MegamanUsr extends Character {
	
	private boolean leftKy;
	private boolean rightKy;
	private boolean upKy;
	private boolean downKy;		
	
	private boolean prevUp=false;
		
	public MegamanUsr(int x,int y){
		super(x,y);
		//loadGammaImg();
		loadMegamanImg();
	}		
	
	public void Update(){
		controlKeys();		
		super.Update();
	}	
 
	public void controlKeys(){	
		
		if(leftKy&&rightKy){ setVelocityXFull(); }
		
		if(leftKy&&!rightKy){			
			
			goLeft(true);
			isStanding(false);
			
			if(!isRunning())
				resetAnimation("Run");
	        	        	
	        if(!isJumping())
	        	isRunning(true);
	        	        		        	
	        setVelocityXFull();	        	
		}
		
		if(rightKy&&!leftKy){			
			
			goLeft(false);
			isStanding(false);
			
			if(!isRunning())
				resetAnimation("Run");        		
	       	
			if(!isJumping()) 
				isRunning(true);
					        	
	       	setVelocityXFull();	        	
		}
		
		if(!leftKy&&!rightKy){			
			
			isRunning(false);
			
			if(!isStanding()){
				resetAnimation("Stand");
				resetValuesX();
				setVelocityX(0); 			
				setScreenVelocityX(0);
			}
        	if(!isJumping())        	
        		isStanding(true);			
		}
		
		if(upKy){		
			if(!prevUp){ jumpCount(false); prevUp=true; }
			
			if(!isJumping()&&!isFalling()&&!jumpCount()){	        	
	        						        	
	        	reachJump(false);		        	
	        	isRunning(false);
	        	isStanding(false);
	        	isJumping(true);	        	
        	}
			
		}else{			
			jumpCount(false);
    		prevUp=false;
        	if(isJumping()){ reachJump(true); }
		}
		
		if(downKy){ }
		
	}		
	
	public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();	         

	        if (key == KeyEvent.VK_LEFT) {leftKy=true;}

	        if (key == KeyEvent.VK_RIGHT) {rightKy=true;}

	        if (key == KeyEvent.VK_UP) {upKy=true;}

	        if (key == KeyEvent.VK_DOWN) {downKy=true;}
	        
	       // if( key == KeyEvent.VK_S){fire();}
	}

	public void keyReleased(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {leftKy=false;}	          

	        if (key == KeyEvent.VK_RIGHT) {rightKy=false;}

	        if (key == KeyEvent.VK_UP) {upKy=false;}

	        if (key == KeyEvent.VK_DOWN) {downKy=false;}
	}	    
	    
}
