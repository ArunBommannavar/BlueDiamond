package bluediamond2;

import com.klg.jclass.chart3d.Chart3dDataView;
import com.klg.jclass.chart3d.JCData3dGridIndex;
import com.klg.jclass.chart3d.data.JCDefault3dGridDataSource;
import com.klg.jclass.chart3d.event.Chart3dGridDataEvent;
import com.klg.jclass.chart3d.j2d.JCChart3dJava2d;

public class OldData2D extends JCDefault3dGridDataSource {

	JCChart3dJava2d chart3d = null;
	Chart3dDataView dataView = null;

	int numberOfXPositioners = 4;
	int numberOfYPositioners = 4;
	int numberOfDetectors = 60;
	int xNumPoints = 100;
	int yNumPoints = 100;
	int selectedPositioner_X = 0;
	int selectedPositioner_Y = 0;
	int selectedDetector = 0;

	double[][] x2D;
	double[][] y2D;
	double[][][] z2D;
	double[][] dets = new double[1][1];

	public OldData2D(JCChart3dJava2d chart3d) {
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

	public double getXValue(int n) {
		double d = xGrid[n];
		return d;
	}

	public double getYValue(int n) {
		double d = yGrid[n];
		return d;
	}

	public double getZValue(int n, int m) {
		return zValues[n][m];
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
	}

	public void initData(int numx, int numy, int numxpos, int numypos, int numdet) {

		xNumPoints = numx;
		yNumPoints = numy;
//		currentPoint = yNumPoints;
		numberOfXPositioners = numxpos;
		numberOfYPositioners = numypos;
		numberOfDetectors = numdet;

		x2D = null;
		y2D = null;
		z2D = null;

		x2D = new double[numberOfXPositioners][xNumPoints];
		y2D = new double[numberOfYPositioners][yNumPoints];
		z2D = new double[numberOfDetectors][xNumPoints][yNumPoints];

		xGrid = new double[xNumPoints];
		yGrid = new double[yNumPoints];
		zValues = new double[xGrid.length][yGrid.length];

		for (int j = 0; j < numberOfDetectors; j++) {
			for (int i = 0; i < xNumPoints; i++) {
				for (int k = 0; k < yNumPoints; k++) {
					z2D[j][i][k] = this.holeValue;
				}
			}
		}

	}

	public void setXPosdata(double[][] da) {

		for (int i = 0; i < numberOfXPositioners; i++) {
			for (int j = 0; j < xNumPoints; j++) {
				x2D[i][j] = da[i][j];
			}
		}
	}

	public void setYPosdata(double[][] da) {
		for (int i = 0; i < numberOfYPositioners; i++) {
			for (int j = 0; j < yNumPoints; j++) {
				y2D[i][j] = da[i][j];
			}
		}
	}

	public void setZData(double[][][] v, int a, int b, int c) {

		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				for (int k = 0; k < c; k++) {
					z2D[i][j][k] = v[i][j][k];
//					System.out.println(" Z-Value = "+z2D[i][j][k]);
				}
			}
		}
		// findMinMaxZvalue(yNumPoints);
	}

    public void reScale() {
        xGrid = new double[xNumPoints];
        for (int i = 0; i < xNumPoints; i++) {
            xGrid[i] = x2D[selectedPositioner_X][i];
        }
        yGrid = new double[yNumPoints];
        for (int i = 0; i < yNumPoints; i++) {
            yGrid[i] = y2D[selectedPositioner_Y][i];
        }
    }

    public void setDefault1DPositioner(int n) {
        setSelectedPositionerX(n);
    }

    public void setSelectedPositionerX(int n) {
        String str = "";
        selectedPositioner_X = n;
        chart3d.setBatched(true);

        for (int i = 0; i < xNumPoints; i++) {
            xGrid[i] = x2D[selectedPositioner_X][i];
        }
/*
        if (isCurrentScanData) {

            str = scan1Vars.getPosName(n);

        } else {
            str = readSavedData.getPosXDesc(n);
        }
*/
        chart3d.getChart3dArea().getXAxis().setTitle(str);
        chart3d.setBatched(false);
        fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_XGRID,
                             new JCData3dGridIndex());
    }

    
    public void setDefault2DPositioner(int n) {
        setSelectedPositionerY(n);
    }

    public void setSelectedPositionerY(int n) {
        String str = "";
        selectedPositioner_Y = n;
        chart3d.setBatched(true);

        for (int i = 0; i < yNumPoints; i++) {
            yGrid[i] = y2D[selectedPositioner_Y][i];
        }
        /*
        if (isCurrentScanData) {

            str = scan2Vars.getPosName(n);
        } else {
            str = readSavedData.getPosYDesc(n);
        }
        */
        chart3d.getChart3dArea().getYAxis().setTitle(str);
        chart3d.setBatched(false);
        fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_YGRID,
                             new JCData3dGridIndex());
    }

    public void setDefaultDetector(int n) {
        selectedDetector = n;
//		plotData();

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
		selectedDetector = det;
		plotData();
	}

	public void plotData() {
		
		chart3d.setBatched(true);
		for (int i = 0; i < xNumPoints; i++) {
			xGrid[i] = x2D[selectedPositioner_X][i];
		}
		for (int i = 0; i < yNumPoints; i++) {
			yGrid[i] = y2D[selectedPositioner_Y][i];
		}

		for (int i = 0; i < xNumPoints; i++) {
			for (int j = 0; j < yNumPoints; j++) {
				zValues[i][j] = z2D[selectedDetector][i][j];
				/*
				 * if (intCutoff) { if (zValues[i][j] > topCutoff) { zValues[i][j] = topCutoff;
				 * }
				 * 
				 * if (zValues[i][j] < botCutoff) { zValues[i][j] = botCutoff; } }
				 */
			}
		}
		chart3d.setBatched(false);
		fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_XGRID, new JCData3dGridIndex());
		fireChart3dDataEvent(Chart3dGridDataEvent.RELOAD_YGRID, new JCData3dGridIndex());
		fireChart3dDataEvent(Chart3dGridDataEvent.RESET, new JCData3dGridIndex());
	}

}
