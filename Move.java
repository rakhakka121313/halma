
public class Move {
	private int ib, jb, ie, je;
	
	public void setIBJB(int ib_in, int jb_in){
		ib = ib_in;
		jb = jb_in;
	}
	public void setIEJE(int ie_in, int je_in){
		ie = ie_in;
		je = je_in;
	}
	public int getIB(){
		return ib;
	}
	public int getJB(){
		return jb;
	}
	public int getIE(){
		return ie;
	}
	public int getJE(){
		return je;
	}
}
