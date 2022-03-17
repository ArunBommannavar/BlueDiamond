package bluediamond2;

import java.io.DataInputStream;
import java.io.IOException;

public class MdaDetector {
	  int detNum;
	  String detName = "";
	  String detDesc = "";
	  String detUnit = "";
	  int numPoints;
	  double[] data;

	  public MdaDetector() {
		  
	  }

	  public void setDetNum(int n) {
	    detNum = n;

	  }

	  public int getDetNum() {
	    return detNum;
	  }

	  public void setNumPoints(int n) {
	    numPoints = n;
	    data = new double[n];
	  }

	  public double getNumPoints() {
	    return numPoints;
	  }

	  public double[] getData() {
	    return data;
	  }

	  public void setDetName(String str) {
	    detName = str;
	  }

	  public String getDetName() {
	    return detName;
	  }

	  public void setDetDesc(String str) {
	    detDesc = str;
	  }

	  public String getDetDesc() {
	    return detDesc;
	  }

	  public void setDetUnit(String str) {
	    detUnit = str;

	  }

	  public String getDetUnit() {
	    return detUnit;
	  }

	  public void readPVInfo(DataInputStream inData) {

	    int size;
	    int asize;
	    byte[] tmpBytes;
	    String str;

	    byte[] extBytes;
	    int extSize;
	    int diffSize;

	    try {

	      int tmp = inData.readInt();

	      setDetNum(tmp);

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	        str = new String(tmpBytes);
	        str = str.trim();
	        setDetName(str);

	        // setDetName(CountedString.getCountedString(inData));

	      }
	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);
//	        tmpBytes = new byte[asize];
//	        inData.read(tmpBytes);

	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	        str = new String(tmpBytes);
	        str = str.trim();
	        setDetDesc(str);

	        // setDetDesc(CountedString.getCountedString(inData));

	      }
	      size = inData.readInt();

	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);
//	        tmpBytes = new byte[asize];
//	        inData.read(tmpBytes);

	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);


	        str = new String(tmpBytes);
	        str = str.trim();
	        setDetUnit(str);
	      }

	    }
	    catch (IOException e) {

	    }
	  }

	  public void readData(DataInputStream in) {
	    float f;
	    try {
	      for (int i = 0; i < numPoints; i++) {
	        f = in.readFloat();

	        data[i] = (double) f;
	   //      System.out.println(" Detector  "+i+"   "+data[i]);
	      }
	    }
	    catch (IOException e) {

	    }
	  }
}
