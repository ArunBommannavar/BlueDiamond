package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class ScanDATA implements PropertyChangeListener {
	
	   EpicsDataObject pvObject = null;
	   String pvName;
	   int val = -1;
	   String temp;

	   protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
	   
	   public ScanDATA(String str) {
		      pvName = str;
		   }

		public void createPV(){
			pvObject = new EpicsDataObject(pvName,true);
			pvObject.addPropertyChangeListener("val", this);
		}
	    public void disconnectChannel() {
	        if (pvObject != null) {
	        	pvObject.setDropPv(true);
	        }
	    }
		public void addPropertyChangeListener(String str, PropertyChangeListener l) {
			changes.addPropertyChangeListener(str, l);
		   	}
		public void removePropertyChangeListener(PropertyChangeListener l) {
			changes.removePropertyChangeListener(l);
		}
		public void removePropertyChangeListener(String str, PropertyChangeListener l) {
			changes.removePropertyChangeListener(str,l);
		}

		public void propertyChange(PropertyChangeEvent evt) {
		      temp = pvObject.getVal();
		      val = Integer.parseInt(temp);
		      changes.firePropertyChange("DATA", -1, temp);
		}
	}
