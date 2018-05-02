package bluediamond2;

import com.klg.jclass.chart3d.Chart3dDataView;
import com.klg.jclass.chart3d.JCData3dGridIndex;
import com.klg.jclass.chart3d.data.JCDefault3dGridDataSource;
import com.klg.jclass.chart3d.event.Chart3dGridDataEvent;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;

public class Data2D extends JCDefault3dGridDataSource {


	private static final long serialVersionUID = 1086522056399066124L;
	int numberOfXPositioners = 4;
	int numberOfYPositioners = 4;
	int numberOfDetectors = 60;
	int xNumPoints = 100;
	int yNumPoints = 100;

	double[][] x2D;
	double[][] y2D;
	double[][][] z2D;
	double[][] dets = new double[1][1];

	boolean xAxisInit = false;
	boolean yAxisInit = false;
	boolean initZArray = false;
	
	double[] posPP_2DY = new double[numberOfYPositioners];
	double[] pMin_2DY = new double[numberOfYPositioners];
	double[] pWidth_2DY = new double[numberOfYPositioners];
	double[][] posPA_2DY = new double[numberOfYPositioners][];
	double[] pStart_2DY = new double[numberOfYPositioners];
	double[] pEnd_2DY = new double[numberOfYPositioners];
	String[] scanMode_2DY = new String[numberOfYPositioners];
	String[] relAbs_2DY = new String[numberOfYPositioners];
	
	double[] posPP_2DX = new double[numberOfXPositioners];
	double[] pMin_2DX = new double[numberOfXPositioners];
	double[] pWidth_2DX = new double[numberOfXPositioners];
	double[][] posPA_2DX = new double[numberOfXPositioners][];
	double[] pStart_2DX = new double[numberOfXPositioners];
	double[] pEnd_2DX = new double[numberOfXPositioners];
	String[] scanMode_2DX = new String[numberOfXPositioners];
	String[] relAbs_2DX = new String[numberOfXPositioners];
	
	int selectedPositioner_X = 0;
	int selectedPositioner_Y = 0;
	int selectedDetector = 0;
	
    JCChart3dJava2d chart3d = null;
    Chart3dDataView dataView = null;
    double topCutoff;
    double botCutoff;
    boolean intCutoff = false;


	public Data2D(JCChart3dJava2d chart3d) {
		this.chart3d = chart3d;
        dataView = chart3d.getDataView(0);
        chart3d.getFooter().setVisible(true);
	}

	public void setXNumPoints(int n) {
		xNumPoints = n;
	}

	public void setYNumPoints(int n) {
		yNumPoints = n;
	}

	public void setxAxisInit(boolean b) {
		xAxisInit = b;
	}

	public void setyAxisInit(boolean b) {
		yAxisInit = b;
	}

	public void setZinitArray(boolean b) {
		initZArray = b;
	}

	public boolean getZinitArray() {
		return initZArray;
	}

	public void initDataArray(int numx, int numy, int numxpos, int numypos, int numdet) {

		xNumPoints = numx;
		yNumPoints = numy;
		numberOfXPositioners = numxpos;
		numberOfYPositioners = numypos;
		numberOfDetectors = numdet;

		x2D = null;
		y2D = null;
		z2D = null;

		xGrid = new double[xNumPoints];
		yGrid = new double[yNumPoints];
		zValues = new double[xGrid.length][yGrid.length];
		initXArray();
		initYArray();
		initZArray();
		

	}

	public void initXArray() {
		x2D = new double[numberOfXPositioners][xNumPoints];
	}

	public void initYArray() {
		y2D = new double[numberOfYPositioners][yNumPoints];
	}

	public void initZArray() {
		z2D = new double[numberOfDetectors][xNumPoints][yNumPoints];

		for (int i = 0; i < numberOfDetectors; i++) {
			for (int j = 0; j < xNumPoints; j++) {
				for (int k = 0; k < yNumPoints; k++) {
					z2D[i][j][k] = this.holeValue;
				}
			}
		}
		initZArray = true;
	}
		
	public void setScanGridRange(){
		for (int i=0; i< xNumPoints;i++){
			xGrid[i]= x2D[selectedPositioner_X][i];
		}
		
		for (int i=0; i< yNumPoints;i++){
			yGrid[i]= y2D[selectedPositioner_Y][i];
		}
		
		
	}
	public void setSelectedPositioner_X(int pos){
		selectedPositioner_X = pos;
		plotData();
	}
	public void setSelectedPositioner_Y(int pos){
		selectedPositioner_Y = pos;
		plotData();
	}
	
	public void setSelectedDetector(int det){
//		System.out.println(" Selected detector = "+selectedDetector);
		selectedDetector = det;
		plotData();
	}
	public void setXAxisMin(int pos, double d) {

	}

	public void setYaxisMin(int pos, double d) {

	}

	public void setXAxisMax(int pos, double d) {

	}

	public void setYaxisMax(int pos, double d) {

	}

	public void setScanMode_2DY(int pos, String str) {
		scanMode_2DY[pos] = str;
	}
	public void setScanMode_2DX(int pos, String str) {
		scanMode_2DX[pos] = str;
	}

	public void setPosPP_2DY(int n, double d) {
		posPP_2DY[n] = d;
	}

