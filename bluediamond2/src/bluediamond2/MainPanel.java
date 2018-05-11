package bluediamond2;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;

public class MainPanel extends JPanel {
	
	JTabbedPane mainTabbedPane;
	private Active_1D_ScanPanel active_1D_ScanPanel;
	private Active_2D_ScanPanel active_2D_ScanPanel;
	private Saved_1D_ScanPanel saved_1D_ScanPanel;
	
	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setLayout(new BorderLayout(0, 0));
		
		mainTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(mainTabbedPane, BorderLayout.CENTER);
		
		active_1D_ScanPanel = new Active_1D_ScanPanel();
		mainTabbedPane.addTab("Active 1-D Scan", null, active_1D_ScanPanel, null);
		mainTabbedPane.setEnabledAt(0, true);
		
		active_2D_ScanPanel = new Active_2D_ScanPanel();
		mainTabbedPane.addTab("Active 2-D scan", null, active_2D_ScanPanel, null);
		mainTabbedPane.setEnabledAt(1, true);
		
		saved_1D_ScanPanel = new Saved_1D_ScanPanel();
		mainTabbedPane.addTab("Saved 1-D Scan", null, saved_1D_ScanPanel, null);
		mainTabbedPane.setEnabledAt(2, true);
	}
	
	public JCChart getChart() {
		return active_1D_ScanPanel.getChart();
	}
	
	public JCChart3dJava2d get3DChart(){
		return active_2D_ScanPanel.get3DChart();
	}
	
	public JCChart getOldChart(){
		return saved_1D_ScanPanel.getSaved_1D_Chart();
	}

	public void set1DDataSource(Data1D d1) {
		active_1D_ScanPanel.set1DDataSource(d1);
	}
	public void set2DDataSource(Data2D d2) {
		active_2D_ScanPanel.set2DDataSource(d2);
	}

	
	public Active_1D_ScanPanel getActive_1D_ScanPanel() {
		return active_1D_ScanPanel;
	}
	
	public Active_2D_ScanPanel getActive_2D_ScanPanel() {
		return active_2D_ScanPanel;
	}
	
	public Saved_1D_ScanPanel getSaved_1D_ScanPanel() {
		return saved_1D_ScanPanel;
	}
	
	public void getSelectedPanelIndex() {
		int selectedTabIndex = mainTabbedPane.getSelectedIndex();
		
		switch(selectedTabIndex) {
		
		case 0:
			active_1D_ScanPanel.setMarkers();
		
		}
	}
}
