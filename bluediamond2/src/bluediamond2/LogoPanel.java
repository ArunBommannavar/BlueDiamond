package bluediamond2;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class LogoPanel extends JPanel {
//	   Image image;
	private BufferedImage image;   
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
		     String imageFile = myImagePath+pth+resource;
	
		     image = ImageIO.read(new File(myImagePath+pth+resource));

		    }
		    catch (Exception e) { /*handled in paintComponent()*/ }
		  }

	  protected void paintComponent(Graphics g)
	  {
	    super.paintComponent(g);
	    
	    if (image != null)
	      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);

	  }

}
