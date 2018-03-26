package bluediamond2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.klg.jclass.chart.ChartDataEvent;
import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.data.JCDefaultDataSource;

public class OldData1D extends JCDefaultDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JCChart oldChart;
	String fileName;
	int dataViewNumber;
	int numberOfPositioners;
	int numberOfDetectors;
	int selectedPositioner;
	int numberOfPoints;
	int numberOfCurrentPoints;
	boolean derivative = false;
	double[][] xVal;
	double[][] yVal;
	double[][] xTemp;
	double[][] yTemp;

	JCAxis xaxis;
	JCAxis yaxis;
	ChartDataView hpDataView;
	List<Integer> selectedDetectors = new ArrayList<Integer>();
	boolean displayModeSwitched = true;


	public OldData1D(JCChart c) {
		oldChart = c;
	}

	public void setFileName(String str) {
		fileName = str;
	}

	public void setDataViewNumber(int n) {
		dataViewNumber = n;
		oldChart.addDataView(dataViewNumber);
		hpDataView = oldChart.getDataView(dataViewNumber);
		xaxis = hpDataView.getXAxis();
		yaxis = hpDataView.getYAxis();
	}

	public int getDataViewNum() {
		return dataViewNumber;
	}

	public void setNumPoints(int n) {
		numberOfPoints = n;
	}

	public void setNumberOfCurrentPoints(int n) {
		numberOfCurrentPoints = n;
	}
	public void setNumPositioners(int n) {
		numberOfPositioners = n;
	}

	public void setNumberOfDetectors(int n) {
		numberOfDetectors = n;
	}

	public void setXVals(double[][] x) {
		for (int i = 0; i < numberOfPositioners; i++) {
			for (int j = 0; j < numberOfPoints; j++) {
				xVal[i][j] = x[i][j];
			}
		}
	}

	public void setYVals(double[][] y) {
		for (int i = 0; i < numberOfDetectors; i++) {
			for (int j = 0; j < numberOfPoints; j++) {
				yVal[i][j] = y[i][j];
			}
		}
	}

	public void addDetectorForDisplay(int i) {
		if (!selectedDetectors.contains(i)) {
			selectedDetectors.add(i);
		}
	}

	public void removeDetectorForDisplay(int i) {
		int n;

		n = selectedDetectors.indexOf(i);
		if (n > -1) {
			selectedDetectors.remove(n);
		}

	}

	public void setSeriesColor(Color c, int n) {
		oldChart.setBatched(true);
		oldChart.getDataView(dataViewNumber).getSeries(n).getStyle().setLineColor(c);
		oldChart.getDataView(dataViewNumber).getSeries(n).getStyle().setSymbolColor(c);
		oldChart.setBatched(false);
		fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
	}

	public void setSeriesThickness(int n, int j) {
		oldChart.setBatched(true);
		oldChart.getDataView(dataViewNumber).getSeries(j).getStyle().setLineWidth(n);
		oldChart.setBatched(false);
		fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
	}

	public void setSeriesSymbol(int n, int j) {
		oldChart.setBatched(true);
		oldChart.getDataView(dataViewNumber).getSeries(j).getStyle().setSymbolShape(n);
		oldChart.setBatched(false);
		fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
	}

	public void setSeriesSymbolSize(int n, int j) {
		oldChart.setBatched(true);
		oldChart.getDataView(dataViewNumber).getSeries(j).getStyle().setSymbolSize(n);
		oldChart.setBatched(false);
		fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
	}

	public void setDetectorForDisplay(int n, boolean b) {
		oldChart.setBatched(true);

		xaxis = oldChart.getDataView(dataViewNumber).getXAxis();
		yaxis = oldChart.getDataView(dataViewNumber).getYAxis();

		if (b) {
			addDetectorForDisplay(n);
		} else {
			removeDetectorForDisplay(n);
		}
		oldChart.getDataView(dataViewNumber).getSeries(n).setVisible(b);
		oldChart.getDataView(dataViewNumber).getSeries(n).setIncluded(b);

		yaxis.setMinIsDefault(true);
		yaxis.setMaxIsDefault(true);

		oldChart.setBatched(false);
		fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
	}
	
	   synchronized public void setDerivative(boolean b) {
		      derivative = b;
		      displayModeSwitched = true;
		      updateDisplay();
		   }

	
	   public void setSelectedPositioner(int n) {
		      selectedPositioner = n;
		      updateDisplay();
//		       checkForMinMax();
//		      userAutoScale.setXMinMax();
//		      userAutoScale.setYMinMax();

		   }

	   synchronized public void updateDisplay() {
		      oldChart.setBatched(true);
		      /**
		       * OK. From the xVal and yVal data arrays, let us
		       * decide if raw or derivative data needs to be
		       * put in xvalues and yvalues arrays.
		       */
		      if (derivative) {
		         if (displayModeSwitched) {
		            xvalues = new double[1][numberOfPoints - 1];
		            yvalues = new double[numberOfDetectors][numberOfPoints - 1];
		         }

		         for (int i = 0; i < numberOfPoints - 1; i++) {
		            xvalues[0][i] = (xVal[selectedPositioner][i] + xVal[selectedPositioner][i + 1]) / 2.0;
		         }
		         for (int i = 0; i < numberOfPoints - 1; i++) {
		            for (int j = 0; j < numberOfDetectors; j++) {
		               yvalues[j][i] = (yVal[j][i + 1] - yVal[j][i]) /
		                               ((xVal[selectedPositioner][i + 1] - xVal[selectedPositioner][i]));
		            }
		         }
		         displayModeSwitched = false;

		      } else {

		         if (displayModeSwitched) {

		            xvalues = new double[1][numberOfPoints];
		            yvalues = new double[numberOfDetectors][numberOfPoints];
		         }

		         for (int i = 0; i < numberOfPoints; i++) {
		            xvalues[0][i] = xVal[selectedPositioner][i];
		         }
		         for (int i = 0; i < numberOfPoints; i++) {
		            for (int j = 0; j < numberOfDetectors; j++) {
		               yvalues[j][i] = yVal[j][i];
		            }
		         }
		         displayModeSwitched = false;

		      }

		      oldChart.setBatched(false);
		      fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
		   }

}
