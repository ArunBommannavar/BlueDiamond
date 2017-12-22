package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;

public class DetectorPanel extends JPanel {

	JPanel detectors_1_30_panel;
	JPanel detectors_31_60_panel;
	
	/**
	 * Create the panel.
	 */
	public DetectorPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		detectors_1_30_panel = new JPanel();
		tabbedPane.addTab("Detectors 1-30", null, detectors_1_30_panel, null);
		detectors_1_30_panel.setLayout(new GridLayout(10, 3, 0, 0));
		
		detectors_31_60_panel = new JPanel();
		tabbedPane.addTab("Detectors 31-60", null, detectors_31_60_panel, null);
		detectors_31_60_panel.setLayout(new GridLayout(10, 3, 0, 0));

	}
	
	public void addDetectorColorPanel_1_30(DetectorColorPanel dp){
		detectors_1_30_panel.add(dp);
	}
	public void addDetectorColorPanel_31_60(DetectorColorPanel dp){
		detectors_31_60_panel.add(dp);
	}


}
