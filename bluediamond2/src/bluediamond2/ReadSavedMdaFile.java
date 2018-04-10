package bluediamond2;

import java.io.File;

import com.klg.jclass.chart.JCChart;


public class ReadSavedMdaFile {

    File inFile;
    int dataRank;
    int scanNumber;
    String fileName = null;
    int totalNumberOfScans;
    int dataViewNum = 0;
    long lastModified;
    int posPrecision = 4;

    int numXPoints;
    int numCurrentXPoint;
    int numXPos;

    int numYPoints;
    int numCurrentYPoint;
    int numYPos;

    int numDets;

    String[] posXName;
    String[] posXDesc;

    double[] posXMin;
    double[] posXMax;

    String[] detName;
    String[] detDesc;
    double[] detMin;
    double[] detMax;

    String[] posYName;
    String[] posYDesc;

    double[] posYMin;
    double[] posYMax;
    double[][] xVal;
    double[][] yVal;
    double[][][] zVal;

    OldData1D oldData1D;// = new OldData1D();

    private final double HOLE_VALUE = Double.MAX_VALUE;
    
    ReadSavedMdaData rmd = new ReadSavedMdaData();
    
    private static ReadSavedMdaFile readSavedMdaFile = new ReadSavedMdaFile();
    Saved_1D_ScanPanel saved_1D_ScanPanel;
    private ReadSavedMdaFile() {
    	
    }
    public static ReadSavedMdaFile getInstance() {
        return readSavedMdaFile;
    }

    
    public void setFile(File file, JCChart oldChart,Saved_1D_ScanPanel saved_1D_ScanPanel) {
    	this.saved_1D_ScanPanel = saved_1D_ScanPanel;
        inFile = file;
        fileName = file.getName();
        lastModified = file.lastModified();
        rmd.setFile(inFile);
        rmd.readMdaData();
        dataRank = rmd.getRank();
        double[][] temp;
        String tempString;
        int[] dims = new int[2];

        if (dataRank == 1) {
        	oldData1D = new OldData1D(oldChart);
        	oldData1D.setNumPoints(rmd.getNumPoints(0));
//           numXPoints = rmd.getNumPoints(0);
//           numCurrentXPoint = rmd.getCurrentPoint(0);
            
            oldData1D.setNumberOfCurrentPoints(rmd.getCurrentPoint(0));
            
//            numXPos = rmd.getNumPos(0);
//            numDets = rmd.getNumDets(0);

            oldData1D.setNumPositioners(rmd.getNumPos(0));
            oldData1D.setNumberOfDetectors(rmd.getNumDets(0));
            
            posXName = new String[numXPos];
            posXDesc = new String[numXPos];

            detName = new String[numDets];
            detDesc = new String[numDets];

            posXName = rmd.getPosName(0);
            posXDesc = rmd.getPosDesc(0);
            detName = rmd.getDetName(0);
            detDesc = rmd.getDetDesc(0);

            xVal = new double[numXPos][numCurrentXPoint];
            yVal = new double[numDets][numCurrentXPoint];

            xVal = rmd.getPosData(0);
            yVal = rmd.getDetsData(0);
//            posMinMax1D();
//            populate1DimScanData();
        } else if (dataRank == 2) {
            numCurrentYPoint = rmd.getCurrentPoint(0);
            /**
             * dims[0] = outer loop numPoints
             * dims[1] = inner loop numPoints
             */

            if (rmd.getCurrentPoint(0) > 1) {
                dims = rmd.getDims();
                numYPoints = dims[0];
                numXPoints = dims[1];

                numYPos = rmd.getNumPos(0);
                numXPos = rmd.getNumPos(1);
                numDets = rmd.getNumDets(1);

                posXName = new String[numXPos];
                posXDesc = new String[numXPos];

                posYName = new String[numYPos];
                posYDesc = new String[numYPos];

                detName = new String[numDets];
                detDesc = new String[numDets];

                posXName = rmd.getPosName(1);
                posXDesc = rmd.getPosDesc(1);

                posYName = rmd.getPosName(0);
                posYDesc = rmd.getPosDesc(0);

                detName = rmd.getDetName(1);
                detDesc = rmd.getDetDesc(1);

                for (int i = 0; i < 60; i++) {
                    if (i < numDets) {
//                        scan1Panel.setDetValid(i);
                        tempString = detDesc[i].trim();
                        if (tempString.equals("")) {
                            tempString = detName[i].trim();
                        }
//                        scan1Panel.setDetName(tempString, i);
                    } else {
 //                       scan1Panel.setDetInvalid(i);
                    }
                }

                for (int i = 0; i < 4; i++) {
                    if (i < numYPos) {
//                        scan1Panel.setPosYValid(i);
                        tempString = posYDesc[i].trim();
                        if (tempString.equals("")) {
                            tempString = posYName[i].trim();
                        }

//                        scan1Panel.setPosYName(tempString, i);
                    } else {
//                        scan1Panel.setPosYInvalid(i);
                    }
                }

                for (int i = 0; i < 4; i++) {
                    if (i < numXPos) {
//                        scan1Panel.setPosXValid(i);
                        tempString = posXDesc[i].trim();
                        if (tempString.equals("")) {
                            tempString = posXName[i].trim();
                        }
//                        scan1Panel.setPosXName(tempString, i);
                    } else {
//                        scan1Panel.setPosXInvalid(i);
                    }
                }

                /**
                 * Initialize the x , y and z values
                 */
                xVal = new double[numXPos][numXPoints];
                yVal = new double[numYPos][numCurrentYPoint];
                zVal = new double[numDets][numXPoints][numCurrentYPoint];

                xVal = rmd.getPosData(1);

                yVal = rmd.getPosData(0);
                for (int i = 0; i < numDets; i++) {
                    for (int j = 0; j < numXPoints; j++) {
                        for (int k = 0; k < numCurrentYPoint; k++) {
                            zVal[i][j][k] = HOLE_VALUE;
                        }
                    }
                }
                /**
                 * Here we need to be sure that we are only reading
                 * current data.
                 */

                int tempXpts;

                //           for (int k=0; k < numYPoints; k++){
                for (int k = 0; k < numCurrentYPoint; k++) {

                    tempXpts = rmd.getCurrentPoint(k + 1);
                    temp = new double[numDets][tempXpts];
                    temp = rmd.getDetsData(k + 1);

                    for (int i = 0; i < numDets; i++) {
                        for (int j = 0; j < tempXpts; j++) {
                            zVal[i][j][k] = temp[i][j];
                        }
                    }
                }

                /**
                 *
                 * Now we have read all data values, number of X,Y positioners
                 * and their names and values.
                 */
                //       data3d.setXNumPoints(numXPoints);
                //       data3d.setYNumPoints(numYPoints);
                //       data3d.setNumDets(numDets);
                //           data3d.initData(numXPoints, numYPoints, numXPos, numYPos, numDets);

//                data3d.setCurrentScanData(false);
//                data3d.initData(numXPoints, numCurrentYPoint, numXPos, numYPos,numDets);
//                data3d.readXPosdata(xVal);
//                data3d.readYPosdata(yVal);
//                data3d.readMdaData(zVal, numDets, numXPoints, numCurrentYPoint);
//                data3d.setDefault1DPositioner(0);
//                data3d.setDefault2DPositioner(0);
//                data3d.setDefaultDetector(0);
//                data3d.reScale();
//                data3d.setFooterString(fileName, lastModified);

            }
        }
        //     save2DAscii_format2();

    }
    
