import java.util.Scanner;

import java.util.ArrayList;
/*
 * AI Checkers (Revised Draft) by 
 * Jake Levy
 * Jesse Kurtz
 * 
 * This is our driver class.  It contains the main fucntion and housed all "test code" during development
 * that has been used to test the effectiveness of our game and its functions.
 *	Now, of course, it serves as home for the main game loop (the game) and is where all the action happens.
 * 
 * 
 * */


public class PlayCheckers2 {

	static boolean inputValid = false;
	static boolean diffValidity = false;
	static char[] checkLetter;
	static String userChoice;
	static String compChoice;
	static String diffChoice;
	static int startRow = 0;
	static int startCol = 0;
	static int endRow = 0;
	static int endCol = 0;
	static int maxDepth;
	static boolean nextJump = false;
	static boolean legal_move;
	static int[] moveArr = new int[4];
	ArrayList<CheckerMove> childMoves = new ArrayList<>();
	public static void main(String[] args) {
		
		System.out.println("**************************************************");
		System.out.println("*             WELCOME TO AI CHECKERS             *");
		System.out.println("*                   written by                   *");
		System.out.println("*           Jake Levy and Jesse Kurtz            *");
		System.out.println("**************************************************");
		
		
		System.out.println("\n\n\n\n");
		System.out.println("THE RULES:");
		System.out.println("1) There are two kinds of pieces, Pawns and Kings.  A Pawn can move one square, diagonally, forward with respect to its home base.");
		System.out.println("2)  When a Pawn reaches the opposing player's home base line, it becomes a King.");
		System.out.println("3)  A King can move one square diagonally, forward or backward.");
		System.out.println("4)  A piece (pawn or king) can only move to a vacant square.");
		System.out.println("    A move can also consist of one or more Jumps. A Jump is a capture move.");
		System.out.println("5)  To capure the opponent's piece, it must be jumped diagonally in the directions that the capturing piece may move, as described above. ");
		System.out.println("6)  A jump must end in empty space. ");
		System.out.println("7)  Multiple, chained jumps are allowed.  In a chain jump, the jumping piece may switch directions according\n"
				+		   "to how it may move as described above.  I.E.  Pawns still cannot jump backwards.");
		System.out.println("8) If a jump is available, you must jump.  However, if there are multiple separate jumps available, then no jump takes precedence over any other,\n"+
							"even if one is a multiple jump chain.");
		System.out.println("9)  If a chain jump is begun, it must be completed.");
		System.out.println("10) Note that this game uses a rule variant in which a piece that has become a king in the current turn can continue, the same move,\n" 
				+ 		   " to capture other pieces.");
		System.out.println("Lastly, please note that to help keep things less deterministic, the human player will always go first, no matter what color they choose.");
		
	
		
		Scanner response = new Scanner(System.in);
		System.out.println("\n\n\n");
		System.out.println("Which color would you like to play as: White or Black?");
		System.out.println("Please indicate your choice by inputting white or black: ");
	
		
		do//to force specific input
		{
			
			String input = response.next();
			userChoice = input.toLowerCase();
			if ((userChoice.equalsIgnoreCase("white"))|| (userChoice.equalsIgnoreCase("black")))
				{
					inputValid = true;
				}
					
			else
				System.out.println("Invalid Input, please try again: ");
			
		}while(inputValid == false);//end do loop
		//here we set the opponent's color based on the user's choice
		if ((userChoice.equalsIgnoreCase("white")))
			{
				compChoice = "Black";
			}
		else
			{
				compChoice = "White";
			}
			System.out.println("Please choose your difficulty level.");
			System.out.println("Enter 'Easy', 'Novice', 'Intermediate', or 'Expert': ");
					
		do//to force the user to input a difficulty choice
		{
			String difficulty = response.next();
					diffChoice = difficulty.toLowerCase();
					if (diffChoice.equalsIgnoreCase("easy"))
					{
						diffValidity = true;
						maxDepth = 2;
					}
					else if (diffChoice.equalsIgnoreCase("novice"))
					{
						diffValidity = true;
						maxDepth = 4;
					}
					else if (diffChoice.equalsIgnoreCase("intermediate"))
					{
						diffValidity = true;
						maxDepth = 6;
					}
					else if (diffChoice.equalsIgnoreCase("expert"))
					{
						diffValidity = true;
						maxDepth = 10;
					}
					else 
					{
						System.out.println("Invalid, please try again: ");
					}
			
		}while(diffValidity == false);//end do loop
		CheckerBoard newGame = new CheckerBoard();//here we create an empty game state
		
		newGame.setUpBoard(userChoice);//and set up the board based on what the user has chosen
		
		
		newGame.printBoard();//display the board
		//Evaluation.printScore(newGame);
		newGame.startTurns(userChoice);//since the human player always goes first (to introduce a bit more randomness to the game)
		
		
		System.out.println("Make your move by inputting your chosen piece's starting coordinates:");

		System.out.println("The current Heuristic eval of the board is:" + Evaluation.TotalEvaluation(newGame));
		
		//while the game is not over, if there are jumps or slides available, the user may input a move on their turn
		//we create an empty int array  based on the user's input, setting each element individually to correspond to where they would 
		//be in a CheckerMove object
		while (newGame.isGameOver()== false){
			if (CheckerMove.areJumpsAvailable(newGame)|| CheckerMove.areSlidesAvailable(newGame)){
		do {//while legal_move == false, outer do loop
		do{
		System.out.print("Please enter Starting Row: ");
			
			while(!response.hasNextInt())
			{
				System.out.println("You must enter an Integer number between 0 & 7, inclusive");
				response.next();
			}
			startRow = response.nextInt(); //store in an array
			if (!CheckerMove.inputRange(startRow))
				System.out.println("You must enter an Integer number between 0 & 7, inclusive");
		} while (!CheckerMove.inputRange(startRow));
		
		
		moveArr[0] = startRow;  
		do{
			System.out.print("Please enter Starting Column: ");
				
				while(!response.hasNextInt())
				{
					System.out.println("You must enter an Integer number between 0 & 7, inclusive");
					System.out.print("Please enter Starting Column: ");
					response.next();
				}
				startCol = response.nextInt(); //store in an array
				if (!CheckerMove.inputRange(startCol))
					System.out.println("You must enter an Integer number between 0 & 7, inclusive");
			} while (!CheckerMove.inputRange(startCol));
			
		moveArr[1] = startCol;
				
		
		do{
			System.out.print("Please enter Ending Row: ");
				
				while(!response.hasNextInt())
				{
					System.out.println("You must enter an Integer number between 0 & 7, inclusive");
					System.out.print("Please enter Ending Row: ");
					response.next();
				}
				endRow = response.nextInt(); //store in an array
				if (!CheckerMove.inputRange(endRow))
					System.out.println("You must enter an Integer number between 0 & 7, inclusive");
			} while (!CheckerMove.inputRange(endRow));
		
			moveArr[2] = endRow;
			do{
				
				System.out.print("Please enter Ending Column: ");
					
					while(!response.hasNextInt())
					{
						System.out.println("You must enter an Integer number between 0 & 7, inclusive");
						System.out.print("Please enter Ending Column: ");
						response.next();
					}
					endCol = response.nextInt(); //store in an array
					if (!CheckerMove.inputRange(endCol))
						System.out.println("You must enter an Integer number between 0 & 7, inclusive");
				} while (!CheckerMove.inputRange(endCol));
			moveArr[3] = endCol;
			System.out.println("\n\n\n\n");
	
			
			//We now create the actual CheckerMove object with the int array we created above
			CheckerMove newMove = new CheckerMove(moveArr);
			legal_move = CheckerMove.isMoveLegal(newGame, newMove);//test to see if its a legal move
			if(legal_move == true)//if the move is legal
			{					
	
				if(Math.abs(endRow - startRow) == 2)//if it is a legal jump, make the jump and then check to see if it is part of a chain of jumps
				{
			
				CheckerMove.makeMove(newGame, newMove);//since input is limited to one move at a time, we call makeMove rather than move_board
				int[] madeMove = new int[4];//create an array for move that we can modify and check in a while loop
				madeMove = newMove.getPositions();//fill it with the just made move positions
				
				System.out.println("You jumped from Row: "+ startRow + " and Column: " + startCol+ " to Row: " + endRow + " and Column: "+ endCol);
				
				
					while (CheckerMove.canJumpFromPosition(newGame, madeMove[2], madeMove[3]))//if the player can jump again from his ending position
					{
					
						//NOTE:  we do not force captures here in case there are two possible jumps mid-chain, we allow the player to choose which next jump, if any, to make
						CheckerMove nextMove = new CheckerMove();//create a new move for this condition
						int nextStartR = madeMove[2];//new move starts at the old ending position
						int nextStartC = madeMove[3];
						int nextEndR;
						int nextEndC;
						do{//mid-level do loop to enforce that we go through this check at least once
							do{//inner do loop to enforce specific input
								System.out.println("There is still a jump available in this chain!  You must continue the chain, so please next ending row: ");
								while(!response.hasNextInt())
								{
									System.out.println("You must enter an Integer number between 0 & 7, inclusive");
									System.out.print("Please enter Ending Row: ");
									response.next();
								}
								nextEndR = response.nextInt(); //store in an array
								if (!CheckerMove.inputRange(nextEndR))
									System.out.println("You must enter an Integer number between 0 & 7, inclusive");
							} while (!CheckerMove.inputRange(nextEndR));//end inner do
							do{//start second inner do to enforce input
								System.out.println("Please enter next ending column: ");
								while(!response.hasNextInt())
								{
									System.out.println("You must enter an Integer number between 0 & 7, inclusive");
									System.out.print("Please enter Ending columun: ");
									response.next();
								}
								nextEndC = response.nextInt(); //store in an array
								if (!CheckerMove.inputRange(nextEndC))
									System.out.println("You must enter an Integer number between 0 & 7, inclusive");
							} while (!CheckerMove.inputRange(nextEndC));//end second inner do
					
							nextMove.setMoves(nextStartR, nextStartC, nextEndR, nextEndC);//fill this new jump up with starting and ending positions
							nextJump = CheckerMove.isMoveLegal(newGame, nextMove);//check to see if this new jump is legal
							if (nextJump == false)//if its not legal, force user to input new coordinates
							{
								System.out.println("That is not a legal move, please try again!!");
							}
							else//if it is legal apply the move to the board and print the board
							{
								CheckerMove.makeMove(newGame, nextMove);
								newGame.printBoard();
								// Evaluation.printScore(newGame);
							}
						}while(nextJump== false);//end of mid-level do loop to make sure the new jump is legal
					 madeMove = nextMove.getPositions();//reset madeMove array to the most recently made move positions so the while condition can be checked
				
					}
				}
				if (Math.abs(endRow - startRow) == 1)//if it is a legal slide, make the move
					{
				
					CheckerMove.makeMove(newGame, newMove);
					System.out.println("You slid from Row: "+ startRow + " and Column: " + startCol+ " to Row: " + endRow + " and Column: "+ endCol);

					}
			}
				else//those are the only two possible legal conditions that would return true from isMovelegal, so anything else means it was an illegal move
				{
					System.out.println("You've made an illegal move.  TRY AGAIN!");
				}
		}while(legal_move == false);//end  of outer do-loop
			}
			else{//if there are no moves left, the user has lost, and the game ends
				System.out.println(userChoice+ " has lost!");
				newGame.endGame();
				
			}
		if(newGame.isGameOver() != true)//else if there are moves left, we switch the turn and print the board
		{
		newGame.switchTurns();
		newGame.printBoard();
		//Evaluation.printScore(newGame);
		
			if (newGame.isGameOver() == false && (CheckerMove.areSlidesAvailable(newGame) || CheckerMove.areJumpsAvailable(newGame)))
			{//now we check for the opponent
				//if opponent still has moves and the game is not over already, we create an empty array to store the best positions
				int[] newMove = new int[4];
				int nextScore = MiniMaxSearch.MiniMax(newGame, 0, maxDepth, newMove);//we call minimx which returns the best found score
				//unfortunately we don't need the score as much as we need the best moves which have been stored in the empty array
				
				CheckerMove compMove = new CheckerMove (newMove);//create a CheckerMove object based on the program's best move
				CheckerMove.move_board(newGame, compMove);//apply the move to the board
			//for convenience we display what the program's move was
				System.out.println(compChoice + " has moved from Row: " + newMove[0] + " and Column: " + newMove[1] + " to Row: " + newMove[2] + "and Column: " + newMove[3]);
				System.out.print("\n\n\n");
				newGame.switchTurns();//switch turns and print the board
				newGame.printBoard();
				//Evaluation.printScore(newGame);
			}
			else 
			{
				System.out.println(compChoice + " has lost!");
				newGame.endGame();
			}
		}
		
		}
		
		
			
			response.close();
			System.exit(0);
	}

}
