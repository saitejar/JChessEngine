/**
 * 
 */

/** An interface to dump all the constants required in the 
 *  chess engine. Any class which wants to access these variables 
 *  should just do an 'implements GlobalConstants'. 
 * @author ranuva
 *
 */
public interface GlobalConstants {
	/* Some useful 64 bit numbers to generate moves of pieces */
	public static final long FILE_A = 1L|1L<<8|1L<<16|1L<<24|1L<<32|1L<<40|1L<<48|1L<<56;
	public static final long FILE_AB = FILE_A|FILE_A<<1;
	public static final long FILE_H = FILE_A<<7;
	public static final long FILE_GH = FILE_H|FILE_H>>1;
	public static final long RANK_8 = 255L;
	public static final long RANK_5 = RANK_8<<24;
	public static final long RANK_4 = RANK_5<<8;
	public static final long RANK_1 = RANK_4<<24;
	
	/**
	 * 8*8 board:
	 * 
	 * - - - - - - - -
	 * - - - - - - - -
	 * - - - - - - - -
	 * - - - - A - A -
	 * - - - A - - - A
	 * - - - - - K - 
	 * - - - A - - - A
	 * - - - - A - A _
	 * 
	 * This number will set all A's to 1, rest zeros
	 * So, if we shift this number left or right, we get the attack
	 * positions of knight from various location.
	 */
	public static long KNIGHT_SPAN = 43234889994L;
	/**
	 * Similar to Knight Span
	 */
	static long KING_SPAN=460039L;
	
	public static final int rankTable[] = {8,8,8,8,8,8,8,8,
											7,7,7,7,7,7,7,7,
											6,6,6,6,6,6,6,6,
											5,5,5,5,5,5,5,5,
											4,4,4,4,4,4,4,4,
											3,3,3,3,3,3,3,3,
											2,2,2,2,2,2,2,2,
											1,1,1,1,1,1,1,1};
	
	public static final char fileTable[] = {'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h',
											'a','b','c','d','e','f','g','h'};
    static long RankMasks8[] =/*from rank1 to rank8*/
    {
        0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
    };
    static long FileMasks8[] =/*from fileA to FileH*/
    {
        0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };
    static long DiagonalMasks8[] =/*from top left to bottom right*/
    {
	0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
	0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
	0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
    };
    static long AntiDiagonalMasks8[] =/*from top right to bottom left*/
    {
	0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
	0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
	0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
    };
    
	public static final byte NO_FLAG = 0;
	public static final byte DOUBLE_PUSH = 1<<4;
	public static final byte EN_PASSANT = 1<<5;
	public static final byte PAWN_PROMOTION = 1<<6;
	public static final byte CASTLE = 48;
	
	public static final byte F_CWK = 7;
	public static final byte F_CWQ = 8;
	public static final byte F_CBK = 9;
	public static final byte F_CBQ = 10;
	
	// CODE to represent the piece in the 4th byte of move 
	public static final byte PAWN = 1;
	public static final byte ROOK = 5;
	public static final byte KNIGHT = 2;
	public static final byte BISHOP = 3;
	public static final byte QUEEN = 6;
}
