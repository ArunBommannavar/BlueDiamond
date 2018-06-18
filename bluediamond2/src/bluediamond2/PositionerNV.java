package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.LABELS;
import gov.aps.jca.dbr.DBR_Enum;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

/*
 *  If there is a valid positioner, then value will be "PV OK" 
 */
public class PositionerNV implements MonitorListener {
	Context context;
	Channel channel = null;
	String[] labels = null;
	Monitor monitor = null;

	String pvName;

	boolean valid = false;
	String pvStatus;
	DBR dbrLabel; 

	boolean init = false;

	public PositionerNV(String str, int i, Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "NV";
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
	
	public void channelLabelsAndPendIO() throws TimeoutException {
		try {
			dbrLabel = channel.get(DBRType.LABELS_ENUM, channel.getElementCount());
			context.pendIO(1.0);
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
			if (monitor != null) {
				monitor.removeMonitorListener(this);
			}
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

	public boolean isValid() {

		if (pvStatus.equals("PV OK")) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}

	public void setInit(boolean b) {
		init = b;
	}

	public void monitorChanged(MonitorEvent event) {

		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			int mm = ((DBR_Enum) convert).getEnumValue()[0];
			pvStatus = labels[mm];
		} else
			System.err.println("Monitor error: " + event.getStatus());
	}

}
