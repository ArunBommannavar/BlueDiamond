package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

/*
 *  If there is a valid positioner, then value will be "PV OK" 
 */
public class PositionerNV implements /* PropertyChangeListener,*/ MonitorListener {
	Context context;	
	Channel channel = null;

	EpicsDataObject pvObject = null;
	
	String pvName;
	int val = -1;
	boolean valid = false;
	String temp;

	boolean init = false;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);

	public PositionerNV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "NV";
	}
/*
	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);
		pvObject.addPropertyChangeListener("val", this);
	}
*/
	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
            context.pendIO(3.0);
        	channel.addMonitor(Monitor.VALUE,this);
        	context.flushIO();
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

	public void removePropertyChangeListener(String str, PropertyChangeListener l) {
		changes.removePropertyChangeListener(str, l);
	}

	public void addPropertyChangeListener(String str, PropertyChangeListener l) {
		changes.addPropertyChangeListener(str, l);

	}


	public boolean isValid() {

		if (temp.equals("PV OK")) {
			valid = true;
		} else {
			valid = false;
		}
		return valid;
	}

	public void setInit(boolean b) {
		init = b;
	}

	/*
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		temp = evtObj.getVal();
	}
*/
	@Override
	public void monitorChanged(MonitorEvent event) {
		
		
	}
}
