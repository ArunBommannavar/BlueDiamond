package bluediamond2;

import java.io.DataInputStream;
import java.io.IOException;

public class MdaTrigger {
	
    int trigNum;
    String trigName="";
    float trigCommand;

    public MdaTrigger() {

    }

    public void setTrigNum(int n) {
        trigNum = n;

    }

    public void setTrigName(String str) {
        trigName = str;
    }

    public String getTrigName() {
        return trigName;
    }

    public void setTrigCommand(float f) {
        trigCommand = f;
    }

    public float getTrigCommand() {
        return trigCommand;
    }

    public void readPVInfo(DataInputStream inData) {

        int size;
        try {
            setTrigNum(inData.readInt());
      //      System.out.println(" Trig Num = "+trigNum);
            size = inData.readInt();
        //    System.out.println(" Trig size = "+size);
            if (size > 0) {
                setTrigName(CountedString.getCountedString(inData));
        //        System.out.println(" Trig Name = "+trigName);
            }
            setTrigCommand(inData.readFloat());
      //      System.out.println(" Trig Command = "+trigCommand);

        } catch (IOException e) {

        }
    }

}
