package bluediamond2;

import java.io.DataInputStream;

public class MdaData {

	  /**
	   * Header
	   */
	  int scanRank;
	  int numPoints;
	  int currentPoint = -1;

	  /**
	   * INFO
	   */
	  String scanName = "";
	  String timeStamp;

	  int numPos;
	  int numDets;
	  int numTrigs;

	  MdaPositioner[] pos;
	  MdaDetector[] det;
	  MdaTrigger[] trig;

	  /**
	   * DATA
	   * For each positioner and for each Detector, hence array of array data
	   */
	  double[][] posData;
	  double[][] detData;

	  public MdaData() {
	  }

	  public void setScanRank(int n) {
	    scanRank = n;
	  }

	  public int getScanRank() {
	    return scanRank;
	  }

	  public void setNumPoints(int n) {
	    numPoints = n;
	  }

	  public void setCurrentPoint(int n) {
	    currentPoint = n;
	  }

	  public void setScanName(String str) {
	    scanName = str;
	  }

	  public String getScanName() {
	    return scanName;
	  }

	  public void setTimeStamp(String str) {
	    timeStamp = str;
	  }

	  public String getTimeStamp() {
	    return timeStamp;
	  }

	  public void setNumPos(int n) {
	    numPos = n;

	    pos = new MdaPositioner[numPos];
	    for (int i=0; i < numPos; i++){
	      pos[i] = new MdaPositioner();
	      pos[i].setNumPoints(numPoints);
	    }
	    posData = new double[numPos][numPoints];
	  }

	  public int getNumPos() {
	    return numPos;
	  }

	  public String[] getPosName(){
	    String[] str = new String[numPos];
	    for (int i=0; i < numPos; i++){
	      str[i] = pos[i].getPosName();
	    }
	    return str;
	  }

	  public String[] getPosDesc(){
	    String[] str = new String[numPos];
	    for (int i=0; i < numPos; i++){
	      str[i] = pos[i].getPosDesc();
	    }


	    return str;
	  }

	  public void setNumDets(int n) {
	    numDets = n;


	    det = new MdaDetector[numDets];
	    for (int i=0; i < numDets; i++){
	      det[i] = new MdaDetector();
	      det[i].setNumPoints(numPoints);
	    }
	    detData = new double[numDets][numPoints];
	  }

	  public int getNumDets() {
	    return numDets;
	  }

	  public String[] getDetName(){
	    String[] str = new String[numDets];
	    for (int i=0; i < numDets; i++){
	      str[i] = det[i].getDetName();
	    }
	    return str;
	  }

	  public String[] getDetDesc(){
	  String[] str = new String[numDets];
	  for (int i=0; i < numDets; i++){
	    str[i] = det[i].getDetDesc();
	  }
	  return str;
	}

	public double[][] getPosData() {
	  for (int i=0; i<numPos;i++){
	  posData[i]=pos[i].getData();
	  }
	return posData;
	}

	public double[][] getDetData() {
	  for (int i=0; i<numDets;i++){
	  detData[i]=det[i].getData();
	  }
	return detData;
	}


	  public void setNumTrigs(int n) {
	    numTrigs = n;
	    trig = new MdaTrigger[numTrigs];
	    for (int i=0; i < numTrigs;i++){
	      trig[i] = new MdaTrigger();
	    }
	  }

	  public int getNumTrigs() {
	    return numTrigs;
	  }

	  public int getNumPoints() {
	    return numPoints;
	  }

	  public int getCurrentPoint() {
	    return currentPoint;
	  }

	  public double[][] getDetsData() {
	    return detData;
	  }

	  public void readPosPVInfo(int i, DataInputStream in) {
	    pos[i].readPVInfo(in);
	  }

	  public void readDetsPVInfo(int i, DataInputStream in) {
	    det[i].readPVInfo(in);
	  }

	  public void readTrigPVInfo(int i, DataInputStream in) {
	    trig[i].readPVInfo(in);
	  }

	  public void readPosData(int i, DataInputStream in) {

	     pos[i].readData(in);
	  }

	  public void readDetData(int i, DataInputStream in) {
	    det[i].readData(in);
	  }

}
