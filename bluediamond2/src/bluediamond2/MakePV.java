package bluediamond2;

import edu.ciw.hpcat.epics.data.EpicsDataObject;

public class MakePV {

	String scan1Str;
	String scan2Str;
	
	EpicsDataObject scan1Obj;
	EpicsDataObject scan2Obj;
	
	
	public MakePV(String s1,String s2){
		scan1Str = s1;
		scan2Str = s2;
	}
	
	public void createPV(){
	}
	
}
