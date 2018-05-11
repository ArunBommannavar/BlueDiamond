package bluediamond2;

import com.klg.jclass.chart.ChartDataView;

public class OldFileList {

	
	boolean tabVisible = true;
	String fileName = "";
	OldData1D oldData1D;
	ChartDataView chartDataView;
	Old_1D_Panel old_1D_Panel;
	
	
	public OldFileList(String str) {
		fileName = str;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setTabVisible(boolean b) {
		tabVisible = b;
	}
	
	public boolean getTabVisible() {
		return tabVisible;
	}
	
	
	public void addOldData1D(OldData1D oldData1D) {
		this.oldData1D = oldData1D;		
	}
	
	public OldData1D getOldData1D() {
		return oldData1D;
	}
	
	public void addChartDataView(ChartDataView chartDataView) {
		this.chartDataView = chartDataView;
	}
	
	public ChartDataView getChartDataView() {
		return chartDataView;
	}
	
	public void addOld_1D_Panel(Old_1D_Panel old_1D_Panel) {
		this.old_1D_Panel = old_1D_Panel;
	}
	
	public Old_1D_Panel getOld_1D_Panel() {
		return old_1D_Panel;
	}
}
