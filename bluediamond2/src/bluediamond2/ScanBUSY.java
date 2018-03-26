package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class ScanBUSY implements PropertyChangeListener{
	EpicsDataObject pvObject;
	String pvName;
	int val  = 0;
	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);
	String changePropertyName = " " ;
	String valString = "";

	public ScanBUSY(String str){
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
		changePropertyName = str;
		changes.addPropertyChangeListener(str, l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
	public void removePropertyChangeListener(String str, PropertyChangeListener l) {		
		changes.removePropertyChangeListener(str,l);
	}
	public int getVal(){
		return val;
	}
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		valString = pvObject.getVal();
		val =  Integer.parseInt(valString);
		changes.firePropertyChange(changePropertyName, "-99", valString);   

	}
}
