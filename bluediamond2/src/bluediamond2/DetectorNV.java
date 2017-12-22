package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class DetectorNV implements PropertyChangeListener {

	EpicsDataObject pvObject = null;
	String pvName;
	String val = null;
    boolean valid = false;
	String temp;
	boolean ready = false;
	boolean init = false;

	public DetectorNV(String str, int i) {
		pvName = str + ".D" + String.format("%02d", i) + "NV";
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
	
	public void setValid(boolean b){
		valid = b;
	}
	public boolean getValid(){
	      temp = pvObject.getVal();		
	      try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	  	
	      if (temp.equals("PV OK")){
	    	 valid = true;
	      }else{
	    	  valid = false;
	      }
		return valid;
	}
	
	public boolean isValid(){
		
			while (!ready) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

	      if (temp.equals("PV OK")){
	    	 valid = true;
	      }else{
	    	  valid = false;
	      }
		
		return valid;
	}
	public void setInit(boolean b) {
		init = b;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		EpicsDataObject evtObj = (EpicsDataObject) evt.getNewValue();
		temp = evtObj.getVal();
		ready = true;
		}
	
}
