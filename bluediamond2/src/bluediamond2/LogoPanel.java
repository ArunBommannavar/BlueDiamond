package bluediamond2;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.net.URL;

public class LogoPanel extends JPanel {

	private static final long serialVersionUID = -3762474256519181220L;
//	private BufferedImage image;   
	/**
	 * Create the panel.
	 */
	public LogoPanel() {
		setLayout(new BorderLayout(0, 0));
		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	   private void jbInit() throws Exception {
		      try
		    {
		     String pth = File.separator;
		     String resource = "blue-diamond.png";
		     String myImagePath=".";
	
		     BufferedImage image = ImageIO.read(new File(myImagePath+pth+resource));
		     ImageIcon icon = new ImageIcon(image);
		     JLabel label = new JLabel(icon);  

		     this.add(label);

		    }
		    catch (Exception e) { /*handled in paintComponent()*/ }
		  }
/*
	  protected void paintComponent(Graphics g)
	  {
	    super.paintComponent(g);
	    
	    if (image != null)
	      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);

	  }
*/
}
