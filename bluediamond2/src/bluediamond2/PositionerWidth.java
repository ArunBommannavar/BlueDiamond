package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerWidth implements PropertyChangeListener {
	EpicsDataObject pvObject = null;
	String pvName;
	double val;


	public PositionerWidth(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "WD";
	}

	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);
		pvObject.addPropertyChangeListener("val", this);
	}

	public void disconnectChannel() {
		if (pvObject != null) {
			pvObject.setDropPv(true);
		}
	}

	public double getVal() {
		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		val = Double.parseDouble(evtObj.getVal());

	}
}
