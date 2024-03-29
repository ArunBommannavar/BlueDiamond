package bluediamond2;

import javax.swing.JCheckBox;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.STRING;

public class PVDescription {

	Context context;
	Channel channel = null;
	String firstPart;
	String secondPart;
	String pvName;
	String recordType;
	boolean recordTypeFound = false;

	public PVDescription(Context context) {
		this.context = context;
	}

	public void makeEpicsDataObject() {

		if (recordType.equals("motor")) {
			pvName = firstPart + ".DESC";
			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (recordType.equals("ao")) {
			pvName = firstPart + ".DESC";

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (recordType.equals("ai")) {
			pvName = firstPart + ".DESC";

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (recordType.equals("scaler")) {
			
			
			if(secondPart.endsWith("T")) {				
				pvName = firstPart + "." + "NM1";

			} else {	
				String str = secondPart.replace("S", "NM");
				pvName = firstPart + "." + str;
			}			
			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (recordType.equals("transform")) {
			String str = ".CMT" + secondPart;
			pvName = firstPart + str;
			
			

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (recordType.equals("mca")) {
			String str = "." + secondPart + "NM";
			pvName = firstPart + str;

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
				recordTypeFound = true;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String temp = firstPart+secondPart;
			System.out.println(" Diagnostics: No Description found for PV = "+temp+"    RTYP = "+recordType);
		}
	}

	public void disconnectChannel() {

		try {
			if (channel != null)
				channel.destroy();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDescription(String str1, String str2, String rc) {

		firstPart = str1;
		secondPart = str2;
		recordType = rc;

		String ret0 = str1+str2;
		String ret= ret0;
//		System.out.println(" PV Description routine :"+str1+"  "+str2+ "   "+ret+  " Record Type = "+rc);
		
		makeEpicsDataObject();
		
		if (recordTypeFound) {
			try {
				DBR dbr = channel.get();
				context.pendIO(1.0);
				dbr = channel.get(DBRType.STRING, 1);
				context.pendIO(1.0);
				ret = ((STRING) dbr).getStringValue()[0];

			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		disconnectChannel();		
//		System.out.println(" Return value = "+ret+" of length = "+ret.length());
		if (ret.length() < 1) ret = ret0;
		
		return ret;
	}

}