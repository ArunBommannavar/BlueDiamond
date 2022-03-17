package bluediamond2;

import java.io.DataInputStream;
import java.io.IOException;

public class CountedString {

	  public CountedString() {
		  
	  }

	  public static int getCorrectedByteSize(int n) {
	    int cor = 0;
	    int mod;
	    int rem;

	    int size = n;
	    mod = size % 4;
	    if (mod > 0) {
	      rem = 4 - mod;
	    }
	    else {
	      rem = 0;
	    }
	    cor = size + rem;
	    return cor;
	  }

	  public static String getCountedString(DataInputStream in) {
	    String str = "";

	    int size;
	    int mod;
	    int rem;
//	    int cor;
	    byte[] tmpByte;
//	    byte[] extBytes;
//	    int extSize;
//	    int diffSize;
//
	    

	    try {
	      size = in.readInt();
	      mod = size % 4;
	      if (mod > 0) {
	        rem = 4 - mod;
	      }
	      else {
	        rem = 0;
	      }
//	      cor = size + rem;
//	      tmpByte = new byte[cor];
	      tmpByte = new byte[size];
	      in.read(tmpByte);
	      str = new String(tmpByte).trim();

	      if (rem >0){
	        tmpByte = new byte[rem];
	        in.read(tmpByte);
	      }


	     }
	    catch (IOException e) {

	    }

	    return str;
	  }
}
