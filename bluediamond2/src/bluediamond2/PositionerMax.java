package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerMax implements PropertyChangeListener{

	EpicsDataObject pvObject;
	String pvName;
	double val;
	EpicsDataObject ret;
	String temp;

	public PositionerMax(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "EP";
	}

	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);
		pvObject.addPropertyChangeListener("val", this);
	}

	public double getDoubleVal() {
		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	       EpicsDataObject evtObj = (EpicsDataObject)evt.getNewValue();	      
	       val = Double.parseDouble( evtObj.getVal());
	}
}
