package bluediamond2;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.STRING;

public class PvRTYP {
	
	Context context;
	Channel rtypeChannel = null;
	String pvName;
	String recordType ="";

	public PvRTYP(String str, Context context) {
		this.context = context;
		this.pvName = str;		
	}
	
	public String getRtyp() {
		String firstPart = pvName;
		int lastIndexOfDot = pvName.lastIndexOf(".");
		if (lastIndexOfDot > 0) {		
			firstPart = pvName.substring(0, lastIndexOfDot);
		}

		String rtypePV = firstPart+".RTYP";
		
		try {
			rtypeChannel = context.createChannel(rtypePV);
			context.pendIO(1.0);
			DBR dbr = rtypeChannel.get(DBRType.STRING, 1);
			context.pendIO(1.0);
			recordType = ((STRING) dbr).getStringValue()[0];
			rtypeChannel.destroy();
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
			System.out.println(
					" TimeOutException PositionerPnPV " + pvName + "  PvRTYP class ");
			e.printStackTrace();
		}	

		return recordType;
		
	}	
	
}
