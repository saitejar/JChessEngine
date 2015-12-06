import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * This Class contains routines to generate all possible legal moves given a
 * board state
 * 
 * @author ranuva 8 A8 H8 7 6 . 5 . . 4 . . . 3 A3 . . 2 A2 B2 . 1 A1 B1 ... H1
 *         A B C D E F G H
 * 
 *         Info: 1.Top left corner is LSB of 64 bit number. 2.Bottom right
 *         corner is MSB of 64 bit number.
 * 
 *         Vertical Columns are called Files. Eg: File A etc. Horizontal Rows
 *         are called Ranks Eg: Rank 4 etc.
 */
public class MoveGen implements GlobalConstants {

	protected static long YOUR_PIECES;
	protected static long NOT_MY_PIECES;
	protected static long EMPTY;
	protected static long OCCUPIED;

	public static long WP = 0L;
	public static long WR = 0L, WN = 0L, WB = 0L, WQ = 0L, WK = 0L, BP = 0L, BR = 0L, BN = 0L, BB = 0L, BQ = 0L,
			BK = 0L, EP = 0L, EPc = 0L;
	public static int MaxDepth = 5;
	public static long perftTotalMoveCounter = 0L;
	public static long perftMoveCounter = 0L;
	public static long NodeCounter = 0L;
	public static boolean CBK = true, CBQ = true, CWK = true, CWQ = true, bw = true;
	
	static Comparator<Integer> comparator = Collections.reverseOrder();

	/**
	 * This method is used to initialize the chess board and set the bit board
	 * values for all the different pieces.
	 * 
	 */

