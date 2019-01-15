package bluediamond2;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.STRING;

public class PVDescription1 {

	Context context;
	Channel channel = null;
	String firstPart;
	String secondPart;
	String pvName;

	public PVDescription1(String first, String second, Context context) {
		this.context = context;
		this.firstPart = first;
		this.secondPart = second;

	}
	
	public void makeMotorEpicsObject() {

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
	}
	
	public void makeAiEpicsObject() {

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
	}
	public void makeA0EpicsObject() {
		
		secondPart = ".DESC";
		pvName = firstPart +secondPart;
		
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
	
	public void makeScalerEpicsObject() {
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

	}
	
	public void makeTransformEpicsDataObject() {
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
	
	public void makeMcaEpicsDataObject() {
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


}
