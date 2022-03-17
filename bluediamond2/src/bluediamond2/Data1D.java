package bluediamond2;

import java.awt.Color;
import javax.swing.SwingUtilities;
import com.klg.jclass.chart.ChartDataEvent;
import com.klg.jclass.chart.ChartDataView;
import com.klg.jclass.chart.JCAxis;
import com.klg.jclass.chart.JCChart;
import com.klg.jclass.chart.data.JCDefaultDataSource;


public class Data1D extends JCDefaultDataSource {

	private static final long serialVersionUID = 7676041947267691757L;
	int numberOfPositioners = 4;
	int numberOfDetectors = 60;
	int cpt;
	int npts = 100;
	private final double HOLE_VALUE = Double.MAX_VALUE;

	double[] posPP = new double[numberOfPositioners];
	double[] pMin = new double[numberOfPositioners];
	double[] pWidth = new double[numberOfPositioners];
	double[][] posPA = new double[numberOfPositioners][];
	double[] pStart = new double[numberOfPositioners];
	double[] pEnd = new double[numberOfPositioners];
	String[] scanMode = new String[numberOfPositioners];
	String[] relAbs = new String[numberOfPositioners];
	String[] posName = new String[numberOfPositioners];

	double[][] xVals = new double[numberOfPositioners][];
	double[][] yVals = new double[numberOfDetectors][];

	boolean dataInitalized = false;

	protected JCChart chart;
	JCAxis xaxis;
	JCAxis yaxis;
	ChartDataView dataView;

	int selectedPositioner = 0;
	boolean derivative = false;
	boolean displayModeSwitched = true;
	boolean autoScale = true;	
	double xaxisUserMin;
	double xaxisUserMax;
	double yaxisUserMin;
	double yaxisUserMax;
	

	public Data1D(JCChart c) {
		this.chart = c;
		dataView = chart.getDataView(0);
		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();

		xaxis.setMinIsDefault(true);
		xaxis.setMaxIsDefault(true);
		yaxis.setMinIsDefault(true);
		yaxis.setMaxIsDefault(true);		
	}
	
	public void setNumberOfPoints(int n) {
		npts = n;
	}

	public int getScan1NumberOfPoints(){
		return npts;
	}
	
	public void setCurrentPoint(int n) {
		cpt = n;
	}

	void setSelectedPositioner(int n) {
		selectedPositioner = n;
		setXAxisScale();
		setYAxisScale();
		updateChartDisplay(cpt,npts);
	}

	public int getSelectedPositioner(){
		return selectedPositioner;
	}
	
		
	public void initDataArray() {
		xVals = new double[numberOfPositioners][npts];
		yVals = new double[numberOfDetectors][npts];

		for (int pos = 0; pos < numberOfPositioners; pos++) {
			for (int i = 0; i < npts; i++) {
				xVals[pos][i] = (double) i;
			}
		}

		for (int det = 0; det < numberOfDetectors; det++) {
			for (int i = 0; i < npts; i++) {
				yVals[det][i] = HOLE_VALUE;
			}
		}
	}

	public void setDataArray(int pos) {
	
		for (int i = 0; i < npts; i++) {
			xVals[pos][i] = pStart[pos] + ((double) i / (double) (npts - 1)) * pWidth[pos];
		}
	}
		
	public void clearYValues() {
		for (int i = 0; i < numberOfDetectors; i++) {
			for (int j = 0; j < npts; j++) {
				yvalues[i][j] = HOLE_VALUE;
			}
		}
	}
	
	public void initChartData() {
		xvalues = new double[1][npts];
		yvalues = new double[numberOfDetectors][npts];

		for (int i = 0; i < numberOfDetectors; i++) {
			for (int j = 0; j < npts; j++) {
				yvalues[i][j] = HOLE_VALUE;
			}
		}
	}

	public void setChartData(int sp) {
		selectedPositioner = sp;

		for (int i = 0; i < npts; i++) {
			xvalues[0][i] = xVals[selectedPositioner][i];
		}
	}

