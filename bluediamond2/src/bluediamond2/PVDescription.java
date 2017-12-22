package bluediamond2;

import javax.swing.JCheckBox;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PVDescription implements Runnable {

	String firstPart;
	String secondPart;
	String pvName;
	String recordType;
	JCheckBox jCheckBox;
	EpicsDataObject pvObject1;
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	public PVDescription(String str1, String str2, String rc, JCheckBox jc) {
		firstPart = str1;
		secondPart = str2;
		recordType = rc;
		jCheckBox = jc;

	}

	public boolean makeEpicsDataObject() {

		boolean proceed = false;

		if (recordType.equals("motor")) {
			pvName = firstPart + ".DESC";
			pvObject1 = new EpicsDataObject(pvName, true);			
			countDownConnection.pendIO();
			proceed = true;
		} else if (recordType.equals("ao")) {
			pvName = firstPart + ".DESC";
			pvObject1 = new EpicsDataObject(pvName, true);
			countDownConnection.pendIO();
			proceed = true;

		} else if (recordType.equals("scaler")) {
			String str = secondPart.replace("S", "NM");
			pvName = firstPart + "." + str;
			pvObject1 = new EpicsDataObject(pvName, true);
			countDownConnection.pendIO();
			proceed = true;
		} else if (recordType.equals("transform")) {
			String str = ".CMT" + secondPart;
			pvName = firstPart + str;
			pvObject1 = new EpicsDataObject(pvName, true);
			countDownConnection.pendIO();
			proceed = true;
		}
		return proceed;
	}

	public void disconnectChannel() {
		if (pvObject1 != null) {
			pvObject1.setDropPv(true);
		}
	}

	public String getDescription() {
		return pvObject1.getVal();
	}

	public void run() {
		boolean proceed = makeEpicsDataObject();
		countDownConnection.pendIO();
		if (proceed) {
			String descr = pvObject1.getVal();
			System.out.println(" Finally = " + descr);

			jCheckBox.setText(descr);
			jCheckBox.getParent().validate();
		}
	}
}