	public static void chessInit() {
		/**
		 * long 64 bit integer to store bitboard positions of all the unique
		 * pieces. These 12 long variables are sufficient to represent the
		 * current configuration or state of the board WP - White Pawn WR -
		 * White Rook WN - White Knight WB - White Bishop WQ - White Queen WK -
		 * White King BP - Black Pawn BR - Black Rook BN - Black Knight BB -
		 * Black Bishop BQ - Black Queen BK - Black King
		 */
		// Lower case to represent Black pieces. Upper case to represent White
		// pieces
		String[][] chessBoard = { { "r", "n", "b", "q", "k", "b", "n", "r" },
				{ "p", "p", "p", "p", "p", "p", "p", "p" }, { " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " " }, { " ", " ", " ", " ", " ", " ", " ", " " },
				{ " ", " ", " ", " ", " ", " ", " ", " " }, { "P", "P", "P", "P", "P", "P", "P", "P" },
				{ "R", "N", "B", "Q", "K", " ", " ", "R" } };
		stringToBitBoard(chessBoard);
	}

	/**
	 * This method is used to set the bit board values of various pieces using
	 * the string representation of the chess board.
	 * 
	 * @param chessBoard
	 *            String representation of the state of chess board
	 * @param WP
	 *            Bit Board Representation of the positions of the White Pawns
	 * @param WR
	 *            Bit Board Representation of the positions of the White Rooks
	 * @param WN
	 *            Bit Board Representation of the positions of the White Knights
	 * @param WB
	 *            Bit Board Representation of the positions of the White Bishops
	 * @param WQ
	 *            Bit Board Representation of the positions of the White Queens
	 * @param WK
	 *            Bit Board Representation of the position of the White King
	 * @param BP
	 *            Bit Board Representation of the positions of the Black Pawns
	 * @param BR
	 *            Bit Board Representation of the positions of the Black Rooks
	 * @param BN
	 *            Bit Board Representation of the positions of the Black Knights
	 * @param BB
	 *            Bit Board Representation of the positions of the Black Bishops
	 * @param BQ
	 *            Bit Board Representation of the positions of the Black Queen
	 * @param BK
	 *            Bit Board Representation of the position of the Black King
	 * 
	 * 
	 */
	public static void stringToBitBoard(String[][] chessBoard) {
		for (int i = 0; i < 64; i++) {
			long bitSet = 1L << i;
			switch (chessBoard[i / 8][i % 8]) {
			case "P":
				WP |= bitSet;
				break;
			case "N":
				WN |= bitSet;
				break;
			case "B":
				WB |= bitSet;
				break;
			case "R":
				WR |= bitSet;
				break;
			case "Q":
				WQ |= bitSet;
				break;
			case "K":
				WK |= bitSet;
				break;
			case "p":
				BP |= bitSet;
				break;
			case "n":
				BN |= bitSet;
				break;
			case "b":
				BB |= bitSet;
				break;
			case "r":
				BR |= bitSet;
				break;
			case "q":
				BQ |= bitSet;
				break;
			case "k":
				BK |= bitSet;
				break;
			}
		}
		printBoard();
	}

	/**
	 * This method is used to print the String representation of the board from
	 * the bit boards values of all the unique pieces.
	 * 
	 * @param WP
	 *            Bit Board Representation of the positions of the White Pawns
	 * @param WR
	 *            Bit Board Representation of the positions of the White Rooks
	 * @param WN
	 *            Bit Board Representation of the positions of the White Knights
	 * @param WB
	 *            Bit Board Representation of the positions of the White Bishops
	 * @param WQ
	 *            Bit Board Representation of the positions of the White Queens
	 * @param WK
	 *            Bit Board Representation of the position of the White King
	 * @param BP
	 *            Bit Board Representation of the positions of the Black Pawns
	 * @param BR
	 *            Bit Board Representation of the positions of the Black Rooks
	 * @param BN
	 *            Bit Board Representation of the positions of the Black Knights
	 * @param BB
	 *            Bit Board Representation of the positions of the Black Bishops
	 * @param BQ
	 *            Bit Board Representation of the positions of the Black Queen
	 * @param BK
	 *            Bit Board Representation of the position of the Black King
	 */
	public static void printBoard() {
		// TODO Auto-generated method stub
		String chessBoard[][] = new String[8][8];
		for (int i = 0; i < 64; i++) {
			chessBoard[i / 8][i % 8] = " ";
		}
		for (int i = 0; i < 64; i++) {
			if (((WP >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "P";
			}
			if (((WN >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "N";
			}
			if (((WB >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "B";
			}
			if (((WR >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "R";
			}
			if (((WQ >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "Q";
			}
			if (((WK >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "K";
			}
			if (((BP >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "p";
			}
			if (((BN >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "n";
			}
			if (((BB >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "b";
			}
			if (((BR >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "r";
			}
			if (((BQ >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "q";
			}
			if (((BK >> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "k";
			}
		}
		for (int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(chessBoard[i]));
		}
	}

	static long movesHV(int s) {
		long binaryS = 1L << s;
		long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS)
				^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
		long possibilitiesVertical = ((OCCUPIED & FileMasks8[s % 8]) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
		return (possibilitiesHorizontal & RankMasks8[s / 8]) | (possibilitiesVertical & FileMasks8[s % 8]);
	}

	static long moveslDrD(int s) {
		long binaryS = 1L << s;
		long possibilitiesDiagonal = ((OCCUPIED & DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long
				.reverse(Long.reverse(OCCUPIED & DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
		long possibilitiesAntiDiagonal = ((OCCUPIED & AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS))
				^ Long.reverse(Long.reverse(OCCUPIED & AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)])
						- (2 * Long.reverse(binaryS)));
		return (possibilitiesDiagonal & DiagonalMasks8[(s / 8) + (s % 8)])
				| (possibilitiesAntiDiagonal & AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
	}

	/**
	 * This method is used to generate all the possible white moves from the
	 * given bit board given configuration Each move is encoded as follows. <br>
	 * byte meaning example <br>
	 * ==================================== <br>
	 * 1 from-square 36 (A5) <br>
	 * 2 to-square 49 (B6) <br>
	 * 3 flags xx (enPassant capture or pawn promotion or castles or double pawn
	 * push) <br>
	 * 4 captured-piece xx (black pawn) <br>
	 * 
	 * @param priorMoves
	 *            Array List of integers representing previous moves
	 * @param WP
	 *            Bit Board Representation of the positions of the White Pawns
	 * @param WR
	 *            Bit Board Representation of the positions of the White Rooks
	 * @param WN
	 *            Bit Board Representation of the positions of the White Knights
	 * @param WB
	 *            Bit Board Representation of the positions of the White Bishops
	 * @param WQ
	 *            Bit Board Representation of the positions of the White Queens
	 * @param WK
	 *            Bit Board Representation of the position of the White King
	 * @param BP
	 *            Bit Board Representation of the positions of the Black Pawns
	 * @param BR
	 *            Bit Board Representation of the positions of the Black Rooks
	 * @param BN
	 *            Bit Board Representation of the positions of the Black Knights
	 * @param BB
	 *            Bit Board Representation of the positions of the Black Bishops
	 * @param BQ
	 *            Bit Board Representation of the positions of the Black Queen
	 * @param BK
	 *            Bit Board Representation of the position of the Black King
	 * 
	 * @return Array List of integers; Each integer(4 Bytes) represents a move.
	 * 
	 */
	public static ArrayList<Integer> getPossibleWhiteMoves() {

		ArrayList<Integer> wMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */

		YOUR_PIECES = BP | BR | BN | BB | BQ; /** Black King is not included */
		NOT_MY_PIECES = ~(WP | WR | WN | WB | WQ | WK
				| BK); /**
						 * Can initialize it to NOT_BLACK_PIECES or
						 * NOT_WHITE_PIECES
						 */
		EMPTY = ~(WP | WR | WN | WB | WQ | WK | BP | BR | BN | BB | BQ
				| BK); /** Bit board of empty slots */
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ
				| BK; /** Bit Board of occupied slots */
		wMoves.addAll(getPossibleWhitePawnMoves()); /** Add pawn moves */
		// System.out.println(wMoves + " yes");
		// System.out.println(wMoves.size());
		wMoves.addAll(getPossibleRookMoves(WR, BP, BR, BN, BB,
				BQ)); /** Add rook moves */
		wMoves.addAll(getPossibleKnightMoves(WN, BP, BR, BN, BB,
				BQ)); /** Add Knight moves */
		wMoves.addAll(getPossibleBishopMoves(WB, BP, BR, BN, BB,
				BQ)); /** Add Bishop moves */
		wMoves.addAll(getPossibleQueenMoves(WQ, BP, BR, BN, BB,
				BQ)); /** Add Queen moves */
		wMoves.addAll(getPossibleKingMoves(WK, BP, BR, BN, BB,
				BQ)); /** Add King moves */
		wMoves.addAll(getPossibleCastleWhiteMoves());
		return wMoves;
	}

	/**
	 * This method is used to generate all the possible white moves from the
	 * given bit board given configuration Each move is encoded as follows. <br>
	 * byte meaning example <br>
	 * ==================================== <br>
	 * 1 from-square 36 (A5) <br>
	 * 2 to-square 49 (B6) <br>
	 * 3 flags xx (enPassant capture or pawn promotion or castles or double pawn
	 * push) <br>
	 * 4 captured-piece xx (black pawn) <br>
	 * 
	 * @param priorMoves
	 *            Array List of integers representing previous moves
	 * @param WP
	 *            Bit Board Representation of the positions of the White Pawns
	 * @param WR
	 *            Bit Board Representation of the positions of the White Rooks
	 * @param WN
	 *            Bit Board Representation of the positions of the White Knights
	 * @param WB
	 *            Bit Board Representation of the positions of the White Bishops
	 * @param WQ
	 *            Bit Board Representation of the positions of the White Queens
	 * @param WK
	 *            Bit Board Representation of the position of the White King
	 * @param BP
	 *            Bit Board Representation of the positions of the Black Pawns
	 * @param BR
	 *            Bit Board Representation of the positions of the Black Rooks
	 * @param BN
	 *            Bit Board Representation of the positions of the Black Knights
	 * @param BB
	 *            Bit Board Representation of the positions of the Black Bishops
	 * @param BQ
	 *            Bit Board Representation of the positions of the Black Queen
	 * @param BK
	 *            Bit Board Representation of the position of the Black King
	 * 
	 * @return Array List of integers; Each integer(4 Bytes) represents a move.
	 * 
	 */
	public static ArrayList<Integer> getPossibleBlackMoves() {

		ArrayList<Integer> wMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */

		YOUR_PIECES = WP | WR | WN | WB | WQ; /** Black King is not included */
		NOT_MY_PIECES = ~(BP | BR | BN | BB | BQ | BK
				| BK); 
/**
				 * Can initialize it to NOT_BLACK_PIECES or
				 * NOT_BHITE_PIECES
				 */
		EMPTY = ~(WP | WR | WN | WB | WQ | WK | BP | BR | BN | BB | BQ
				| BK); /** Bit board of empty slots */
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ
				| BK; /** Bit Board of occupied slots */
		wMoves.addAll(getPossibleBlackPawnMoves()); /** Add pawn moves */
		// System.out.println("after adding pawn moves - "+wMoves.size());
		wMoves.addAll(getPossibleRookMoves(BR, WP, WR, WN, WB,
				WQ)); /** Add rook moves */
		// System.out.println("after adding rook moves - "+wMoves.size());
		wMoves.addAll(getPossibleKnightMoves(BN, WP, WR, WN, WB,
				WQ)); /** Add Knight moves */
		// System.out.println("after adding Knight moves - "+wMoves.size());
		wMoves.addAll(getPossibleBishopMoves(BB, WP, WR, WN, WB,
				WQ)); /** Add Bishop moves */
		// System.out.println("after adding bishop moves - "+wMoves.size());
		wMoves.addAll(getPossibleQueenMoves(BQ, WP, WR, WN, WB,
				WQ)); /** Add Queen moves */
		// System.out.println("after adding queen moves - "+wMoves.size());
		wMoves.addAll(getPossibleKingMoves(BK, WP, WR, WN, WB,
				WQ)); /** Add King moves */
		// System.out.println("after adding king moves - "+wMoves.size());
		wMoves.addAll(getPossibleCastleBlackMoves());
		return wMoves;
	}

	/**
	 * @param K
	 * @param YP
	 * @return
	 */
	public static ArrayList<Integer> getPossibleKingMoves(long K, long YP, long YR, long YN, long YB, long YQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wKMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long attackRange;
		int move = 0, temp = 0;
		int pLoc = Long.numberOfTrailingZeros(K);
		// System.out.println(pLoc + "- white ing pos :"+ WK);
		if (pLoc > 9) {
			attackRange = KING_SPAN << (pLoc - 9);
		} else {
			attackRange = KING_SPAN >> (9 - pLoc);
		}
		if (pLoc % 8 < 4) {
			attackRange &= ~FILE_GH & NOT_MY_PIECES;
		} else {
			attackRange &= ~FILE_AB & NOT_MY_PIECES;
		}
		long j = attackRange & ~(attackRange - 1);
		while (j != 0) {
			int index = Long.numberOfTrailingZeros(j);
			// printBoard();
			move |= pLoc;
			move |= index << 8;
			if ((j & YOUR_PIECES) != 0) {
				if ((YP & j) != 0)
					temp = PAWN;
				if ((YR & j) != 0)
					temp = ROOK;
				if ((YN & j) != 0)
					temp = KNIGHT;
				if ((YB & j) != 0)
					temp = BISHOP;
				if ((YQ & j) != 0)
					temp = QUEEN;
			}
			move |= temp << 24;
			wKMoves.add(move);
			move = 0;
			attackRange &= ~j;
			j = attackRange & ~(attackRange - 1);
		}
		return wKMoves;
	}

	/**
	 * @param Q
	 * @param YP
	 * @return
	 */
	public static ArrayList<Integer> getPossibleQueenMoves(long Q, long YP, long YR, long YN, long YB, long YQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wQMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long i = Q & ~(Q - 1);
		int move = 0;
		long possibility;
		while (i != 0) {
			int pLoc = Long.numberOfTrailingZeros(i);
			possibility = (moveslDrD(pLoc) | movesHV(pLoc)) & NOT_MY_PIECES;
			long j = possibility & ~(possibility - 1);
			long temp = 0;
			while (j != 0) {
				int index = Long.numberOfTrailingZeros(j);
				move |= pLoc;
				move |= index << 8;
				if ((j & YOUR_PIECES) != 0) {
					if ((YP & j) != 0)
						temp = PAWN;
					if ((YR & j) != 0)
						temp = ROOK;
					if ((YN & j) != 0)
						temp = KNIGHT;
					if ((YB & j) != 0)
						temp = BISHOP;
					if ((YQ & j) != 0)
						temp = QUEEN;
				}
				move |= temp << 24;
				wQMoves.add(move);
				move = 0;
				possibility &= ~j;
				j = possibility & ~(possibility - 1);
			}
			Q &= ~i;
			i = Q & ~(Q - 1);
		}
		return wQMoves;
	}

	/**
	 * @param N
	 * @param YP
	 * @return
	 */
	public static ArrayList<Integer> getPossibleKnightMoves(long N, long YP, long YR, long YN, long YB, long YQ) {
		ArrayList<Integer> wNMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long p = N & ~(N - 1);
		int temp = 0, move = 0;
		long attackRange;
		while (p != 0) {
			int pLoc = Long.numberOfTrailingZeros(p);
			if (pLoc > 18) {
				attackRange = KNIGHT_SPAN << (pLoc - 18);
			} else {
				attackRange = KNIGHT_SPAN >> (18 - pLoc);
			}
			if (pLoc % 8 < 4) {
				attackRange &= ~FILE_GH & NOT_MY_PIECES;
			} else {
				attackRange &= ~FILE_AB & NOT_MY_PIECES;
			}
			long j = attackRange & ~(attackRange - 1);
			while (j != 0) {
				int index = Long.numberOfTrailingZeros(j);
				move |= pLoc;
				move |= index << 8;
				if ((j & YOUR_PIECES) != 0) {
					if ((YP & j) != 0)
						temp = PAWN;
					if ((YR & j) != 0)
						temp = ROOK;
					if ((YN & j) != 0)
						temp = KNIGHT;
					if ((YB & j) != 0)
						temp = BISHOP;
					if ((YQ & j) != 0)
						temp = QUEEN;
				}
				move |= temp << 24;
				wNMoves.add(move);
				move = 0;
				attackRange &= ~j;
				j = attackRange & ~(attackRange - 1);
			}
			N &= ~p;
			p = N & ~(N - 1);
		}
		return wNMoves;
	}

	/**
	 * @param B
	 * @param YP
	 * @return
	 */
	public static ArrayList<Integer> getPossibleBishopMoves(long B, long YP, long YR, long YN, long YB, long YQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wBMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long i = B & ~(B - 1);
		int move = 0;
		long possibility;
		while (i != 0) {
			int pLoc = Long.numberOfTrailingZeros(i);
			possibility = moveslDrD(pLoc) & NOT_MY_PIECES;
			long j = possibility & ~(possibility - 1);
			long temp = 0;
			while (j != 0) {
				int index = Long.numberOfTrailingZeros(j);
				move |= pLoc;
				move |= index << 8;
				if ((j & YOUR_PIECES) != 0) {
					if ((YP & j) != 0)
						temp = PAWN;
					if ((YR & j) != 0)
						temp = ROOK;
					if ((YN & j) != 0)
						temp = KNIGHT;
					if ((YB & j) != 0)
						temp = BISHOP;
					if ((YQ & j) != 0)
						temp = QUEEN;
				}
				move |= temp << 24;
				wBMoves.add(move);
				move = 0;
				possibility &= ~j;
				j = possibility & ~(possibility - 1);
			}
			B &= ~i;
			i = B & ~(B - 1);
		}
		return wBMoves;
	}

	/**
	 * @param R
	 * @param YP
	 * @return
	 */
	public static ArrayList<Integer> getPossibleRookMoves(long R, long YP, long YR, long YN, long YB, long YQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> RMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long i = R & ~(R - 1);
		int move = 0;
		long possibility;
		while (i != 0) {
			int pLoc = Long.numberOfTrailingZeros(i);
			possibility = movesHV(pLoc) & NOT_MY_PIECES;
			long j = possibility & ~(possibility - 1);
			long temp = 0;
			while (j != 0) {
				int index = Long.numberOfTrailingZeros(j);
				move |= pLoc;
				move |= index << 8;
				if ((j & YOUR_PIECES) != 0) {
					if ((YP & j) != 0)
						temp = PAWN;
					if ((YR & j) != 0)
						temp = ROOK;
					if ((YN & j) != 0)
						temp = KNIGHT;
					if ((YB & j) != 0)
						temp = BISHOP;
					if ((YQ & j) != 0)
						temp = QUEEN;
				}
				move |= temp << 24;
				RMoves.add(move);
				move = 0;
				possibility &= ~j;
				temp = 0;
				j = possibility & ~(possibility - 1);
			}
			R &= ~i;
			i = R & ~(R - 1);
		}
		return RMoves;
	}

	/**
	 * @param priorMoves
	 * @param WP
	 * @param BQ
	 * @param BB
	 * @param BN
	 * @param BR
	 * @param BP
	 * @return -
	 */
	public static ArrayList<Integer> getPossibleWhitePawnMoves() {
		// TODO Auto-generated method stub
		ArrayList<Integer> wPMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long PAWN_MOVES, nextSetBit = 0;
		int move = 0, temp = 0;
		// Right Capture
		// System.out.println("BLACK pieces");
		// drawBitboard(YOUR_PIECES);
		PAWN_MOVES = (WP >> 7) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_A);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 7);
			if ((BP & nextSetBit) != 0)
				temp = PAWN;
			if ((BR & nextSetBit) != 0)
				temp = ROOK;
			if ((BN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((BB & nextSetBit) != 0)
				temp = BISHOP;
			if ((BQ & nextSetBit) != 0)
				temp = QUEEN;

			move |= temp << 24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Left Capture
		PAWN_MOVES = (WP >> 9) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_H);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 9);
			if ((BP & nextSetBit) != 0)
				temp = PAWN;
			if ((BR & nextSetBit) != 0)
				temp = ROOK;
			if ((BN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((BB & nextSetBit) != 0)
				temp = BISHOP;
			if ((BQ & nextSetBit) != 0)
				temp = QUEEN;

			move |= temp << 24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Simple Push
		PAWN_MOVES = (WP >> 8) & EMPTY & ~RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			// System.out.println(nextSetBit + " ---- "+
			// Long.numberOfTrailingZeros(nextSetBit));
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 8);
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Double Push
		PAWN_MOVES = (WP >> 16) & (EMPTY) & (RANK_4) & (EMPTY >> 8);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 16);
			move |= DOUBLE_PUSH << 16;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// EnPassant - right
		nextSetBit = (WP << 1) & BP & RANK_5 & ~FILE_A & EP;
		if (nextSetBit != 0) {
			move = 0;
			move |= PAWN << 24;
			move |= EN_PASSANT << 16;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 1);
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 8) << 8;
			wPMoves.add(move);
		}
		// EnPassant - left
		nextSetBit = (WP >> 1) & BP & RANK_5 & ~FILE_H & EP;
		if (nextSetBit != 0) {
			move = 0;
			move |= PAWN << 24;
			move |= EN_PASSANT << 16;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 1);
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 8) << 8;
			wPMoves.add(move);
		}
		// Pawn Promotion capture right
		PAWN_MOVES = (WP >> 7) & YOUR_PIECES & RANK_8 & ~FILE_A;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 7);
			if ((BP & nextSetBit) != 0)
				temp = PAWN;
			if ((BR & nextSetBit) != 0)
				temp = ROOK;
			if ((BN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((BB & nextSetBit) != 0)
				temp = BISHOP;
			if ((BQ & nextSetBit) != 0)
				temp = QUEEN;
			move |= temp << 24;
			move |= PAWN_PROMOTION << 16;
			temp = move | (ROOK << 16);
			wPMoves.add(temp);
			temp = move | (KNIGHT << 16);
			wPMoves.add(temp);
			temp = move | (BISHOP << 16);
			wPMoves.add(temp);
			temp = move | (QUEEN << 16);
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}

		// Pawn Promotion capture left
		PAWN_MOVES = (WP >> 9) & YOUR_PIECES & RANK_8 & ~FILE_H;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 9);
			if ((BP & nextSetBit) != 0)
				temp = PAWN;
			if ((BR & nextSetBit) != 0)
				temp = ROOK;
			if ((BN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((BB & nextSetBit) != 0)
				temp = BISHOP;
			if ((BQ & nextSetBit) != 0)
				temp = QUEEN;
			move |= temp << 24;
			move |= PAWN_PROMOTION << 16;
			temp = move | (ROOK << 16);
			wPMoves.add(temp);
			temp = move | (KNIGHT << 16);
			wPMoves.add(temp);
			temp = move | (BISHOP << 16);
			wPMoves.add(temp);
			temp = move | (QUEEN << 16);
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}

		// Pawn Promotion Simple Push
		PAWN_MOVES = (WP >> 8) & EMPTY & RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 8);
			move |= PAWN_PROMOTION << 16;
			temp = move | (ROOK << 16);
			wPMoves.add(temp);
			temp = move | (KNIGHT << 16);
			wPMoves.add(temp);
			temp = move | (BISHOP << 16);
			wPMoves.add(temp);
			temp = move | (QUEEN << 16);
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		return wPMoves;
	}

	/**
	 * @param priorMoves
	 * @param BP
	 * @param WQ
	 * @param WB
	 * @param WN
	 * @param WR
	 * @param BP
	 * @return --
	 */
	public static ArrayList<Integer> getPossibleBlackPawnMoves() {
		// TODO Auto-generated method stub
		ArrayList<Integer> bPMoves = new ArrayList<Integer>(); /**
																 * Array list of
																 * moves
																 */
		long PAWN_MOVES, nextSetBit = 0;
		int move = 0, temp = 0;
		// Right Capture
		// System.out.println("BLACK pieces");
		// drawBitboard(YOUR_PIECES);
		PAWN_MOVES = (BP << 7) & (YOUR_PIECES) & ~(RANK_1) & ~(FILE_H);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 7);
			if ((WP & nextSetBit) != 0)
				temp = PAWN;
			if ((WR & nextSetBit) != 0)
				temp = ROOK;
			if ((WN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((WB & nextSetBit) != 0)
				temp = BISHOP;
			if ((WQ & nextSetBit) != 0)
				temp = QUEEN;

			move |= temp << 24;
			bPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Left Capture
		PAWN_MOVES = (BP << 9) & (YOUR_PIECES) & ~(RANK_1) & ~(FILE_A);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 9);
			if ((WP & nextSetBit) != 0)
				temp = PAWN;
			if ((WR & nextSetBit) != 0)
				temp = ROOK;
			if ((WN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((WB & nextSetBit) != 0)
				temp = BISHOP;
			if ((WQ & nextSetBit) != 0)
				temp = QUEEN;

			move |= temp << 24;
			bPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Simple Push
		PAWN_MOVES = (BP << 8) & EMPTY & ~RANK_1;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			// System.out.println(nextSetBit + " ---- "+
			// Long.numberOfTrailingZeros(nextSetBit));
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 8);
			bPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// Double Push
		PAWN_MOVES = (BP << 16) & (EMPTY) & (RANK_5) & (EMPTY << 8);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 16);
			move |= DOUBLE_PUSH << 16;
			bPMoves.add(move);
			move = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}
		// EnPassant - right
		nextSetBit = (BP >> 1) & WP & RANK_4 & ~FILE_H & EP;
		if (nextSetBit != 0) {
			move = 0;
			move |= PAWN << 24;
			move |= EN_PASSANT << 16;
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 1);
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 8) << 8;
			bPMoves.add(move);
		}
		// EnPassant - left
		nextSetBit = (BP << 1) & WP & RANK_4 & ~FILE_A & EP;
		if (nextSetBit != 0) {
			move = 0;
			move |= PAWN << 24;
			move |= EN_PASSANT << 16;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 1);
			move |= (Long.numberOfTrailingZeros(nextSetBit) + 8) << 8;
			bPMoves.add(move);
		}

		// Pawn Promotion capture right
		PAWN_MOVES = (BP << 7) & YOUR_PIECES & RANK_1 & ~FILE_H;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			// System.out.println("Helooooo.... SOMETHING SOMETHING...");
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 7);
			if ((WP & nextSetBit) != 0)
				temp = PAWN;
			if ((WR & nextSetBit) != 0)
				temp = ROOK;
			if ((WN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((WB & nextSetBit) != 0)
				temp = BISHOP;
			if ((WQ & nextSetBit) != 0)
				temp = QUEEN;
			move |= temp << 24;
			move |= PAWN_PROMOTION << 16;
			temp = move | (ROOK << 16);
			bPMoves.add(temp);
			temp = move | (KNIGHT << 16);
			bPMoves.add(temp);
			temp = move | (BISHOP << 16);
			bPMoves.add(temp);
			temp = move | (QUEEN << 16);
			bPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}

		// Pawn Promotion capture left
		PAWN_MOVES = (BP << 9) & YOUR_PIECES & RANK_1 & ~FILE_A;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 9);
			if ((WP & nextSetBit) != 0)
				temp = PAWN;
			if ((WR & nextSetBit) != 0)
				temp = ROOK;
			if ((WN & nextSetBit) != 0)
				temp = KNIGHT;
			if ((WB & nextSetBit) != 0)
				temp = BISHOP;
			if ((WQ & nextSetBit) != 0)
				temp = QUEEN;

			move |= temp << 24;
			move |= PAWN_PROMOTION << 16;
			temp = move | (ROOK << 16);
			bPMoves.add(temp);
			temp = move | (KNIGHT << 16);
			bPMoves.add(temp);
			temp = move | (BISHOP << 16);
			bPMoves.add(temp);
			temp = move | (QUEEN << 16);
			bPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}

		// Pawn Promotion Simple Push
		PAWN_MOVES = (BP << 8) & EMPTY & RANK_1;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES - 1);
		while (nextSetBit != 0) {
			move = 0;
			move |= (Long.numberOfTrailingZeros(nextSetBit)) << 8;
			move |= (Long.numberOfTrailingZeros(nextSetBit) - 8);
			move |= PAWN_PROMOTION << 16;
			temp = move | ROOK << 16;
			bPMoves.add(temp);
			temp = move | KNIGHT << 16;
			bPMoves.add(temp);
			temp = move | BISHOP << 16;
			bPMoves.add(temp);
			temp = move | QUEEN << 16;
			bPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES &= ~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES - 1);
		}

		return bPMoves;
	}

	public static ArrayList<Integer> getPossibleCastleWhiteMoves() {
		ArrayList<Integer> movesCastle = new ArrayList<Integer>();
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ | BK;
		int move = 0;
		long unSafe = unsafeForWhite();
		// Castle White King
		if (CWK) { // => that king and the rook, dint move till now, we shd
					// update this later.
			// positions between shd be unoccupied and safe!
			// printBoard();
			if (((1L << 63) & WR) != 0 && ((1L << 60) & WK) != 0) {
				// if(((unSafe |OCCUPIED) & ((1L<<61)|(1L<<62)))==0){
				if (((unSafe & ((1L << 61) | (1L << 62) | (1L << 60))) == 0)
						&& (OCCUPIED & ((1L << 61) | (1L << 62))) == 0) {
					move |= CASTLE << 16;
					move |= F_CWK << 16;
					move |= 60;
					move |= 62 << 8;
					movesCastle.add(move);
				}
			}
		}
		if (CWQ) { // => that king and the rook, dint move till now, we shd
					// update this later.
			// positions between shd be unoccupied and safe!
			move = 0;
			// printBoard();
			if (((1L << 56) & WR) != 0 && ((1L << 60) & WK) != 0) {
				// if(((unSafe |OCCUPIED) & ((1L<<58)|(1L<<59)))==0){
				if (((unSafe & ((1L << 58) | (1L << 59) | (1L << 60))) == 0)
						&& (OCCUPIED & ((1L << 57) | (1L << 58) | (1L << 59))) == 0) {
					move |= CASTLE << 16;
					move |= F_CWQ << 16;
					move |= 58 << 8;
					move |= 60;
					movesCastle.add(move);
				}
			}
		}
		return movesCastle;

	}

	public static ArrayList<Integer> getPossibleCastleBlackMoves() {
		ArrayList<Integer> movesCastle = new ArrayList<Integer>();
		int move = 0;
		long unSafe = unsafeForBlack();
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ | BK;
		// printBoard();
		// Castle White King
		if (CBK) { // => that king and the rook, dint move till now, we shd
					// update this later.
			// positions between shd be unoccupied and safe!
			// if(((unSafe |OCCUPIED) & ((1L<<5)|(1L<<6)))==0){
			if (((1L << 7) & BR) != 0 && ((1L << 4) & BK) != 0) {
				if (((unSafe & ((1L << 5) | (1L << 6) | (1L << 4))) == 0)
						&& (OCCUPIED & ((1L << 5) | (1L << 6))) == 0) {
					move |= CASTLE << 16;
					move |= F_CBK << 16;
					move |= 4;
					move |= 6 << 8;
					movesCastle.add(move);
				}
			}
		}
		move = 0;
		if (CBQ) { // => that king and the rook, dint move till now, we shd
					// update this later.
			// positions between shd be unoccupied and safe!
			// System.out.println("ayyaa idher ??");
			// drawBitboard(unSafe);
			// drawBitboard(OCCUPIED);
			// if(((unSafe |OCCUPIED) & ((1L<<2)|(1L<<3)))==0){
			if (((1L) & BR) != 0 && ((1L << 4) & BK) != 0) {
				if (((unSafe & ((1L << 2) | (1L << 3) | (1L << 4))) == 0)
						&& (OCCUPIED & ((1L << 1) | (1L << 2) | (1L << 3))) == 0) {
					move |= CASTLE << 16;
					move |= F_CBQ << 16;
					move |= 4;
					move |= 2 << 8;
					movesCastle.add(move);
				}
			}
		}
		return movesCastle;
	}

	public static long unsafeForWhite() {
		long unsafe;
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ | BK;
		// pawn
		unsafe = ((BP << 7) & ~FILE_H);// pawn capture right
		unsafe |= ((BP << 9) & ~FILE_A);// pawn capture left
		long possibility;
		// knight
		Long temp = BN;
		long i = temp & ~(temp - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			if (iLocation > 18) {
				possibility = KNIGHT_SPAN << (iLocation - 18);
			} else {
				possibility = KNIGHT_SPAN >> (18 - iLocation);
			}
			if (iLocation % 8 < 4) {
				possibility &= ~FILE_GH;
			} else {
				possibility &= ~FILE_AB;
			}
			unsafe |= possibility;
			temp &= ~i;
			i = temp & ~(temp - 1);
		}
		// bishop/queen
		long QB = BQ | BB;
		i = QB & ~(QB - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			possibility = moveslDrD(iLocation);
			unsafe |= possibility;
			QB &= ~i;
			i = QB & ~(QB - 1);
		}
		// rook/queen
		long QR = BQ | BR;
		i = QR & ~(QR - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			possibility = movesHV(iLocation);
			unsafe |= possibility;
			QR &= ~i;
			i = QR & ~(QR - 1);
		}
		// king
		int iLocation = Long.numberOfTrailingZeros(BK);
		if (iLocation > 9) {
			possibility = KING_SPAN << (iLocation - 9);
		} else {
			possibility = KING_SPAN >> (9 - iLocation);
		}
		if (iLocation % 8 < 4) {
			possibility &= ~FILE_GH;
		} else {
			possibility &= ~FILE_AB;
		}
		unsafe |= possibility;
		// System.out.println();
		// drawBitboard(unsafe);
		return unsafe;
	}

	public static long unsafeForBlack() {
		long unsafe;
		OCCUPIED = WP | WN | WB | WR | WQ | WK | BP | BN | BB | BR | BQ | BK;
		// pawn
		unsafe = ((WP >> 7) & ~FILE_A);// pawn capture right
		unsafe |= ((WP >> 9) & ~FILE_H);// pawn capture left
		long possibility;
		// knight
		long temp = WN;
		long i = temp & ~(temp - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			if (iLocation > 18) {
				possibility = KNIGHT_SPAN << (iLocation - 18);
			} else {
				possibility = KNIGHT_SPAN >> (18 - iLocation);
			}
			if (iLocation % 8 < 4) {
				possibility &= ~FILE_GH;
			} else {
				possibility &= ~FILE_AB;
			}
			unsafe |= possibility;
			temp &= ~i;
			i = temp & ~(temp - 1);
		}
		// bishop/queen
		long QB = WQ | WB;
		i = QB & ~(QB - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			possibility = moveslDrD(iLocation);
			unsafe |= possibility;
			QB &= ~i;
			i = QB & ~(QB - 1);
		}
		// rook/queen
		long QR = WQ | WR;
		i = QR & ~(QR - 1);
		while (i != 0) {
			int iLocation = Long.numberOfTrailingZeros(i);
			possibility = movesHV(iLocation);
			unsafe |= possibility;
			QR &= ~i;
			i = QR & ~(QR - 1);
		}
		// king
		int iLocation = Long.numberOfTrailingZeros(WK);
		if (iLocation > 9) {
			possibility = KING_SPAN << (iLocation - 9);
		} else {
			possibility = KING_SPAN >> (9 - iLocation);
		}
		if (iLocation % 8 < 4) {
			possibility &= ~FILE_GH;
		} else {
			possibility &= ~FILE_AB;
		}
		unsafe |= possibility;
		// System.out.println();
		// drawBitboard(unsafe);
		return unsafe;
	}

	public static void makeMove(int move, boolean bw) {
		EP = 0L;
		long[] R = { 0L, 0L, 0L }; /**
									 * To return the changed bit board and also
									 * tell which bit board is changed
									 */
		int from, to, promotedTo, moveType = 0, oppPieceKilled;
		from = move & 0x000000ff;
		to = (move & 0x0000ff00) >> 8;
		EPc++;
		if (EPc == 2) {
			EP = 0L;
			EPc = 0L;
		}
		promotedTo = (move & 0x000f0000) >> 16;
		moveType = (move & 0x00f00000) >> 16;
		oppPieceKilled = (move & 0xff000000) >> 24;
		// System.out.println(oppPieceKilled + " - piece killed");
		if (bw) {
			if (oppPieceKilled != 0) {
				if (oppPieceKilled == QUEEN) {
					BQ &= ~(1L << to);
				}
				if (oppPieceKilled == ROOK) {
					BR &= ~(1L << to);
				}
				if (oppPieceKilled == BISHOP) {
					BB &= ~(1L << to);
				}
				if (oppPieceKilled == KNIGHT) {
					BN &= ~(1L << to);
				}
				if (oppPieceKilled == PAWN) {
					if (moveType == EN_PASSANT) {
						BP &= ~(1L << (to + 8));
					} else {
						BP &= ~(1L << to);
					}
				}
			}
		} else {
			if (oppPieceKilled != 0) {
				if (oppPieceKilled == QUEEN) {
					WQ &= ~(1L << to);
				}
				if (oppPieceKilled == ROOK) {
					WR &= ~(1L << to);
				}
				if (oppPieceKilled == BISHOP) {
					WB &= ~(1L << to);
				}
				if (oppPieceKilled == KNIGHT) {
					WN &= ~(1L << to);
				}
				if (oppPieceKilled == PAWN) {
					if (moveType == EN_PASSANT) {
						WP &= ~(1L << (to - 8));
					} else {
						WP &= ~(1L << to);
					}
				}
			}
		}
		// Pawn Promotion:
		long temp = 0L;
		if (moveType == PAWN_PROMOTION) {
			if (bw) {
				WP &= ~(1L << from);
				if (promotedTo == QUEEN) {
					WQ |= (1L << to);
				}
				if (promotedTo == ROOK) {
					WR |= (1L << to);
				}
				if (promotedTo == BISHOP) {
					WB |= (1L << to);
				}
				if (promotedTo == KNIGHT) {
					WN |= (1L << to);
				}
			} else {
				BP &= ~(1L << from);
				if (promotedTo == QUEEN) {
					BQ |= (1L << to);
				}
				if (promotedTo == ROOK) {
					BR |= (1L << to);
				}
				if (promotedTo == BISHOP) {
					BB |= (1L << to);
				}
				if (promotedTo == KNIGHT) {
					BN |= (1L << to);
				}
			}
			return;
		}
		// Double Push
		if (moveType == DOUBLE_PUSH) {
			EP = FileMasks8[from % 8]; // file of the pawn position
			if (bw) {
				WP = (WP & ~(1L << from)) | (1L << to);
				EPc = 0L;
			} else {
				BP = (BP & ~(1L << from)) | (1L << to);
				EPc = 0L;
			}
			return;
		}
		// Castling
		if (moveType == CASTLE) {
			// System.out.print(fileTable[move & 0x000000ff]+""+rankTable[move &
			// 0x000000ff]+ "");
			// System.out.print(","+fileTable[(move &
			// 0x0000ff00)>>8]+""+rankTable[(move & 0x0000ff00)>>8]+ "-");
			if (promotedTo == F_CWK && bw) {
				// System.out.println("**************");
				// printBoard();
				// System.out.println("-------------");
				WR &= ~(1L << 63);
				WR |= (1L << 61);
				WK &= ~(1L << 60);
				WK |= (1L << 62);
				CWK = false;
				CWQ = false;
				// printBoard();
				// System.out.println("************");
			}
			if (promotedTo == F_CWQ && bw) {
				// System.out.println("**************");
				// printBoard();
				// System.out.println("-------------");
				// System.out.println("hello");
				WR &= ~(1L << 56);
				WR |= (1L << 59);
				WK &= ~(1L << 60);
				WK |= (1L << 58);
				CWQ = false;
				CWK = false;
				// printBoard();
				// System.out.println("**************");
			}
			if (promotedTo == F_CBK && (!bw)) {
				// System.out.println("**************");
				// printBoard();
				// System.out.println("-------------");
				BR &= ~(1L << 7);
				BR |= (1L << 5);
				BK &= ~(1L << 4);
				BK |= (1L << 6);
				CBK = false;
				CBQ = false;
				// printBoard();
				// System.out.println("**************");
			}
			if (promotedTo == F_CBQ && (!bw)) {
				// System.out.println("**************");
				// printBoard();
				// System.out.println("-------------");
				BR &= ~(1L);
				BR |= (1L << 3);
				BK &= ~(1L << 4);
				BK |= (1L << 2);
				CBQ = false;
				CBK = false;
				// printBoard();
				// System.out.println("**************");
			}
			return;
		}
		if (bw) {
			if ((WP & (1L << from)) != 0) {
				WP = (WP & ~(1L << from)) | (1L << to);
			}
			if ((WR & (1L << from)) != 0) {
				WR = (WR & ~(1L << from)) | (1L << to);
			}
			if ((WN & (1L << from)) != 0) {
				WN = (WN & ~(1L << from)) | (1L << to);
			}
			if ((WB & (1L << from)) != 0) {
				WB = (WB & ~(1L << from)) | (1L << to);
			}
			if ((WQ & (1L << from)) != 0) {
				WQ = (WQ & ~(1L << from)) | (1L << to);
			}
			if ((WQ & (1L << from)) != 0) {
				WQ = (WQ & ~(1L << from)) | (1L << to);
			}
			if ((WK & (1L << from)) != 0) {
				WK = (WK & ~(1L << from)) | (1L << to);
			}
		} else {
			if ((BP & (1L << from)) != 0) {
				BP = (BP & ~(1L << from)) | (1L << to);
			}
			if ((BR & (1L << from)) != 0) {
				BR = (BR & ~(1L << from)) | (1L << to);
			}
			if ((BN & (1L << from)) != 0) {
				BN = (BN & ~(1L << from)) | (1L << to);
			}
			if ((BB & (1L << from)) != 0) {
				BB = (BB & ~(1L << from)) | (1L << to);
			}
			if ((BQ & (1L << from)) != 0) {
				BQ = (BQ & ~(1L << from)) | (1L << to);
			}
			if ((BQ & (1L << from)) != 0) {
				BQ = (BQ & ~(1L << from)) | (1L << to);
			}
			if ((BK & (1L << from)) != 0) {
				BK = (BK & ~(1L << from)) | (1L << to);
			}
		}
		return;
	}

    public static int getFirstLegalMove(ArrayList<Integer> moves) {
		boolean CWKt, CWQt, CBKt, CBQt, bwt;
		long WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt, EPt, EPct;
		CWKt = CWK;CWQt = CWQ;CBKt = CBK;CBQt = CBQ;bwt = bw;WPt = WP;WRt = WR;WNt = WN;WBt = WB;WQt = WQ;WKt = WK;BPt = BP;BRt = BR;BNt = BN;BBt = BB;BQt = BQ;BKt = BK;EPt = EP;EPct = EPc;
	
        for (int i=0;i<moves.size();i++) {
            makeMove(moves.get(i), bwt);
            if (((WK&unsafeForWhite())==0 && bw) ||
                    ((BK&unsafeForBlack())==0 && !bw)) {
    			CWK = CWKt;CWQ = CWQt;CBK = CBKt;CBQ = CBQt;bw = bwt;WP = WPt;WR = WRt;WN = WNt;WB = WBt;WQ = WQt;WK = WKt;BP = BPt;BR = BRt;BN = BNt;BB = BBt;BQ = BQt;BK = BKt;EP = EPt;EPc = EPct;
                return i;
            }
            CWK = CWKt;CWQ = CWQt;CBK = CBKt;CBQ = CBQt;bw = bwt;WP = WPt;WR = WRt;WN = WNt;WB = WBt;WQ = WQt;WK = WKt;BP = BPt;BR = BRt;BN = BNt;BB = BBt;BQ = BQt;BK = BKt;EP = EPt;EPc = EPct;
        }
        CWK = CWKt;CWQ = CWQt;CBK = CBKt;CBQ = CBQt;bw = bwt;WP = WPt;WR = WRt;WN = WNt;WB = WBt;WQ = WQt;WK = WKt;BP = BPt;BR = BRt;BN = BNt;BB = BBt;BQ = BQt;BK = BKt;EP = EPt;EPc = EPct;
        return -1;
    }
    
	public static void orderMoves(ArrayList<Integer> moves){
		Collections.sort(moves, comparator);
	}
	public static void drawBitboard(long bitBoard) {
		String chessBoard[][] = new String[8][8];
		for (int i = 0; i < 64; i++) {
			chessBoard[i / 8][i % 8] = "";
		}
		for (int i = 0; i < 64; i++) {
			if (((bitBoard >>> i) & 1) == 1) {
				chessBoard[i / 8][i % 8] = "P";
			}
			if ("".equals(chessBoard[i / 8][i % 8])) {
				chessBoard[i / 8][i % 8] = " ";
			}
		}
		for (int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(chessBoard[i]));
		}
	}

	// Standard Routine: Source Online , A string to represent the board state,
	// whose turn etc.
	public static void importFEN(String fenString) {
		WP = 0;
		WN = 0;
		WB = 0;
		WR = 0;
		WQ = 0;
		WK = 0;
		BP = 0;
		BN = 0;
		BB = 0;
		BR = 0;
		BQ = 0;
		BK = 0;
		CWK = false;
		CWQ = false;
		CBK = false;
		CBQ = false;
		int charIndex = 0;
		int boardIndex = 0;
		while (fenString.charAt(charIndex) != ' ') {
			switch (fenString.charAt(charIndex++)) {
			case 'P':
				WP |= (1L << boardIndex++);
				break;
			case 'p':
				BP |= (1L << boardIndex++);
				break;
			case 'N':
				WN |= (1L << boardIndex++);
				break;
			case 'n':
				BN |= (1L << boardIndex++);
				break;
			case 'B':
				WB |= (1L << boardIndex++);
				break;
			case 'b':
				BB |= (1L << boardIndex++);
				break;
			case 'R':
				WR |= (1L << boardIndex++);
				break;
			case 'r':
				BR |= (1L << boardIndex++);
				break;
			case 'Q':
				WQ |= (1L << boardIndex++);
				break;
			case 'q':
				BQ |= (1L << boardIndex++);
				break;
			case 'K':
				WK |= (1L << boardIndex++);
				break;
			case 'k':
				BK |= (1L << boardIndex++);
				break;
			case '/':
				break;
			case '1':
				boardIndex++;
				break;
			case '2':
				boardIndex += 2;
				break;
			case '3':
				boardIndex += 3;
				break;
			case '4':
				boardIndex += 4;
				break;
			case '5':
				boardIndex += 5;
				break;
			case '6':
				boardIndex += 6;
				break;
			case '7':
				boardIndex += 7;
				break;
			case '8':
				boardIndex += 8;
				break;
			default:
				break;
			}
		}
		bw = (fenString.charAt(++charIndex) == 'w');
		charIndex += 2;
		while (fenString.charAt(charIndex) != ' ') {
			switch (fenString.charAt(charIndex++)) {
			case '-':
				break;
			case 'K':
				CWK = true;
				break;
			case 'Q':
				CWQ = true;
				break;
			case 'k':
				CBK = true;
				break;
			case 'q':
				CBQ = true;
				break;
			default:
				break;
			}
		}
		if (fenString.charAt(++charIndex) != '-') {
			EP = FileMasks8[fenString.charAt(charIndex++) - 'a'];
		}
	}

	public static void perftRoot(int depth) throws IOException {
		int move;
		int to, from;
		boolean CWKt, CWQt, CBKt, CBQt, bwt;
		long WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt, EPt, EPct;
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if (bw) {
			moves = MoveGen.getPossibleWhiteMoves();
		} else {
			moves = MoveGen.getPossibleBlackMoves();
		}
		for (int i = 0; i < moves.size(); i++) {
			CWKt = CWK;
			CWQt = CWQ;
			CBKt = CBK;
			CBQt = CBQ;
			bwt = bw;
			WPt = WP;
			WRt = WR;
			WNt = WN;
			WBt = WB;
			WQt = WQ;
			WKt = WK;
			BPt = BP;
			BRt = BR;
			BNt = BN;
			BBt = BB;
			BQt = BQ;
			BKt = BK;
			EPt = EP;
			EPct = EPc;
			move = moves.get(i);
			from = move & 0x000000ff;
			to = (move & 0x0000ff00) >> 8;
			if (to == 63 && from == 53) {
				System.out.println("check");
			}
			MoveGen.makeMove(move, bw);
			EPc = 0;
			// int r = System.in.read();
			// printBoard();
			// drawBitboard(MoveGen.unsafeForWhite());
			if (((WK & MoveGen.unsafeForWhite()) == 0 && bw) || ((BK & MoveGen.unsafeForBlack()) == 0 && !bw)) {
				// System.out.println("------before-------, turn = " + bw);
				// printBoard();
				// System.out.println("------before-------, turn = " + bw);
				if ((move & 0x00f00000) == 0) {// 'regular' move
					int start = move & 0x000000ff;
					if (bw) {
						if (((1L << start) & WKt) != 0) {
							CWK = false;
							CWQ = false;
						}
						if (((1L << start) & WRt & (1L << 63)) != 0) {
							CWK = false;
						}
						if (((1L << start) & WRt & (1L << 56)) != 0) {
							CWQ = false;
						}
					} else {
						if (((1L << start) & BKt) != 0) {
							CBK = false;
							CBQ = false;
						}
						if (((1L << start) & BRt & (1L << 7)) != 0) {
							CBK = false;
						}
						if (((1L << start) & BRt & 1L) != 0) {
							CBQ = false;
						}
					}
				}
				System.out.print(fileTable[move & 0x000000ff] + "" + rankTable[move & 0x000000ff] + "");
				System.out.print(
						"" + fileTable[(move & 0x0000ff00) >> 8] + "" + rankTable[(move & 0x0000ff00) >> 8] + "-");
				// MoveGen.printBoard();
				// System.out.println(MoveGen.CBK +""+MoveGen.CBQ+""+MoveGen.CWK
				// +""+MoveGen.CWQ+""+MoveGen.EP+""+MoveGen.EPc);
				// System.out.println("------after-------, turn = " + bw);
				// printBoard();
				// System.out.println("------after-------, turn = " + bw);
				perftMoveCounter = 0;
				bw = !bw;
				perft(depth + 1);
				// System.out.print(fileTable[move &
				// 0x000000ff]+""+rankTable[move & 0x000000ff]+ "");
				// System.out.print(","+fileTable[(move &
				// 0x0000ff00)>>8]+""+rankTable[(move & 0x0000ff00)>>8]+ "-");

				if (perftMoveCounter == 0)
					perftMoveCounter = 1;
				perftTotalMoveCounter += perftMoveCounter;
				System.out.println(perftMoveCounter);
				perftMoveCounter = 0;
			}
			CWK = CWKt;
			CWQ = CWQt;
			CBK = CBKt;
			CBQ = CBQt;
			bw = bwt;
			WP = WPt;
			WR = WRt;
			WN = WNt;
			WB = WBt;
			WQ = WQt;
			WK = WKt;
			BP = BPt;
			BR = BRt;
			BN = BNt;
			BB = BBt;
			BQ = BQt;
			BK = BKt;
			EP = EPt;
			EPc = EPct;
		}
		System.out.println("Moves:" + perftTotalMoveCounter);
	}

	public static void perft(int depth) throws IOException {
		int move, from, to;
		boolean CWKt, CWQt, CBKt, CBQt, bwt;
		// System.out.println(perftMoveCounter);
		long WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt, EPt, EPct;
		if (depth < MaxDepth) {
			ArrayList<Integer> moves = new ArrayList<Integer>();
			if (bw) {
				moves = MoveGen.getPossibleWhiteMoves();
			} else {
				moves = MoveGen.getPossibleBlackMoves();
			}
			// System.out.println(moves.size());
			for (int i = 0; i < moves.size(); i++) {
				CWKt = CWK;CWQt = CWQ;
				CBKt = CBK;
				CBQt = CBQ;
				bwt = bw;
				WPt = WP;
				WRt = WR;
				WNt = WN;
				WBt = WB;
				WQt = WQ;
				WKt = WK;
				BPt = BP;
				BRt = BR;
				BNt = BN;
				BBt = BB;
				BQt = BQ;
				BKt = BK;
				EPt = EP;
				EPct = EPc;
				move = moves.get(i);
				from = move & 0x000000ff;
				to = (move & 0x0000ff00) >> 8;
				// System.out.println("------before-------, turn = " + bw);
				// printBoard();
				// System.out.println("------before-------, turn = " + bw);

				// System.out.print(fileTable[move & 0x000000ff]+"
				// "+rankTable[move & 0x000000ff]+ " ");
				// System.out.println(", "+fileTable[(move & 0x0000ff00)>>8]+"
				// "+rankTable[(move & 0x0000ff00)>>8]+ " - ");
				// printBoard();
				MoveGen.makeMove(move, bw);
				// System.out.println("------after-------, turn = " + bw);
				// printBoard();
				// System.out.println("------after-------, turn = " + bw);
				// int r = System.in.read();
				// System.out.println("IN:"+i + " depth - "+depth + " "+WP+"
				// "+WR+" "+WN+" "+WB+" "+WQ+" "+WK+" "+BP+" "+BN+" "+BRt+ " "+
				// BB+" "+BQ+" "+BK+" "+EP+" "+CWK+" "+CWQ+" "+CBK+" "+CBQ+"
				// "+bw);
				// System.out.println("INt:"+i + " depth - "+depth + " "+WPt+ "
				// "+WRt + " "+ WNt+" "+WBt+" "+WQt+" "+WKt+" "+BPt+" "+BRt+ "
				// "+ BNt+" "+BBt+" "+BQt+" "+BKt+" "+EPt+" "+CWKt+" "+CWQt+"
				// "+CBKt+" "+CBQt+" "+bwt);

				// System.out.println(bw + " - bw, depth = "+ depth + " , move
				// no = "+ i);

				if (((WK & MoveGen.unsafeForWhite()) == 0 && bw) || ((BK & MoveGen.unsafeForBlack()) == 0 && !bw)) {
					// System.out.println("yo");
					if ((move & 0x00f00000) == 0) {// 'regular' move
						int start = move & 0x000000ff;
						if (bw) {
							if (((1L << start) & WKt) != 0) {
								CWKt = false;
								CWQ = false;
							}
							if (((1L << start) & WRt & (1L << 63)) != 0) {
								CWK = false;
							}
							if (((1L << start) & WRt & (1L << 56)) != 0) {
								CWQ = false;
							}
							if ((move | 0xff000000) >> 24 == ROOK) {
								if (((1L << to) & BRt & (1L << 7)) != 0) {
									CBK = false;
								}
								if (((1L << to) & BRt & (1L)) != 0) {
									CBQ = false;
								}
							}
						} else {
							if (((1L << start) & BRt & (1L << 7)) != 0) {
								CBK = false;
							}
							if (((1L << start) & BRt & 1L) != 0) {
								CBQ = false;
							}
							if (((1L << start) & BKt) != 0) {
								CBKt = false;
								CBQ = false;
							}
							if ((move | 0xff000000) >> 24 == ROOK) {
								if (((1L << to) & WRt & (1L << 63)) != 0) {
									CWK = false;
								}
								if (((1L << to) & BRt & (1L << 56)) != 0) {
									CWQ = false;
								}
							}
						}
					}
					bw = !bw;
					if (depth + 1 == MaxDepth) {
						perftMoveCounter++;
					}
					perft(depth + 1);
				}
				CWK = CWKt;
				CWQ = CWQt;
				CBK = CBKt;
				CBQ = CBQt;
				bw = bwt;
				WP = WPt;
				WR = WRt;
				WN = WNt;
				WB = WBt;
				WQ = WQt;
				WK = WKt;
				BP = BPt;
				BR = BRt;
				BN = BNt;
				BB = BBt;
				BQ = BQt;
				BK = BKt;
				EP = EPt;
				EPc = EPct;
			}
		}
	}
}
