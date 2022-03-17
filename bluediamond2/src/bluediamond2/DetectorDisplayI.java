package bluediamond2;

import java.awt.Color;
import java.util.List;

public interface DetectorDisplayI {
	   public void setNumberOfDetectors(int n);
	   public void addDetectorForDisplay(int i);
	   public void removeDetectorForDisplay(int i);
	   public void setDetectorForDisplay(int n, boolean b);
	   public void setSeriesColor(Color c, int n);
	   public void setSeriesThickness(int n,int j);
	   public void setSeriesSymbol(int n,int j);
	   public void setSeriesSymbolSize(int n, int j);
	   public int getSeriesSymbol(int n);
	   public int getSeriesSymbolSize(int n);
	   public int getSeriesThickness(int n);
	   public List<Integer> getSelectedChartDetectors();	   
}
