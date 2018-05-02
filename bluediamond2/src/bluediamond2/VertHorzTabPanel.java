package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.border.TitledBorder;

public class VertHorzTabPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public VertHorzTabPanel() {
		setBackground(new Color(128, 128, 0));
		setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel vertHorzPanel = new JPanel();
		vertHorzPanel.setBackground(new Color(128, 128, 0));
		GridBagConstraints gbc_vertHorzPanel = new GridBagConstraints();
		gbc_vertHorzPanel.gridheight = 2;
		gbc_vertHorzPanel.insets = new Insets(0, 0, 5, 0);
		gbc_vertHorzPanel.fill = GridBagConstraints.BOTH;
		gbc_vertHorzPanel.gridx = 0;
		gbc_vertHorzPanel.gridy = 0;
		add(vertHorzPanel, gbc_vertHorzPanel);
		vertHorzPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(128, 128, 0));
		tabbedPane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertHorzPanel.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel vertPanel = new JPanel();
		vertPanel.setBackground(new Color(128, 128, 0));
		tabbedPane.addTab("Vertical", null, vertPanel, null);
		vertPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel vertLeftPanel = new JPanel();
		vertLeftPanel.setBackground(new Color(128, 128, 0));
		vertLeftPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertPanel.add(vertLeftPanel);
		vertLeftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel vertLeftLabelPanel = new JPanel();
		vertLeftLabelPanel.setBorder(new TitledBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertLeftLabelPanel.setBackground(new Color(153, 153, 102));
		vertLeftPanel.add(vertLeftLabelPanel, BorderLayout.WEST);
		vertLeftLabelPanel.setLayout(new BorderLayout(1, 1));
		
		JLabel vertLeftLabel = new JLabel("LEFT");
		vertLeftLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		vertLeftLabel.setForeground(new Color(0, 0, 0));
		vertLeftLabel.setBackground(Color.CYAN);
		vertLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		vertLeftLabelPanel.add(vertLeftLabel, BorderLayout.CENTER);
		vertLeftLabel.setUI(new VerticalLabelUI(false));
		
		JPanel vertLeftValueMovePanel = new JPanel();
		vertLeftPanel.add(vertLeftValueMovePanel, BorderLayout.CENTER);
		vertLeftValueMovePanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel vertLeftValueLabelPanel = new JPanel();
		vertLeftValueLabelPanel.setBackground(new Color(153, 153, 102));
		vertLeftValueLabelPanel.setBorder(new TitledBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 204, 204), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertLeftValueMovePanel.add(vertLeftValueLabelPanel);
		vertLeftValueLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBackground(new Color(153, 153, 102));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		vertLeftValueLabelPanel.add(lblNewLabel_3, BorderLayout.CENTER);
		
		JPanel vertLeftMovePanel = new JPanel();
		vertLeftMovePanel.setBackground(new Color(153, 153, 102));
		vertLeftMovePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertLeftValueMovePanel.add(vertLeftMovePanel);
		
		JButton leftMoveButton = new JButton("Move");
		leftMoveButton.setFont(new Font("Cambria", leftMoveButton.getFont().getStyle() | Font.BOLD | Font.ITALIC, leftMoveButton.getFont().getSize() + 1));
		leftMoveButton.setBackground(new Color(128, 128, 0));
		vertLeftMovePanel.add(leftMoveButton);
		
		JPanel vertRightPanel = new JPanel();
		vertRightPanel.setBackground(new Color(128, 128, 0));
		vertRightPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertPanel.add(vertRightPanel);
		vertRightPanel.setLayout(new BorderLayout(2, 2));
		
		JPanel vertRightLabelPanel = new JPanel();
		vertRightLabelPanel.setForeground(new Color(128, 128, 0));
		vertRightLabelPanel.setBackground(new Color(153, 153, 102));
		vertRightLabelPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertRightPanel.add(vertRightLabelPanel, BorderLayout.WEST);
		vertRightLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_6 = new JLabel("RIGHT");
		lblNewLabel_6.setForeground(new Color(0, 0, 0));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		vertRightLabelPanel.add(lblNewLabel_6, BorderLayout.CENTER);
		lblNewLabel_6.setUI(new VerticalLabelUI(false));
		
		JPanel vertRightValueMovePanel = new JPanel();
		vertRightValueMovePanel.setBackground(new Color(153, 153, 102));
		vertRightPanel.add(vertRightValueMovePanel, BorderLayout.CENTER);
		vertRightValueMovePanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel vertRightValueLabelPanel = new JPanel();
		vertRightValueLabelPanel.setBackground(new Color(153, 153, 102));
		vertRightValueLabelPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(128, 128, 0), null, new Color(211, 211, 211), null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertRightValueMovePanel.add(vertRightValueLabelPanel);
		vertRightValueLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBackground(new Color(153, 153, 102));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		vertRightValueLabelPanel.add(lblNewLabel_4, BorderLayout.CENTER);
		
		JPanel vertRightMovePanel = new JPanel();
		vertRightMovePanel.setBackground(new Color(153, 153, 102));
		vertRightMovePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(107, 142, 35), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertRightValueMovePanel.add(vertRightMovePanel);
		
		JButton moveRightButton = new JButton("Move");
		moveRightButton.setBackground(new Color(128, 128, 0));
		vertRightMovePanel.add(moveRightButton);
		
		JPanel vertCenterPanel = new JPanel();
		vertCenterPanel.setBackground(new Color(153, 153, 51));
		vertCenterPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertPanel.add(vertCenterPanel);
		vertCenterPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel vertCenterLabelPanel = new JPanel();
		vertCenterLabelPanel.setForeground(new Color(0, 0, 0));
		vertCenterLabelPanel.setBackground(new Color(153, 153, 102));
		vertCenterLabelPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(205, 133, 63), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertCenterPanel.add(vertCenterLabelPanel, BorderLayout.WEST);
		vertCenterLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_7 = new JLabel("CENTER");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setForeground(new Color(0, 0, 0));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		vertCenterLabelPanel.add(lblNewLabel_7);
		lblNewLabel_7.setUI(new VerticalLabelUI(false));
		
		JPanel vertCenterValueMovePanel = new JPanel();
		vertCenterValueMovePanel.setBackground(new Color(153, 153, 102));
		vertCenterPanel.add(vertCenterValueMovePanel, BorderLayout.CENTER);
		vertCenterValueMovePanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel vertCenterValuePanel = new JPanel();
		vertCenterValuePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertCenterValuePanel.setBackground(new Color(153, 153, 102));
		vertCenterValueMovePanel.add(vertCenterValuePanel);
		vertCenterValuePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBackground(new Color(128, 128, 0));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		vertCenterValuePanel.add(lblNewLabel_5, BorderLayout.CENTER);
		
		JPanel vertCenterMovePanel = new JPanel();
		vertCenterMovePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(160, 82, 45), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertCenterMovePanel.setBackground(new Color(153, 153, 102));
		vertCenterValueMovePanel.add(vertCenterMovePanel);
		
		JButton moveCenterButton = new JButton("Move");
		moveCenterButton.setBackground(new Color(128, 128, 0));
		vertCenterMovePanel.add(moveCenterButton);
		
		JPanel vertWidthPanel = new JPanel();
		vertWidthPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		vertWidthPanel.setBackground(new Color(128, 128, 0));
		vertPanel.add(vertWidthPanel);
		vertWidthPanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(153, 153, 102));
		panel_7.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 204, 153), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertWidthPanel.add(panel_7);
		
		JLabel widthLabel = new JLabel("Width =");
		widthLabel.setFont(widthLabel.getFont().deriveFont(widthLabel.getFont().getStyle() | Font.BOLD | Font.ITALIC, widthLabel.getFont().getSize() + 1f));
		panel_7.add(widthLabel);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(128, 128, 0), null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_8.setBackground(new Color(153, 153, 102));
		vertWidthPanel.add(panel_8);
		
		JLabel scanCenterLabel = new JLabel("Scan Center =");
		panel_8.add(scanCenterLabel);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		vertWidthPanel.add(panel_17);
		panel_17.setBackground(new Color(153, 153, 102));

		JLabel scanCenterDiffLabel = new JLabel("Difference = ");
		panel_17.add(scanCenterDiffLabel);
		
		JPanel horzPanel = new JPanel();
		horzPanel.setBackground(new Color(128, 128, 0));
		tabbedPane.addTab("Horizontal", null, horzPanel, null);
		horzPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel horzTopPanel = new JPanel();
		horzPanel.add(horzTopPanel);
		horzTopPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel horizTopLabelPanel = new JPanel();
		horzTopPanel.add(horizTopLabelPanel, BorderLayout.WEST);
		
		JPanel horzTopValuePanel = new JPanel();
		horzTopPanel.add(horzTopValuePanel, BorderLayout.CENTER);
		horzTopValuePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel horzTopValueLabel = new JLabel("New label");
		horzTopValuePanel.add(horzTopValueLabel, BorderLayout.CENTER);
		
		JPanel horzBottomPanel = new JPanel();
		horzPanel.add(horzBottomPanel);
		horzBottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel horzBottomLabelPanel = new JPanel();
		horzBottomPanel.add(horzBottomLabelPanel, BorderLayout.WEST);
		
		JPanel horzBottomValuePanel = new JPanel();
		horzBottomPanel.add(horzBottomValuePanel, BorderLayout.CENTER);
		horzBottomValuePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		horzBottomValuePanel.add(lblNewLabel, BorderLayout.CENTER);
		
		JPanel horzCenterPanel = new JPanel();
		horzPanel.add(horzCenterPanel);
		horzCenterPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel horzCenterLabelPanel = new JPanel();
		horzCenterPanel.add(horzCenterLabelPanel, BorderLayout.WEST);
		
		JPanel horzCenterValuePanel = new JPanel();
		horzCenterPanel.add(horzCenterValuePanel, BorderLayout.CENTER);
		horzCenterValuePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		horzCenterValuePanel.add(lblNewLabel_1, BorderLayout.CENTER);
		
		JPanel horzWidthPanel = new JPanel();
		horzPanel.add(horzWidthPanel);
		horzWidthPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel horzWidthLabelPanel = new JPanel();
		horzWidthPanel.add(horzWidthLabelPanel, BorderLayout.WEST);
		
		JPanel horzWidthValuePanel = new JPanel();
		horzWidthPanel.add(horzWidthValuePanel, BorderLayout.CENTER);
		horzWidthValuePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		horzWidthValuePanel.add(lblNewLabel_2, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(153, 153, 102));

		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(210, 105, 30)));
		panel_15.setBackground(new Color(153, 153, 102));

		panel_1.add(panel_15);
		
		JLabel scanStatusLabel = new JLabel("Scan Status");
		panel_15.add(scanStatusLabel);
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_16.setBackground(new Color(153, 153, 102));

		panel_1.add(panel_16);
		
		JButton resetMarkerButton = new JButton("Reset Markers");
		panel_16.add(resetMarkerButton);

	}

}

