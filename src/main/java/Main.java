import java.util.ArrayList;

/**
 * 
 */

/**
 * @author ranuva
 *
 */


public class Main implements GlobalConstants{

	/**
	 * @param args
	 */
	public static void main(String [] args){
		// TODO Auto-generated method stub
		//MoveGen.chessInit();
		MoveGen.importFEN("rnb1kbnr/3ppppp/p1p5/8/1p6/1P2P3/2PK1PPP/RNBQ1BNR w kq - 0 7");
		MoveGen.printBoard();

        MoveGen.perftRoot(0);
	}

}
