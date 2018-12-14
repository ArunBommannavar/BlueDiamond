package bluediamond2;

public class WriteAsciiFile {

	
	private boolean writeAscii = false;
	
	String fileName = "";
	
	public WriteAsciiFile() {
		
	}
	
	public void setWriteAscii(boolean b) {
		writeAscii = b;
	}

	public boolean getWriteAscii() {
		return writeAscii;
	}
	
	public void setFileName(String str) {
		fileName = str;
	}
	
}