	public void setChartRawData() {
		for (int i = 0; i < npts; i++) {
			xvalues[0][i] = xVals[selectedPositioner][i];
		}

		for (int det = 0; det < numberOfDetectors; det++) {
			for (int i = 0; i < npts; i++) {
				yvalues[det][i] = yVals[det][i];
			}
		}
	}

	public void setDerivativeData() {
		for (int i = 0; i < npts; i++) {
			xvalues[0][i] = xVals[selectedPositioner][i];
		}

		for (int det = 0; det < numberOfDetectors; det++) {
			for (int i = 0; i < npts; i++) {
				yvalues[det][i] = yVals[det][i];
			}
		}
	}

    public void setLogLinear(boolean b) {
       
        runSafe(new Runnable() {
            public void run() {
                chart.setBatched(true);               
                yaxis.setLogarithmic(b);
                chart.setBatched(false);

                fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
            }
        });

    }

	public void setScanMode(int pos, String str) {
		scanMode[pos] = str;
	}

	public void setPosPP(int n, double d) {
		posPP[n] = d;
	}

	public void setPosMin(int pos, double d) {
		pMin[pos] = d;
	}

	public void setPosWidth(int pos, double d) {
		pWidth[pos] = d;
	}

	public void setPosPA(int pos, double[] d) {
		posPA[pos] = d;
	}

	public void setPosRelAbs(int pos, String str) {
		relAbs[pos] = str;
	}

	public void setPosName(int pos, String str) {
		posName[pos] = str;
	}

	public void setXaxisTitle() {

	}

	public void setAutoScale(boolean b){
		autoScale = b;
		if(b){
			setXAxisScale();
			setYAxisScale();
		}
	}
		
	public void setxAxisUserMin(double d){
		xaxisUserMin = d;
	}
	
	public void setxAxisUserMax(double d){
		xaxisUserMax = d;
	}
	
	public void setyAxisUserMin(double d){
		yaxisUserMin = d;
	}
	
	public void setyAxisUserMax(double d){
		yaxisUserMax = d;
	}
	
