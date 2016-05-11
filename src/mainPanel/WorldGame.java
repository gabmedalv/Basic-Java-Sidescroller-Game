//Gabriel Medina Alvarez    

package mainPanel;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import physics.PhysicWorld;
import scenario.WorldMap;
import characters.MegamanUsr;

public class WorldGame {
	
	private MegamanUsr megaman;
	private WorldMap mundoI;	
	private PhysicWorld physics;
		
	public WorldGame(){
		
		megaman=new MegamanUsr(0,0);		
		mundoI=new WorldMap("resources/maps/map2.txt");
		physics=new PhysicWorld();
		mundoI.setInitialPosition(megaman);
	}
	
	public void updateWorld(long elapsedTime){
				
		megaman.Update();
		mundoI.move(megaman);
		megaman.JumpAndMoveX(elapsedTime);
		mundoI.updateMapState(megaman,elapsedTime);
		mundoI.collideFrames(megaman);
		physics.WorldColissions(megaman, mundoI);	
		
	}
	
	public void printWorld(Graphics2D g2d){		
		mundoI.drawMap(g2d, megaman);		
	}
	
	public void keyPressed(KeyEvent e) {megaman.keyPressed(e);}

	public void keyReleased(KeyEvent e) {megaman.keyReleased(e);}

}
