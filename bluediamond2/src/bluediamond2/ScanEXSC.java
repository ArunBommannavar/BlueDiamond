package bluediamond2;

import edu.ciw.hpcat.epics.data.*;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ScanEXSC implements PropertyChangeListener {
	
	Context context;
	Channel channel = null;
	
	EpicsDataObject pvObject = null;
	String pvName;
	int val = -1;
	EpicsDataObject ret;
	String valString;
	String changePropertyName = " " ;

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public ScanEXSC(String str,Context context){
		this.context = context;
		pvName = str;
	}
	
	public void createPV(){
		pvObject = new EpicsDataObject(pvName,true);
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
	
	public void propertyChange(PropertyChangeEvent evt) {
	      Object evtObj = evt.getNewValue();
	      ret = (EpicsDataObject)evtObj;
	      valString = ret.getVal();
	      val = Integer.parseInt(valString);	  
	      
    	  changes.firePropertyChange(changePropertyName, "-99", valString);   
	   

	}
}
