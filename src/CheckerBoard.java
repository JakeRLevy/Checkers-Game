//import java.util.Collections;
import java.util.ArrayList;
//import java.util.Hashtable;

public class CheckerBoard{// implements Comparator<CheckerBoard>{
	static ArrayList<CheckerMove> childMoves;
	 static final int EMPTY= 0;
	 static final int wPiece = 1;
	 static final int wKing = 2;
	 static final int bPiece = 3;
	 static final int bKing = 4;
	private boolean whitePlaysUp;  //allows us to keep track of the board orientation, human always plays up but may be either color, so instead we keep track of whether or not white plays up
	private int whoseTurn;
	int[][] gameBoard = new int[8][8];
	private int i;
	private boolean gameOver = false;
	int j;
	private int Hvalue =0;
	
	
	/**
	 * Written by Jake Levy
	 * 
	 */
	public CheckerBoard()//constructor for the board itself
	{
		
			
	}

	/**
	 * Written by Jake Levy
	 * 
	 */
	public CheckerBoard(int[][] newBoard, int turn, boolean orientation)//temporarily used as a copy constructor
	{
		this.gameBoard = newBoard;
		this.whitePlaysUp = orientation;
		this.whoseTurn = turn;
	}
	//this code directly borrowed from authors Victor Huang and Sung Ha Huh.
	public static int[][] copy_board(int[][] board)
	{
	    int[][] copy = new int[8][8];

	    for (int i=0; i<8; i++)//if i change row numbers I have to change this
	      System.arraycopy(board[i],0,copy[i],0,8);
	    return copy;
	  }//end copy_board

	/**
	 * Written by Jake Levy
	 * 
	 */
	public boolean isGameOver()
	{
		return this.gameOver;
	}

	//Written by Jesse Kurtz
	
	public void setHvalue( int Evaluation )
	{
		this.Hvalue = Evaluation;
	}
	public int getHval()
	{
		return this.Hvalue;
	}
/**
 * swapPieces, removePiece, & makeKing
 * Written by Jesse Kurtz
 * Edited by Jake Levy
 * @param rowB
 * @param colB
 * @param rowW
 * @param colW
 */
	
	public void swapPieces (int rowB, int colB, int rowW, int colW)
	{
		int swap;
		
		swap  = this.gameBoard[rowB][colB];
		
		this.gameBoard[rowB][colB] = this.gameBoard[rowW][colW];
		this.gameBoard[rowW][colW] = swap;
		
	}
	public void removePiece( int row, int col)
	{
		this.gameBoard[row][col] = CheckerBoard.EMPTY;
	}
	
	
	public void makeKing (int row, int col)
	{
		if( this.gameBoard[row][col] == CheckerBoard.wPiece)
			this.gameBoard[row][col]  = CheckerBoard.wKing;
		else if (this.gameBoard[row][col] == CheckerBoard.bPiece)
			this.gameBoard[row][col] = CheckerBoard.bKing;
		else
			System.out.println("you can't make this one into a king!");
			
	}
	
//Written by Jake Levy
	public void endGame()
	{
		this.gameOver = true;
	}
	
	//Written by Jake Levy

	public void setUpBoard(String userChoice)
	{
		if (userChoice.equalsIgnoreCase("black"))
		{
			whitePlaysUp = false;
			for (j = 0; j<8; j++)
			{
				for (i = 0; i< 3; i++)
				{
					if ((j%2) == 0)
					{
						if((i%2) == 0)
						{
							this.gameBoard[i][j] = EMPTY;
						}
						else
							this.gameBoard[i][j] = wPiece;
					}
					else if ((j%2) == 1)
					{
						if (i%2 == 1)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = wPiece;
					}
				}
				for (i = 5; i<8; i++)
				{
					if ((j%2)==0)
					{
						if((i%2) == 0)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = bPiece;
					}
					else if ((j%2)==1)
					{
						if((i%2) == 1)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = bPiece;
					}
				}
			}
		}//end if
		
		else 
		{
			whitePlaysUp = true;
			for (j = 0; j<8; j++)
			{
				for (i = 0; i< 3; i++)
				{
					if ((j%2) == 0)
					{
						if((i%2) == 0)
						{
							this.gameBoard[i][j] = EMPTY;
						}
						else
							this.gameBoard[i][j] = bPiece;
					}
					else if ((j%2) == 1)
					{
						if (i%2 == 1)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = bPiece;
					}
				}
				for (i = 5; i<8; i++)
				{
					if ((j%2)==0)
					{
						if((i%2) == 0)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = wPiece;
					}
					else if ((j%2)==1)
					{
						if((i%2) == 1)
							this.gameBoard[i][j] = EMPTY;
						else
							this.gameBoard[i][j] = wPiece;
					}
				}
			}
		}
	}
	//Written by Jake Levy 

