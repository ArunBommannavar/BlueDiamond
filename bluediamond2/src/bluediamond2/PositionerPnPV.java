package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBR_String;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class PositionerPnPV implements MonitorListener {
	
	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	String pvPvVal = "";

	Channel motorChannel = null;
	
	public PositionerPnPV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "PV";
	}


	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			System.out.println("createChannel PV Name = "+pvName+"  createChannel IllegalArgumentException | IllegalStateException | CAException");

			e.printStackTrace();
		}
	}
	
	public void createMotorChannel() {
		try {
			if (motorChannel!=null) {
				motorChannel.destroy();
				motorChannel= null;
			}
			motorChannel = context.createChannel(pvPvVal);
            context.pendIO(3.0);
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			System.out.println(" PV Name = "+pvName+"  createMotorChannel CAException or IllegalStateException");
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println(" PV Name = "+pvName+"  pendIO exception");
			e.printStackTrace();
		}
	}
	
	public void setMonitor() {
		try {
			monitor = channel.addMonitor(Monitor.VALUE, this);
			context.flushIO();

		} catch (IllegalStateException e) {
			System.out.println(" PV Name = "+pvName+"  setMonitor IllegalStateException");
			e.printStackTrace();
		} catch (CAException e) {
			System.out.println(" PV Name = "+pvName+"  createMotorChannel CAException ");
			e.printStackTrace();
		}
	}
	
	public void disconnectChannel() {		
		try {
			if(monitor != null)
				monitor.removeMonitorListener(this);
			if(motorChannel!=null)
				motorChannel.destroy();
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

	public void initMotorChannelPV() {
		pvPvVal="";
	}
	public String getVal() {
		return pvPvVal;
	}

	public void movePositioner(double d) {
		try {
			motorChannel.put(d);
			context.pendIO(1.0);
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
	
	public void movePositioner(String str) {
		try {
			motorChannel.put(str);
			context.pendIO(1.0);
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
	
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			pvPvVal = ((DBR_String) convert).getStringValue()[0];
			if (pvPvVal.length() >0) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					createMotorChannel();
				}
			});
			thread.start();
			}
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);
	}
}
