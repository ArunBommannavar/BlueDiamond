package bluediamond2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class PositionerPnPP implements PropertyChangeListener{
	EpicsDataObject pvObject = null;
	String pvName;
	double val;
	
	public PositionerPnPP(String str, int i){
    	pvName = str + ".P" + String.valueOf(i) + "PP";    		
	}
	
	public void createPV(){
		pvObject = new EpicsDataObject(pvName, true);		
		pvObject.addPropertyChangeListener("val", this);
	}
	
	   public void disconnectChannel() {
	        if (pvObject != null) {
	        	pvObject.setDropPv(true);
	        }
	    }

	public double getDoubleVal(){
	
		return val;		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String strVal = pvObject.getVal();
		val = Double.parseDouble(strVal);		

		
	}
}
