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
import gov.aps.jca.dbr.DBR_Float;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class DetectorDnnRA implements MonitorListener{

	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;	
	double[] val;
	float[] floatVal;

	public DetectorDnnRA(String str, int n,Context context) {
		this.context = context;
		pvName = str + ".D" + String.format("%02d", n) + "DA";
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
	public double[] convertFloatsToDoubles(float[] input)
	{
	    if (input == null)
	    {
	        return null; // Or throw an exception - your choice
	    }
	    double[] output = new double[input.length];
	    for (int i = 0; i < input.length; i++)
	    {
	        output[i] = input[i];
	    }
	    return output;
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

	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			floatVal =(((DBR_Float) convert).getFloatValue());
			val = convertFloatsToDoubles(floatVal);

			
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);
	}		
}
