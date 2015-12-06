import java.util.*;

/**
 * @author ranuva
 *
 */

public class UCI implements GlobalConstants {
	static String ENGINENAME = "Knight";
	
	
	public static void uciCommunication() {
		Scanner input = new Scanner(System.in);
		while (true) {
			String inputString = input.nextLine();
			if ("uci".equals(inputString)) {
				inputUCI();
			} else if (inputString.startsWith("setoption")) {
				inputSetOption(inputString);
			} else if ("isready".equals(inputString)) {
				inputIsReady();
			} else if ("ucinewgame".equals(inputString)) {
				inputUCINewGame();
			} else if (inputString.startsWith("position")) {
				inputPosition(inputString);
			} else if (inputString.startsWith("go")) {
				inputGo();
			} else if (inputString.equals("quit")) {
				inputQuit();
			} else if ("print".equals(inputString)) {
				inputPrint();
			}
		}
	}

	public static void inputUCI() {
		System.out.println("id name " + ENGINENAME);
		System.out.println("id author ranuva");
		// options go here
		System.out.println("uciok");
	}

	public static void inputSetOption(String inputString) {
		// set options
	}

	public static void inputIsReady() {
		System.out.println("readyok");
	}

	public static void inputUCINewGame() {
		// This would initialize all the variables to initial state. (y) simple
		MoveGen.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	public static void inputPosition(String input) {
		input = input.substring(9).concat(" ");
		if (input.contains("startpos ")) {
			input = input.substring(9);
			MoveGen.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		} else if (input.contains("fen")) {
			input = input.substring(4);
			MoveGen.importFEN(input);
		}
		if (input.contains("moves")) {
			input = input.substring(input.indexOf("moves") + 6);
			while (input.length() > 0) {
				ArrayList<Integer> moves;
				if (MoveGen.bw) {
					moves = MoveGen.getPossibleWhiteMoves();
				} else {
					moves = MoveGen.getPossibleBlackMoves();
				}
				algebraToMove(input, moves);
				input = input.substring(input.indexOf(' ') + 1);
			}
		}
	}

	public static void inputGo() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		ArrayList<Integer> R = new ArrayList<Integer>();
		if (MoveGen.bw) {
			moves = MoveGen.getPossibleWhiteMoves();
		} else {
			moves = MoveGen.getPossibleBlackMoves();
		}
		//MoveGen.orderMoves(moves);
		//System.out.println("bestmove " + moveToAlgebra(moves.get(0)));
		//R = AlphaBeta.pvSearch(-10000, 10000, 0);
		int index = MoveGen.getFirstLegalMove(moves);
		System.out.println("bestmove " + moveToAlgebra(moves.get(index)));
		//getFirstLegalMove();
		//int index = (int) (Math.floor(Math.random() * (moves.size())));
		//System.out.println("bestmove " + moveToAlgebra(moves.get(R.get(1))));
	}

	public static String moveToAlgebra(int move) {
		String append = "";
		int start = 0, end = 0, promotedTo;
		start = move & 0x000000ff;
		end = (move & 0x0000ff00) >> 8;
		promotedTo = (move & 0x000f0000) >> 16;
		if (promotedTo == ROOK)
			append = "r";
		if (promotedTo == QUEEN)
			append = "q";
		if (promotedTo == BISHOP)
			append = "b";
		if (promotedTo == KNIGHT)
			append = "n";
		String returnMove = "";
		returnMove += (char) ('a' + (start % 8));
		returnMove += (char) ('8' - (start / 8));
		returnMove += (char) ('a' + (end % 8));
		returnMove += (char) ('8' - (end / 8));
		returnMove += append;
		return returnMove;
	}

	public static void algebraToMove(String input, ArrayList<Integer> moves) {
		int start = 0, end = 0, move, promotedTo;
		int from = (input.charAt(0) - 'a') + (8 * ('8' - input.charAt(1)));
		int to = (input.charAt(2) - 'a') + (8 * ('8' - input.charAt(3)));
		for (int i = 0; i < moves.size(); i++) {
			move = moves.get(i);
			start = move & 0x000000ff;
			end = (move & 0x0000ff00) >> 8;
			promotedTo = (move & 0x000f0000) >> 16;
			char x = input.charAt(4);
			int specialCh = 0;
			if (x == ' ') {
				specialCh = 0;
			}
			if (x == 'r') {
				specialCh = ROOK;
			}
			if (x == 'q') {
				specialCh = QUEEN;
			}
			if (x == 'b') {
				specialCh = BISHOP;
			}
			if (x == 'n') {
				specialCh = KNIGHT;
			}
			if ((start == from) && (end == to) && ((specialCh == promotedTo) || (specialCh == 0))) {
				if ((move & 0x00f00000) == 0) {// 'regular' move
					if (MoveGen.bw) {
						if (((1L << start) & MoveGen.WK) != 0) {
							MoveGen.CWK = false;
							MoveGen.CWQ = false;
						}
						if (((1L << start) & MoveGen.WR & (1L << 63)) != 0) {
							MoveGen.CWK = false;
						}
						if (((1L << start) & MoveGen.WR & (1L << 56)) != 0) {
							MoveGen.CWQ = false;
						}
						if ((move | 0xff000000) >> 24 == ROOK) {
							if (((1L << to) & MoveGen.BR & (1L << 7)) != 0) {
								MoveGen.CBK = false;
							}
							if (((1L << to) & MoveGen.BR & (1L)) != 0) {
								MoveGen.CBQ = false;
							}
						}
					} else {
						if (((1L << start) & MoveGen.BR & (1L << 7)) != 0) {
							MoveGen.CBK = false;
						}
						if (((1L << start) & MoveGen.BR & 1L) != 0) {
							MoveGen.CBQ = false;
						}
						if (((1L << start) & MoveGen.BK) != 0) {
							MoveGen.CBK = false;
							MoveGen.CBQ = false;
						}
						if ((move | 0xff000000) >> 24 == ROOK) {
							if (((1L << to) & MoveGen.WR & (1L << 63)) != 0) {
								MoveGen.CWK = false;
							}
							if (((1L << to) & MoveGen.BR & (1L << 56)) != 0) {
								MoveGen.CWQ = false;
							}
						}
					}
				}
				MoveGen.makeMove(move, MoveGen.bw);
				MoveGen.bw = !MoveGen.bw;
				break;
			}
		}
	}

	public static void inputQuit() {
		System.exit(0);
	}

	public static void inputPrint() {
		MoveGen.printBoard();
	}
}