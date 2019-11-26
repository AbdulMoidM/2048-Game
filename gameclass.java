/*Author: Abdul Moid Munawar
  Project: 2048 Game
  Email: amunawar@u.rochester.edu
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class gameclass {
	/*
	 Empty cells in my array are represented by zero
	 */

	static ArrayList<String> elementThatMerged= new ArrayList<String>();//to store elements that merge during one move, so they do not merge again
	static boolean validMove=false;//to check if move was valid, for each move
	static boolean moveDuringMerge=false;//to check if there was any form of merging during a move, to ultimately decide whether to generate a random number or not
	static boolean couldNotGenerate=false;//to check if number could not be generated in generateRandom method. This will lead to automatic gameover
	static int mergeCounter;//to check how many times elements merged during each move

	/**method to print out 2d arr that is taken as input**/
	public static void print2Darr(int [][] arr) {
		System.out.println("-      -      -      -      -      -");//at top
		for (int i=0; i<arr.length;i++) {//for rows of arr
			System.out.print("|      ");//start of each row
			for (int j=0; j<arr[0].length;j++) {//for columns of arr

				if(arr[i][j]!=0) {
					System.out.print(arr[i][j]);//print out every element in row one by one with spaces
				}else {
					System.out.print("*      ");
				}
				if(arr[i][j]!=0) {
					if(arr[i][j]<10) {//to add extra space for single digits
						System.out.print("      ");
					}else if(arr[i][j]<100) {//to add extra space for double digits
						System.out.print("     ");
					}else if(arr[i][j]<1000) {//to add extra space for trip;e digits
						System.out.print("    ");
					}else {
						System.out.print("   ");
					}
				}
			}
			System.out.println("|");//end of each row
		}
		System.out.println("-      -      -      -      -      -");//at bottom
	}

	/**method to check if game is over, and if player lost or won. It is called after every valid move**/
	public static String checkGameOver(int [][] arr) {
		if(couldNotGenerate) {//if generateRandom method could not generate then it means player has lost
			return "gameover";
		}
		boolean gameOver=false;//will be used to check if player lost 
		boolean gameWon=false;//will be used to check if player lost 
		//loops below check if player won game by creating a 2048
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(arr[i][j]==2048) {
					gameWon=true;//true if 2048 found
				}
			}
		}
		if(gameWon) {
			return "won";
		}

		if(checkIfFull(arr)) {//if array is full then it is candidate for gameover. The nested if statements will
			//check if there are any equal adjacent elements that allow a move, otherwise gameover
			gameOver=true;//start with premise gameOver is true, if equal adjacent elements found then this is reversed
			for(int i = 0; i < 4; i++){ //will traverse array to see if any two adjacent elements are equal
				for(int j = 0; j < 4; j++){
					if(i-1>=0) {//to avoid null pointer exception
						if(arr[i][j]==arr[i-1][j]) {//if element below is equal
							gameOver=false;	//if any adjacent elements are equal, game cannot end
						}
					}
					if(i+1<=3) {//to avoid null pointer exception
						if(arr[i][j]==arr[i+1][j]) {//if element above is equal
							gameOver=false;	//if any adjacent elements are equal, game cannot end
						}
					}
					if(j+1<=3) {//to avoid null pointer exception
						if(arr[i][j]==arr[i][j+1]) {//if element on right is equal
							gameOver=false;	//if any adjacent elements are equal, game cannot end
						}
					}
					if(j-1>=0) {//to avoid null pointer exception
						if(arr[i][j]==arr[i][j-1]) {//if element on left is equal
							gameOver=false;	//if any adjacent elements are equal, game cannot end
						}
					}
				}
			}
		}

		if(gameOver) {//is gameOver came out true
			return "gameover";
		}else {
			return "continue";
		}
	}

	/**method to check if array is full. Used in checkGameOver method **/
	public static boolean checkIfFull(int [][] arr) {
		boolean full=true;//start with true, if any 0 found then becomes false
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(arr[i][j] == 0) {
					full=false;
				}
			}
		}
		return full;
	}
	/**method to generate random 2 or 4 in empty cell**/
	public static void generateRandom(int [][] arr) {

		if(!checkIfFull(arr)) {//array must be not full to continue
			boolean setValue=false;//will be used to make sure a number is placed inside the array
			Random r= new Random();
			int[] arr2or4={2,2,2,2,2,2,2,2,4,4};//2 occurs 8 times and 4 2 times, so that 2 has a probability of being selected of 0.8, and 4 has 0.2
			while(setValue==false) {
				int i=r.nextInt(4);
				int j=r.nextInt(4);
				if(arr[i][j] == 0) {
					arr[i][j]=arr2or4[r.nextInt(10)];//randomly generates an index of arr with 2s and 4s and 2 has a greater probablity of occuring
					setValue=true;//the first time an empty cell is filled, this boolean becomes true and loop ends
				}
			}
		}else {
			couldNotGenerate=true;//if array was full when this method was called, it means game is over. This boolean is used in checkGameOver method
		}
	}

	/**method to merge elements during move to the right*/
	public static void mergeMoveRight(int [][] arr) {
		moveDuringMerge=false;//will become true only when two elements merge
		for(int i = 0; i < 4; i++){
			for(int j = 3; j >=1; j--){
				//the long if condition below is to ensure that first two adjacent elements are equal, then that both these
				//elements are not in the list that keeps track of elements that already merged during one move
				if(arr[i][j] == arr[i][j-1] && !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j])) && !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j-1)+Integer.toString(arr[i][j-1]))&&arr[i][j]!=0) {//numbers match in direction that we are moving
					arr[i][j-1]=2*arr[i][j];
					arr[i][j] = 0;
					elementThatMerged.add(Integer.toString(i)+Integer.toString(j-1)+Integer.toString(arr[i][j-1]));
					moveDuringMerge=true;
					mergeCounter++;//increment for every successful merge
				}
			}
		}
	}

	/**method to shift elements to the left**/
	public static void moveLeft(int [][] arr) {
		mergeCounter=0;//start each move with zero
		boolean neverMoved=true;//becomes true when any element moves
		boolean movedDuringTraversal=true;//to check if there was any movement in this method
		while(	movedDuringTraversal==true) {
			mergeMoveLeft(arr);
			movedDuringTraversal=false;
			for(int i = 0; i < 4; i++){
				for(int j = 3; j>=1; j--){
					if(arr[i][j]!=0 && arr[i][j-1]==0) {//if there is empty cell in direction that we need to move
						arr[i][j-1]=arr[i][j];//swap
						//if statement below removed element from merge list if it was already there, and adds 
						//the new position of the element into the list
						if(elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))) {
							elementThatMerged.remove(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]));
							elementThatMerged.add(Integer.toString(i)+Integer.toString(j-1)+Integer.toString(arr[i][j-1]));}
						arr[i][j]=0;
						movedDuringTraversal=true;
						neverMoved=false;
						validMove=true;
					}
				}
			}

			mergeMoveLeft(arr);
		}
		if (neverMoved && !moveDuringMerge) {//either movement or merging must happen for move to be valid
			validMove=false;
		}
		elementThatMerged.clear();//clear this list after every move
		if(validMove) {
			generateRandom(arr);//generate number only for valid moves
		}
	}

	/**method to merge elements during move to the left*/
	public static void mergeMoveLeft(int [][] arr) {
		moveDuringMerge=false;//will become true only when two elements merge
		for(int i = 0; i < 4; i++){
			for(int j = 0; j<3; j++){
				//the long if condition below is to ensure that first two adjacent elements are equal, then that both these
				//elements are not in the list that keeps track of elements that already merged during one move
				if(arr[i][j] == arr[i][j+1] && !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))&& !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j+1)+Integer.toString(arr[i][j+1]))&&arr[i][j]!=0) {//numbers match in direction that we are moving
					arr[i][j+1]=2*arr[i][j];
					arr[i][j] = 0;
					elementThatMerged.add(Integer.toString(i)+Integer.toString(j+1)+Integer.toString(arr[i][j+1]));
					moveDuringMerge=true;
					mergeCounter++;//increment for every successful merge
				}
			}
		}
	}
	/**method to shift elements to the right**/
	public static void moveRight(int [][] arr) {
		mergeCounter=0;//start every move with it equal to zero
		boolean neverMoved=true;//becomes true when any element moves
		boolean movedDuringTraversal=true;//to check if there was any movement in this method
		while(	movedDuringTraversal==true) {
			mergeMoveRight(arr);
			movedDuringTraversal=false;
			for(int i = 0; i < 4; i++){
				for(int j = 0; j<3; j++){
					if(arr[i][j]!=0 && arr[i][j+1]==0) {//if there is empty cell in direction that we need to move
						arr[i][j+1]=arr[i][j];//swap
						//if statement below removed element from merge list if it was already there, and adds 
						//the new position of the element into the list
						if(elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))) {
							elementThatMerged.remove(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]));
							elementThatMerged.add(Integer.toString(i)+Integer.toString(j+1)+Integer.toString(arr[i][j+1]));}
						arr[i][j]=0;
						movedDuringTraversal=true;
						neverMoved=false;
						validMove=true;
					}
				}
			}

			mergeMoveRight(arr);
		}
		if (neverMoved && !moveDuringMerge) {//either movement or merging must happen for move to be valid
			validMove=false;
		}
		elementThatMerged.clear();//clear this list after every move
		if(validMove) {
			generateRandom(arr);//generate number only for valid moves
		}
	}

	/**method to merge elements during move upwards*/
	public static void mergeMoveUp(int [][] arr) {
		moveDuringMerge=false;//will become true only when two elements merge
		for(int i = 0; i <3; i++){
			for(int j = 0; j<4; j++){
				//the long if condition below is to ensure that first two adjacent elements are equal, then that both these
				//elements are not in the list that keeps track of elements that already merged during one move
				if(arr[i+1][j] == arr[i][j] && !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))&& !elementThatMerged.contains(Integer.toString(i+1)+Integer.toString(j)+Integer.toString(arr[i+1][j])) &&arr[i][j]!=0) {//numbers match in direction that we are moving
					arr[i+1][j]=2*arr[i][j];
					arr[i][j] = 0;
					elementThatMerged.add(Integer.toString(i+1)+Integer.toString(j)+Integer.toString(arr[i+1][j]));
					moveDuringMerge=true;
					mergeCounter++;//increment for every successful merge
				}
			}
		}
	}
	/**method to shift elements up**/
	public static void moveUp(int [][] arr) {
		mergeCounter=0;//start every move with it equal to zero
		boolean neverMoved=true;//becomes true when any element moves
		boolean movedDuringTraversal=true;//to check if there was any movement in this method
		while(	movedDuringTraversal==true) {
			mergeMoveUp(arr);
			movedDuringTraversal=false;
			for(int i = 3; i >=1; i--){
				for(int j = 0; j<4; j++){
					if(arr[i][j]!=0 && arr[i-1][j]==0) {//if there is empty cell in direction that we need to move
						arr[i-1][j]=arr[i][j];//swap
						//if statement below removed element from merge list if it was already there, and adds 
						//the new position of the element into the list
						if(elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))) {
							elementThatMerged.remove(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]));
							elementThatMerged.add(Integer.toString(i-1)+Integer.toString(j)+Integer.toString(arr[i-1][j]));}
						arr[i][j]=0;
						movedDuringTraversal=true;
						neverMoved=false;
						validMove=true;

					}
				}
			}

			mergeMoveUp(arr);
		}
		if (neverMoved && !moveDuringMerge) {//either movement or merging must happen for move to be valid
			validMove=false;
		}
		elementThatMerged.clear();//clear this list after every move
		if(validMove) {
			generateRandom(arr);//generate number only for valid moves
		}
	}

	/**method to merge elements during move downwards*/
	public static void mergeMoveDown(int [][] arr) {
		moveDuringMerge=false;//will become true only when two elements merge
		for(int i = 3; i >=1; i--){
			for(int j = 0; j<4; j++){
				//the long if condition below is to ensure that first two adjacent elements are equal, then that both these
				//elements are not in the list that keeps track of elements that already merged during one move
				if(arr[i][j] == arr[i-1][j] && !elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))&& !elementThatMerged.contains(Integer.toString(i-1)+Integer.toString(j)+Integer.toString(arr[i-1][j]))&&arr[i][j]!=0) {//numbers match in direction that we are moving
					arr[i-1][j]=2*arr[i][j];
					arr[i][j] = 0;
					elementThatMerged.add(Integer.toString(i-1)+Integer.toString(j)+Integer.toString(arr[i-1][j]));
					moveDuringMerge=true;
					mergeCounter++;//increment for every successful merge
				}
			}
		}
	}

	/**method to shift elements down**/
	public static void moveDown(int [][] arr) {
		mergeCounter=0;//start every move with it equal to zero
		boolean neverMoved=true;//becomes true when any element moves
		boolean movedDuringTraversal=true;//to check if there was any movement in this method
		while(	movedDuringTraversal==true) {
			mergeMoveDown(arr);
			movedDuringTraversal=false;
			for(int i = 0; i<3; i++){
				for(int j = 0; j<4; j++){
					if(arr[i][j]!=0 && arr[i+1][j]==0) {//if there is empty cell in direction that we need to move
						arr[i+1][j]=arr[i][j];//swap
						//if statement below removed element from merge list if it was already there, and adds 
						//the new position of the element into the list
						if(elementThatMerged.contains(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]))) {
							elementThatMerged.remove(Integer.toString(i)+Integer.toString(j)+Integer.toString(arr[i][j]));
							elementThatMerged.add(Integer.toString(i+1)+Integer.toString(j)+Integer.toString(arr[i+1][j]));
						}
						arr[i][j]=0;
						movedDuringTraversal=true;
						neverMoved=false;
						validMove=true;
					}
				}
			}
			mergeMoveDown(arr);
		}
		if (neverMoved && !moveDuringMerge) {//either movement or merging must happen for move to be valid
			validMove=false;
		}
		elementThatMerged.clear();//clear this list after every move
		if(validMove) {
			generateRandom(arr);//generate number only for valid moves
		}
	}

	/**method to initialise board with two filled cells**/
	public static void initialiseBoard(int [][] arr) {
		for(int i=0;i<4;i++) { //intialize array with all zeros
			for (int j=0;j<4;j++){
				arr[i][j]=0;
			}
		}
		generateRandom(arr);//generate first number
		generateRandom(arr);//generate another number
	}

	/**method to find max number in array**/
	public static int max(int [][] arr) {
		int max=0;//starts as lowest possible value in array
		for(int i=0;i<4;i++) { 
			for (int j=0;j<4;j++){
				if(arr[i][j]>max) {//whenever value greater than max found, update max
					max=arr[i][j];
				}
			}
		}
		return max;
	}

	/**MAIN METHOD**/
	public static void main(String[] args) {
		int[][] arr = new int[4][4];//array created that will be used throughout game

		Scanner scan=new Scanner(System.in);
		boolean forInfiniteLoop=true;//to keep inner while loop running unless break occurs
		boolean keepPlaying=true;//to control whether outer while loop is exited or not
		int validMoves;//to keep count of valid moves
		boolean restart;//to check if player wants to restart

		while (keepPlaying) {//outer loop
			restart=false;//start off as false, turn true if player enters 'r' later
			validMoves=0;//start as zero for every new game
			System.out.println("WELCOME TO THE 2048 GAME");
			System.out.println("Would you like a summary of the Game's History before we start? Enter 'h' if yes, enter any other key if no.");
			String response=scan.next();
			if(response.equals("h")) {//give history of game
				System.out.println(" THE HISTORY OF 2048. 2048 was created in March 2014 by an Italian web developer\n who was inspired by the game of three's. The then 19-year old Gabrielle Cirulli created\n the game in just one weekend and never expected the\n game to sky-rocket the way it did. It received over 4 million views just after its release.");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
			}
			System.out.println("LETS START! Here are the rules:");

			System.out.println("Whenever you enter move you can enter 'r' to restart game, and 'q' to end game at any point");
			System.out.println("During the game, enter w, a, s, or d to move up, left, down, or right");
			initialiseBoard(arr);//board initialised or reset
			System.out.println("");
			print2Darr(arr);//initial board shown to user
			boolean firstMove=true;//so that feedback is not given for first move;
			while(forInfiniteLoop) {//inner loop of game
				boolean inputValid;//to check if input from user is a valid one
				do {
					if(validMove) {//so that feedback is only given for valid moves
						if(mergeCounter==0 && !firstMove) {//to give user enjoyable feedback over their move
							System.out.println("No merge but no pressure! Keep going :D");
						}
						if(mergeCounter==1) {//to give user enjoyable feedback over their move
							System.out.println("Solid Single Merge!");
						}
						if(mergeCounter==2) {//to give user enjoyable feedback over their move
							System.out.println("Nice! A Double Merge!!");
						}if(mergeCounter==3) {//to give user enjoyable feedback over their move
							System.out.println("Cool! A Triple Merge!!! Now that's something :O");
						}if(mergeCounter==4) {//to give user enjoyable feedback over their move
							System.out.println("WHOA! Quadruple Merge :O!!!! That's raree! ");
						}
						if(mergeCounter>4){
							System.out.println("Perfect Move! Words cannot describe the rarity of more than 4 merges in one go! \n This shall go down in history.");
						}
					}
					inputValid=true;//set to true before new movement
					if(firstMove) {
						System.out.print("What will be your first move! :  ");
					}else {
						System.out.print("Enter your next move:  ");
					}
					String move=scan.next();
					if(move.equals("w")){
						moveUp(arr);
					}else if(move.equals("a")){
						moveLeft(arr);
					}else if(move.equals("d")){
						moveRight(arr);
					}else if(move.equals("s")){
						moveDown(arr);
					}else if(move.equals("q")){
						//confirmation is asked below before break
						System.out.println("Are you sure you want to quit? Enter 'y' or 'Y' is yes, enter any other key if no.");
						String input=scan.next();
						if(input.equals("y") || input.equals("Y")) {
							keepPlaying=false;
							break;
						}else {
							System.out.println("No worries, you can enter your move again.");
							inputValid=false;
						}
					}else if(move.equals("r")){
						//confirmation is asked below before break
						System.out.println("Are you sure you want to restart the game? Enter 'y' or 'Y' is yes, enter any other key if no.");
						String input=scan.next();
						if(input.equals("y") || input.equals("Y")) {
							restart=true;
							break;
						}else {
							System.out.println("No worries, you can enter your move again.");
							inputValid=false;
						}
					}else {
						System.out.println("Invalid input!");
						inputValid=false;
					}
				}while(!inputValid);//end of input from user
				if(!keepPlaying || restart) {//if user pressed exit or restart
					break;//exit inner while loop with keepPlaying=false if q pressed, and true if r pressed
				}

				if(validMove) {//move has to be valid to make it worth checking for game won or game over
					String end=checkGameOver(arr);//to get response from checkGameOver method

					if(end.equals("gameover")) {//method returned gameover
						System.out.println("GAME OVER! This was your final state:");
						print2Darr(arr);
						//below user is asked if he wants to play again 
						System.out.println("Your game stats will be printed after your resonse.");
						System.out.println("Would you like to play again? Enter 'y' if yes, enter any other key if no.");
						String input=scan.next();
						if(input.equals("y")) {
							break;//exit inner loop with keepPlaying=true
						}else {
							keepPlaying=false;
							break;//exit inner loop with keepPlaying=false
						}
					}else {//if game not over

						if(end.equals("won")) {//method returned won
							System.out.println("CONGRAGULATIONS! You won! This was your final state:");
							print2Darr(arr);
							//below user is asked if he wants to play again 
							System.out.println("Your game stats will be printed after your resonse.");
							System.out.println("Would you like to play again? Enter 'r' if yes, enter any other key if no.");
							String input=scan.next();
							if(input.equals("y")) {//exit inner loop with keepPlaying=true
								break;
							}else {
								keepPlaying=false;
								break;//exit inner loop with keepPlaying=false
							}
						}
					}
					System.out.println("");
					System.out.println("");
					System.out.println("");
					print2Darr(arr);
					validMoves++;//increase validMoves by 1
				}else {
					System.out.println("That does not move any element, try another direction!");//if invalid move
				}
				firstMove=false;
			}//end of inner while loop

			System.out.println("Your maximum number was : "+ max(arr));//print out maximum number in array
			System.out.print("You made " + validMoves + " valid Moves."); //print out number of valid moves
			if(validMoves<20) {
				System.out.print(" Short Game! Hope you stick around longer next time. ");
			}
			else if(validMoves>100) {
				System.out.print(" Wow, that was a long game :O! Hope you enjoyed!");
			} else {
				System.out.println("Good Game!");
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
			//game restarted if keepPlaying remained true.

		}//end of outer while loop

		System.out.println("You have exited the 2048 Game");//when outer while loop exited
		scan.close();

	}//end of main method

}