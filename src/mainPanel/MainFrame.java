//Gabriel Medina Alvarez    

package mainPanel;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
		
    public MainFrame() {

        add(new GamePanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 625);
        setLocationRelativeTo(null);
        setTitle("SideScrollerTest GMA");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
   
}