	public void setPosPP_2DX(int n, double d) {
		posPP_2DX[n] = d;
	}

	public void setPosMin_2DY(int pos, double d) {
		pMin_2DY[pos] = d;
	}

	public void setPosMin_2DX(int pos, double d) {
		pMin_2DX[pos] = d;
	}

	public void setPosWidth_2DY(int pos, double d) {
		pWidth_2DY[pos] = d;
	}

	public void setPosWidth_2DX(int pos, double d) {
		pWidth_2DX[pos] = d;
	}

	public void setPosPA_2DY(int pos, double[] d) {
		posPA_2DY[pos] = d;
	}
	
	public void setPosPA_2DX(int pos, double[] d) {
		posPA_2DX[pos] = d;
	}

	public void setPosRelAbs_2DY(int pos, String str) {
		relAbs_2DY[pos] = str;
	}
	
	public void setPosRelAbs_2DX(int pos, String str) {
		relAbs_2DX[pos] = str;
	}

	public void setYAxisRange(int n) {
		if (scanMode_2DY[n].equals("LINEAR")) {

			if (relAbs_2DY[n].equals("RELATIVE")) {
				pStart_2DY[n] = posPP_2DY[n] + pMin_2DY[n];
				pEnd_2DY[n] = pStart_2DY[n] + pWidth_2DY[n];
			} else {
				pStart_2DY[n] = pMin_2DY[n];
				pEnd_2DY[n] = pMin_2DY[n] + pWidth_2DY[n];
			}

		} else if (scanMode_2DY[n].equals("TABLE")) {
			pStart_2DY[n] = posPA_2DY[n][0];
			pEnd_2DY[n] = posPA_2DY[n][yNumPoints - 1];
		}

		for (int i = 0; i < yNumPoints; i++) {
			y2D[n][i] = pStart_2DY[n] + ((double) i / (double) (yNumPoints - 1)) * pWidth_2DY[n];
		}
	}
	
	public void setXAxisRange(int n) {
		if (scanMode_2DX[n].equals("LINEAR")) {

			if (relAbs_2DX[n].equals("RELATIVE")) {
				pStart_2DX[n] = posPP_2DX[n] + pMin_2DX[n];
				pEnd_2DX[n] = pStart_2DX[n] + pWidth_2DX[n];
			} else {
				pStart_2DX[n] = pMin_2DX[n];
				pEnd_2DX[n] = pMin_2DX[n] + pWidth_2DX[n];
			}

		} else if (scanMode_2DX[n].equals("TABLE")) {
			pStart_2DX[n] = posPA_2DX[n][0];
			pEnd_2DX[n] = posPA_2DX[n][xNumPoints - 1];
		}

		for (int i = 0; i < xNumPoints; i++) {
			x2D[n][i] = pStart_2DX[n] + ((double) i / (double) (xNumPoints - 1)) * pWidth_2DX[n];
		}
	}

	public double getXPosMin(int pos){
		return x2D[pos][0];
	}
	
	public double getYPosMin(int pos){
		return y2D[pos][0];
	}
	
	public double getXPosMax(int pos){
		return x2D[pos][xNumPoints-1];
	}
	
	public double getYPosMax(int pos){
		return y2D[pos][yNumPoints - 1];
	}
	
	public void setXDataArray(int posX, double[] posXArray) {
		x2D[posX] = posXArray;
	}

	public void setDetectorDataArray(int y, int det, double[] zv){	
//		System.out.println(" XPoints = "+xNumPoints);
			for (int j = 0; j < xNumPoints; j++) {				
					z2D[det][j][y-1] = zv[j];
//					System.out.println(" 2D Z Value = "+zv[j]);
			}
	}
	
	public void setIntCutoff(boolean b){
		intCutoff = b;
	}
	
	public void setTopCutoff(double d){
		topCutoff = d;
	}

	public void setBotCutoff(double d){
		botCutoff = d;
	}
    public double getXValue(int n) {
        double d = xGrid[n];
        return d;
    }

    public double getYValue(int n) {
        double d = yGrid[n];
        return d;
    }

    public double getZValue(int n, int m){
    	return zValues[n][m];
    }
    public void plotData() {
        chart3d.setBatched(true);
        for (int i=0; i<xNumPoints; i++){
        	xGrid[i]=x2D[selectedPositioner_X][i];
        }
        for (int i=0; i<yNumPoints; i++){
        	yGrid[i]=y2D[selectedPositioner_Y][i];
        }
        
        for (int i = 0; i < xNumPoints; i++) {
            for (int j = 0; j < yNumPoints; j++) {
                zValues[i][j] = z2D[selectedDetector][i][j];
                
                if (intCutoff) {
                    if (zValues[i][j] > topCutoff) {
                        zValues[i][j] = topCutoff;
                    }

                    if (zValues[i][j] < botCutoff) {
                        zValues[i][j] = botCutoff;
                    }
                }
                
            }
        }
        chart3d.setBatched(false);
        fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_XGRID,
                             new JCData3dGridIndex());
        fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_YGRID,
                             new JCData3dGridIndex());
        fireChart3dDataEvent(Chart3dGridDataEvent.RESET, new JCData3dGridIndex());
    }
}
