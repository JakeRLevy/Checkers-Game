/*
 * In this file 
 * WHITE:  MIN
 * BLACK:   MAX
 */
import java.util.Random;
/**
 * 
 * @author Jake Levy and Jesse Kurtz
 *
 */
public class Evaluation {

	static int TotalScore;
	static int PawnScore;
	static int KingScore;
	static int EdgeScore;
	static int PosScore;
	static int BlockedHomeScore;
	static int vulnerabilityScore;
	static Random randFactor = new Random(System.currentTimeMillis());
	public static int TotalEvaluation(CheckerBoard currentGame)
	{
		int[][] gameBoard= currentGame.getBoard();
		boolean orientation = currentGame.getOrientation();
		int turn = currentGame.whoseTurn();
		TotalScore = EvaluationCountPawns(gameBoard) + EvaluationCountKings(gameBoard) + 
				NonOffensiveKings(gameBoard) + 	PosEval(gameBoard, orientation) + blockedHomeScore(gameBoard, orientation)+ vulnerablePos(gameBoard, orientation, turn); 
		//System.out.println("The total Score is: "+ TotalScore);
		//System.out.println("the Block Score is: " + blockedHomeScore(gameBoard, orientation));
	//	System.out.println("The Pawn Score is: " + EvaluationCountPawns(gameBoard) + " The King Score is : " +  
	//	EvaluationCountKings(gameBoard));
	//	System.out.println("The Non-offensive Kings Score is: " + NonOffensiveKings(gameBoard) + "and the positional score is: " + PosEval(gameBoard, orientation));
		 int randWeight = randFactor.nextInt(100);
		 int addweight = randWeight%10;
		 TotalScore += addweight;
		 
		 //These are a rnadomization factor so that the program does not always pick the exact same moves if all moves are equal
		return TotalScore;
	}
	
	/**
	 * this function was used to test the different evaluation functions and print the score to the screen for debugging.
	 * @param currentGame
	 */
	public static void printScore (CheckerBoard currentGame)
	{
		int[][] gameBoard = currentGame.getBoard();
		boolean orientation = currentGame.getOrientation();
		int turn = currentGame.whoseTurn();
		System.out.println("The vulnerability score is " + vulnerablePos(gameBoard, orientation, turn));
		System.out.println("The total score is " + TotalEvaluation(currentGame) );
		
	}
		
	/**
	 * Written by Jesse Kurtz
	 * Edited by Jake Levy
	 * @param position
	 * @return int
	 */
	private static int EvaluationCountPawns(int[][] position)
	{
		PawnScore = 0;
		for (int x=0; x<8; x++)
		{
			for (int y=0; y<8; y++)
			{
				if (CheckerMove.whosThere(position[x][y]) == CheckerBoard.wPiece)
				{
					PawnScore -=100;
				}
				else if (CheckerMove.whosThere(position[x][y]) == CheckerBoard.bPiece )
				{
					PawnScore +=100;
				}
			}	
					
		}
		return PawnScore;	
}
	
	
	
	
	/**
	 * Written by Jesse Kurtz
	 * Edited by Jake Levy
	 * @param position
	 * @return
	 */

	private static int EvaluationCountKings(int[][] position)
	{
		KingScore = 0;
		for (int x=0; x<8; x++)
		{
			for (int y=0; y<8; y++)
			{
				if (position[x][y] == CheckerBoard.wKing)
				{
					KingScore -=125;
				}
				else if (position[x][y] == CheckerBoard.bKing)
				{
					KingScore +=125;
				}
			}	
					
		}
		return KingScore;	
	}
	
