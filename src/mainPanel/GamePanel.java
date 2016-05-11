//Gabriel Medina Alvarez    

package mainPanel;
	
	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
    import java.awt.Image;
	import java.awt.Toolkit;
	
	import java.awt.event.KeyAdapter;
	import java.awt.event.KeyEvent;
	
import javax.swing.JPanel;	
		
	
	@SuppressWarnings("serial")
	public class GamePanel extends JPanel implements Runnable {

	    private WorldGame escenario;
		  		  	    
	    private Thread animator;
	    private static int DEFAULT_FPS = 50;      
	    private final long PERIOD= (long) 1000.0/DEFAULT_FPS;
		    
	    private Graphics dbg;
	    private Image dbImage = null;
		    
	    private static final int PWIDTH = 800;   // size of panel
	    private static final int PHEIGHT = 600;


		     
	    public GamePanel() {
		    	
	    	addKeyListener(new TAdapter());
	        setFocusable(true);
	        setBackground(Color.WHITE);
	        setDoubleBuffered(true);		        
		                
	        escenario=new WorldGame();
		      
	    }

	    public void addNotify(){
	    	super.addNotify();
	    	animator=new Thread(this);
	    	animator.start();
	    }
		    
	    public void gameUpdate(long elapsedTime){		    	
	    	escenario.updateWorld(elapsedTime);		    		    	
	    }    
	
	
	    private void gameRender( ) {
	    // draw the current frame to an image buffer
	        if (dbImage == null){  // create the buffer
		        dbImage = createImage(PWIDTH, PHEIGHT);
		        if (dbImage == null) {
		            System.out.println("dbImage is null");
		            return;
		        }
		        else
		          dbg = dbImage.getGraphics( );
		    }
				      // clear the background
		    dbg.setColor(Color.lightGray);
		    dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
				      // draw game elements
		      // ...			      			      
		    Graphics2D g2d = (Graphics2D)dbg;   
		    escenario.printWorld(g2d);  
		    dbg.setColor(Color.black);
		    dbg.drawString("GABMEDALV GameTest v.1.01", 20, 30);
		    
		    }
	    
	    private void paintScreen( ) {
	    // actively render the buffer image to the screen		    
	      Graphics g;
	      try {
	        g = this.getGraphics( );  // get the panel's graphic context
	        if ((g != null) && (dbImage != null))
	          g.drawImage(dbImage, 0, 0, null);
	        Toolkit.getDefaultToolkit( ).sync( );  // sync the display on some systems
	        g.dispose( );
	      }
	      catch (Exception e)
	      { System.out.println("Graphics context error: " + e);  }
	    } // end of paintScreen( )
		    
		    
	    public void run(){
		    	
	    	 long beforeTime, timeDiff, sleepTime,currTime;
	         beforeTime = System.currentTimeMillis();
		         
	         currTime = beforeTime;

	         while (true) {		        	 
		        	
	        	 long elapsedTime =System.currentTimeMillis() - currTime;
	             currTime += elapsedTime;
		            
	             gameUpdate(elapsedTime);
	             gameRender();
	             paintScreen();
 
	             /*
	             timeDiff = System.currentTimeMillis() - beforeTime;
	             sleepTime = PERIOD - timeDiff;
		            		           		             
	             if (sleepTime < 0)
	                 sleepTime = 2;
	             try {
	                 Thread.sleep(sleepTime);
	             } catch (InterruptedException e) {System.out.println("interrupted");}

	             beforeTime = System.currentTimeMillis();*/
	         }    	
		    	
	    }
		    
	private class TAdapter extends KeyAdapter {

	   	public void keyReleased(KeyEvent e) {
	         escenario.keyReleased(e);       
	    }

	    public void keyPressed(KeyEvent e) {
	         escenario.keyPressed(e);         
	         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {System.exit(0);}	         
	    }
    }
}


