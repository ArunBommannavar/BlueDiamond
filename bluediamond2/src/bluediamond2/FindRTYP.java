package bluediamond2;

import javax.swing.JCheckBox;

import edu.ciw.hpcat.epics.data.CountDownConnection;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class FindRTYP implements Runnable {

	String firstPart;
	String secondPart;
	String pvName;
	EpicsDataObject pvObject;
	JCheckBox jCheckBox;
	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	public FindRTYP() {
		
	}

	public void createPV(String str1, String str2, JCheckBox jc){
		firstPart = str1;
		secondPart = str2;
		pvName = str1 + ".RTYP";
		jCheckBox = jc;
		pvObject = new EpicsDataObject(pvName, true);
		countDownConnection.pendIO();
	}
	public String findRtyp() {
		return pvObject.getVal();
	}

	@Override
	public void run() {
		String rtyp = findRtyp();
		PVDescription description = new PVDescription(firstPart, secondPart, rtyp, jCheckBox);
		new Thread(description).start();
	}

}
