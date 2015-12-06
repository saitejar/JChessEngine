import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author ranuva
 *
 */
public class AlphaBeta implements GlobalConstants{
	
	
	 static int V_WP[] = {0,  0,  0,  0,  0,  0,  0,  0,
	 50, 50, 50, 50, 50, 50, 50, 50,
	 10, 10, 20, 30, 30, 20, 10, 10,
	  5,  5, 10, 25, 25, 10,  5,  5,
	  0,  0,  0, 20, 20,  0,  0,  0,
	  5, -5,-10,  0,  0,-10, -5,  5,
	  5, 10, 10,-20,-20, 10, 10,  5,
	  0,  0,  0,  0,  0,  0,  0,  0};
	 
	 static int V_BP[] = { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 
			 5 , 10 , 10 , -20 , -20 , 10 , 10 , 5 , 
			 5 , -5 , -10 , 0 , 0 , -10 , -5 , 5 , 
			 0 , 0 , 0 , 20 , 20 , 0 , 0 , 0 , 
			 5 , 5 , 10 , 25 , 25 , 10 , 5 , 5 , 
			 10 , 10 , 20 , 30 , 30 , 20 , 10 , 10 , 
			 50 , 50 , 50 , 50 , 50 , 50 , 50 , 50 , 
			 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
	 
	 static int V_WN[] = {-50,-40,-30,-30,-30,-30,-40,-50,
			 -40,-20,  0,  0,  0,  0,-20,-40,
			 -30,  0, 10, 15, 15, 10,  0,-30,
			 -30,  5, 15, 20, 20, 15,  5,-30,
			 -30,  0, 15, 20, 20, 15,  0,-30,
			 -30,  5, 10, 15, 15, 10,  5,-30,
			 -40,-20,  0,  5,  5,  0,-20,-40,
			 -50,-40,-30,-30,-30,-30,-40,-50};
	 static int V_BN[] = {-50 , -40 , -30 , -30 , -30 , -30 , -40 , -50 , 
			 -40 , -20 , 0 , 5 , 5 , 0 , -20 , -40 , 
			 -30 , 5 , 10 , 15 , 15 , 10 , 5 , -30 , 
			 -30 , 0 , 15 , 20 , 20 , 15 , 0 , -30 , 
			 -30 , 5 , 15 , 20 , 20 , 15 , 5 , -30 , 
			 -30 , 0 , 10 , 15 , 15 , 10 , 0 , -30 , 
			 -40 , -20 , 0 , 0 , 0 , 0 , -20 , -40 , 
			 -50 , -40 , -30 , -30 , -30 , -30 , -40 , -50};
	 
	 static int V_WR[] = {  0,  0,  0,  0,  0,  0,  0,  0,
			  5, 10, 10, 10, 10, 10, 10,  5,
			  -5,  0,  0,  0,  0,  0,  0, -5,
			  -5,  0,  0,  0,  0,  0,  0, -5,
			  -5,  0,  0,  0,  0,  0,  0, -5,
			  -5,  0,  0,  0,  0,  0,  0, -5,
			  -5,  0,  0,  0,  0,  0,  0, -5,
			   0,  0,  0,  5,  5,  0,  0,  0};
	 
	 static int V_BR[] = { 0 , 0 , 0 , 5 , 5 , 0 , 0 , 0 , 
			 -5 , 0 , 0 , 0 , 0 , 0 , 0 , -5 , 
			 -5 , 0 , 0 , 0 , 0 , 0 , 0 , -5 , 
			 -5 , 0 , 0 , 0 , 0 , 0 , 0 , -5 , 
			 -5 , 0 , 0 , 0 , 0 , 0 , 0 , -5 , 
			 -5 , 0 , 0 , 0 , 0 , 0 , 0 , -5 , 
			 5 , 10 , 10 , 10 , 10 , 10 , 10 , 5 , 
			 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , };
	 static int V_WB[] = {-20,-10,-10,-10,-10,-10,-10,-20,
			 -10,  0,  0,  0,  0,  0,  0,-10,
			 -10,  0,  5, 10, 10,  5,  0,-10,
			 -10,  5,  5, 10, 10,  5,  5,-10,
			 -10,  0, 10, 10, 10, 10,  0,-10,
			 -10, 10, 10, 10, 10, 10, 10,-10,
			 -10,  5,  0,  0,  0,  0,  5,-10,
			 -20,-10,-10,-10,-10,-10,-10,-20};
	 static int V_BB[] = {-20 , -10 , -10 , -10 , -10 , -10 , -10 , -20 , 
			 -10 , 5 , 0 , 0 , 0 , 0 , 5 , -10 , 
			 -10 , 10 , 10 , 10 , 10 , 10 , 10 , -10 , 
			 -10 , 0 , 10 , 10 , 10 , 10 , 0 , -10 , 
			 -10 , 5 , 5 , 10 , 10 , 5 , 5 , -10 , 
			 -10 , 0 , 5 , 10 , 10 , 5 , 0 , -10 , 
			 -10 , 0 , 0 , 0 , 0 , 0 , 0 , -10 , 
			 -20 , -10 , -10 , -10 , -10 , -10 , -10 , -20};
	 
	 static int V_WQ[] = {-20,-10,-10, -5, -5,-10,-10,-20,
			 -10,  0,  0,  0,  0,  0,  0,-10,
			 -10,  0,  5,  5,  5,  5,  0,-10,
			  -5,  0,  5,  5,  5,  5,  0, -5,
			   0,  0,  5,  5,  5,  5,  0, -5,
			 -10,  5,  5,  5,  5,  5,  0,-10,
			 -10,  0,  5,  0,  0,  0,  0,-10,
			 -20,-10,-10, -5, -5,-10,-10,-20};
	 static int V_BQ[] = {-20 , -10 , -10 , -5 , -5 , -10 , -10 , -20 , 
			 -10 , 0 , 0 , 0 , 0 , 5 , 0 , -10 , 
			 -10 , 0 , 5 , 5 , 5 , 5 , 5 , -10 , 
			 -5 , 0 , 5 , 5 , 5 , 5 , 0 , 0 , 
			 -5 , 0 , 5 , 5 , 5 , 5 , 0 , -5 , 
			 -10 , 0 , 5 , 5 , 5 , 5 , 0 , -10 , 
			 -10 , 0 , 0 , 0 , 0 , 0 , 0 , -10 , 
			 -20 , -10 , -10 , -5 , -5 , -10 , -10 , -20};
	 
	 static int V_WK_MIDDLE[] = {-30,-40,-40,-50,-50,-40,-40,-30,
			 -30,-40,-40,-50,-50,-40,-40,-30,
			 -30,-40,-40,-50,-50,-40,-40,-30,
			 -30,-40,-40,-50,-50,-40,-40,-30,
			 -20,-30,-30,-40,-40,-30,-30,-20,
			 -10,-20,-20,-20,-20,-20,-20,-10,
			  20, 20,  0,  0,  0,  0, 20, 20,
			  20, 30, 10,  0,  0, 10, 30, 20};
	 
	 static int V_BK_MIDDLE[] = {20 , 30 , 10 , 0 , 0 , 10 , 30 , 20 , 
			 20 , 20 , 0 , 0 , 0 , 0 , 20 , 20 , 
			 -10 , -20 , -20 , -20 , -20 , -20 , -20 , -10 , 
			 -20 , -30 , -30 , -40 , -40 , -30 , -30 , -20 , 
			 -30 , -40 , -40 , -50 , -50 , -40 , -40 , -30 , 
			 -30 , -40 , -40 , -50 , -50 , -40 , -40 , -30 , 
			 -30 , -40 , -40 , -50 , -50 , -40 , -40 , -30 , 
			 -30 , -40 , -40 , -50 , -50 , -40 , -40 , -30};
	 
	 static int V_WK_END[] = {-50,-40,-30,-20,-20,-30,-40,-50,
			 			-30,-20,-10,  0,  0,-10,-20,-30,
			 			-30,-10, 20, 30, 30, 20,-10,-30,
			 			-30,-10, 30, 40, 40, 30,-10,-30,
			 			-30,-10, 30, 40, 40, 30,-10,-30,
			 			-30,-10, 20, 30, 30, 20,-10,-30,
			 			-30,-30,  0,  0,  0,  0,-30,-30,
			 			-50,-30,-30,-30,-30,-30,-30,-50};
	 static int V_BK_END[] = {-50 , -30 , -30 , -30 , -30 , -30 , -30 , -50 , 
		 -30 , -30 , 0 , 0 , 0 , 0 , -30 , -30 , 
		 -30 , -10 , 20 , 30 , 30 , 20 , -10 , -30 , 
		 -30 , -10 , 30 , 40 , 40 , 30 , -10 , -30 , 
		 -30 , -10 , 30 , 40 , 40 , 30 , -10 , -30 , 
		 -30 , -10 , 20 , 30 , 30 , 20 , -10 , -30 , 
		 -30 , -20 , -10 , 0 , 0 , -10 , -20 , -30 , 
		 -50 , -40 , -30 , -20 , -20 , -30 , -40 , -50};
 	 
	 
	public static int evaluate(long wp, long wr, long wn, long wb, long wq, long wk,
								long bp, long br, long bn, long bb, long bq, long bk){
		int eval =0;
		// TODO Auto-generated method stub
		String chessBoard[][] = new String[8][8];
		for (int i = 0; i < 64; i++) {
			chessBoard[i / 8][i % 8] = " ";
		}
		for (int i = 0; i < 64; i++) {
			if (((wp >> i) & 1) == 1) {
				eval+= V_WP[i];
			}
			if (((wn >> i) & 1) == 1) {
				eval+=V_WN[i];
			}
			if (((wb >> i) & 1) == 1) {
				eval+=V_WB[i];
			}
			if (((wr >> i) & 1) == 1) {
				eval+=V_WR[i];
			}
			if (((wq >> i) & 1) == 1) {
				eval+=V_WQ[i];
			}
			if (((wk >> i) & 1) == 1) {
				if((wr|wb|wn|wq|bb|br|bn|bq)==0 || (wq|bq)==0){
					eval-=V_BK_END[i];
				}
				else{
					eval-=V_BK_MIDDLE[i];
				}
			}
			if (((bp >> i) & 1) == 1) {
				eval-=V_BP[i];
			}
			if (((bn >> i) & 1) == 1) {
				eval-=V_BN[i];
			}
			if (((bb >> i) & 1) == 1) {
				eval-=V_BB[i];
			}
			if (((br >> i) & 1) == 1) {
				eval-=V_BR[i];
			}
			if (((bq >> i) & 1) == 1) {
				eval-=V_BQ[i];
			}
			if (((bk >> i) & 1) == 1) {
				if((wr|wb|wn|wq|bb|br|bn|bq)==0 || (wq|bq)==0){
					eval-=V_BK_END[i];
				}
				else{
					eval-=V_BK_MIDDLE[i];
				}
			}
		}
		
		return eval;
	}

    public static int zWSearch(int beta,int depth) {//fail-hard zero window search, returns either beta-1 or beta
    	//System.out.println("depth ="+ depth + ", bw = "+ MoveGen.bw );
        int score = Integer.MIN_VALUE, move = 0;
        long WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt, EPt, EPct;
        boolean CWKt,CWQt,CBKt,CBQt,bwt;
        CWKt = MoveGen.CWK;CWQt = MoveGen.CWQ;CBKt = MoveGen.CBK;
        CBQt = MoveGen.CBQ;bwt = MoveGen.bw;WPt = MoveGen.WP;
        WRt = MoveGen.WR;WNt = MoveGen.WN;WBt = MoveGen.WB;
        WQt = MoveGen.WQ;WKt = MoveGen.WK;BPt = MoveGen.BP;
        BRt = MoveGen.BR;BNt = MoveGen.BN;BBt = MoveGen.BB;
        BQt = MoveGen.BQ;BKt = MoveGen.BK;EPt = MoveGen.EP;
        EPct = MoveGen.EPc;
        //alpha == beta - 1
        //this is either a cut- or all-node
        if (depth == MoveGen.MaxDepth)
        {
            score = evaluate(MoveGen.WP,MoveGen.WR,MoveGen.WN,MoveGen.WB,
            MoveGen.WQ,MoveGen.WK,MoveGen.BP,
            MoveGen.BR,MoveGen.BN,MoveGen.BB,
            MoveGen.BQ,MoveGen.BK);
            //System.out.println("zero ");
            return score;
        }
        ArrayList<Integer> moves;
        if (MoveGen.bw) {
            moves=MoveGen.getPossibleWhiteMoves();
        } else {
            moves=MoveGen.getPossibleBlackMoves();
        }
        MoveGen.orderMoves(moves);
        for (int i=0;i<moves.size();i++) {
            CWKt = MoveGen.CWK;CWQt = MoveGen.CWQ;CBKt = MoveGen.CBK;
            CBQt = MoveGen.CBQ;bwt = MoveGen.bw;WPt = MoveGen.WP;
            WRt = MoveGen.WR;WNt = MoveGen.WN;WBt = MoveGen.WB;
            WQt = MoveGen.WQ;WKt = MoveGen.WK;BPt = MoveGen.BP;
            BRt = MoveGen.BR;BNt = MoveGen.BN;BBt = MoveGen.BB;
            BQt = MoveGen.BQ;BKt = MoveGen.BK;EPt = MoveGen.EP;EPct = MoveGen.EPc;
        	move = moves.get(i);
            int sc = Integer.MIN_VALUE;
            MoveGen.NodeCounter++;
            MoveGen.makeMove(move, MoveGen.bw);
            if (((MoveGen.WK & MoveGen.unsafeForWhite()) == 0 && MoveGen.bw) || ((MoveGen.BK & MoveGen.unsafeForBlack()) == 0 && !MoveGen.bw)) {
				if ((move & 0x00f00000) == 0) {// 'regular' move
					int start = move & 0x000000ff;
					if (MoveGen.bw) {
						if (((1L << start) & WKt) != 0) {
							MoveGen.CWK = false;
							MoveGen.CWQ = false;
						}
						if (((1L << start) & WRt & (1L << 63)) != 0) {
							MoveGen.CWK = false;
						}
						if (((1L << start) & WRt & (1L << 56)) != 0) {
							MoveGen.CWQ = false;
						}
					} else {
						if (((1L << start) & BKt) != 0) {
							MoveGen.CBK = false;
							MoveGen.CBQ = false;
						}
						if (((1L << start) & BRt & (1L << 7)) != 0) {
							MoveGen.CBK = false;
						}
						if (((1L << start) & BRt & 1L) != 0) {
							MoveGen.CBQ = false;
						}
					}
				}
                sc = -zWSearch(1 - beta,depth+1);
            }
            if (sc >= beta)
            {
                return sc;
            }
            MoveGen.CWK = CWKt;MoveGen.CWQ = CWQt;MoveGen.CBK = CBKt;MoveGen.CBQ = CBQt;MoveGen.bw = bwt;
            MoveGen.WP = WPt;MoveGen.WR = WRt;MoveGen.WN = WNt;MoveGen.WB = WBt;MoveGen.WQ = WQt;MoveGen.WK = WKt;
            MoveGen.BP = BPt;MoveGen.BR = BRt;MoveGen.BN = BNt;MoveGen.BB = BBt;MoveGen.BQ = BQt;MoveGen.BK = BKt;
            MoveGen.EP = EPt;MoveGen.EPc = EPct;
        }
        return beta - 1;
    }

    public static ArrayList<Integer> pvSearch(int alpha,int beta,int depth) {
        int bestScore, move, bestMoveIndex;   
        ArrayList<Integer> R = new ArrayList<Integer>();
        ArrayList<Integer> S = new ArrayList<Integer>();
        R.add(0);
        R.add(0);
        long WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt, EPt, EPct;
        boolean CWKt,CWQt,CBKt,CBQt,bwt;
        CWKt = MoveGen.CWK;CWQt = MoveGen.CWQ;CBKt = MoveGen.CBK;
        CBQt = MoveGen.CBQ;bwt = MoveGen.bw;WPt = MoveGen.WP;
        WRt = MoveGen.WR;WNt = MoveGen.WN;WBt = MoveGen.WB;
        WQt = MoveGen.WQ;WKt = MoveGen.WK;BPt = MoveGen.BP;
        BRt = MoveGen.BR;BNt = MoveGen.BN;BBt = MoveGen.BB;
        BQt = MoveGen.BQ;BKt = MoveGen.BK;EPt = MoveGen.EP;EPct = MoveGen.EPc;
        if (depth == MoveGen.MaxDepth)
        {
            bestScore = evaluate(MoveGen.WP,MoveGen.WR,MoveGen.WN,MoveGen.WB,
                    MoveGen.WQ,MoveGen.WK,MoveGen.BP,
                    MoveGen.BR,MoveGen.BN,MoveGen.BB,
                    MoveGen.BQ,MoveGen.BK);
            R.set(0, bestScore);
            R.set(1, null);
            return R;
        }
        ArrayList<Integer> moves = new ArrayList<Integer>();
        if (MoveGen.bw) {
            moves=MoveGen.getPossibleWhiteMoves();
        } else {
            moves=MoveGen.getPossibleWhiteMoves();
        }
        MoveGen.orderMoves(moves);
        int topMove = MoveGen.getFirstLegalMove(moves);
        if (topMove == -1)
        { 
            R.set(1, null);
            if(MoveGen.bw==true)
            	R.set(0, CHECK_MATE);
            //else
            //R.set(0, -CHECK_MATE);
            return R;
        }
        move = moves.get(topMove);
        MoveGen.makeMove(move, MoveGen.bw);
		if ((move & 0x00f00000) == 0) {
			int start = move & 0x000000ff;
			if (MoveGen.bw) {
				if (((1L << start) & WKt) != 0) {
					MoveGen.CWK = false;
					MoveGen.CWQ = false;
				}
				if (((1L << start) & WRt & (1L << 63)) != 0) {
					MoveGen.CWK = false;
				}
				if (((1L << start) & WRt & (1L << 56)) != 0) {
					MoveGen.CWQ = false;
				}
			} else {
				if (((1L << start) & BKt) != 0) {
					MoveGen.CBK = false;
					MoveGen.CBQ = false;
				}
				if (((1L << start) & BRt & (1L << 7)) != 0) {
					MoveGen.CBK = false;
				}
				if (((1L << start) & BRt & 1L) != 0) {
					MoveGen.CBQ = false;
				}
			}
		}
		MoveGen.bw = !MoveGen.bw;
        R = pvSearch(-beta,-alpha,depth+1);
        bestScore = -R.get(0);
        R.set(0, bestScore);
        MoveGen.NodeCounter++;
        if (Math.abs(bestScore) == CHECK_MATE)
        {
            R.set(1, move);
            R.set(0, bestScore);
            return R;
        }
        if (bestScore > alpha)
        {
        	bestMoveIndex = topMove;
            if (bestScore >= beta)
            {
                R.set(1, bestMoveIndex);
                R.set(0, bestScore);
                return R;
            }
            R.set(1, bestMoveIndex);
            R.set(0, bestScore);
            alpha = bestScore;
        }
        bestMoveIndex = topMove;
        MoveGen.CWK = CWKt;MoveGen.CWQ = CWQt;MoveGen.CBK = CBKt;MoveGen.CBQ = CBQt;MoveGen.bw = bwt;
        MoveGen.WP = WPt;MoveGen.WR = WRt;MoveGen.WN = WNt;MoveGen.WB = WBt;MoveGen.WQ = WQt;MoveGen.WK = WKt;
        MoveGen.BP = BPt;MoveGen.BR = BRt;MoveGen.BN = BNt;MoveGen.BB = BBt;MoveGen.BQ = BQt;MoveGen.BK = BKt;
        MoveGen.EP = EPt;MoveGen.EPc = EPct;
        for (int i=topMove+1;i<moves.size();i++) {
            CWKt = MoveGen.CWK;CWQt = MoveGen.CWQ;CBKt = MoveGen.CBK;
            CBQt = MoveGen.CBQ;bwt = MoveGen.bw;WPt = MoveGen.WP;
            WRt = MoveGen.WR;WNt = MoveGen.WN;WBt = MoveGen.WB;
            WQt = MoveGen.WQ;WKt = MoveGen.WK;BPt = MoveGen.BP;
            BRt = MoveGen.BR;BNt = MoveGen.BN;BBt = MoveGen.BB;
            BQt = MoveGen.BQ;BKt = MoveGen.BK;EPt = MoveGen.EP;EPct = MoveGen.EPc;
        	move = moves.get(i);
            int score = Integer.MIN_VALUE;
            MoveGen.NodeCounter++;
            MoveGen.makeMove(move, MoveGen.bw);
            if (((MoveGen.WK & MoveGen.unsafeForWhite()) == 0 && MoveGen.bw) || ((MoveGen.BK & MoveGen.unsafeForBlack()) == 0 && !MoveGen.bw)) {
				if ((move & 0x00f00000) == 0) {// 'regular' move
					int start = move & 0x000000ff;
					if (MoveGen.bw) {
						if (((1L << start) & WKt) != 0) {
							MoveGen.CWK = false;
							MoveGen.CWQ = false;
						}
						if (((1L << start) & WRt & (1L << 63)) != 0) {
							MoveGen.CWK = false;
						}
						if (((1L << start) & WRt & (1L << 56)) != 0) {
							MoveGen.CWQ = false;
						}
					} else {
						if (((1L << start) & BKt) != 0) {
							MoveGen.CBK = false;
							MoveGen.CBQ = false;
						}
						if (((1L << start) & BRt & (1L << 7)) != 0) {
							MoveGen.CBK = false;
						}
						if (((1L << start) & BRt & 1L) != 0) {
							MoveGen.CBQ = false;
						}
					}
				}
				MoveGen.bw = !MoveGen.bw;
	            score = -zWSearch(-alpha,depth+1);
	            if ((score > alpha) && (score < beta))
	            {
	                 S = pvSearch(-beta,-alpha,depth+1);
	                 score = -S.get(0);
	                 MoveGen.NodeCounter++;
	                 if (score>alpha)
	                 {
	                	R.set(0, alpha);
	                	R.set(1, i);
	                    bestMoveIndex = i;
	                    alpha = score;
	                 }
	            }
	            if ((score != Integer.MIN_VALUE) && (score > bestScore))
	            {
	                if (score >= beta)
	                {
	                	R.set(0, score);
	                	R.set(1, i);
	                    return R;
	                }
	                bestScore = score;
	                R.set(0, score);
	                R.set(1, i);
	                if (Math.abs(bestScore) == CHECK_MATE)
	                {
		                R.set(0, score);
		                R.set(1, i);
	                    return R;
	                }
	            }
            }
            MoveGen.CWK = CWKt;MoveGen.CWQ = CWQt;MoveGen.CBK = CBKt;MoveGen.CBQ = CBQt;MoveGen.bw = bwt;
            MoveGen.WP = WPt;MoveGen.WR = WRt;MoveGen.WN = WNt;MoveGen.WB = WBt;MoveGen.WQ = WQt;MoveGen.WK = WKt;
            MoveGen.BP = BPt;MoveGen.BR = BRt;MoveGen.BN = BNt;MoveGen.BB = BBt;MoveGen.BQ = BQt;MoveGen.BK = BKt;
            MoveGen.EP = EPt;MoveGen.EPc = EPct;
      	}
        return R;
    }
}