	public void setYAxisScale() {
		chart.setBatched(true);
		yaxis = chart.getDataView(0).getYAxis();


		if (autoScale){
			yaxis.setMinIsDefault(true);
			yaxis.setMaxIsDefault(true);

		}else{			
			yaxis.setMin(yaxisUserMin);
			yaxis.setMax(yaxisUserMax);
		}
				
		chart.setBatched(false);
		runSafe(new Runnable() {
			public void run() {
				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	
	public void setXAxisRange() {

		for (int i = 0; i < numberOfPositioners; i++) {
			if (scanMode[i].equals("RELATIVE")) {
				pStart[i] = posPP[i] + pMin[i];
				pEnd[i] = pStart[i] + pWidth[i];
			} else {
				pStart[i] = pMin[i];
				pEnd[i] = pMin[i] + pWidth[i];
			}
		}
	}

	public void setXAxisScale() {
		
		chart.setBatched(true);
		xaxis = chart.getDataView(0).getXAxis();
		yaxis = chart.getDataView(0).getYAxis();

		xaxis.setMinIsDefault(true);
		xaxis.setMaxIsDefault(true);

		if (autoScale){
		double tempXmin = xVals[selectedPositioner][0];
		double tempXmax = xVals[selectedPositioner][npts - 1];
		double temp1;
		double temp2;
		double delta;

		if (tempXmin > tempXmax) {
			temp1 = tempXmin;
			temp2 = tempXmax;
			tempXmin = temp2;
			tempXmax = temp1;
		}

		delta = 0.1 * (tempXmax - tempXmin);
//		System.out.println(" tempXmin = "+tempXmin+"  delta = "+delta);
		xaxis = dataView.getXAxis();
		xaxis.setMin(tempXmin - delta);
		xaxis.setMax(tempXmax + delta);
		}else{
			
			xaxis.setMin(xaxisUserMin);
			xaxis.setMax(xaxisUserMax);
			xaxis.setMinIsDefault(false);
			xaxis.setMaxIsDefault(false);

		}
		chart.setBatched(false);
		runSafe(new Runnable() {
			public void run() {
				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	public void setXAxisRange(int n) {
//		System.out.println(" In Data1D  setXAxisRange posiitoner =  "+n+" number of points = "+npts);
//		System.out.println(" scanmode ="+ scanMode[n]+"  relAbs = "+relAbs[n]);
		
		if (scanMode[n].equals("LINEAR")) {

			if (relAbs[n].equals("RELATIVE")) {
				pStart[n] = posPP[n] + pMin[n];
				pEnd[n] = pStart[n] + pWidth[n];
			} else {
				pStart[n] = pMin[n];
				pEnd[n] = pMin[n] + pWidth[n];
			}
			for (int i = 0; i < npts; i++) {
				xVals[n][i] = pStart[n] + ((double) i / (double) (npts - 1)) * pWidth[n];
//				System.out.println(" X Vals = "+ xVals[n][i]);
			}

		} else if (scanMode[n].equals("TABLE")) {
			pStart[n] = posPA[n][0];
			pEnd[n] = posPA[n][npts - 1];
			for (int i = 0; i < npts; i++) {
				xVals[n][i] =  posPA[n][i];
			}
		} else if (scanMode[n].equals("FLY")){
			pStart[n] = posPP[n] + pMin[n];
			pEnd[n] = pStart[n] + pWidth[n];
			for (int i = 0; i < npts; i++) {
				xVals[n][i] = pStart[n] + ((double) i / (double) (npts - 1)) * pWidth[n];
			}
		}
	}

	public void setPositionerValue(int pos, int cpt, double d) {
		xVals[pos][cpt - 1] = d;
	}

	synchronized public void setDetectorValue(int det, int cpt, double d) {		
		yVals[det][cpt - 1] = d;		
	}

	public void setPositionerDataArray(int pos, double[] d) {
		xVals[pos] = d;
		for (int i=0; i<d.length;i++) {
		}
	}

	public void setDetectorDataArray(int det, double[] d) {
//		System.out.println(" Data1D Set Detector Data Array = "+det+"  Array Size  = "+d.length);
		yVals[det] = d;
	}

	synchronized public void setDerivative(boolean b) {
		derivative = b;
		/**
		 * Now tell the chart to update itself
		 *
		 */
		displayModeSwitched = true;
		updateChartDisplay(cpt, npts);

	}

	public void setDetectorForDisplay(int n, boolean b) {

		final int nm = n;
		final boolean bm = b;
		runSafe(new Runnable() {
			public void run() {

				chart.setBatched(true);
				xaxis = chart.getDataView(0).getXAxis();
				yaxis = chart.getDataView(0).getYAxis();
				/*
				 * if (bm) { addDetectorForDisplay(nm); } else {
				 * removeDetectorForDisplay(nm); }
				 */
				chart.getDataView(0).getSeries(nm).setVisible(bm);
				chart.getDataView(0).getSeries(nm).setIncluded(bm);
				yaxis.setMinIsDefault(true);
				yaxis.setMaxIsDefault(true);
				chart.setBatched(false);
//				System.out.println(" Set Detector for display = "+n+"  total series = "+chart.getDataView(0).getNumSeries());
				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	public double getScanCenter() {
		double d;
		d = (xvalues[0][0] + xvalues[0][npts - 1]) / 2.0;
		return d;
	}

	public void updateChartDisplay() {
		chart.setBatched(true);

		chart.setBatched(false);
		runSafe(new Runnable() {
			public void run() {
				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	synchronized public void updateChartDisplay(int n, int m) {
		/**
		 * Check for derivative mode If derivative mode then make sure there are
		 * atleast 2 data points Check if switched from raw to derivative mode
		 * (or the reverse) If switched then make new
		 * arrays(DisplayModeSwitched).
		 */
		int rawTotalPlotPints = m;
		int rawLastPlotPoint = n;
		int derTotalPlotPints = m - 1;
		int derLastPlotPoint = n - 1;
		chart.setBatched(true);

		if (derivative) {
			if (derTotalPlotPints > 1) {
				if (displayModeSwitched) {
					xvalues = new double[1][derTotalPlotPints];
					yvalues = new double[numberOfDetectors][derTotalPlotPints];
					for (int i = 0; i < numberOfDetectors; i++) {
						for (int j = 0; j < derTotalPlotPints; j++) {
							yvalues[i][j] = HOLE_VALUE;
						}
					}
				}
				for (int i = 0; i < derTotalPlotPints; i++) {
					xvalues[0][i] = (xVals[selectedPositioner][i] + xVals[selectedPositioner][i + 1]) / 2.0;
				}

				for (int i = 0; i < derLastPlotPoint; i++) {
					for (int j = 0; j < numberOfDetectors; j++) {
						yvalues[j][i] = (yVals[j][i + 1] - yVals[j][i])
								/ ((xVals[selectedPositioner][i + 1] - xVals[selectedPositioner][i]));
					}
				}
			}
		} else {
			if (displayModeSwitched) {
				xvalues = new double[1][rawTotalPlotPints];
				yvalues = new double[numberOfDetectors][rawTotalPlotPints];
				for (int i = 0; i < numberOfDetectors; i++) {
					for (int j = 0; j < rawTotalPlotPints; j++) {
						yvalues[i][j] = HOLE_VALUE;
					}
				}
			}
			for (int i = 0; i < rawTotalPlotPints; i++) {
				xvalues[0][i] = xVals[selectedPositioner][i];
			}
			for (int i = 0; i < rawLastPlotPoint; i++) {
				for (int j = 0; j < numberOfDetectors; j++) {
					yvalues[j][i] = yVals[j][i];
				}
			}
			displayModeSwitched = false;
		}

		chart.setBatched(false);
		runSafe(new Runnable() {
			public void run() {
				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});

	}

	/**
	 * Convenient method to run Runnable in the Thread safe manner.
	 * 
	 * @param task
	 */
	public static void runSafe(Runnable task) {
		if (SwingUtilities.isEventDispatchThread()) {
			task.run();
		} else {
			SwingUtilities.invokeLater(task);
		}
	}

	public int getDataViewNum() {
		return 0;
	}

	public int getSeriesSymbol(int n) {
		int m;

		m = chart.getDataView(0).getSeries(n).getStyle().getSymbolShape();
		return m;
	}

	public int getSeriesThickness(int n) {
		int m;

		m = chart.getDataView(0).getSeries(n).getStyle().getLineWidth();
		return m;
	}

	public int getSeriesSymbolSize(int n) {
		int m;

		m = chart.getDataView(0).getSeries(n).getStyle().getSymbolSize();
		return m;
	}

	public Color getSeriesLineColor(int n) {
		
		Color c = chart.getDataView(0).getSeries(n).getStyle().getLineColor();
		return c;
	}

	public void setSeriesColor(Color c, int n) {
		final int nm = n;
		final Color cf = c;
		runSafe(new Runnable() {
			public void run() {

				chart.setBatched(true);
				chart.getDataView(0).getSeries(nm).getStyle().setLineColor(cf);
				chart.getDataView(0).getSeries(nm).getStyle().setSymbolColor(cf);
				chart.setBatched(false);

				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	public void setSeriesThickness(int n, int j) {

		final int nm = n;
		final int jm = j;
		runSafe(new Runnable() {
			public void run() {

				chart.setBatched(true);
				chart.getDataView(0).getSeries(jm).getStyle().setLineWidth(nm);

				chart.setBatched(false);

				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	public void setSeriesSymbol(int n, int j) {
		final int nm = n;
		final int jm = j;
		runSafe(new Runnable() {
			public void run() {

				chart.setBatched(true);
				chart.getDataView(0).getSeries(jm).getStyle().setSymbolShape(nm);
				chart.setBatched(false);

				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}

	public void setSeriesSymbolSize(int n, int j) {
		final int nm = n;
		final int jm = j;

		runSafe(new Runnable() {
			public void run() {

				chart.setBatched(true);
				chart.getDataView(0).getSeries(jm).getStyle().setSymbolSize(nm);
				chart.setBatched(false);

				fireChartDataEvent(ChartDataEvent.RESET, 0, 0);
			}
		});
	}
}
