import java.util.ArrayList;

/**
 * 
 */

/**
 * @author ranuva
 *
 */


public class Main {

	/**
	 * @param args
	 */
	public static void main(String [] args){
		// TODO Auto-generated method stub
		System.out.println(args);
		BoardInit.chessInit();
		long a = 1L|1L<<8|1L<<16|1L<<24|1L<<32|1L<<40|1L<<48|1L<<56;
		ArrayList<Integer> b = new ArrayList();
		ArrayList<Integer> c = new ArrayList();
		b.add(1);
		b.add(2);
		c.addAll(b);
		System.out.println(c);
	}

}
