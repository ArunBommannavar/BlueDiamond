package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBR_Short;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ScanEXSC implements MonitorListener {
	
	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	int val;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
	String changePropertyName = " ";


	public ScanEXSC(String str,Context context){
		this.context = context;
		pvName = str;
	}
		
	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
 //           context.pendIO(3.0);
			
 
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			
			e.printStackTrace();
		}
	}

	public void setMonitor() {
		try {
			monitor = channel.addMonitor(Monitor.VALUE, this);
//			context.flushIO();

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

	public void addPropertyChangeListener(String str, PropertyChangeListener l) {
		changePropertyName = str;
		changes.addPropertyChangeListener(str, l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
	public void removePropertyChangeListener(String str, PropertyChangeListener l) {		
		changes.removePropertyChangeListener(str,l);
	}
	
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			short shortVal = ((DBR_Short) convert).getShortValue()[0];
			val = (int) shortVal;
			changes.firePropertyChange(changePropertyName, "-99", val);
		} else
			System.err.println("Monitor error: " + event.getStatus() + "  PV = " + pvName);
	}

}
