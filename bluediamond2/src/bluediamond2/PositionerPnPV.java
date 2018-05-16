package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;
import edu.ciw.hpcat.epics.data.PvName;

public class PositionerPnPV implements PropertyChangeListener {
	EpicsDataObject pvObject = null;
	String pvName;
	String val = " ";
	String desc = " NA";
	EpicsDataObject posValObject = null;
	String posPvName = "";
	String tempPvName = "";
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	public PositionerPnPV(String str, int i) {
		pvName = str + ".P" + String.valueOf(i) + "PV";
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

	public void movePositioner(double d) {
		String str = String.valueOf(d);
		posValObject.putVal(str);

	}
	
	public void movePositioner(String str) {
		posValObject.putVal(str);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object source = evt.getSource();

		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		val = evtObj.getVal();
		String pv = ((PvName) source).getPvName();
		if (pv.endsWith("PV")) {
			tempPvName = val.trim();
			if (tempPvName.length() > 0) {
				if (!tempPvName.equals(posPvName)) {
					if (posValObject != null) {
						posValObject.disconnectChannel();
						posValObject=null;
					}
					posPvName = tempPvName;
					posValObject = new EpicsDataObject(posPvName, true);
				}
			}
		}
	}
}
