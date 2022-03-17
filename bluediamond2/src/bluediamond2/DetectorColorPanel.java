package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class DetectorColorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JCheckBox detCheckbox;
	JButton colorButton;

	/**
	 * Create the panel.
	 */
	public DetectorColorPanel() {
		setForeground(new Color(255, 255, 224));
		setBackground(new Color(250, 250, 210));
		setLayout(new BorderLayout(0, 0));

		detCheckbox = new JCheckBox("New check box");
		detCheckbox.setSelected(true);
		detCheckbox.setBackground(new Color(255, 255, 255));
		detCheckbox.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(detCheckbox, BorderLayout.CENTER);

		colorButton = new JButton("Color");
		colorButton.setBackground(new Color(250, 250, 210));
		colorButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(colorButton, BorderLayout.EAST);

	}

	public JCheckBox getDetPanelCheckBox() {
		return detCheckbox;
	}

	public JButton getDetPanelButton() {
		return colorButton;
	}

	public void addJDetCheckBoxName(String str) {
		detCheckbox.setName(str);
	}

	public void addDetColorButtonName(String str) {
		colorButton.setName(str);
	}

	public void setValid(boolean b) {
		detCheckbox.setEnabled(b);
		detCheckbox.setVisible(b);
		colorButton.setEnabled(b);
		colorButton.setVisible(b);
	}

	public void setEnabled(boolean b) {
		detCheckbox.setEnabled(b);
		colorButton.setEnabled(b);
	}

	public void setVisible(boolean b) {
		detCheckbox.setVisible(b);
		colorButton.setVisible(b);

	}
}
