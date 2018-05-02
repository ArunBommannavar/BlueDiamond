package bluediamond2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.klg.jclass.chart.ChartDataEvent;
import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.data.JCDefaultDataSource;

public class OldData1D extends JCDefaultDataSource implements DetectorDisplayI, PositionerDisplayI,DataViewParmsI,UpdateDisplayI,XRangeI {

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

	String[] posName;
	String[] posDesc;

	String[] posXName;
	String[] posXDesc;

	double[] posXMin;
	double[] posXMax;

	String[] detName;
	String[] detDesc;
	double[] detMin;
	double[] detMax;
	
	JCAxis xaxis;
	JCAxis yaxis;
	double xAxisMin;
	double xAxisMax;
	double yAxisMin;
	double yAxisMax;

	ChartDataView hpDataView;
	List<Integer> selectedDetectors = new ArrayList<Integer>();
	boolean displayModeSwitched = true;
	private final double HOLE_VALUE = Double.MAX_VALUE;
	int[] posPrecision = { 5, 5, 5, 5 };
	int pposPrecision = 4;


	public OldData1D(JCChart c) {
		oldChart = c;
	}

	public void setFileName(String str) {
		fileName = str;
	}

	public void setDataViewNumber(int n) {
		dataViewNumber = n;
		oldChart.addDataView(dataViewNumber);
		oldChart.getDataView(dataViewNumber).setDataSource(this);
		hpDataView = oldChart.getDataView(dataViewNumber);
		xaxis = hpDataView.getXAxis();
		yaxis = hpDataView.getYAxis();
	}

	public int getDataViewNum() {
		return dataViewNumber;
	}

