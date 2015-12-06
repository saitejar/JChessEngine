import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

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
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// MoveGen.chessInit();
		UCI.uciCommunication();
		MoveGen.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		MoveGen.printBoard();
		System.out.println(MoveGen.CBK + "" + MoveGen.CBQ + "" + MoveGen.CWK + "" + MoveGen.CWQ + "" + MoveGen.EP + ""
				+ MoveGen.EPc);
		long startTime = System.currentTimeMillis();
		AlphaBeta.pvSearch(-1000,1000,0);
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		System.out.println(MoveGen.NodeCounter);
		System.out.println("hello");
	//#MoveGen.perftRoot(0);
		// MoveGen.perft(0);
		//System.out.println(MoveGen.perftMoveCounter);
	}

}
