import java.util.Arrays;

public class BoardInit implements Utility{
	
   /**
   * This method is used to initialize the chess board and set the 
   * bit board values for all the different pieces.
   * 
   */
	
	public static void chessInit(){
		/** long 64 bit integer to store bitboard positions of all the unique pieces.
		 * These 12 long variables are sufficient to represent the current configuration or state of the board
		 * WP - White Pawn
		 * WR - White Rook
		 * WN - White Knight
		 * WB - White Bishop
		 * WQ - White Queen
		 * WK - White King
		 * BP - Black Pawn
		 * BR - Black Rook
		 * BN - Black Knight
		 * BB - Black Bishop
		 * BQ - Black Queen
		 * BK - Black King
		 */
		long WP=0L,WR=0L,WN=0L,WB=0L,WQ=0L,WK=0L,BP=0L,BR=0L,BN=0L,BB=0L,BQ=0L,BK=0L;
		// Lower case to represent Black pieces. Upper case to represent White pieces
		String[][] chessBoard = {
                {"r","n","b","q","k","b","n","r"},
                {"p","p","p","p","p","p","P","p"},
                {" "," "," "," "," ","P"," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {" "," "," "," "," "," "," "," "},
                {"P","P","P"," "," "," ","P","P"},
                {"R","N","B","Q","K","B","N","R"}};
		stringToBitBoard(chessBoard,WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK);
	}
   /**
   * This method is used to set the bit board values of 
   * various pieces using the string representation of the 
   * chess board.
   * @param chessBoard  String representation of the state of chess board 
   * @param WP	Bit Board Representation of the positions of the White Pawns
   * @param WR	Bit Board Representation of the positions of the White Rooks
   * @param WN	Bit Board Representation of the positions of the White Knights
   * @param WB	Bit Board Representation of the positions of the White Bishops
   * @param WQ	Bit Board Representation of the positions of the White Queens
   * @param WK	Bit Board Representation of the position of the White King
   * @param BP	Bit Board Representation of the positions of the Black Pawns
   * @param BR	Bit Board Representation of the positions of the Black Rooks
   * @param BN	Bit Board Representation of the positions of the Black Knights
   * @param BB	Bit Board Representation of the positions of the Black Bishops
   * @param BQ	Bit Board Representation of the positions of the Black Queen
   * @param BK	Bit Board Representation of the position of the Black King
   * 
   * 
   */
	public static void stringToBitBoard(String[][] chessBoard,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK){
        for (int i=0;i<64;i++) {
        	long bitSet = 1L<<i;
            System.out.println(chessBoard[i/8][i%8]);
            switch (chessBoard[i/8][i%8]) {
                case "P":WP|=bitSet;
                    break;
                case "N": WN|=bitSet;
                    break;
                case "B": WB|=bitSet;
                    break;
                case "R": WR|=bitSet;
                    break;
                case "Q": WQ|=bitSet;
                    break;
                case "K": WK|=bitSet;
                    break;
                case "p": BP|=bitSet;
                    break;
                case "n": BN|=bitSet;
                    break;
                case "b": BB|=bitSet;
                    break;
                case "r": BR|=bitSet;
                    break;
                case "q": BQ|=bitSet;
                    break;
                case "k": BK|=bitSet;
                    break;
            }
        }
        System.out.println(WP + " here, please");
        printBoard(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK);
        Main.WP = WP; Main.WR = WR; Main.WN = WN; Main.WB = WB; Main.WQ = WQ; Main.WK = WK;
        Main.BP = BP; Main.BR = BR; Main.BN = BN; Main.BB = BB; Main.BQ = BQ; Main.BK = BK;
    }

   /**
   * This method is used to print the String representation of the board
   * from the bit boards values of all the unique pieces.
   *  
   * @param WP	Bit Board Representation of the positions of the White Pawns
   * @param WR	Bit Board Representation of the positions of the White Rooks
   * @param WN	Bit Board Representation of the positions of the White Knights
   * @param WB	Bit Board Representation of the positions of the White Bishops
   * @param WQ	Bit Board Representation of the positions of the White Queens
   * @param WK	Bit Board Representation of the position of the White King
   * @param BP	Bit Board Representation of the positions of the Black Pawns
   * @param BR	Bit Board Representation of the positions of the Black Rooks
   * @param BN	Bit Board Representation of the positions of the Black Knights
   * @param BB	Bit Board Representation of the positions of the Black Bishops
   * @param BQ	Bit Board Representation of the positions of the Black Queen
   * @param BK	Bit Board Representation of the position of the Black King
   */
	private static void printBoard(long WP, long WR, long WN, long WB, long WQ, long WK, long BP, long BR, long BN,
			long BB, long BQ, long BK) {
		// TODO Auto-generated method stub
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
        System.out.println(WP);
	}
}