    private void posMinMax1D() {

        posXMin = new double[numXPos];
        posXMax = new double[numXPos];

        detMin = new double[numDets];
        detMax = new double[numDets];

        for (int i = 0; i < numXPos; i++) {
            posXMin[i] = xVal[i][0];
            posXMax[i] = xVal[i][0];

            for (int j = 1; j < numXPoints; j++) {
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

        for (int i = 0; i < numDets; i++) {
            detMin[i] = yVal[i][0];
            detMax[i] = yVal[i][0];

            for (int j = 1; j < numXPoints; j++) {
                if (yVal[i][j] < detMin[i]) {
                    detMin[i] = yVal[i][j];
                }
                if (yVal[i][j] > detMax[i]) {
                    detMax[i] = yVal[i][j];
                }
            }
        }
    }
    public double getPrecisionedData(double d1) {
        double d = Math.floor(d1 * Math.pow(10, posPrecision) + 0.5);
        d = d / Math.pow(10, posPrecision);
        return d;
    }
    /*
    public void populate1DimScanData() {
       if (!oldFileFrame.isListed(inFile.getName())) {
            oldFileFrame.addNewFile(inFile, chart, data, dataViewNum);
            oldFileFrame.setPosName(posXName);
            oldFileFrame.setPosDesc(posXDesc);
            oldFileFrame.setDetName(detName);
            oldFileFrame.setDetDesc(detDesc);

            oldFileFrame.setPosMin(posXMin);
            oldFileFrame.setPosMax(posXMax);
            oldFileFrame.setDetMin(detMin);
            oldFileFrame.setDetMax(detMax);

//            oldFileFrame.setVisible(true);
            oldFileFrame.populatePanel();

            ods = new OldOneDimDataSource(chart, inFile.getName(), dataViewNum);

            ods.setDataViewNumb(dataViewNum);

            chart.addDataView(dataViewNum);
            chart.getDataView(dataViewNum).setDataSource(ods);

            // Set number of points, positioners and detectors
            ods.setNumPoints(numCurrentXPoint);
            ods.setNumPositioners(numXPos);
            ods.setNumberOfDetectors(numDets);
            // Initialize the arrays.
            ods.initArrays();
            // set names for positioners and detectors
            ods.setPosName(posXName);
            ods.setPosDesc(posXDesc);
            ods.setDetName(detName);
            ods.setDetDesc(detDesc);

            // Now fill up the X and Y values
            ods.setXVals(xVal);
            ods.setYVals(yVal);
            // set the min and max values for positioners
            ods.setPosMinMax();
            ods.displayDets();
            ods.setDerivative(data.getDerivative());

            dataViewNum++;

        }*/ 
    /*
    public void populate1DimScanData() {
    if (!saved_1D_ScanPanel.isListed(inFile.getName())) {
         saved_1D_ScanPanel.addNewFile(inFile, chart, data, dataViewNum);
         saved_1D_ScanPanel.setPosName(posXName);
         saved_1D_ScanPanel.setPosDesc(posXDesc);
         saved_1D_ScanPanel.setDetName(detName);
         saved_1D_ScanPanel.setDetDesc(detDesc);

         saved_1D_ScanPanel.setPosMin(posXMin);
         saved_1D_ScanPanel.setPosMax(posXMax);
         saved_1D_ScanPanel.setDetMin(detMin);
         saved_1D_ScanPanel.setDetMax(detMax);

//         saved_1D_ScanPanel.setVisible(true);
         saved_1D_ScanPanel.populatePanel();

         ods = new OldOneDimDataSource(chart, inFile.getName(), dataViewNum);

         ods.setDataViewNumb(dataViewNum);

         chart.addDataView(dataViewNum);
         chart.getDataView(dataViewNum).setDataSource(ods);

         // Set number of points, positioners and detectors
         ods.setNumPoints(numCurrentXPoint);
         ods.setNumPositioners(numXPos);
         ods.setNumberOfDetectors(numDets);
         // Initialize the arrays.
         ods.initArrays();
         // set names for positioners and detectors
         ods.setPosName(posXName);
         ods.setPosDesc(posXDesc);
         ods.setDetName(detName);
         ods.setDetDesc(detDesc);

         // Now fill up the X and Y values
         ods.setXVals(xVal);
         ods.setYVals(yVal);
         // set the min and max values for positioners
         ods.setPosMinMax();
         ods.displayDets();
         ods.setDerivative(data.getDerivative());

         dataViewNum++;

     }
    */
    }


