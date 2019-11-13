package bluediamond2;


import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.DBR_String;
import gov.aps.jca.dbr.STRING;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;

public class DetectorPV implements MonitorListener{
	Context context;
	Channel channel = null;
	Monitor monitor = null;

	String pvName;
	String val = null;
	String detectorDescription=" ";
	String rawdetectorDescription=" ";
	String recordType = "unKnown";


	public DetectorPV(String str, int i,Context context) {
		this.context = context;
		pvName = str + ".D" + String.format("%02d", i) + "PV";
	}
	
	public void createChannel() {
		try {
			channel = context.createChannel(pvName);
		} catch (IllegalArgumentException | IllegalStateException | CAException e) {
			System.out.println("createChannel PV Name = "+pvName+"  createChannel IllegalArgumentException | IllegalStateException | CAException");

			e.printStackTrace();
		}
	}

	public void setMonitor() {
		try {
			monitor = channel.addMonitor(Monitor.VALUE, this);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
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
	}

	public String getVal() {
		return val;
	}
	
	public String getDetectorDescription() {		
		return detectorDescription;
	}

	public void findDetectorPVdescription() {

		String recordType = "unKnown";
		PvRTYP pvRTYP = new PvRTYP(rawdetectorDescription, context);
		recordType = pvRTYP.getRtyp();
		String secondPart;

		int lastIndexOfDot = rawdetectorDescription.lastIndexOf(".");
		String firstPart = rawdetectorDescription.substring(0, lastIndexOfDot);
		secondPart = rawdetectorDescription.substring(lastIndexOfDot + 1);

		PVDescription pVDescription = new PVDescription(context);
		detectorDescription = pVDescription.getDescription(firstPart, secondPart, recordType);	

	}

	
	
	
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = ((DBR_String) convert).getStringValue()[0];
			rawdetectorDescription = val;
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);				
	}
}
