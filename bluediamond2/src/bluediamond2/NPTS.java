package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class NPTS implements PropertyChangeListener{

	Context context;
	Channel channel = null;
	
	EpicsDataObject pvObject = null;
	String pvName;
	int val;
	String temp;
	String changePropertyName ;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public NPTS(String str,Context context) {
		this.context = context;
		pvName = str;
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
	public int getVal() {
		return val;
	}

	public void putVal(String str) {
		pvObject.putVal(str);
	}

	public void putVal(int n){
		String strN = String.valueOf(n);
		pvObject.putVal(strN);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	       EpicsDataObject evtObj = (EpicsDataObject)evt.getNewValue();	      
	       val = Integer.parseInt(evtObj.getVal());
	   	   changes.firePropertyChange(changePropertyName, new Object(), evtObj);   

	}

}
