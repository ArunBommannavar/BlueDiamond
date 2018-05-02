package bluediamond2;


public class Startup implements Runnable {
	String scan1Pv;
	String scan2Pv;
	Scan1PositionerParms scan1PositionerParms;
	Scan1DetectorParms scan1DetectorParms;
	Scan2PositionerParms scan2PositionerParms;
	
	public Startup(){
		

	}
	
	public void setPrefix(String str0, String str1){
		scan1Pv = str0;
		scan2Pv=str1;
	}
		
	public void setScan1PositionerParm(Scan1PositionerParms spp1){
		this.scan1PositionerParms = spp1;
	}
	public void setScan1DetectorParm(Scan1DetectorParms sdp1){
		this.scan1DetectorParms = sdp1;
	}
	public void setScan2PositionerParm(Scan2PositionerParms spp2){
		this.scan2PositionerParms = spp2;
	}
		
	public void run() {		
		scan1PositionerParms.createPosPVs();
		scan2PositionerParms.createPosPVs();
		scan1DetectorParms.createDetPVs();

		scan1PositionerParms.validatePositioners();
		scan2PositionerParms.validatePositioners();
		scan1DetectorParms.validateDetectors();		

	}
}
