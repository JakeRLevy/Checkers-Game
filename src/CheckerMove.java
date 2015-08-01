import java.util.ArrayList;
import java.util.Comparator;
/**
 * CheckerMove Class
 * Written by Jake Levy and Jesse Kurtz
 * 
 *
 */
//Need to make this and CheckerBoard and other classes part of the same package

/*
 * boolean orientation = true indicates that white plays up (human) and black plays down (computer) and 
 * a false indicates white plays down(computer) and black plays up (human)*/


public class CheckerMove {
	
	private static ArrayList<CheckerMove> MovesList;
	
	int[] MoveArr = new int[4];
	String test;
	private int resultingH=0;  //variable to store the value of the resulting board
	
	//Written by Jake Levy
	//basic constructor
	public CheckerMove()
	{
		
	}
	
	//Written by: Jake Levy
	public void setResultVal(int eval)//this is only used for move ordering in minimax
	{
		this.resultingH = eval;
	}
	//Written by Jake Levy
	public int getResultVal()//also only used for move ordering 
	{
		return this.resultingH;
	}
	//Written by Jesse Kurtz
	public CheckerMove(int a , int b, int c, int d)
	{
		this.MoveArr[0] = a;
		this.MoveArr[1] = b;
		this.MoveArr[2] = c;
		this.MoveArr[3] = d;
	}
	//Written by Jake Levy
	public CheckerMove(int[] chosenMove)
	{
		this.MoveArr = chosenMove;
	}
	//written by Jesse Kurtz
	public void setMoves(int startR, int startC, int endR, int endC)
	{
		this.MoveArr[0] = startR;
		this.MoveArr[1] = startC;
		this.MoveArr[2] = endR;
		this.MoveArr[3] = endC;
		
	}
	/**
	 * toString
	 * written by: Jake Levy
	 */
	
	public String toString()//to test that moves have been correctly stored
	{
		test = "Contained in the CheckerMove: ";
		for(int x =0; x<this.MoveArr.length; x++)
		{
			test = test +" "+ this.MoveArr[x];
			
		}
		return test;
	}
	
	/**
	 * endOfGame
	 * Written by Jesse Kurtz
	 * Edited :
	 * @param currentGame
	 * @return
	 */
	
	public static boolean endOfGame(CheckerBoard currentGame)
	{
	
		if (areJumpsAvailable(currentGame) == true)	
		{	
			return false;	
		} 
	
		else if (areSlidesAvailable(currentGame) == true)
		{	
			return false;	
		}
		
		else 
			return true; 
		
	}
	/*

	
	//This function takes in the current move and the current game, calls the isMoveLegal function
	//to make sure the move is legal.   Then if it is, it uses the CheckerMove info to modify
	//the CheckerBoard state representation.  it returns true if the move was successfully applied to the
	//board state, and false if it was not*/
	/**
	 * makeMove
	 * Written by Jake Levy
	 * Edited by Jesse Kurtz
	 * @param currentGame
	 * @param currentMove
	 * @return
	 */
	public static boolean makeMove(CheckerBoard currentGame, CheckerMove currentMove)//returns true if move was successfully applied
	{	
		int [][] gameBoard = currentGame.getBoard();
		boolean orientation = currentGame.getOrientation();
		int[] positions = currentMove.getPositions();
		int startR = positions[0];
		int startC = positions[1];
		int endR = positions[2];
		int endC = positions[3];
		boolean isThisMoveLegal = isMoveLegal(currentGame, currentMove);
		if (isThisMoveLegal)//if it is a legal move (i.e. if it is a legal jump or slide)
		{
			if(Math.abs(endC - startC) == 1)//if it is a slide
			{
				currentGame.swapPieces(startR, startC, endR, endC);
				if (orientation)//if white is playing up
				{
					if (gameBoard[endR][endC] == CheckerBoard.wPiece && endR == 0)//needs to be tested
						currentGame.makeKing(endR, endC);
					else if (gameBoard[endR][endC] == CheckerBoard.bPiece && endR == 7)
						currentGame.makeKing(endR, endC);
					}
				else//if white is playing down
				{
					if (gameBoard[endR][endC] == CheckerBoard.wPiece && endR == 7)
						currentGame.makeKing(endR, endC);
					else if  (gameBoard[endR][endC] == CheckerBoard.bPiece && endR == 0)
						currentGame.makeKing(endR, endC);						
				}
				
			}
			
			else//it is a jump
			{
				currentGame.swapPieces(startR, startC, endR, endC);
				int cap_row = (endR + startR)/2;
				int cap_col = (endC + startC)/2;
				currentGame.removePiece(cap_row, cap_col);
				if (orientation)//if white is playing up
				{
					if (gameBoard[endR][endC] == CheckerBoard.wPiece && endR == 0)//needs to be tested
						currentGame.makeKing(endR, endC);
					else if (gameBoard[endR][endC] == CheckerBoard.bPiece && endR == 7)
						currentGame.makeKing(endR, endC);
					}
				else//if white is playing down
				{
					if (gameBoard[endR][endC] == CheckerBoard.wPiece && endR == 7)
						currentGame.makeKing(endR, endC);
					else if  (gameBoard[endR][endC] == CheckerBoard.bPiece && endR == 0)
						currentGame.makeKing(endR, endC);						
				}
				
			}
		}
		return isThisMoveLegal;
	}
	/**
	 * inputRange
	 * Written by Jake Levy
	 * Edited by Jesse Kurtz
	 * @param x
	 * @return
	 */
	//Checks to make sure entered move data from human player is within the range of the board
	 public static boolean inputRange(int x)
     {
     	if(x>=0)
     	{
     		if (x<=7)
     		{
     			return true;
     		}
     		else
     			return false;
     		
     	}
     	else
     		return false;
     }
	 
