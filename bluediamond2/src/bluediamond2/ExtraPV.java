package bluediamond2;

import java.io.DataInputStream;
import java.io.IOException;

public class ExtraPV {

    String pvName;
    String pvDesc;
    int pvType;
    int size;
    int count;
    String unit;
    String string_value;

    int[] long_Value;
    byte[] char_Value;
    double[] double_Value;
    float[] float_Value;
    int[] int_Value;

    final int DBR_STRING = 0;
    final int DBR_CTRL_STRING = 28;
    final int DBR_CTRL_SHORT = 29;
    final int DBR_CTRL_INT = DBR_CTRL_SHORT;
    final int DBR_CTRL_FLOAT = 30;
    final int DBR_CTRL_ENUM = 31;
    final int DBR_CTRL_CHAR = 32;
    final int DBR_CTRL_LONG = 33;
    final int DBR_CTRL_DOUBLE = 34;
    boolean print = false;

    public ExtraPV() {

    }

    public String getPvName() {
        return pvName;
    }

    public String getPvDesc(){
        return pvDesc;
    }

    public void readData(DataInputStream in) {

        try {
            size = in.readInt();
            if (size > 0) {
                pvName = CountedString.getCountedString(in);

       //         System.out.println(" Extra PV Name = "+pvName);
            }
            size = in.readInt();
            if (size > 0) {
                pvDesc = CountedString.getCountedString(in);
            }

            pvType = in.readInt();
 //           System.out.println(" Extra PV type = "+pvType);
            if (pvType != 0) {
            }

            switch (pvType) {
            case DBR_STRING:
                size = in.readInt();
                if (size > 0) {
                    string_value = CountedString.getCountedString(in);
                }
                break;
/*
            case DBR_CTRL_CHAR:
                count = in.readInt();
                System.out.println("Extra Count = "+count);
                char_Value = new byte[count];
                for (int i = 0; i < count; i++) {
                    char_Value[i] = in.readByte();
                    System.out.println("Extra char value =" + char_Value[i]);
                    System.out.println(" After CHAR read a byte "+in.re);
                }
                break;
                 */

            case DBR_CTRL_CHAR:
                count = in.readInt();
                char_Value = new byte[count];
             //   System.out.println("Extra Count available bytes before  = " + in.available()+" count = "+count);
                for (int i=0; i < count; i++){
                       char_Value[i] =in.readByte();
                     //  System.out.println("Extra char value =" +char_Value[i]+" Remaining bytes = "+in.available());

                }

                int mod = count % 8;
     //           System.out.println(" Mod = "+mod);

                int rem;
                if (mod > 0){
                    rem = 8-mod;

                    for (int i=0; i < rem; i++){
                        in.readByte();
                    }
                }else {
                    rem=0;
                }

        //        System.out.println(" Remaining bytes after CHAR = "+in.available());

                break;

            case DBR_CTRL_SHORT:
                count = in.readInt();
  //              System.out.println("Extra Count = " + count);

                size = in.readInt();
                if (size > 0) {
                    String unit = CountedString.getCountedString(in);
                    if (print)
                        System.out.println("Extra Unit=" + unit);
                }

                int_Value = new int[count];
                for (int i = 0; i < count; i++) {
                    int_Value[i] = in.readInt();
          //          System.out.println("Extra short value " + i + " =  " + int_Value[i]);
                }
                break;
            case DBR_CTRL_LONG:
                count = in.readInt();
       //         System.out.println("Extra Long Count = " + count);

                size = in.readInt();
                if (size > 0) {
                    String unit = CountedString.getCountedString(in);
                    if (print)
                        System.out.println("Extra Unit=" + unit);
                }

                long_Value = new int[count];
                for (int i = 0; i < count; i++) {
                    long_Value[i] = in.readInt();
               //     System.out.println("Extra long value " + i + " = " + long_Value[i]);
                }

                break;
            case DBR_CTRL_FLOAT:
                count = in.readInt();
         //       System.out.println("Extra Count = " + count);

                size = in.readInt();
                if (size > 0) {
                    String unit = CountedString.getCountedString(in);
                    if (print)
                        System.out.println("Extra Unit=" + unit);
                }

                float_Value = new float[count];
                for (int i = 0; i < count; i++) {
                    float_Value[i] = in.readFloat();
               //     System.out.println("Extra float value " + i + " = " + float_Value[i]);
                }

                break;

             case DBR_CTRL_ENUM:
                  count = in.readInt();
     //              System.out.println("Extra Enum Count = " + count);
                   size = in.readInt();
     //              System.out.println("Extra Enum Size = " + size);

                 break;


            case DBR_CTRL_DOUBLE:
                count = in.readInt();
                size = in.readInt();
                /*
                if (size > 0) {
                    String unit = CountedString.getCountedString(in);
                }
*/
                double_Value = new double[count];
                for (int i = 0; i < count; i++) {
                    double_Value[i] = in.readDouble();
                }

                break;
            }

        } catch (IOException e) {

        }
    }

    public byte getCountedChar(DataInputStream in){
        byte b = 0;
//        int i;

        return b;

    }
}
