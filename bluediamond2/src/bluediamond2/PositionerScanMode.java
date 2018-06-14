package bluediamond2;


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

public class PositionerScanMode implements MonitorListener {
	Context context;
	Channel channel = null;
	Monitor monitor = null;
	String[] labels = null;
	DBR dbrLabel; 

	String pvName;
	String val;


	public PositionerScanMode(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "SM";
	}

	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
 //           context.pendIO(3.0);
 
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			
			e.printStackTrace();
		}
	}
	
	public void channelLabels() {
		try {
			dbrLabel = channel.get(DBRType.LABELS_ENUM, channel.getElementCount());
//			context.pendIO(3.0);
//			labels = ((LABELS) dbr).getLabels();
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


	public String getVal() {
		return val;
	}

	@Override
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			int mm = ((DBR_Enum) convert).getEnumValue()[0];
			val = labels[mm];
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);		
	}


}
