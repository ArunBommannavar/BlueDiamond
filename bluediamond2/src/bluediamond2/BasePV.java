package bluediamond2;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;

public class BasePV {
	Context context;
	Channel channel = null;
	String pvName;
	public BasePV(String str,Context context) {
		this.context = context;
		pvName = str;
	}
	
	public BasePV(Context context) {
		this.context = context;
	}
		
	public void setPvName(String str) {
		pvName = str;
	}
	
	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
 
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			System.out.println("createChannel PV Name = "+pvName+"  createChannel IllegalArgumentException | IllegalStateException | CAException");
			e.printStackTrace();
		}
	}
}
