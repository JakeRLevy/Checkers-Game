
import java.util.ArrayList;
import java.util.Collections;
/*
 * In this file 
 * WHITE:  MIN
 * BLACK:   MAX
 */
/**
 * 
 * @author Jake Levy
 *
 */
public class MiniMaxSearch {

/**
 * Author: Jake Levy
 * Edited by: Jesse Kurtz
 * @param currentGame
 * @param currentDepth
 * @param maxDepth
 * @param currentMove
 * @return  int bestScore for given player (max or min)
 */
	final static int INFINITY = Integer.MAX_VALUE;
	public static int MiniMax(CheckerBoard currentGame, int currentDepth, int maxDepth, int[] actualMove)
	{	
		int[][] board = currentGame.getBoard();
		int turn = currentGame.whoseTurn();
		boolean orientation = currentGame.getOrientation();
		
		return MiniMax(board,turn,orientation, currentDepth, maxDepth, actualMove, -INFINITY, INFINITY);
	}
	private static int nextTurn(int currentTurn)
	{
		if (currentTurn == CheckerBoard.bPiece)
			return CheckerBoard.wPiece;
		else
			return CheckerBoard.bPiece;
				
	}
		private static int MiniMax(int[][] currentBoard, int currentTurn, boolean orientation, int currentDepth, int maxDepth, int[] actualMove, int alpha, int beta)
		{
			int bestScore;
			int currentScore = 0;
			ArrayList<CheckerMove> childrenMoves= new ArrayList<>();
			int[][] tempBoard, nextBoard;
			int[] bestMove = new int[4];
		
			CheckerBoard Node = new CheckerBoard(currentBoard, currentTurn, orientation);//create a node to test/evaluate
			
			
			//if at either maxDepth or if there are no moves for the current player's turn, evaluate the board we have and return the score
		if (maxDepth != 0 && (currentDepth == maxDepth || CheckerMove.endOfGame(Node)))//if we are at max depth or if this is a terminal state, return the evaluation of the board
			{//include a conditional to make sure that we have special code to handle reflexive play (maxDepth==0)
			//CheckerBoard leafNode = new CheckerBoard(currentBoard, currentTurn, orientation);
			 bestScore = Evaluation.TotalEvaluation(Node);
			return bestScore;
			}
		else//combine the node generation in the code above to remove redundancy and add a terminal test to the above option
		{
			childrenMoves = CheckerMove.generateMoves(Node);//generates all possible moves for a given game state, based on whose turn it is at the moment
			//if it is black's turn we are on a max node
			if (currentTurn == CheckerBoard.bPiece)
			{
				bestScore = alpha;//therefore set the best score to black's best score(max)
			}
			else//else if it is whites turn and we are on a min node
			{
				bestScore = beta;//therefore set the best score to white's best score(min)
			}
				//might remove this because its not completely necessary
			if (childrenMoves.size() == 1)
			{
				if (currentDepth == 0)//if there is only one move available at the very first depth, we must make that move no matter what
				{					//therefore there is no point in searching any deeper to find out the outcomes
					bestMove = childrenMoves.remove(0).getPositions();//return the only move in the arraylist this isn't working
					CheckerMove tempMove = new CheckerMove(bestMove);
					CheckerMove.move_board(Node, tempMove);
					bestScore = Evaluation.TotalEvaluation(Node);//call the evaluation on the modified board
					for (int z=0;z<bestMove.length;z++)
					{
						actualMove[z] = bestMove[z];
					}
					
					return bestScore;
					
				}
			}
			if (childrenMoves.size() == 0)//there are no moves, this is a terminal state
			{
		
				//CheckerBoard leafNode = new CheckerBoard(currentBoard, currentTurn, orientation);
				 bestScore = Evaluation.TotalEvaluation(Node);
				
				/*end the game*/
				return bestScore;
			}
			if (currentTurn == CheckerBoard.bPiece)//max node
			{
				/* Move Ordering:  Author: Jake Levy
				 * To implement the move ordering in minimax we have created two Comparators, a max and min
				 * to accomplish the ordering of moves, we first perform a "shallow search" by evaluating the 
				 * heuristic value of the childStates created by the generated moves.  then depending on whether it is
				 * max's (black) or min's (white) turn, we order the moves based on a specific comparator, descending order for Max
				 * (highest value moves first), ascending order for Min (lowest value moves first).  Because calculating the heuristic
				 * values for every possible node defeats the purpose of alpha beta pruning.  We only order the moves on the first 
				 * ply (ply 0).   Our hope is that ordering the first  ply will produce more effective cutoffs in search.  This is based on
				 * the intuition that the best possible future moves for the computer will be the eventual result of the best possible 
				 * immediate moves
				 */int resultScore;
				if(currentDepth == 0|| currentDepth==1){//if we are at the first or second ply
					for(CheckerMove each: childrenMoves)
					{
						//System.out.println("Max Sort");
						tempBoard = CheckerBoard.copy_board(currentBoard);
						CheckerBoard tempGame = new CheckerBoard(tempBoard, currentTurn, orientation);
						CheckerMove.move_board(tempGame, each);
						 resultScore = Evaluation.TotalEvaluation(tempGame);
						each.setResultVal(resultScore);//perform a shallow search on the board states
					}
					Collections.sort(childrenMoves, CheckerMove.MaxComparator);//sort the moves based the value of resulting board states

				}
				for (CheckerMove each: childrenMoves)
				{
					tempBoard = CheckerBoard.copy_board(currentBoard);
					CheckerBoard tempGame = new CheckerBoard(tempBoard, currentTurn, orientation);
					CheckerMove.move_board(tempGame, each);
					nextBoard = tempGame.getBoard();//retrieve the newly modified board
					currentScore = Math.max(alpha, MiniMax(nextBoard, nextTurn(currentTurn), orientation, currentDepth+1, maxDepth, actualMove, alpha, beta));
						if (currentScore>alpha)
						{
							alpha = currentScore;		
							bestMove = each.getPositions();//if this is a higher score than alpha, this is a better move
						}	
						
						if (alpha>=beta)
						{//System.out.println("Alpha CUTOFF at depth" + currentDepth);
							break;}
				
				}
				for (int z=0;z<bestMove.length;z++)
				{
					actualMove[z] = bestMove[z];
				}
				return alpha;
			}
			else//min node
			{
				if(currentDepth == 0 || currentDepth == 1){//if we are at the first or second ply
					for(CheckerMove each: childrenMoves)
					{
					//	System.out.println("min sort");
						tempBoard = CheckerBoard.copy_board(currentBoard);
						CheckerBoard tempGame = new CheckerBoard(tempBoard, currentTurn, orientation);
						CheckerMove.move_board(tempGame, each);
						int resultScore = Evaluation.TotalEvaluation(tempGame);
						each.setResultVal(resultScore);//perform a shallow search on the board states
					}
					Collections.sort(childrenMoves, CheckerMove.MinComparator);//sort the moves based the value of resulting board states

				}
				for (CheckerMove each: childrenMoves)
				{
					tempBoard = CheckerBoard.copy_board(currentBoard);
					CheckerBoard tempGame = new CheckerBoard(tempBoard, currentTurn, orientation);
					CheckerMove.move_board(tempGame, each);
					nextBoard = tempGame.getBoard();//retrieve the newly modified board
					currentScore = Math.min(beta, MiniMax(nextBoard, nextTurn(currentTurn), orientation, currentDepth+1, maxDepth, actualMove, alpha, beta));
						if (currentScore<beta)//if this score is lower than beta, this is min's best move
						{
							beta = currentScore;	
							bestMove = each.getPositions();//set the best move coordinates
							
						}	
						
						if (beta<=alpha){
						//	System.out.println("Beta cutoff at depth: "+ currentDepth);
							break;}
				
				}
				for (int z=0;z<bestMove.length;z++)
				{
					actualMove[z] = bestMove[z];
				}
				return beta;
			}
		
		

		}
		
	}

}

