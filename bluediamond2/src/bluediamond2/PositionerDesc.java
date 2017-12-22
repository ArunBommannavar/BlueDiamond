package bluediamond2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerDesc implements PropertyChangeListener{
	EpicsDataObject pvObject;
	String pvName;
	String val;
	
	String descStr = "";
    int dotRBV = 0;
	PropertyChangeSupport changes = new PropertyChangeSupport(this);
	String changePropertyName ;

    public PositionerDesc(String str) {       
    	descStr = str; 
     }
    
	public void createPV() {
	    String temp;	
    	
        if (descStr.endsWith(".RBV")) {
            dotRBV = descStr.lastIndexOf(".RBV");
            temp = descStr.substring(0, dotRBV);

            pvName = temp + ".DESC";
            pvObject = new EpicsDataObject(pvName,false);
            pvObject.addPropertyChangeListener("val", this);

        } else if (descStr.endsWith(".VAL")) {
            dotRBV = descStr.lastIndexOf(".VAL");
            temp = descStr.substring(0, dotRBV);

            pvName = temp + ".DESC";
            pvObject = new EpicsDataObject(pvName,false);
            pvObject.addPropertyChangeListener("val", this);
        }        
        
        else {
        	pvName = descStr + ".DESC";
        	pvObject = new EpicsDataObject(descStr, true);
        	pvObject.addPropertyChangeListener("val", this);
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

	public String getValue(){
		return descStr;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	      Object evtObj = evt.getNewValue();
	      descStr = ((EpicsDataObject)evtObj).getVal();
//	      changes.firePropertyChange(changePropertyName, new Object() , this);
		
	}
}
