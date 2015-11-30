import java.util.ArrayList;
import java.util.Arrays;

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
public class MoveGen implements GlobalConstants, Utility{
	
	protected static long YOUR_PIECES;
	protected static long NOT_MY_PIECES;
	protected static long EMPTY;
	protected static long OCCUPIED;
	
    static long movesHV(int s)
    {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED&FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal&RankMasks8[s / 8]) | (possibilitiesVertical&FileMasks8[s % 8]);
    }
    
    static long moveslDrD(int s)
    {
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesDiagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
    }
   /**
   * This method is used to generate all the possible white moves
   * from the given bit board given configuration
   * Each move is encoded as follows. <br>
   * byte  meaning        example <br>
   * ==================================== <br>
   * 1    from-square 		 36 (A5) <br>
   * 2    to-square			 49 (B6) <br>
   * 3    flags  			 xx (enPassant capture or pawn promotion or castles or double pawn push) <br>
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
	public static ArrayList<Integer> getPossibleWhiteMoves(ArrayList<Integer> priorMoves,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK){
		
		ArrayList<Integer> wMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
		
		YOUR_PIECES = BP|BR|BN|BB|BQ; 								/** Black King is not included */
		NOT_MY_PIECES = ~(WP|WR|WN|WB|WQ|WK|BK);					/** Can initialize it to NOT_BLACK_PIECES or NOT_WHITE_PIECES */
		EMPTY = ~(WP|WR|WN|WB|WQ|WK|BP|BR|BN|BB|BQ|BK); 			/** Bit board of empty slots */
		OCCUPIED = WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;				/** Bit Board of occupied slots */
		wMoves.addAll(getPossibleWhitePawnMoves(priorMoves, WP, BP,BR,BN,BB,BQ)); 	/** Add pawn moves */
		System.out.println(wMoves + " yes");
		System.out.println(wMoves.size());
		wMoves.addAll(getPossibleRookMoves(WR, BP,BR,BN,BB,BQ)); 	/** Add rook moves */
		wMoves.addAll(getPossibleKnightMoves(WN, BP,BR,BN,BB,BQ)); /** Add Knight moves */
		wMoves.addAll(getPossibleBishopMoves(WB, BP,BR,BN,BB,BQ)); /** Add Bishop moves */
		wMoves.addAll(getPossibleQueenMoves(WQ, BP,BR,BN,BB,BQ)); 	/** Add Queen moves */
		wMoves.addAll(getPossibleKingMoves(WK, BP,BR,BN,BB,BQ)); 	/** Add King moves */
		System.out.println(wMoves);
		System.out.println(wMoves.size());
		BK = (unsafeForBlack(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK)) & BK;
		return wMoves;
	}

	/**
	* This method is used to generate all the possible white moves
	* from the given bit board given configuration
	* Each move is encoded as follows. <br>
	* byte  meaning        example <br>
	* ==================================== <br>
	* 1    from-square 		 36 (A5) <br>
	* 2    to-square			 49 (B6) <br>
	* 3    flags  			 xx (enPassant capture or pawn promotion or castles or double pawn push) <br>
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
	public static ArrayList<Integer> getPossibleBlackMoves(ArrayList<Integer> priorMoves,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK){
		
		ArrayList<Integer> wMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
		
		YOUR_PIECES = WP|WR|WN|WB|WQ; 								/** Black King is not included */
		NOT_MY_PIECES = ~(BP|BR|BN|BB|BQ|BK|BK);					/** Can initialize it to NOT_BLACK_PIECES or NOT_BHITE_PIECES */
		EMPTY = ~(WP|WR|WN|WB|WQ|WK|BP|BR|BN|BB|BQ|BK); 			/** Bit board of empty slots */
		OCCUPIED = WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;				/** Bit Board of occupied slots */
		wMoves.addAll(getPossibleWhitePawnMoves(priorMoves, WP, BP,BR,BN,BB,BQ)); 	/** Add pawn moves */
		System.out.println(wMoves + " yes");
		System.out.println(wMoves.size());
		wMoves.addAll(getPossibleRookMoves(WR, BP,BR,BN,BB,BQ)); 	/** Add rook moves */
		wMoves.addAll(getPossibleKnightMoves(WN, BP,BR,BN,BB,BQ)); /** Add Knight moves */
		wMoves.addAll(getPossibleBishopMoves(WB, BP,BR,BN,BB,BQ)); /** Add Bishop moves */
		wMoves.addAll(getPossibleQueenMoves(WQ, BP,BR,BN,BB,BQ)); 	/** Add Queen moves */
		wMoves.addAll(getPossibleKingMoves(WK, BP,BR,BN,BB,BQ)); 	/** Add King moves */
		System.out.println(wMoves);
		System.out.println(wMoves.size());
		BK = (unsafeForBlack(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK)) & BK;
		return wMoves;
	}

	/**
	 * @param priorMoves 
	 * @param WK
	 * @return
	 */
	public static ArrayList<Integer> getPossibleKingMoves(long WK, long BP, long BR, long BN, long BB, long BQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wKMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
        long attackRange;
        int move = 0, temp = 0;
        int pLoc=Long.numberOfTrailingZeros(WK);
        System.out.println(pLoc + "- white ing pos :"+ WK);
        if (pLoc>9)
        {
            attackRange=KING_SPAN<<(pLoc-9);
        }
        else {
            attackRange=KING_SPAN>>(9-pLoc);
        }
        if (pLoc%8<4)
        {
            attackRange &=~FILE_GH&NOT_MY_PIECES;
        }
        else {
            attackRange &=~FILE_AB&NOT_MY_PIECES;
        }
        long j=attackRange&~(attackRange-1);
        while (j != 0)
        {
            int index=Long.numberOfTrailingZeros(j);
            move |= pLoc;
            move |= index<<8;
            if((j&YOUR_PIECES)!=0){
    			if((BP&j)!=0)
    				temp = PAWN;
    			if((BR&j)!=0)
    				temp = ROOK;	
    			if((BN&j)!=0)
    				temp = KNIGHT;
    			if((BB&j)!=0)
    				temp = BISHOP;
    			if((BQ&j)!=0)
    				temp = QUEEN;
            }      
            move |= temp<<24;
            wKMoves.add(move);
            move = 0;
            attackRange&=~j;
            j=attackRange&~(attackRange-1);
        }
        return wKMoves;
	}
	
	/**
	 * @param priorMoves 
	 * @param Q
	 * @return
	 */
	public static ArrayList<Integer> getPossibleQueenMoves(long Q,long BP, long BR, long BN, long BB, long BQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wQMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
        long i=Q&~(Q-1);
        int move = 0;
        long possibility;
        while(i != 0)
        {
            int pLoc=Long.numberOfTrailingZeros(i);
            possibility=(moveslDrD(pLoc)|movesHV(pLoc))&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
			long temp = 0;
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move |= pLoc;
                move |= index<<8;
                if((j&YOUR_PIECES)!=0){
        			if((BP&j)!=0)
        				temp = PAWN;
        			if((BR&j)!=0)
        				temp = ROOK;	
        			if((BN&j)!=0)
        				temp = KNIGHT;
        			if((BB&j)!=0)
        				temp = BISHOP;
        			if((BQ&j)!=0)
        				temp = QUEEN;
                }
                move |= temp<<24;
                wQMoves.add(move);
                move = 0;
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            Q&=~i;
            i=Q&~(Q-1);
        }
		return wQMoves;
	}
	
	/**
	 * @param priorMoves 
	 * @param N
	 * @return
	 */
	public static ArrayList<Integer> getPossibleKnightMoves(long N, long BP, long BR, long BN, long BB, long BQ) {
		ArrayList<Integer> wNMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
        long p=N&~(N-1); 
        int temp = 0, move=0;
        long attackRange;
        while(p != 0)
        {
            int pLoc=Long.numberOfTrailingZeros(p);
            if (pLoc>18)
            {
                attackRange=KNIGHT_SPAN<<(pLoc-18);
            }
            else {
                attackRange=KNIGHT_SPAN>>(18-pLoc);
            }
            if (pLoc%8<4)
            {
                attackRange &=~FILE_GH&NOT_MY_PIECES;
            }
            else {
                attackRange &=~FILE_AB&NOT_MY_PIECES;
            }
            long j=attackRange&~(attackRange-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move |= pLoc;
                move |= index<<8;
                if((j&YOUR_PIECES)!=0){
        			if((BP&j)!=0)
        				temp = PAWN;
        			if((BR&j)!=0)
        				temp = ROOK;	
        			if((BN&j)!=0)
        				temp = KNIGHT;
        			if((BB&j)!=0)
        				temp = BISHOP;
        			if((BQ&j)!=0)
        				temp = QUEEN;
                }      
                move |= temp<<24;
                wNMoves.add(move);
                move = 0;
                attackRange&=~j;
                j=attackRange&~(attackRange-1);
            }
            N&=~p;
            p=N&~(N-1);
        }
        return wNMoves;
    }

	/**
	 * @param priorMoves 
	 * @param B
	 * @return
	 */
	public static ArrayList<Integer> getPossibleBishopMoves(long B,long BP, long BR, long BN, long BB, long BQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wBMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
        long i=B&~(B-1);
        int move = 0;
        long possibility;
        while(i != 0)
        {
            int pLoc=Long.numberOfTrailingZeros(i);
            possibility=moveslDrD(pLoc)&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
			long temp = 0;
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move |= pLoc;
                move |= index<<8;
                if((j&YOUR_PIECES)!=0){
        			if((BP&j)!=0)
        				temp = PAWN;
        			if((BR&j)!=0)
        				temp = ROOK;	
        			if((BN&j)!=0)
        				temp = KNIGHT;
        			if((BB&j)!=0)
        				temp = BISHOP;
        			if((BQ&j)!=0)
        				temp = QUEEN;
                }
                move |= temp<<24;
                wBMoves.add(move);
                move = 0;
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            B&=~i;
            i=B&~(B-1);
        }
		return wBMoves;
	}
	
	/**
	 * @param priorMoves 
	 * @param R
	 * @return
	 */
	public static ArrayList<Integer> getPossibleRookMoves(long R, long BP, long BR, long BN, long BB, long BQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wRMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
        long i=R&~(R-1);
        int move = 0;
        long possibility;
        while(i != 0)
        {
            int pLoc=Long.numberOfTrailingZeros(i);
            possibility=movesHV(pLoc)&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
			long temp = 0;
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move |= pLoc;
                move |= index<<8;
                if((j&YOUR_PIECES)!=0){
        			if((BP&j)!=0)
        				temp = PAWN;
        			if((BR&j)!=0)
        				temp = ROOK;	
        			if((BN&j)!=0)
        				temp = KNIGHT;
        			if((BB&j)!=0)
        				temp = BISHOP;
        			if((BQ&j)!=0)
        				temp = QUEEN;
                }
                move |= temp<<24;
                wRMoves.add(move);
                move = 0;
                possibility&=~j;
                temp = 0;
                j=possibility&~(possibility-1);
            }
            R&=~i;
            i=R&~(R-1);
        }
		return wRMoves;
	}
	
	/**
	 * @param priorMoves 
	 * @param WP
	 * @param BQ 
	 * @param BB 
	 * @param BN 
	 * @param BR 
	 * @param BP 
	 * @return
	 */
	public static ArrayList<Integer> getPossibleWhitePawnMoves(ArrayList<Integer> priorMoves, long WP, long BP, long BR, long BN, long BB, long BQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wPMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
		long PAWN_MOVES, nextSetBit = 0;
		int move = 0, temp = 0;
		// Right Capture
		System.out.println("BLACK pieces");
		drawBitboard(YOUR_PIECES);
		PAWN_MOVES = (WP>>7) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_A);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+7);
			if((BP&nextSetBit)!=0)
				temp = PAWN;
			if((BR&nextSetBit)!=0)
				temp = ROOK;	
			if((BN&nextSetBit)!=0)
				temp = KNIGHT;
			if((BB&nextSetBit)!=0)
				temp = BISHOP;
			if((BQ&nextSetBit)!=0)
				temp = QUEEN;
			
			move |= temp<<24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Left Capture
		PAWN_MOVES = (WP>>9) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_H);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+9);
			if((BP&nextSetBit)!=0)
				temp = PAWN;
			if((BR&nextSetBit)!=0)
				temp = ROOK;	
			if((BN&nextSetBit)!=0)
				temp = KNIGHT;
			if((BB&nextSetBit)!=0)
				temp = BISHOP;
			if((BQ&nextSetBit)!=0)
				temp = QUEEN;
			
			move |= temp<<24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Simple Push
		PAWN_MOVES = (WP>>8)&EMPTY&~RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			System.out.println(nextSetBit + " ---- "+ Long.numberOfTrailingZeros(nextSetBit));
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+8);
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Double Push
		PAWN_MOVES = (WP>>16)&(EMPTY)&(RANK_4)&(EMPTY>>8);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+16);
			move |= DOUBLE_PUSH<<16;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// EnPassant - add later
		/*if(priorMoves.isEmpty()!=true){
			move = priorMoves.get(priorMoves.size()-1); 
			int diff = ((move&(0xff000000))>>3) - ((move&(0x00ff0000)) >>2);
			if(Math.abs(diff) == 16){
				
			}
			if()
		}*/
		
		// Pawn Promotion capture right
		PAWN_MOVES=(WP>>7)&YOUR_PIECES&RANK_8&~FILE_A;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+7);
			if((BP&nextSetBit)!=0)
				temp = PAWN;
			if((BR&nextSetBit)!=0)
				temp = ROOK;	
			if((BN&nextSetBit)!=0)
				temp = KNIGHT;
			if((BB&nextSetBit)!=0)
				temp = BISHOP;
			if((BQ&nextSetBit)!=0)
				temp = QUEEN;
			move |= temp<<24;
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		// Pawn Promotion capture left
		PAWN_MOVES=(WP>>9)&YOUR_PIECES&RANK_8&~FILE_H;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+9);
			if((BP&nextSetBit)!=0)
				temp = PAWN;
			if((BR&nextSetBit)!=0)
				temp = ROOK;	
			if((BN&nextSetBit)!=0)
				temp = KNIGHT;
			if((BB&nextSetBit)!=0)
				temp = BISHOP;
			if((BQ&nextSetBit)!=0)
				temp = QUEEN;
			move |= temp<<24;
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		
		// Pawn Promotion Simple Push
		PAWN_MOVES=(WP>>8)&EMPTY&RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+8);
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		return wPMoves;
	}
	/**
	 * @param priorMoves 
	 * @param WP
	 * @param WQ 
	 * @param WB 
	 * @param WN 
	 * @param WR 
	 * @param WP 
	 * @return
	 */
	public static ArrayList<Integer> getPossibleBlackPawnMoves(ArrayList<Integer> priorMoves, long BP, long WP, long WR, long WN, long WB, long WQ) {
		// TODO Auto-generated method stub
		ArrayList<Integer> wPMoves = new ArrayList<Integer>(); 		/** Array list of moves*/
		long PAWN_MOVES, nextSetBit = 0;
		int move = 0, temp = 0;
		// Right Capture
		System.out.println("BLACK pieces");
		drawBitboard(YOUR_PIECES);
		PAWN_MOVES = (WP<<7) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_A);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+7);
			if((WP&nextSetBit)!=0)
				temp = PAWN;
			if((WR&nextSetBit)!=0)
				temp = ROOK;	
			if((WN&nextSetBit)!=0)
				temp = KNIGHT;
			if((WB&nextSetBit)!=0)
				temp = BISHOP;
			if((WQ&nextSetBit)!=0)
				temp = QUEEN;
			
			move |= temp<<24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Left Capture
		PAWN_MOVES = (WP>>9) & (YOUR_PIECES) & ~(RANK_8) & ~(FILE_H);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+9);
			if((WP&nextSetBit)!=0)
				temp = PAWN;
			if((WR&nextSetBit)!=0)
				temp = ROOK;	
			if((WN&nextSetBit)!=0)
				temp = KNIGHT;
			if((WB&nextSetBit)!=0)
				temp = BISHOP;
			if((WQ&nextSetBit)!=0)
				temp = QUEEN;
			
			move |= temp<<24;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Simple Push
		PAWN_MOVES = (WP>>8)&EMPTY&~RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			System.out.println(nextSetBit + " ---- "+ Long.numberOfTrailingZeros(nextSetBit));
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+8);
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// Double Push
		PAWN_MOVES = (WP>>16)&(EMPTY)&(RANK_4)&(EMPTY>>8);
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+16);
			move |= DOUBLE_PUSH<<16;
			wPMoves.add(move);
			move = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		// EnPassant - add later
		/*if(priorMoves.isEmpty()!=true){
			move = priorMoves.get(priorMoves.size()-1); 
			int diff = ((move&(0xff000000))>>3) - ((move&(0x00ff0000)) >>2);
			if(Math.abs(diff) == 16){
				
			}
			if()
		}*/
		
		// Pawn Promotion capture right
		PAWN_MOVES=(WP>>7)&YOUR_PIECES&RANK_8&~FILE_A;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+7);
			if((WP&nextSetBit)!=0)
				temp = PAWN;
			if((WR&nextSetBit)!=0)
				temp = ROOK;	
			if((WN&nextSetBit)!=0)
				temp = KNIGHT;
			if((WB&nextSetBit)!=0)
				temp = BISHOP;
			if((WQ&nextSetBit)!=0)
				temp = QUEEN;
			move |= temp<<24;
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		// Pawn Promotion capture left
		PAWN_MOVES=(WP>>9)&YOUR_PIECES&RANK_8&~FILE_H;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+9);
			if((WP&nextSetBit)!=0)
				temp = PAWN;
			if((WR&nextSetBit)!=0)
				temp = ROOK;	
			if((WN&nextSetBit)!=0)
				temp = KNIGHT;
			if((WB&nextSetBit)!=0)
				temp = BISHOP;
			if((WQ&nextSetBit)!=0)
				temp = QUEEN;
			move |= temp<<24;
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		
		// Pawn Promotion Simple Push
		PAWN_MOVES=(WP>>8)&EMPTY&RANK_8;
		nextSetBit = PAWN_MOVES & ~(PAWN_MOVES-1);
		while(nextSetBit!=0){
			move |= (Long.numberOfTrailingZeros(nextSetBit))<<8;
			move |= (Long.numberOfTrailingZeros(nextSetBit)+8);
			move |= PAWN_PROMOTION<<16;
			temp = move|ROOK<<16;
			wPMoves.add(temp);
			temp = move|KNIGHT<<16;
			wPMoves.add(temp);
			temp = move|BISHOP<<16;
			wPMoves.add(temp);
			temp = move|QUEEN<<16;
			wPMoves.add(temp);
			move = 0;
			temp = 0;
			PAWN_MOVES&=~nextSetBit;
			nextSetBit = (PAWN_MOVES) & ~(PAWN_MOVES-1);
		}
		
		return wPMoves;
	}
    public static long unsafeForWhite(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK)
    {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((BP<<7)&~FILE_A);//pawn capture right
        unsafe|=((BP<<9)&~FILE_H);//pawn capture left
        long possibility;
        //knight
        long i=BN&~(BN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            BN&=~i;
            i=BN&~(BN-1);
        }
        //bishop/queen
        long QB=BQ|BB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=moveslDrD(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=BQ|BR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=movesHV(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(BK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        System.out.println();
        drawBitboard(unsafe);
        return unsafe;
    }
    public static long unsafeForBlack(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK)
    {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((WP>>>7)&~FILE_A);//pawn capture right
        unsafe|=((WP>>>9)&~FILE_H);//pawn capture left
        long possibility;
        //knight
        long i=WN&~(WN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            WN&=~i;
            i=WN&~(WN-1);
        }
        //bishop/queen
        long QB=WQ|WB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=moveslDrD(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=WQ|WR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=movesHV(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(WK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        System.out.println();
        drawBitboard(unsafe);
        return unsafe;
    }
    
    public static void drawBitboard(long bitBoard) {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]="";
        }
        for (int i=0;i<64;i++) {
            if (((bitBoard>>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if ("".equals(chessBoard[i/8][i%8])) {chessBoard[i/8][i%8]=" ";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
}

