package bluediamond2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import edu.ciw.hpcat.epics.data.EpicsDataObject;


public class PositionerMin implements PropertyChangeListener{

	EpicsDataObject pvObject = null;
	String pvName;
	double val;

	
    public PositionerMin(String str, int i) {
    	pvName = str + ".P" + String.valueOf(i) + "SP";    	
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
	       EpicsDataObject evtObj = (EpicsDataObject)evt.getNewValue();	      
	       val = Double.parseDouble( evtObj.getVal());

		
	}
}
