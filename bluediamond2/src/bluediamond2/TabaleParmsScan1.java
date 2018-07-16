package bluediamond2;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TabaleParmsScan1 extends JPanel {
	private JTextField table_xPositioner1;
	private JTextField table_xPositioner2;

	/**
	 * Create the panel.
	 */
	public TabaleParmsScan1() {
		setLayout(new MigLayout("", "[grow][]", "[][]"));
		
		table_xPositioner1 = new JTextField();
		table_xPositioner1.setEditable(false);
		add(table_xPositioner1, "cell 0 0,growx");
		table_xPositioner1.setColumns(10);
		
		JButton btnXPositioner1Button = new JButton("Read");
		add(btnXPositioner1Button, "cell 1 0");
		
		table_xPositioner2 = new JTextField();
		add(table_xPositioner2, "cell 0 1,growx");
		table_xPositioner2.setColumns(10);
		
		JButton btnXPositioner2Button = new JButton("Read");
		add(btnXPositioner2Button, "cell 1 1");

	}

}
