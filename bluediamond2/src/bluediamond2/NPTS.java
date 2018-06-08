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

public class NPTS implements MonitorListener {

	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	int val;

	String changePropertyName;

	public NPTS(String str, Context context) {
		this.context = context;
		pvName = str;
	}

	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
			context.pendIO(3.0);

		} catch (IllegalArgumentException | IllegalStateException | CAException e) {

			e.printStackTrace();
		} catch (TimeoutException e) {

			e.printStackTrace();
		}
	}

	public void setMonitor() {
		try {
			monitor = channel.addMonitor(Monitor.VALUE, this);
			context.flushIO();

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnectChannel() {
		try {
			if (monitor != null)
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

	public int getVal() {
		return val;
	}

	public void putVal(String str) {
		try {
			channel.put(str);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void putVal(int n) {
		try {
			channel.put(n);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = ((DBR_Int) convert).getIntValue()[0];
			
		} else
			System.err.println("Monitor error: " + event.getStatus() + "  PV = " + pvName);
	}

}
