package bluediamond2;

import java.io.DataInputStream;
import java.io.IOException;

public class MdaPositioner {
	  int posNum;
	  String posName = "";
	  String posDesc = "";
	  String posStepMode="";
	  String posUnit="";
	  String posReadbackName="";
	  String posReadbackDesc="";
	  String posReadbackUnit="";
	  int numPoints;
	  double[] data;

	  public MdaPositioner() {
	  }

	  public void setPosNum(int n) {
	    posNum = n;
	  }

	  public int getPosNum() {
	    return posNum;
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

	  public void setPosName(String str) {
	    posName = str;
	  }

	  public String getPosName() {
	    return posName;
	  }

	  public void setPosDesc(String str) {
	    posDesc = str;
	  }

	  public String getPosDesc() {
	    return posDesc;
	  }

	  public void setPosStepMode(String str) {
	    posStepMode = str;
	  }

	  public String getPosStepMode() {
	    return posStepMode;
	  }

	  public void setPosUnit(String str) {
	    posUnit = str;
	  }

	  public String getPosUnit() {
	    return posUnit;
	  }

	  public void setPosReadbackName(String str) {
	    posReadbackName = str;
	  }

	  public String getPosReadbackName() {
	    return posReadbackName;
	  }

	  public void setPosReadbackDesc(String str) {
	    posReadbackDesc = str;
	  }

	  public String getPosReadbackDesc() {
	    return posReadbackDesc;
	  }

	  public void setPosReadbackUnit(String str) {
	    posReadbackUnit = str;
	  }

	  public String getPosReadbackUnit() {
	    return posReadbackUnit;
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

	      setPosNum(tmp);

	      size = inData.readInt();
	      if (size > 0) {

	        str = CountedString.getCountedString(inData);
	             setPosName(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);
	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;
	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	         str = new String(tmpBytes);
	         str = str.trim();

	        setPosDesc(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();

//	        asize = CountedString.getCorrectedByteSize(asize);
	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	        str = new String(tmpBytes);
	        str = str.trim();
	        setPosStepMode(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);
	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);


	        str = new String(tmpBytes);
	        str = str.trim();
	        setPosUnit(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);

	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	        str = new String(tmpBytes);
	        str = str.trim();
	        setPosReadbackName(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);

	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);

	        str = new String(tmpBytes);
	        str = str.trim();
	        setPosReadbackDesc(str);
	      }

	      size = inData.readInt();
	      if (size > 0) {
	        asize = inData.readInt();
//	        asize = CountedString.getCorrectedByteSize(asize);

	        extSize = CountedString.getCorrectedByteSize(asize);
	        diffSize = extSize-asize;

	        tmpBytes = new byte[asize];
	        extBytes = new byte[diffSize];
	        inData.read(tmpBytes);
	        inData.read(extBytes);


	        str = new String(tmpBytes);
	        str = str.trim();
	        setPosReadbackUnit(str);
	      }

	    }
	    catch (IOException e) {

	    }
	  }

	  public void readData(DataInputStream in) {

	    try {
	      for (int i = 0; i < numPoints; i++) {
	        data[i] = in.readDouble();

	      }
	    }
	    catch (IOException e) {

	    }
	  }
}
