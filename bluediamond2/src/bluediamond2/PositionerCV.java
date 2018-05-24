package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class PositionerCV implements PropertyChangeListener {
	Context context;
	Channel channel = null;
	
	EpicsDataObject pvObject = null;
	String pvName;
	double val;
	EpicsDataObject ret;
	String temp;

	public PositionerCV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".R" + String.valueOf(i) + "CV";
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


	public double getDoubleVal() {

		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		temp = evtObj.getVal();
		val = Double.parseDouble(temp);

	}

}