	public ChartDataView getHpDataView() {		
		
		return hpDataView;		
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

	public void initArrays() {
		xVal = new double[numberOfPositioners][numberOfPoints];
		yVal = new double[numberOfDetectors][numberOfPoints];
		posXMin = new double[numberOfPositioners];
		posXMax = new double[numberOfPositioners];
		xvalues = new double[1][numberOfPoints];
		yvalues = new double[numberOfDetectors][numberOfPoints];
	}

	public void setPosName(String[] s) {
		int n = s.length;
		posName = new String[n];
		for (int i = 0; i < n; i++) {
			posName[i] = s[i];
		}
	}

	public void setPosDesc(String[] s) {
		int n = s.length;
		posDesc = new String[n];
		for (int i = 0; i < n; i++) {
			posDesc[i] = s[i];
		}
	}

	public void setDetName(String[] s) {
		int n = s.length;
		detName = new String[n];
		for (int i = 0; i < n; i++) {
			detName[i] = s[i];
		}
	}

	public void setDetDesc(String[] s) {
		int n = s.length;
		detDesc = new String[n];
		for (int i = 0; i < n; i++) {
			detDesc[i] = s[i];
		}
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


	public void calculatePosDetMinMax() {

		posXMin = new double[numberOfPositioners];
		posXMax = new double[numberOfPositioners];

		detMin = new double[numberOfDetectors];
		detMax = new double[numberOfDetectors];

		for (int i = 0; i < numberOfPositioners; i++) {
			posXMin[i] = xVal[i][0];
			posXMax[i] = xVal[i][0];

			for (int j = 1; j < numberOfPoints; j++) {
				if (xVal[i][j] < posXMin[i]) {
					posXMin[i] = xVal[i][j];
				}
				if (xVal[i][j] > posXMax[i]) {
					posXMax[i] = xVal[i][j];
				}
			}
			posXMin[i] = getPrecisionedData(posXMin[i]);
			posXMax[i] = getPrecisionedData(posXMax[i]);
		}

		for (int i = 0; i < numberOfDetectors; i++) {
			detMin[i] = yVal[i][0];
			detMax[i] = yVal[i][0];

			for (int j = 1; j < numberOfPoints; j++) {
				if (yVal[i][j] < detMin[i]) {
					detMin[i] = yVal[i][j];
				}
				if (yVal[i][j] > detMax[i]) {
					detMax[i] = yVal[i][j];
				}
			}
			detMin[i] = getPrecisionedData(detMin[i]);
			detMax[i] = getPrecisionedData(detMax[i]);

		}
	}


	public double getPrecisionedData(double d1) {
		double d = Math.floor(d1 * Math.pow(10, pposPrecision) + 0.5);
		d = d / Math.pow(10, pposPrecision);
		return d;
	}
/*
	public void checkForMinMax() {
		for (int i = 0; i < numberOfPositioners; i++) {
			if (pMin[i] > pMax[i]) {

				double temp = pMax[i];
				pMax[i] = pMin[i];
				pMin[i] = temp;
			}
		}
		xAxisMin = pMin[selectedPositioner];
		xAxisMax = pMax[selectedPositioner];
	}
*/
	public void setPosXMin(int n,double d) {
		posXMin[n] = d;		
	}
	
	public void setPosXMax(int n,double d) {
		posXMax[n] = d;		
	}
	
	public void setDetMin(int n,double d) {
		detMin[n] = d;		
	}
	
	public void setDetMax(int n,double d) {
		detMax[n] = d;		
	}
	
	public double getPosXMin(int n) {
		return posXMin[n];
	}
	
	public double getPosXMax(int n) {
		return posXMax[n];
	}
	
	public double getDetMin(int n) {
		return detMin[n];
	}
	
	public double getDetMax(int n) {
		return detMax[n];
	}
	
	public double[] getPosXMinArray() {
		return posXMin;
	}
	
	public double[] getPosXMaxArray() {
		return posXMax;
	}
	
	public double[] getDetMinArray() {
		return detMin;
	}
	
	public double[] getDetMaxArray() {
		return detMax;
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

	public void displayDets(int n) {
//		setSelectedPositioner(0);

		for (int i = 0; i < numberOfDetectors; i++) {
			if (oldChart.getDataView(n).getSeries(i).isVisible()) {
				addDetectorForDisplay(i);
				setDetectorForDisplay(i, true);
			} else {
				removeDetectorForDisplay(i);
				setDetectorForDisplay(i, false);
			}
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

		hpDataView = oldChart.getDataView(dataViewNumber);

		xaxis = hpDataView.getXAxis();
		yaxis = hpDataView.getYAxis();

		if (b) {
			addDetectorForDisplay(n);
		} else {
			removeDetectorForDisplay(n);
		}
		hpDataView.getSeries(n).setVisible(b);
		hpDataView.getSeries(n).setIncluded(b);

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

		// userAutoScale.setXMinMax();
		// userAutoScale.setYMinMax();

	}

	public int getSelectedPositioner() {
		System.out.println(" Selected Positioner  "+selectedPositioner);
		return selectedPositioner;
	}
	
	public List getSelectedChartDetectors() {
		List list = new ArrayList();

		for (int i = 0; i < numberOfDetectors; i++) {
			if (oldChart.getDataView(dataViewNumber).getSeries(i).isVisible()) {
				list.add(i);
			}
		}
		return list;
	}

	synchronized public void updateDisplay() {
		oldChart.setBatched(true);
		/**
		 * OK. From the xVal and yVal data arrays, let us decide if raw or derivative
		 * data needs to be put in xvalues and yvalues arrays.
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
					yvalues[j][i] = (yVal[j][i + 1] - yVal[j][i])
							/ ((xVal[selectedPositioner][i + 1] - xVal[selectedPositioner][i]));
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

	@Override
	public int getSeriesSymbol(int n) {
	       int m;
	        m = oldChart.getDataView(0).getSeries(n).getStyle().getSymbolShape();
	        return m;
	  	}

	@Override
	public int getSeriesSymbolSize(int n) {
	       int m;
	        m = oldChart.getDataView(0).getSeries(n).getStyle().getSymbolSize();
	        return m;
	}

	@Override
	public int getSeriesThickness(int n) {
        int m;
        m = oldChart.getDataView(0).getSeries(n).getStyle().getLineWidth();
        return m;
	}

}