	//This function checks the edges of the board and causes a player to "lose points" if
	//their kings are not in an offensive position. i.e on left or right edge
	//Min's non-offensive kings would gain points and max's would lose them to
	//indicate that this is undesirable
	//becuase this only checks the left or right edges we do not need the orientation 
	//if we were checking the "home bases" we would need to pass in the orientation variable
	/**
	 * Written by Jesse Kurtz
	 * Edited by Jake Levy
	 * @param position
	 * @return
	 */
	private static int NonOffensiveKings(int[][] position)
	{
		
		EdgeScore = 0;
		
	
		for (int x=0; x<7; x++)//iterate through the rows but
		{
			for (int y=0; y<7; y++)//only check the left and right edges
			{
				if (position[x][y] == CheckerBoard.wKing && (x == 0 || y == 0))
				{
					EdgeScore +=20;//each player loses points (respective to their view of the game) for any king on any edge of the board
				}
				
				if (position[x][y] == CheckerBoard.bKing && (x == 0 || y == 0))
				{
					EdgeScore -=20;
				}
			}
		}
		return EdgeScore;
	}
	/**
	 * Written by Jake Levy
	 * Edited by Jesse Kurtz
	 * @param game
	 * @param orientation
	 * @return
	 */
	private static int PosEval(int[][] game, boolean orientation)
	{
		PosScore= 0;	
		
		
		if (orientation == true)//if white is playing up
		{
			for (int x = 0; x <8; x++)
			{
				for(int y = 0; y<8; y++)
				{
					if (game[x][y] == CheckerBoard.wPiece)//advancing pawns further down the board increases their value
					{

						PosScore-=(10*(7-x));
						if (y == 3 || y==4)
						{
							PosScore -=25;//extra points for controlling the center of the board
						}
						if (y == 2 || y == 5)
						{
							PosScore-=10;
						}
					}
					else if (game[x][y] == CheckerBoard.bPiece)
					{

						PosScore+=(10*x);
						if (y ==3 || y == 4)
						{
							PosScore +=25;
						
						}
						if (y == 2 || y == 5)
						{
							PosScore+=10;
						}

					}
				}
			}
		}
		else
		{
			for (int x = 0; x <8; x++)
			{
				for(int y = 0; y<8; y++)
				{
					if (game[x][y] == CheckerBoard.wPiece)//advancing pawns increases their value
					{
						PosScore-=(10*x);
						if (y == 3 || y==4)
						{
							PosScore -=25;//extra points for controlling the center of the board
						}
						if (y == 2 || y == 5)
						{
							PosScore-=10;
						}
					}
					if (game[x][y] == CheckerBoard.bPiece)
					{
						PosScore+=(10*(7-x));
						if (y == 3 || y==4)
						{
							PosScore +=25;//extra points for controlling the center of the board
						}
						if (y == 2 || y == 5)
						{
							PosScore+=10;
						}
					}
				}
			}
		}
		return PosScore;
	}
	private static int vulnerablePos(int[][] game, boolean orientation, int turn)
	{
		vulnerabilityScore = 0;
		for (int row =0; row<8;row++)
		{
			for (int col = 0; col<8; col++)
			{
				if (CheckerMove.whosThere(game[row][col]) == CheckerBoard.wPiece && CheckerMove.canBeJumped(game, orientation, turn, row, col))
				{
					vulnerabilityScore += 95;//min gains points to indicate this is undesirable, negating base value of a pawn..kings worth half
				}
				if (CheckerMove.whosThere(game[row][col]) == CheckerBoard.bPiece && CheckerMove.canBeJumped(game, orientation,turn, row, col))
				{
					vulnerabilityScore -= 95; //max loses points
				}
				/*if (CheckerMove.whosThere(game[row][col]) == CheckerBoard.wPiece && !CheckerMove.canBeJumped(game, orientation, turn, row, col))
				{
					vulnerabilityScore -=95; //gains some points for positions that are not vulnerable
				}
				if (CheckerMove.whosThere(game[row][col]) == CheckerBoard.bPiece && !CheckerMove.canBeJumped(game, orientation, turn, row, col))
				{
					vulnerabilityScore +=95; //gains some points for positions that are not vulnerable
				}*/
			}
		}
		return vulnerabilityScore;
	}

	private static int blockedHomeScore(int [][] game, boolean orientation)
	{
		BlockedHomeScore = 0;
		int wCounter = 0;
		int bCounter = 0;
		
		if( orientation == true)//white plays up
		{
			if (CheckerMove.BlackHasPawns(game)){//if black  has pawns then if white's home is blocked white gains (as min, its a subtraction) 90 points
				//System.out.println("Made it Black1");
				for (int col = 0; col<8; col++)
				{
					if ((CheckerMove.whosThere(game[7][col])  == CheckerBoard.wPiece))//And Oppenent has pawns to be kinged
					{
						
						wCounter++;
					}
				}
			
				switch(wCounter){
				 case 1:
					 BlockedHomeScore-=25;
					 break;
				 case 2:
					 BlockedHomeScore-=50;
					 break;
				 case 3: 
					 BlockedHomeScore-=75;
					 break;
				 case 4:
					 BlockedHomeScore-=100;
					 break;
				 }
				
			}//if white has pawns and black's home is blocked then black gains (as max its an addition) 90 points
			 if (CheckerMove.WhiteHasPawns(game))// we call switchTurns to switch to the opponents perspective
			 {
				 //System.out.println("Made it white1");
				 for (int col = 0; col<8; col++)
				 {
					 if ((CheckerMove.whosThere(game[0][col]) == CheckerBoard.bPiece))
					 {
						 bCounter++;
					 }
				 }
				 switch(bCounter){
				 case 1:
					 BlockedHomeScore+=25;
					 break;
				 case 2:
					 BlockedHomeScore+=50;
					 break;
				 case 3: 
					 BlockedHomeScore+=75;
					 break;
				 case 4:
					 BlockedHomeScore+=100;
					 break;
				 }
			 }
		}
		else//white plays down
		{
			if (CheckerMove.BlackHasPawns(game)){//if my opponent has pawns then if my home is blocked i gain (as min, its a subtraction) 70 points
				for (int col = 0; col<8; col++)
				{
					if ((CheckerMove.whosThere(game[0][col])  == CheckerBoard.wPiece))//And Oppenent has pawns to be kinged
					{
						wCounter++;
					}
				}
				 switch(wCounter){
				 case 1:
					 BlockedHomeScore-=25;
					 break;
				 case 2:
					 BlockedHomeScore-=50;
					 break;
				 case 3: 
					 BlockedHomeScore-=75;
					 break;
				 case 4:
					 BlockedHomeScore-=100;
					 break;
				 }
			}//if I have pawns and his home is blocked then i lose (as white its an addition) 70 points
			 if (CheckerMove.WhiteHasPawns(game))// we call switchTurns to switch to the opponents perspective
			 {
				 for (int col = 0; col<8; col++)
				 {
					 if ((CheckerMove.whosThere(game[7][col]) == CheckerBoard.bPiece))
					 {
						 bCounter++;
					 }
				 }
				 switch(bCounter){
				 case 1:
					 BlockedHomeScore+=25;
					 break;
				 case 2:
					 BlockedHomeScore+=50;
					 break;
				 case 3: 
					 BlockedHomeScore+=75;
					 break;
				 case 4:
					 BlockedHomeScore+=100;
					 break;
				 }
			 }
		}
		
		return BlockedHomeScore;
	}
	
}