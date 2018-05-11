package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class ScanStatusMessage implements PropertyChangeListener {

	
	EpicsDataObject pvObject;
	String pvName;
	String changePropertyName = " " ;
	String valString = "";

	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	
	
	public ScanStatusMessage(String str){
		pvName = str;		
	}	
	
	public void createPV(){
		pvObject = new EpicsDataObject(pvName, true);	
		pvObject.addPropertyChangeListener("val", this);
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


	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		valString = pvObject.getVal();
		changes.firePropertyChange(changePropertyName, "-99", valString);   
	}

}
