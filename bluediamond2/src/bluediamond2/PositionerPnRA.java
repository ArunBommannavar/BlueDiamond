package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBR_Double;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class PositionerPnRA implements MonitorListener{
	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;	
	double[] val;
	public PositionerPnRA(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "RA";
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
			if (channel!=null)
			channel.destroy();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public double[] getArrayValues() {
		return val;	
	}

	public double[] getArrayValues(int n) {

		double[] values = new double[n];
		
		for (int i=0; i<n;i++){
			values[i] = val[i];
		}
		return values;
	}

	@Override
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = ((DBR_Double) convert).getDoubleValue();
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);
	}		

}
