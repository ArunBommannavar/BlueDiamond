package bluediamond2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.DBR_Enum;
import gov.aps.jca.dbr.LABELS;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;


public class ScanDSTATE implements MonitorListener{
	
	Context context;
	Channel channel = null;
	String[] labels = null;
	Monitor monitor = null;

	String pvName;
	String val;
	DBR dbrLabel; 

	String changePropertyName ;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public ScanDSTATE(String str,Context context){
		this.context = context;
		pvName = str;	
	}
	
	
	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			
			e.printStackTrace();
		}
	}

	public void channelLabels() {
		try {
			dbrLabel = channel.get(DBRType.LABELS_ENUM, channel.getElementCount());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setLabels() {
		labels = ((LABELS) dbrLabel).getLabels();
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
			int mm = ((DBR_Enum) convert).getEnumValue()[0];
			val = labels[mm];			
			changes.firePropertyChange(changePropertyName, "-99", val); 
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);
		
	}
}