	 public int[] getPositions()
	 {
		 return this.MoveArr;
	 }
	 /**
	  * isMoveLegal
	  * Written by Jesse Kurtz
	  * Edited by Jake Levy
	  * @param currentGame
	  * @param testMove
	  * @return
	  */
	 public static boolean isMoveLegal(CheckerBoard currentGame, CheckerMove testMove)
	 {
		 int[] testArr = testMove.getPositions();//get the starting and ending positions of the move being tested
		 int startR = testArr[0];
		 int startC = testArr[1];
		 int endR = testArr[2];
		 int endC = testArr[3];
		 boolean isMoveLegal;
		
		 if (Math.abs(endR-startR) == 1 && Math.abs(endC-startC) == 1)//if it seems like a legit slide
		 {
			// System.out.println("TEST");
			 isMoveLegal = isSlideLegal(currentGame, testMove);//test the slide
		 }
		 else if( Math.abs(endR-startR) == 2 && Math.abs(endC-startC) == 2)//if it seems like a legit jump
		 {
			 isMoveLegal = isJumpLegal(currentGame, testMove);//test the jump
		 }
		 else //anything else can't be legal
		 {
			// System.out.println("Test in isMoveLegal");
			 isMoveLegal = false;
		 }
		 
		 return isMoveLegal;
	 }
	 /**
	  * isJumpLegal
	  * Written by Jake Levy
	  * Edited by Jesse Kurtz
	  * @param currentGame
	  * @param testMove
	  * @return
	  */
	 private static boolean isJumpLegal(CheckerBoard currentGame, CheckerMove testMove)
	 {
		 int[] testArr = testMove.getPositions();//get the starting and ending positions of the move being tested
		 boolean orientation = currentGame.getOrientation();//if true then white plays up
		 int currentBoard[][] = currentGame.getBoard();
		 int startR = testArr[0];
		 int startC = testArr[1];
		 int endR = testArr[2];
		 int endC = testArr[3];
		 int turn = currentGame.whoseTurn();
		
		 if ((outOfBounds(startR, startC)  || outOfBounds(endR, endC)))
		 {
			// System.out.println("You have chosen an illegal move.  Please make a new move: ");			
			 return false;//return illegal
		 }
		 
		 if (!areJumpsAvailable(currentGame))//if there are no jumps available
		 {
			//System.out.println("There are no jumps available, try to slide a piece!");	 //for error testing
			 return false;
		 }
		 if (currentBoard[endR][endC]!= CheckerBoard.EMPTY)
		 	{
			// System.out.println(" You cannot land a jump into a non-empty Space");//for error testing
			 	return false;
		 	}
		if (whosThere(currentBoard[startR][startC]) != turn )
		{
			//System.out.println("This is not your piece to jump");//error testing println
			return false;
		}
		//outside control block only calls this function if there is a column separation of 2, indicating a possible jump either up or down the board
		int capturedRow = (endR+startR)/2;
		int capturedCol = (endC+startC)/2;
		
		int captured = currentBoard[capturedRow][capturedCol];
		//add switch and if/else controls to check through possible jumps and orientations
		if (turn == CheckerBoard.wPiece)
		{
			if(whosThere(captured) != CheckerBoard.bPiece)
				return false;
		}
		else if (turn == CheckerBoard.bPiece)
		{
			if(whosThere(captured) != CheckerBoard.wPiece)
				return false;
		}
		int piece = currentBoard[startR][startC];//current piece, the one that jumps

		if (orientation)//if white is playing up the board
		{
			switch(piece)
			{
			case CheckerBoard.wPiece:
				if (endR - startR != -2)
					return false;
				break;
			case CheckerBoard.bPiece:
				if (endR - startR != 2)
					return false;
				break;
			case CheckerBoard.wKing:
			case CheckerBoard.bKing:
				if (Math.abs(endR-startR) != 2)
					return false;
				break;
			}
			return true;
			
		}
		else //white is playing down
		{
			switch (piece)
			{
			case CheckerBoard.wPiece:
				if (endR - startR != 2)
					return false;
				break;
			case CheckerBoard.bPiece:
				if (endR - startR != -2)
					return false;
				break;
			case CheckerBoard.wKing:
			case CheckerBoard.bKing:
				if (Math.abs(endR-startR) != 2)
					return false;
				break;
			}
			return true;
		}
			
	 }
	 /**
	  * isSlideLegal
	  * Written by Jake Levy
	  * Edited by Jesse Kurtz
	  * @param currentGame
	  * @param testMove
	  * @return
	  */
	 private static boolean isSlideLegal(CheckerBoard currentGame, CheckerMove testMove)
	 {
		 int[] testArr = testMove.getPositions();//get the starting and ending positions of the move being tested
		 boolean orientation = currentGame.getOrientation();//if true then white plays up
		 int currentBoard[][] = currentGame.getBoard();
		 int startR = testArr[0];
		 int startC = testArr[1];
		 int endR = testArr[2];
		 int endC = testArr[3];
		 int turn = currentGame.whoseTurn();
		 
		 if (outOfBounds(startR, startC)  || outOfBounds(endR, endC))
			 {
				// System.out.println("You have chosen an illegal move.  Please make a new move: ");			
				 return false;//return illegal
			 }
		 //This function seems redundant since we check to make sure the starting piece is the current players turn
		/* if((whosThere(currentBoard[startR][startC])) == CheckerBoard.EMPTY)//if empty at starting location
		 {
			 System.out.println("You can't start a move in an empty space.  Please make a new move: ");
				 return false;//return illegal
		 }*/
		 
		 else if ((whosThere(currentBoard[startR][startC]))!= turn)//if the piece in the beginning slot is not current players piece or is Empty
		 	{
			// System.out.println("This is not your piece.  Please make a new move: ");			
			 return false;//return illegal
		 	}
		 else if (currentBoard[endR][endC] != CheckerBoard.EMPTY)//if not empty at ending location
		 {
			// System.out.println("You must move into an empty space.  Please make a new move: ");			
			 return false;//return illegal
		 }
		 else if(areJumpsAvailable(currentGame))//if there are jumps they must be made first according to rules
		 {
			 System.out.println("You must make any jump that is available.  Please make a new move: ");	//may need to remove this text		
			 return false;//return illegal
		 }
			 int current_piece = currentBoard[startR][startC];
			 
		if (Math.abs(startC - endC) == 1) //if they are only sliding left or right one column
		{//move this if to an outside isMoveLegal function which checks moves and calls isJump or isSlide appropriately
			if (orientation)//if white is playing up
				{
					switch (current_piece)
					{
					case CheckerBoard.wPiece:
						if( (startR - endR) == 1 ){//white moves up one
							return true;}
						break;
					case CheckerBoard.bPiece:
						if ((startR - endR) == -1){//if black moves down
							return true;}
						break;
				 
					case CheckerBoard.wKing:
					case CheckerBoard.bKing:
						if ( Math.abs(startR - endR) == 1){
							return true;}
						break;
					}
					return false;
				}
			else//if white is playing down
			{
				switch (current_piece)
				{
					case CheckerBoard.wPiece:
						if( (startR - endR) == -1 )//white moves down one
							return true;
						break;
					case CheckerBoard.bPiece:
						if ((startR - endR) == 1)//if black moves up
							return true;
						break;				 
					case CheckerBoard.wKing:
					case CheckerBoard.bKing:
						if ( Math.abs(startR - endR) == 1)
							return true;
						break;
				}
			
			 return false;
			}
		}
		
		 return false;//if they are trying to slide more than 1 or less than 1 it is illegal
	 }
	 
	
	  /**
	   * Written by Jake Levy
	   * edited by: Jesse Kurtz
	   * @param spot
	   * @return
	   */
	 public static int whosThere(int spot)//returns who (which player) is in this spot
	 {
		 switch (spot)
		 {
		 case CheckerBoard.wKing:
		 case CheckerBoard.wPiece:
			 return CheckerBoard.wPiece;
		 case CheckerBoard.bKing:
		 case CheckerBoard.bPiece:
			 return CheckerBoard.bPiece;
		
		
		 }
		return CheckerBoard.EMPTY; 
	 }
	 
