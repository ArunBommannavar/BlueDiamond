package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBR_Int;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class CPT implements MonitorListener {

	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	int val;
	
	public CPT(String str,Context context) {
		this.context = context;
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

	public void setMonitor() {
		try {
			monitor = channel.addMonitor(Monitor.VALUE, this);

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	
	public void disconnectChannel() {		
		try {
			if(monitor != null)
				monitor.removeMonitorListener(this);
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

	synchronized public int getValue() {
		return val;
	}

	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = ((DBR_Int) convert).getIntValue()[0];
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);
	}
}
