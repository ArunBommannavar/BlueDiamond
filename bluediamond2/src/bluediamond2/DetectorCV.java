package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBR_Double;
import gov.aps.jca.dbr.DBR_Float;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class DetectorCV implements MonitorListener {

	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	double val;

	public DetectorCV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".D" + String.format("%02d", i) + "CV";
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


	public double getDoubleVal() {
		return val;
	}

	@Override
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = (double) ((DBR_Float) convert).getFloatValue()[0];
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);		
	}

}
