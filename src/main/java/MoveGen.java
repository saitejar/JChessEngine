import java.util.ArrayList;

/**This Class contains routines to generate all possible
 * legal moves given a board state
 * @author ranuva
 * 8 A8                   H8
 * 7 
 * 6 .
 * 5 .            .
 * 4 .        .           .
 * 3 A3    .              .
 * 2 A2 B2                .
 * 1 A1 B1 ...            H1
 *    A  B  C  D  E  F  G  H 
 *    
 * Info: 1.Top left corner is LSB of 64 bit number. 
 *  	 2.Bottom right corner is MSB of 64 bit number.
 *  
 * Vertical Columns are called Files. Eg: File A etc.
 * Horizontal Rows are called Ranks   Eg: Rank 4 etc.
 */
public class MoveGen {
	/* Some useful 64 bit numbers to generate moves of pieces */
	static long FILE_A = 1L|1L<<8|1L<<16|1L<<24|1L<<32|1L<<40|1L<<48|1L<<56;
	static long FILE_AB = FILE_A|FILE_A<<1;
	static long FILE_H = FILE_A<<7;
	static long FILE_GH = FILE_H|FILE_H>>1;
	static long RANK_8 = 255L;
	static long RANK_5 = RANK_8<<24;
	static long RANK_4 = RANK_5<<8;
	static long RANK_1 = RANK_4<<24;

   /**
   * This method is used to generate all the possible white moves
   * from the given bit board given configuration
   * Each move is encoded as follows. <br>
   * byte  meaning        example <br>
   * ==================================== <br>
   * 1    from-square 		 36 (A5) <br>
   * 2    to-square			 49 (B6) <br>
   * 3    flags  			 xx (en-passant capture or pawn promotion or castles or double pawn push) <br>
   * 4 	  captured-piece  	 xx (black pawn) <br>
   * 
   * @param priorMoves  Array List of integers representing previous moves
   * @param WP	Bit Board Representation of the positions of the White Pawns
   * @param WR	Bit Board Representation of the positions of the White Rooks
   * @param WN	Bit Board Representation of the positions of the White Knights
   * @param WB	Bit Board Representation of the positions of the White Bishops
   * @param WQ	Bit Board Representation of the positions of the White Queens
   * @param WK	Bit Board Representation of the position  of the White King
   * @param BP	Bit Board Representation of the positions of the Black Pawns
   * @param BR	Bit Board Representation of the positions of the Black Rooks
   * @param BN	Bit Board Representation of the positions of the Black Knights
   * @param BB	Bit Board Representation of the positions of the Black Bishops
   * @param BQ	Bit Board Representation of the positions of the Black Queen
   * @param BK	Bit Board Representation of the position  of the Black King
   * 
   * @return Array List of integers; Each integer(4 Bytes) represents a move.
   *  
   */
	public static ArrayList<Integer> getPossibleWhiteMoves(ArrayList<Integer[]> priorMoves,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK){
		
		ArrayList<Integer> wMoves = new ArrayList<Integer>(); 				/** Array list of moves*/
		
		wMoves.addAll(getPossibleWhitePawnMoves(priorMoves, WP)); 	/** Add pawn moves */
		wMoves.addAll(getPossibleWhiteRookMoves(priorMoves, WR)); 	/** Add rook moves */
		wMoves.addAll(getPossibleWhiteKnightMoves(priorMoves, WN)); /** Add Knight moves */
		wMoves.addAll(getPossibleWhiteBishopMoves(priorMoves, WB)); /** Add Bishop moves */
		wMoves.addAll(getPossibleWhiteQueenMoves(priorMoves, WQ)); 	/** Add Queen moves */
		wMoves.addAll(getPossibleWhiteKingMoves(priorMoves, WK)); 	/** Add King moves */
		
		return wMoves;
	}

	/**
	 * @param priorMoves 
	 * @param WK
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhiteKingMoves(ArrayList<Integer[]> priorMoves, long wK) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param priorMoves 
	 * @param WQ
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhiteQueenMoves(ArrayList<Integer[]> priorMoves, long wQ) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param priorMoves 
	 * @param WB
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhiteBishopMoves(ArrayList<Integer[]> priorMoves, long wB) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param priorMoves 
	 * @param WN
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhiteKnightMoves(ArrayList<Integer[]> priorMoves, long wN) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param priorMoves 
	 * @param WR
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhiteRookMoves(ArrayList<Integer[]> priorMoves, long wR) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param priorMoves 
	 * @param WP
	 * @return
	 */
	private static ArrayList<Integer> getPossibleWhitePawnMoves(ArrayList<Integer[]> priorMoves, long WP) {
		// TODO Auto-generated method stub
		return null;
	}
}
