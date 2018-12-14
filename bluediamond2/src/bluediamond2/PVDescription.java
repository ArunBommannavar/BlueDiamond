package bluediamond2;

import javax.swing.JCheckBox;


import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.STRING;

public class PVDescription{

	Context context;
	Channel channel = null;
	String firstPart;
	String secondPart;
	String pvName;
	String recordType;
	JCheckBox jCheckBox;

	public PVDescription(String str1, String str2, String rc, JCheckBox jc,Context context) {
    	this.context = context;
		firstPart = str1;
		secondPart = str2;
		recordType = rc;
		jCheckBox = jc;
	}

	public void makeEpicsDataObject() {

		
		if (recordType.equals("motor")) {
			pvName = firstPart + ".DESC";
			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
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
			String str = secondPart.replace("S", "NM");
			pvName = firstPart + "." + str;

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
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
			String str = "."+secondPart+"NM";
			pvName = firstPart+str;

			try {
				channel = context.createChannel(pvName);
				context.pendIO(1.0);
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
	}


	public void disconnectChannel() {		
		
		try {
			if (channel!=null)
			channel.destroy();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

}

	public String getDescription() {
		String ret = " ";
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

		return ret;
	}

}