	 //checks to make sure the move happening is possible
	 /**
	  * Written by: Jake Levy
	  * Edited by: Jesse Kurtz
	  * @param currentGame
	  * @return
	  */
	 public static boolean areSlidesAvailable (CheckerBoard currentGame)//returns true if there are any slides available on the board
	 {
		 int[][] currentBoard = currentGame.getBoard(); 
		 
		// int[] positions = testMove.getPositions();
		 int turn = currentGame.whoseTurn();
		 
		 for(int row = 0; row<8; row++)
		 {
			 for (int col=0; col<8;col++)
			 {
				 int piece = currentBoard[row][col];
				 
				 if( (whosThere(piece) == turn) &&  ( canSlidefromPosition(currentGame, row, col)))//if the piece in the xy spot is the current player's piece and there is A slide available
					 return true;//return true
			 }
		 }
		 return false;
		 
	 }
	
	 public static boolean BlackHasPawns(int[][] currentGame)
	 {
		for (int x = 0; x<8; x++)
		{
			for (int y = 0; y <8; y++)
			{
				if (currentGame[x][y] == CheckerBoard.bPiece)
				{
					return true;
				}
			}
		}
		return false;
		 
	 }
	 public static boolean WhiteHasPawns(int[][] currentGame)
	 {
		 for (int x = 0; x<8; x++)
		 {
			 for (int y = 0; y<8; y++)
			 {
				 if (currentGame[x][y] == CheckerBoard.wPiece)
				 {
					 return true;
					 
				 }
			 }
		 }
		 return false;
	 }
	/* private static int switchTurns(int currentTurn)
	 {
		 if (currentTurn == 3)
			 return 1;
		 else
			 return 3;
	 }*/
	 public static boolean canBeJumped(int[][] game, boolean orientation, int currentTurn, int row, int col)
	 {
//		 int nextTurn = switchTurns(currentTurn);
		 if (orientation == true)//if white is playing up
		 {
			switch(game[row][col])
			{
				case CheckerBoard.wKing:
				case CheckerBoard.wPiece:
					if (currentTurn == CheckerBoard.bPiece){//the purpose of this is for the program to look at the next turn to see if it will leave itself in a vulnerable position
						if(!(outOfBounds(row-1, col-1)))//after making a move
						{
							if((whosThere(game[row-1][col-1])) == CheckerBoard.bPiece && isEmpty(game, row+1, col+1))
								return true;
						}
						if (!(outOfBounds(row-1, col+1)))
						{
							if((whosThere(game[row-1][col+1])) == CheckerBoard.bPiece && isEmpty(game, row+1, col-1))
								return true;
						}
						if(!(outOfBounds(row+1, col+1)))
						{
							if( game[row+1][col+1] == CheckerBoard.bKing && isEmpty(game, row-1, col-1))
								return true;
						}
						if(!(outOfBounds(row+1, col-1)))
						{
							if( game[row+1][col-1] == CheckerBoard.bKing && isEmpty(game, row-1, col+1))
								return true;
						}
					}
					break;
				case CheckerBoard.bPiece:
				case CheckerBoard.bKing:
					if (currentTurn == CheckerBoard.wPiece){
						if(!(outOfBounds(row-1, col-1)))
						{
							if(  (game[row-1][col-1]) == CheckerBoard.wKing && isEmpty(game, row+1, col+1))
								return true;
						}
						if (!(outOfBounds(row-1, col+1)))
						{
							if( (game[row-1][col+1]) == CheckerBoard.wKing && isEmpty(game, row+1, col-1))
								return true;
						}
						if(!(outOfBounds(row+1, col+1)))
						{
							if( whosThere(game[row+1][col+1]) == CheckerBoard.wPiece && isEmpty(game, row-1, col-1))
								return true;
						}
						if(!(outOfBounds(row+1, col-1)))
						{
							if( whosThere(game[row+1][col-1]) == CheckerBoard.wPiece && isEmpty(game, row-1, col+1))
								return true;
						}
					}
						break;
					
					default:
						return false;
			}
		 }
		 else
		 {//white plays down
			 switch(game[row][col])
				{
					case CheckerBoard.wKing:
					case CheckerBoard.wPiece:
						if (currentTurn == CheckerBoard.bPiece){
							if(!(outOfBounds(row-1, col-1)))
							{
								if(  (game[row-1][col-1]) == CheckerBoard.bKing && isEmpty(game, row+1, col+1))
									return true;
							}
							if (!(outOfBounds(row-1, col+1)))
							{
								if( (game[row-1][col+1]) == CheckerBoard.bKing && isEmpty(game, row+1, col-1))
									return true;
							}
							if(!(outOfBounds(row+1, col+1)))
							{
								if( whosThere(game[row+1][col+1]) == CheckerBoard.bPiece && isEmpty(game, row-1, col-1))
									return true;
							}
							if(!(outOfBounds(row+1, col-1)))
							{
								if( whosThere(game[row+1][col-1]) == CheckerBoard.bPiece && isEmpty(game, row-1, col+1))
									return true;
							}
						}
							break;
					case CheckerBoard.bPiece://black plays up
					case CheckerBoard.bKing:
						if (currentTurn  == CheckerBoard.wPiece){
							if(!(outOfBounds(row-1, col-1)))
							{
								if((whosThere(game[row-1][col-1])) == CheckerBoard.wPiece && isEmpty(game, row+1, col+1))
									return true;
							}
							if (!(outOfBounds(row-1, col+1)))
							{
								if((whosThere(game[row-1][col+1])) == CheckerBoard.wPiece && isEmpty(game, row+1, col-1))
									return true;
							}
							if(!(outOfBounds(row+1, col+1)))
							{
								if( game[row+1][col+1] == CheckerBoard.wKing && isEmpty(game, row-1, col-1))
									return true;
							}
							if(!(outOfBounds(row+1, col-1)))
							{
								if( game[row+1][col-1] == CheckerBoard.wKing && isEmpty(game, row-1, col+1))
									return true;
							}
						}
							break;
						default:
							return false;
				}
		 }
		 return false;
	 }
	 /**
	  * canJumpFromPosition
	  * Written by: Jesse Kurtz
	  * Edited by: Jake Levy
	  * @param currentGame
	  * @param row
	  * @param col
	  * @return
	  */
	 public static boolean canJumpFromPosition(CheckerBoard currentGame, int row, int col)
	 {
			boolean orientation = currentGame.getOrientation();
			int[][] coords = currentGame.getBoard();
			
		
			if (orientation == true)//if white is playing up
			{
							switch (coords[row][col])
							{
							case CheckerBoard.wPiece:
							if(!(outOfBounds(row-1, col-1))){  //make sure the spot we are trying to jump over actually exists
								if ( (whosThere(coords[row-1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col-2) )//input is specified and limited to 0-7 so we don't need to worry about moving beyond that range of numbers
	                                return true;//isEmpty checks the range for its own input and returns false if the numbers aren't in on the board thus, we just need to make
							}	//sure that we check the the middle spot
							if (!outOfBounds(row-1, col+1)){
								 if ((whosThere(coords[row-1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col+2))
									return true;
							}
								break;
							case CheckerBoard.bPiece://black playing down
								if (!outOfBounds(row+1, col+1)){
									if ( (whosThere(coords[row+1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row+2,col+2))
									return true;
								}
								 if (!outOfBounds(row+1, col-1)){
									if (( whosThere(coords[row+1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords, row+2, col-2))
									return true;
								 }
									break;
							case CheckerBoard.bKing:
								if (!outOfBounds(row+1, col+1)){
									if ( ( whosThere(coords[row+1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row+2, col+2))
										return true;
								}
								
								if (!outOfBounds(row-1,col-1)){
									if	(( whosThere(coords[row-1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col-2))
										return true;
								}
								if (!outOfBounds(row+1, col-1)){
									if (( whosThere(coords[row+1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords,row+2,col-2))
										return true;
								}
								if (!outOfBounds(row-1, col+1)){
									if (( whosThere(coords[row-1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col+2))
										return true;
								}
								break;
											
							case CheckerBoard.wKing:
								
								if (!outOfBounds(row+1, col+1)){
									if ( ( whosThere(coords[row+1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row+2, col+2))
										return true;
								}
								
								if (!outOfBounds(row-1,col-1)){
									if	(( whosThere(coords[row-1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col-2))
										return true;
								}
								if (!outOfBounds(row+1, col-1)){
									if (( whosThere(coords[row+1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords,row+2,col-2))
										return true;
								}
								if (!outOfBounds(row-1, col+1)){
									if (( whosThere(coords[row-1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col+2))
										return true;
								}									
								break;	
								
							}//end of switch statement
		
				return false;
			}
			
			else//if white plays down/black plays up
			{
				switch (coords[row][col])
				{
				case CheckerBoard.bPiece:
				if(!(outOfBounds(row-1, col-1))){  //make sure the spot we are trying to jump over actually exists
					if ( (whosThere(coords[row-1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col-2) )//input is specified and limited to 0-7 so we don't need to worry about moving beyond that range of numbers
                        return true;//isEmpty checks the range for its own input and returns false if the numbers aren't in on the board thus, we just need to make
				}	//sure that we check the the middle spot
				if (!outOfBounds(row-1, col+1)){
					 if ((whosThere(coords[row-1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col+2))
						return true;
				}
					break;
				case CheckerBoard.wPiece://black playing down
					if (!outOfBounds(row+1, col+1)){
						if ( (whosThere(coords[row+1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row+2,col+2))
						return true;
					}
					 if (!outOfBounds(row+1, col-1)){
						if (( whosThere(coords[row+1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords, row+2, col-2))
						return true;
					 }
						break;
				case CheckerBoard.bKing:
					if (!outOfBounds(row+1, col+1)){
						if ( ( whosThere(coords[row+1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row+2, col+2))
							return true;
					}
					
					if (!outOfBounds(row-1,col-1)){
						if	(( whosThere(coords[row-1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col-2))
							return true;
					}
					if (!outOfBounds(row+1, col-1)){
						if (( whosThere(coords[row+1][col-1]) == CheckerBoard.wPiece) && isEmpty(coords,row+2,col-2))
							return true;
					}
					if (!outOfBounds(row-1, col+1)){
						if (( whosThere(coords[row-1][col+1]) == CheckerBoard.wPiece) && isEmpty(coords, row-2, col+2))
							return true;
					}
					break;
								
				case CheckerBoard.wKing:
					
					if (!outOfBounds(row+1, col+1)){
						if ( ( whosThere(coords[row+1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row+2, col+2))
							return true;
					}
					
					if (!outOfBounds(row-1,col-1)){
						if	(( whosThere(coords[row-1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col-2))
							return true;
					}
					if (!outOfBounds(row+1, col-1)){
						if (( whosThere(coords[row+1][col-1]) == CheckerBoard.bPiece) && isEmpty(coords,row+2,col-2))
							return true;
					}
					if (!outOfBounds(row-1, col+1)){
						if (( whosThere(coords[row-1][col+1]) == CheckerBoard.bPiece) && isEmpty(coords, row-2, col+2))
							return true;
					}									
					break;	
					
				}//end of switch statement
				return false;
			}
	 }
	 /**
	  * areJumpsAvailable
	  * Written by: Jake Levy
	  * Edited by:Jesse Kurtz
	  * @param currentGame
	  * @return
	  */
	 public static boolean areJumpsAvailable(CheckerBoard currentGame)//used for goal test as well making sure the human player is making a legal move
	 {
		 int[][] board = currentGame.getBoard();
		 int turn = currentGame.whoseTurn();
		 
		 for (int x = 0; x<8; x++)
		 {
			 for (int y= 0; y<8; y++)
			 {
				 if ((whosThere(board[x][y]) == turn) && canJumpFromPosition(currentGame, x, y))
					 return true;
			 }
		 }
		 return false;
	 }
	 /**
	  * outOfBounds 
	  * Written by : Jesse Kurtz
	  * @param x
	  * @param y
	  * @return
	  */
	 
	 public static boolean outOfBounds(int x, int y)//returns true if numbers are out of bounds of the board
	 {//used primarily to make sure that moves we are checking are possible (i.e. when checking to see if a jump exists, we should not be able to check on board positions that do not exist)
		 //used by the computer player for bounds checking
		 //human player bounds are limited at io level
		 if( x<0 || x>7 || y<0 ||y>7 )
		 return true;
		 else
			 return false;
	 }
	 /**
	  * canSlidefromPosition
	  * Written by: Jesse Kurtz
	  * Edited by: Jake Levy
	  * @param currentGame
	  * @param r
	  * @param col
	  * @return
	  */
	public static boolean canSlidefromPosition(CheckerBoard currentGame, int r, int col)
	{
		boolean orientation = currentGame.getOrientation();
		int[][] coords = currentGame.getBoard();
		
		if (orientation == true)//if white is playing up
		{
						switch (coords[r][col])
						{
						case CheckerBoard.wPiece:
					
								if ( isEmpty(coords,r-1,col-1) || isEmpty(coords,r-1,col+1) )//input is specified and limited to 0-7 so we don't need to worry about moving beyond that range of numbers
									return true;
							
							break;
						case CheckerBoard.bPiece://black playing down
							
							
								if ( isEmpty(coords, r+1, col-1) || isEmpty(coords, r+1, col+1) )
									return true;
							
							break;
						case CheckerBoard.bKing:
						case CheckerBoard.wKing:
							
								if ( isEmpty(coords,r-1,col-1) || isEmpty(coords,r-1,col+1) || isEmpty(coords, r+1, col-1) || isEmpty(coords, r+1, col+1) )
									return true;
							
								break;	
							
						}//end of switch statement
	
			return false;
		}
		else //if black is playing up
		{
		
						switch (coords[r][col])
						{
						case CheckerBoard.bPiece:
							if ( isEmpty(coords,r-1,col-1) || isEmpty(coords,r-1,col+1) )//input is specified and limited to 0-7 so we don't need to worry about moving beyond that range of numbers
                                return true;
							break;
						case CheckerBoard.wPiece://black playing down
							if ( isEmpty(coords, r+1, col-1) || isEmpty(coords, r+1, col+1) )
								return true;
							break;
						case CheckerBoard.bKing:
						case CheckerBoard.wKing:
							if ( isEmpty(coords,r-1,col-1) || isEmpty(coords,r-1,col+1) || isEmpty(coords, r+1, col-1) || isEmpty(coords, r+1, col+1) )
								return true;
							break;	
						}
		return false;	
		}
	}
	//This takes in the Board state and checks to see if a position is empty, given its coordinates.
	/**
	 * isEmpty
	 * Written by Jake Levy
	 * Edited by Jesse Kurtz
	 * @param coords
	 * @param r
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(int[][] coords, int r, int c)
	{
	if(!(outOfBounds(r,c)))
		{
			if (coords[r][c] == CheckerBoard.EMPTY)
				return true;
		}
		return false;

	}
	//end isEmpty

	//generates all possible moves for a given board, depending on whoseTurn it is
	//chain jumps are represented as moves greater than the board itself, and encapsulate
	//the chain data.  i.e. a chain of jumps down the diagonal of a board  (0 ,0=> 2, 2=> 4,4=> 6,6)
	//may be represented as {0, 0, 642, 642} with each number representing a part of the chain, in reverse order.
	//It then returns an ArrayList containing all generated moves.
	//If a jump is possible, that jump must be taken so we have separated the generation of slides and jumps
	//since if a jump move is possible, a slide cannot occur and if a slide is possible, it implies there are no jumps
	/**
	 * GenerateMoves
	 * Written by: Jake Levy
	 * Edited by: Jesse Kurtz
	 * @param currentGame
	 * @return
	 */
	public static ArrayList<CheckerMove> generateMoves(CheckerBoard currentGame)
	{
		MovesList = new ArrayList<>();
		boolean orientation = currentGame.getOrientation();
		MovesList.clear();
		CheckerMove testMove = new CheckerMove();
		boolean test_move;
		///i will pass in the whole board and get turn and board data from the game state
		int currentTurn = currentGame.whoseTurn();
		int[][] gameBoard = currentGame.getBoard();
		if (areJumpsAvailable(currentGame))//if the current player has jumps available, they must make one of those jumps
		{//so we can effectively eliminate any slide as a possible move, even if it would create a more beneficial board
			//hopefully this will also save time in move generation and game search by limiting the branching factor
			for(int row = 0; row<8; row++ )
			{//iterate through the board
				for (int col = 0; col<8; col++)
				{
					if (whosThere(gameBoard[row][col]) == currentTurn && canJumpFromPosition(currentGame, row, col))
					{//if the current piece belongs to the current Turn and they can jump from this position
					// we iterate through all possible jumps from this position, adding those that are legal single moves to the moves list
						for (int testR=-2; testR<=2; testR+=4)
						{
							for (int testC=-2; testC<=2; testC+=4)
							{
								
								testMove.setMoves(row, col, row+testR, col+testC);
								test_move = isMoveLegal(currentGame, testMove); 
								if (test_move == true)
								{
									
									int[][] tempBoard = CheckerBoard.copy_board(gameBoard);
									CheckerBoard tempGame = new CheckerBoard(tempBoard, currentTurn, orientation);
									boolean next_move = makeMove(tempGame, testMove);//apply the current move to the temp game to see if another jump is possible
									if(next_move ==true && canJumpFromPosition(tempGame, row+testR, col+testC))//if it was a legal move and another jump is available from the landed position
									{
										forceCaptures(tempGame, testMove, MovesList, 10);									}
									else if (next_move == true && !canJumpFromPosition(tempGame, row+testR, col+testC))
									{
										CheckerMove LegalMove = new CheckerMove (row, col, row+testR, col+testC);
										MovesList.add(LegalMove);
									}//end if/else
								}//end if (test_move == true)
							}//end inner for-loop to test individual moves
						}//end outer for-loop to test individual moves
					}//if whosThere && canJump
				}//end inner for-loop for iterating over board
			}//end outer for-loop for iterating over board
			return MovesList;
		}//end if Jumpsareavailable
		else //if there are no jumps available any slide is a possibility and can help shape the board
		{
			MovesList.clear();
			//System.out.println("No jumps available!");
			for (int row=0; row<8; row++)
			{
				for (int col=0; col<8; col++)
				{
					if (whosThere(gameBoard[row][col]) == currentTurn && canSlidefromPosition(currentGame, row, col))
			 		{
						for (int a= -1; a<= 1; a+=2)
						{
							for (int b= -1; b<= 1; b+=2)
							{
								
								testMove.setMoves(row, col, row+a, col+b);
								//System.out.println("this :" + testMove.toString() + " generated");
								test_move = isMoveLegal(currentGame, testMove);
								if (test_move == true)//if the move is legal
								{
									CheckerMove LegalMove = new CheckerMove(row, col, row+a, col+b);
									MovesList.add(LegalMove);// can't chain a jump to a slide so this ends the move
								}
								//else do nothing
								//System.out.println(MovesList.toString());
								
							}
						}
							
					}
				}
			}
			return MovesList;
		}		
		
	}
	
	
	
	
	//generates all possible boards resulting from the current player's set of possible moves
	//and returns them as an ArrayList
	//The following function is borrowed from authors Victor Huang and Sung Ha Huh. It has been modified from the original version.
	//It is used by the program to apply moves generated by the generateMoves function in the MiniMax algorithm. this is necessary for
	//the moves that are stored as chain jumps in order to process them correctly
	public static void move_board(CheckerBoard currentGame, CheckerMove inputMove)
	{	int[] move = inputMove.getPositions();
		int startx = move[0];
		int starty = move[1];
		int endx = move[2];
		int endy = move[3];
		while (endx>0 || endy>0)
		{
			int newEndR = endx%10;
			int newEndC = endy%10;
			CheckerMove currentMove = new CheckerMove(startx, starty, newEndR, newEndC);//mod included here to cover the case of multiple jumps
			makeMove(currentGame, currentMove);
			startx = newEndR;//if 
			starty = newEndC;//but it does ensure the move can't escape the board
			endx = endx/10;
			endy = endy/10;
		} 
		
	}	


//this code directly borrowed with some modification from authors Victor Huang and Sung Ha Huh.
private static void forceCaptures(CheckerBoard currentGame, CheckerMove currentMove, ArrayList<CheckerMove> Moves, int multiplier){
int[] move = currentMove.getPositions();
int newx = move[2], newy = move[3];
 CheckerMove testMove = new CheckerMove();
 int[][] board = currentGame.getBoard();
 int turn = currentGame.whoseTurn();
 boolean orientation = currentGame.getOrientation();
 
 while (newx>7 || newy>7){
	newx/=10;
	newy/=10;
 }//this while loop exists because of how jump chains are stored.  If the stored Move is 0,0, 42,42  then 42, 42 becomes 4,4 the start of the next jump
 for (int i=-2; i<=2; i+=4)
   for (int j=-2; j<=2; j+=4)
     if (!outOfBounds(newx+i,newy+j)) {
		int[][] tempPosition = CheckerBoard.copy_board(board);
		CheckerBoard tempBoard = new CheckerBoard(tempPosition, turn, orientation);
		testMove.setMoves(newx,newy,newx+i,newy+j);
		boolean current_move = makeMove(tempBoard, testMove);//if the move is illegal nothing happens
														//but if its legal, the internal 2D array board is changed
		if (current_move == true && !canJumpFromPosition(tempBoard, newx+i, newy+j))
		{
			int[] new_move = new int[4];
			new_move[0] = move[0];
			new_move[1] = move[1];
			new_move[2] = move[2]+(newx+i)*multiplier;
  			new_move[3] = move[3]+(newy+j)*multiplier;
  			CheckerMove totalMove = new CheckerMove(new_move);
  			Moves.add(totalMove);
		} // if 
		else if (current_move == true && canJumpFromPosition(tempBoard, newx+i, newy+j))
		{
			int[] new_move = new int[4];
			new_move[0] = move[0];
			new_move[1] = move[1];
			new_move[2] = move[2]+(newx+i)*multiplier;
  			new_move[3] = move[3]+(newy+j)*multiplier;
			CheckerMove newMove = new CheckerMove(new_move);
			forceCaptures(currentGame, newMove, Moves, multiplier*10);
		}
	 } //end

}//forceCaptures

/**
 * Comparators
 * Written by Jake Levy
 * Edited by Jesse Kurtz
 */
public static Comparator<CheckerMove> MaxComparator = new Comparator<CheckerMove>()
{
	public int compare(CheckerMove a, CheckerMove b)
	{
		return (b.getResultVal() - a.getResultVal());//should result in a list ordered in descending order by Hvalue stored
	}
};
public static Comparator<CheckerMove> MinComparator = new Comparator<CheckerMove>()
{
	public int compare(CheckerMove a, CheckerMove b)
	{
		return (a.getResultVal() - b.getResultVal());
	}
};
}
