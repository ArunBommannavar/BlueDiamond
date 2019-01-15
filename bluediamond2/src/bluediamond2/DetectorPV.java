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
/*
	public void findDetectorDescription() {
		PVDescription1 pVDescription1; 
	}
*/
	
	
	public void findDetectorPVdescription() {

		PVDescription1 pVDescription1; 
		String secondPart;
//		String motorChannelName = motorChannel.getName();
		detectorDescription = pvName;

		int lastIndexOfDot = detectorDescription.lastIndexOf(".");
		String firstPart = detectorDescription.substring(0, lastIndexOfDot);
		secondPart = detectorDescription.substring(lastIndexOfDot + 1);

		Channel rtypeChannel;
		String rtypePV = firstPart+".RTYP";

		try {
			rtypeChannel = context.createChannel(rtypePV);
			context.pendIO(1.0);
			DBR dbr = rtypeChannel.get(DBRType.STRING, 1);
			context.pendIO(1.0);
			recordType = ((STRING) dbr).getStringValue()[0];
			rtypeChannel.destroy();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			System.out.println(
					" TimeOutException PositionerPnPV " + pvName + "  find PV Description of " + detectorDescription);
			e.printStackTrace();
		}
		if (recordType.equals("motor")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeMotorEpicsObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();
			
		} else if (recordType.equals("ao")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeA0EpicsObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();
			
		} else if (recordType.equals("scaler")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeScalerEpicsObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();

		} else if (recordType.equals("transform")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeTransformEpicsDataObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();

		} else if (recordType.equals("mca")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeMcaEpicsDataObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();
		}else if (recordType.equals("ai")) {
			pVDescription1 = new PVDescription1(firstPart,secondPart,context);
			pVDescription1.makeAiEpicsObject();
			detectorDescription = pVDescription1.getDescription();
			pVDescription1.disconnectChannel();
			
		}


	}

	
	
	
	public void monitorChanged(MonitorEvent event) {
		if (event.getStatus() == CAStatus.NORMAL) {
			DBR convert = event.getDBR();
			val = ((DBR_String) convert).getStringValue()[0];
		} else
			System.err.println("Monitor error: " + event.getStatus()+"  PV = "+pvName);				
	}
}
