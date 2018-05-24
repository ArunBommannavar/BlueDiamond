package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;
import edu.ciw.hpcat.epics.data.PvName;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.TimeoutException;

public class PositionerPnPV implements PropertyChangeListener {
	Context context;
	Channel channel = null;
	
	EpicsDataObject pvObject = null;
	String pvName;
	String val = " ";
	String desc = " NA";
	EpicsDataObject posValObject = null;
	String posPvName = "";
	String tempPvName = "";
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	public PositionerPnPV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".P" + String.valueOf(i) + "PV";
	}

	public void createPV() {
		pvObject = new EpicsDataObject(pvName, true);
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
