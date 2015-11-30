import java.util.ArrayList;

/**
 * 
 */

/**
 * @author ranuva
 *
 */


public class Main {

	public static long WP=0L,WR=0L,WN=0L,WB=0L,WQ=0L,WK=0L,BP=0L,BR=0L,BN=0L,BB=0L,BQ=0L,BK=0L;
	/**
	 * @param args
	 */
	public static void main(String [] args){
		// TODO Auto-generated method stub
		System.out.println(args);
		BoardInit.chessInit();
		@SuppressWarnings("unused")
		long a = 1L|1L<<8|1L<<16|1L<<24|1L<<32|1L<<40|1L<<48|1L<<56;
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> c = new ArrayList<Integer>();
		b.add(1);
		b.add(2);
		c.addAll(b);
		ArrayList<Integer> priorMoves = new ArrayList<Integer>(); 
		System.out.println(WP + "hello");
		MoveGen.getPossibleWhiteMoves(priorMoves, WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK);
		System.out.println(c);
	}

}
