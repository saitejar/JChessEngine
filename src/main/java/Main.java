import java.io.IOException;
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
	 * @throws IOException 
	 */
	public static void main(String [] args) throws IOException{
		// TODO Auto-generated method stub
		//MoveGen.chessInit();
		MoveGen.importFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
		MoveGen.printBoard();
		System.out.println(MoveGen.CBK +""+MoveGen.CBQ+""+MoveGen.CWK +""+MoveGen.CWQ+""+MoveGen.EP+""+MoveGen.EPc);
        MoveGen.perftRoot(0);
        //MoveGen.perft(0);
        System.out.println(MoveGen.perftMoveCounter);
	}

}