	public void setOrientation(boolean whiteUp)
	{
		if (whiteUp == true)
			whitePlaysUp = true;
		else
			whitePlaysUp = false;

	}
	//Written by Jake Levy

	public boolean getOrientation()
	{
		return whitePlaysUp;
	}
	//Written by Jake Levy 

	public void printBoard()
	{
		System.out.println("The Game LEGEND:");
		System.out.println("\nWhite Pawn: 1");
		System.out.println("White King: 2");
		System.out.println("Black Pawn: 3");
		System.out.println("Black King: 4");
		System.out.println("The current Game looks like this: \n\n");
		for( i = 0; i <8; i++)
			{
				if (i == 0)
					System.out.print("Col:    " + i+"  ");
				else if ( i == 7)
					System.out.println(i);
				else
					System.out.print(i+ "  ");
			}
		
	System.out.print("\n");
		for (i= 0; i<8; i++)
		{
			for(j = 0; j<8; j++)
			{
				if (j == 0)
				{
					System.out.print("Row:" + i+"  ");
					System.out.print("|");
				}
				
				System.out.print(this.gameBoard[i][j]);
				System.out.print("|");
			}
		
			System.out.print("\n");
		}	
	}
	//Written by Jesse Kurtz

	public void startTurns(String userChoice)//human always plays up but white does not, so we set the orientation of the board here
	{
		if (userChoice.equalsIgnoreCase("black"))
			
			{
			this.whoseTurn = 3; //whoseTurn for black is set to 3, same as a black piece
			setOrientation(false);
			System.out.println("It is Black's turn first!");
			}
			
		else
		{
			this.whoseTurn = 1; //white is player1 even if they go second
			setOrientation(true);
			System.out.println("It is White's turn first!");
		}
		//return whoseTurn;
	}
	
	//written by Jake Levy
	public void switchTurns()
	{
		
		if( this.whoseTurn == 1)
		{
			this.whoseTurn = 3;
			//System.out.println("Now it is Black's Turn");
		}
		else{
			this.whoseTurn = 1;
			//System.out.println("Now it is White's Turn");
		}
	}
	
	//Written by Jake Levy & Jesse Kurtz

	public int whoseTurn()
	{
		return this.whoseTurn;
	}
	//Written by Jake Levy & Jesse Kurtz
	public int[][] getBoard()
	{
		return this.gameBoard;
	}
//Written by Jake Levy & Jesse Kurtz
	public int getPiece(int row, int col)
	{
		return this.gameBoard[row][col];
	}
	//
	/**
	 * Written by Jake Levy
	 * whatMoves is a test function for the checkerBoard class that is not used in the final version
	 * Its main purpose was to print out what  oves were available to the current player and print them to the screen.
	 * This allowed us to verify that the generateMoves function was working correctly
	 * @param currentGame
	 */
	public static void whatMoves(CheckerBoard currentGame)
	{
		


		childMoves = CheckerMove.generateMoves(currentGame);
	
		for(CheckerMove each: childMoves)
		{
			System.out.println(each.toString());
			System.out.println("The resulting Board had an H value of: " + each.getResultVal());
		}
		
		
	}
}
