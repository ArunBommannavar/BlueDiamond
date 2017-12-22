package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerRelAbs implements PropertyChangeListener {
	EpicsDataObject pvObject = null;
	String pvName;
	String val;

	public PositionerRelAbs(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "AR";
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

	public String getVal() {
		return val;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		val = evtObj.getVal();
	}

}
