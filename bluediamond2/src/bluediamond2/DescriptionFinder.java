package bluediamond2;

import javax.swing.JCheckBox;
import edu.ciw.hpcat.epics.data.CountDownConnection;

public class DescriptionFinder implements Runnable {

	CountDownConnection countDownConnection = CountDownConnection.getInstance();

	JCheckBox jCheckBox;
	String pvName;
	String firstPart;
	String secondPart;
	FindRTYP findRtyp;

	public DescriptionFinder(String str,JCheckBox jc){
		pvName = str;
		jCheckBox = jc;

	}
	
	public void setSearch(){
		int lastIndexOfDot;

		lastIndexOfDot = pvName.lastIndexOf(".");

		System.out.println(" Last index of . = " + lastIndexOfDot);
		firstPart = pvName.substring(0, lastIndexOfDot);
		secondPart = pvName.substring(lastIndexOfDot + 1);
		System.out.println(" first Part ="+firstPart+"  Second Part = "+secondPart);

	}
	
	@Override
	public void run() {
//		setSearch();
	System.out.println(" In DescriptionFinder Run loop");	
		findRtyp = new FindRTYP();
		findRtyp.createPV(firstPart,secondPart,jCheckBox);
		new Thread(findRtyp).start();


	}

}
