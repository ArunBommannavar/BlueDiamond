package bluediamond2;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadSavedMdaData {

	  File inFile;
	  int dataRank;
	  float version;
	  int scanNumber;
	  String fileName = null;
	  int isRegular;
	  int extraPointer;
	  int[] dims;
	  ByteArrayInputStream inBytes;
	  DataInputStream inData;
	  byte[] fileBytes;
	  ExtraPV[] extraPV;
	  MdaData[] mdaData;

	  public ReadSavedMdaData() {
	  }
/*
	  public static ReadSavedMdaData getInstance() {
	    return readSavedMdaData;
	  }
*/
	  public void setFileName(String str) {
	    fileName = str;
	    inFile = new File(fileName);

	  }

	  public void setFile(File fl) {
	    inFile = fl;

	  }

	  public String getFileName() {
	    return fileName;
	  }

	  public int getRank() {
	    return dataRank;
	  }

	  public int getRegular(){
	    return isRegular;
	  }
	  public int getNumPoints(int n) {
	    return mdaData[n].getNumPoints();
	  }

	  public int getCurrentPoint(int n) {
	    return mdaData[n].getCurrentPoint();
	  }

	  public int getNumPos(int n) {
	    return mdaData[n].getNumPos();
	  }

	  public int getNumDets(int n) {
	    return mdaData[n].getNumDets();
	  }

	  public String[] getPosName(int n){
	    return  mdaData[n].getPosName();
	  }

	  public String[] getDetName(int n){
	    return mdaData[n].getDetName();
	  }

	  public String[] getPosDesc(int n){
	    return mdaData[n].getPosDesc();
	  }

	  public String[] getDetDesc(int n){
	    return mdaData[n].getDetDesc();
	  }

	  public double[][] getPosData(int n) {
	    return mdaData[n].getPosData();
	  }

	  public double[][] getDetsData(int n) {
	    return mdaData[n].getDetData();
	  }


	  public int[] getDims(){
	    return dims;

	  }
	  public void readFileHeader() {

	  }


	  public void readMdaData() {

	    int size;
	    byte[] bytes;
	    String str;
	    int scanRank;
	    int scanNumPoints;
	    int scanCurrentNumPoint;
	    int[] xlc;
	    int tmpInt;
//	    int nminus;

	    int asize;
	    byte[] extBytes;
	    int extSize;
	    int diffSize;

	    try {
	      fileBytes = getBytesFromFile(inFile);

	      inBytes = new ByteArrayInputStream(fileBytes);
	      inData = new DataInputStream(inBytes);

	      version = inData.readFloat();
	      scanNumber = inData.readInt();
	      dataRank = inData.readInt();
	      dims = new int[dataRank];
	      int numScans = 1;
	      for (int i0 = 0; i0 < dataRank; i0++) {
	        dims[i0] = inData.readInt();
	        numScans = numScans * dims[i0];
	      }
	      numScans = numScans / dims[dataRank - 1];

	      isRegular = inData.readInt();
	      extraPointer = inData.readInt();

	      if (dataRank > 1) {
	        numScans = numScans + 1;
	      }
	      mdaData = new MdaData[numScans ];
	      for (int i = 0; i < numScans; i++) {

	        mdaData[i] = new MdaData();
	        scanRank = inData.readInt();
	        mdaData[i].setScanRank(scanRank);

	        scanNumPoints = inData.readInt();
	        scanCurrentNumPoint = inData.readInt();
	        mdaData[i].setNumPoints(scanNumPoints);
	        mdaData[i].setCurrentPoint(scanCurrentNumPoint);

	        /**
	         * INFO
	         */


	        if (scanRank > 1) {
	          xlc = new int[scanNumPoints];

	          for (int ik = 0; ik < scanNumPoints; ik++) {
	            xlc[ik] = inData.readInt();
	          }
	        }

	        size = inData.readInt();
	        /**
	         *  Read scan name
	         */

	        if (size > 0) {
	          asize = inData.readInt();
	          extSize = CountedString.getCorrectedByteSize(asize);
	          diffSize = extSize-asize;
	          System.out.println(" Asize = "+asize);
	          bytes = new byte[asize];
	          inData.read(bytes);
	          str = new String(bytes);

	          str = str.trim();
	          mdaData[i].setScanName(str);
	          extBytes = new byte[diffSize];
	          inData.read(extBytes);

	        }

	        /**
	         * Read Time stamp
	         */
	        size = inData.readInt();
	        if (size > 0) {

	          asize = inData.readInt();
	          extSize = CountedString.getCorrectedByteSize(asize);
	          diffSize = extSize-asize;
	          bytes = new byte[asize];
	          inData.read(bytes);
	          str = new String(bytes);
	          str = str.trim();
	          mdaData[i].setTimeStamp(str);
	          extBytes = new byte[diffSize];
	          inData.read(extBytes);

	        }

	        tmpInt = inData.readInt();
	        mdaData[i].setNumPos(tmpInt);
	  //      System.out.println(" Number of positioners = "+tmpInt);

	        tmpInt = inData.readInt();
	        mdaData[i].setNumDets(tmpInt);
	  //      System.out.println(" Number of detectors = "+tmpInt);

	        tmpInt = inData.readInt();
	        mdaData[i].setNumTrigs(tmpInt);
	  //      System.out.println(" Number of triggers = "+tmpInt);

	        int numPos = mdaData[i].getNumPos();

	        for (int j = 0; j < numPos; j++) {
	          mdaData[i].readPosPVInfo(j, inData);
	        }

	        int numDets = mdaData[i].getNumDets();

	        for (int j = 0; j < numDets; j++) {
	          mdaData[i].readDetsPVInfo(j, inData);

	        }

	        int numTrigs = mdaData[i].getNumTrigs();

	        for (int j = 0; j < numTrigs; j++) {
	          mdaData[i].readTrigPVInfo(j, inData);

	        }

	        for (int j = 0; j < numPos; j++) {

	          mdaData[i].readPosData(j, inData);
	        }

	        for (int j = 0; j < numDets; j++) {
	          mdaData[i].readDetData(j, inData);
	        }
	       }

	      int numExtraPV = inData.readInt();
	      if (numExtraPV > 0) {
	        extraPV = new ExtraPV[numExtraPV];

	        for (int i1 = 0; i1 < numExtraPV; i1++) {
	          extraPV[i1] = new ExtraPV();
	          extraPV[i1].readData(inData);
	        }
	      }
	    }
	    catch (IOException e) {

	    }
	  }

	  public byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();
	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	      // File is too large

	      System.out.println(" File Length = " + length);
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[ (int) length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           &&
	           (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	      offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	      throw new IOException("Could not completely read file " +
	                            file.getName());
	     
	    
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	  }
	
}
