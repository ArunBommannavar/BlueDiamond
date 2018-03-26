package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class DetectorColorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBox detCheckbox;
	JButton colorButton;

	/**
	 * Create the panel.
	 */
	public DetectorColorPanel() {
		setForeground(Color.RED);
		setBackground(Color.LIGHT_GRAY);
		setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));

		detCheckbox = new JCheckBox("New check box");
		detCheckbox.setSelected(true);
		detCheckbox.setBackground(Color.LIGHT_GRAY);
		detCheckbox.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(detCheckbox, BorderLayout.CENTER);

		colorButton = new JButton("Color");
		colorButton.setEnabled(false);
		colorButton.setBackground(Color.LIGHT_GRAY);
		colorButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		add(colorButton, BorderLayout.EAST);

	}


	public JCheckBox getDetPanelCheckBox() {
		return detCheckbox;
	}

	public JButton getDetPanelButton() {
		return colorButton;
	}

	public void addJDetCheckBoxName(String str){
		detCheckbox.setName(str);
	}
	
	public void addDetColorButtonName(String str){
		colorButton.setName(str);
	}
	
	public void setValid(boolean b) {
		detCheckbox.setEnabled(b);
		detCheckbox.setVisible(b);
		colorButton.setEnabled(b);
		colorButton.setVisible(b);
		
	}
	
	public void setEnabled(boolean b){
		detCheckbox.setEnabled(b);
		colorButton.setEnabled(b);
	}
	
	public void setVisible(boolean b){
		detCheckbox.setVisible(b);
		colorButton.setVisible(b);

	}
}
