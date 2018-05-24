package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class DetectorDnnRA implements PropertyChangeListener{

	Context context;
	Channel channel = null;
	
	EpicsDataObject pvObject = null;
	String pvName;
	double[] val;
	int cpt;

	public DetectorDnnRA(String str, int n,Context context) {
		this.context = context;
		pvName = str + ".D" + String.format("%02d", n) + "DA";
	}

	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);
		pvObject.addPropertyChangeListener("val", this);

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
	
	/*
	if (pvObject != null) {
		pvObject.setDropPv(true);
	}
	*/
}


	public void setCPT(int n){
		cpt = n;
	}
	public double[] getDoubleArrayVal() {
		double[] d;

		String[] strVal = pvObject.getArrayVal();
		d = new double[strVal.length];
		for (int i = 0; i < strVal.length; i++) {
			d[i] = Double.parseDouble(strVal[i]);
		}
		return d;
	}
	public double[] getDoubleArrayVal(int n) {
		double[] d = new double[n];
		for (int i = 0; i < n; i++) {
			d[i] = val[i];
		}
		return d;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String[] strVal;
		pvObject.getArrayVal();		
		strVal = pvObject.getArrayVal();
		val= new double[strVal.length];

		for (int i = 0; i < strVal.length; i++) {
			val[i]= Double.parseDouble(strVal[i]);
		}	
		
	}	
}
