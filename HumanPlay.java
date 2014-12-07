import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;

public class HumanPlay {

	public boolean makeMove(Stone [][] board, int xb_in, 
			int yb_in, int xe_in, int ye_in){
		Move humanMove = new Move();
		List<Stone> listOfStones = new ArrayList<Stone>();
		boolean haveMoved = false;

		readBoardToList(board, listOfStones);
		sortListOfStones(listOfStones, xb_in, yb_in);
		humanMove.setIBJB(listOfStones.get(0).getI(), listOfStones.get(0).getJ());
		sortListOfStones(listOfStones, xe_in, ye_in);
		humanMove.setIEJE(listOfStones.get(0).getI(), listOfStones.get(0).getJ());
		//here, we will check that the stone being moved is blue, and that the
		//place that we move it to is empty
		boolean stoneOk=checkStones( board, humanMove);
		Move weAreGoing=new Move();
		weAreGoing.setIBJB(humanMove.getIB(), humanMove.getJB());
		boolean haveMadeDoubleMove=false;
		List<Move> listOfMoves = new ArrayList<Move>();
		boolean moveOk=isMoveValid( board, humanMove,weAreGoing, haveMadeDoubleMove,listOfMoves);
		System.out.println("moveOk"+ ":" + moveOk);
		if (stoneOk && moveOk){
			//now, make the move
			board[humanMove.getIB()][humanMove.getJB()].setIsstone(false);
			board[humanMove.getIB()][humanMove.getJB()].setColor(Color.green);
			board[humanMove.getIE()][humanMove.getJE()].setColor(Color.blue);
			board[humanMove.getIE()][humanMove.getJE()].setIsstone(true);

			System.out.println("setting stone true in makemove: ");
			System.out.println("humanMove.getIE(): " +humanMove.getIE());
			System.out.println("humanMove.getJE(): " +humanMove.getJE());

			haveMoved = true;
			xb_in = yb_in = xe_in = ye_in = -1;
			listOfMoves.clear();
		}
		else {
			haveMoved = false;
			xb_in = yb_in = xe_in = ye_in = -1;
			System.out.println("*************did not make move****************************************************************");
			System.out.println("humanMove.getIE(): " +humanMove.getIE());
			System.out.println("humanMove.getJE(): " +humanMove.getJE());
			listOfMoves.clear();
		}
		return haveMoved;
	}

	private void readBoardToList(Stone [][] board, List<Stone> listOfStones){

		for( int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				listOfStones.add(board[i][j]);
			}
		}
	}

	private void sortListOfStones(List<Stone> listOfStones, int x_in, int y_in){
		Stone tempStone = new Stone();
		int x, y, rstonei, rstonej;

		x = x_in;
		y = y_in;

		for(int i = 0; i < listOfStones.size(); i++){
			for(int j = i+1; j < listOfStones.size(); j++){
				rstonei = calcRadius(listOfStones.get(i).getX()-x, 
						listOfStones.get(i).getY()-y);
				rstonej = calcRadius(listOfStones.get(j).getX()-x, 
						listOfStones.get(j).getY()-y);

				if( (rstonei > rstonej) ){
					tempStone = listOfStones.get(i);
					listOfStones.set(i, listOfStones.get(j));
					listOfStones.set(j, tempStone);
				}
			}
		}
	}
	private int calcRadius(int x, int y){
		int r = 0;

		r = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return r;
	}
	private boolean checkStones(Stone [][] board, Move humanMove){

		//first check that the stone we move is blue
		//then check that the place we move it to is empty
		if ((board[humanMove.getIB()][humanMove.getJB()].getColor() == Color.blue) &&
				(board[humanMove.getIE()][humanMove.getJE()].isStone == false))
			return true;
		return false;

	}

	private boolean checkIfMoveInList(Move whereWeAre,List<Move> listOfMoves){

		//check if move in list, if it is, we have been there before
		//and shouldn't go there again
		for(Iterator<Move> i = listOfMoves.iterator(); i.hasNext(); ) {
			Move item = i.next();

			if(whereWeAre.getIB()==item.getIB() && whereWeAre.getJB()==item.getJB()){
				System.out.println("checkIfMoveInList:returning true");
				return true;
			}
		}
		return false;
	}
	private boolean isMoveValid(Stone [][] board, Move humanMove, Move whereWeAre, boolean haveMadeDoubleMove,List<Move> listOfMoves){
		Move weAreGoing=new Move();
		Move whereWeAreLocal=new Move();

		System.out.println("***********entering isMoveValid*********");
		
		whereWeAreLocal.setIBJB(whereWeAre.getIB(), whereWeAre.getJB());
		listOfMoves.add(whereWeAreLocal);
		if (humanMove.getIE()==whereWeAreLocal.getIB() && humanMove.getJE()==whereWeAreLocal.getJB()){
			System.out.println("returning true; we want to go here");
			return true;
		}

		if (humanMove.getIB()==whereWeAreLocal.getIB() && humanMove.getJB()==whereWeAreLocal.getJB()){
			System.out.println("We are back; original square");
			haveMadeDoubleMove=false;
			listOfMoves.clear();
		}

		for (double alpha  = 0; alpha <= 2*Math.PI; alpha= alpha + (Math.PI/4)){
			int i=0;
			int j=0;

			i=(int) Math.round(Math.cos(alpha));
			j=(int)Math.round(Math.sin(alpha));
			if((whereWeAreLocal.getIB()+2*i)<=15 && (whereWeAreLocal.getJB()+2*j<=15) && (whereWeAreLocal.getIB()+2*i)>=0 && (whereWeAreLocal.getJB()+2*j>=0)){
				weAreGoing.setIBJB(whereWeAreLocal.getIB()+2*i, whereWeAreLocal.getJB()+2*j);
			}

			System.out.println("***new for round****");
			//check first no stone here, if no stone, go here,
			//if end, return true, if stone, jump over
			if ((whereWeAreLocal.getIB()+i)<=15 && (whereWeAreLocal.getJB()+j<=15) && (whereWeAreLocal.getIB()+i)>=0 && (whereWeAreLocal.getJB()+j>=0)) {
				if (board[whereWeAreLocal.getIB()+i][whereWeAreLocal.getJB()+j].isStone == false && !haveMadeDoubleMove){
					System.out.println("inside single");
					int sum1=whereWeAreLocal.getIB()+i;
					int sum2=whereWeAreLocal.getJB()+j;

					if (humanMove.getIE()==(sum1) && humanMove.getJE()==(sum2)){
						System.out.println("single:returning true");
						return true;
					} else {
						//return false;
					}
				} else if((whereWeAreLocal.getIB()+2*i)<=15 && (whereWeAreLocal.getJB()+2*j<=15) && (whereWeAreLocal.getIB()+2*i)>=0 && (whereWeAreLocal.getJB()+2*j>=0)){
					boolean inList=checkIfMoveInList(weAreGoing, listOfMoves);
					System.out.println("inside double");
					System.out.println("double: inlist: " +inList);
					System.out.println("double: what's in the list here: ");
					for(Iterator<Move> listCount = listOfMoves.iterator(); listCount.hasNext(); ) {
						Move item = listCount.next();
						System.out.println("double: in list jb: " +item.getIB() +" in list ib: " +item.getJB());	
					}

					System.out.println("board[whereWeAre.getIB()+2*i][whereWeAre.getJB()+2*j].isStone " +board[whereWeAreLocal.getIB()+2*i][whereWeAreLocal.getJB()+2*j].isStone  +" board[whereWeAre.getIB()+i][whereWeAre.getJB()+j].isStone: " +board[whereWeAreLocal.getIB()+i][whereWeAreLocal.getJB()+j].isStone);
					System.out.println("double: inlist: " +inList);
					System.out.println("***********************");
					System.out.println("double: whereweareib: " +whereWeAreLocal.getIB() +" wherewearejb: " +whereWeAreLocal.getJB());
					System.out.println("double: weAreGoingib: " +weAreGoing.getIB() +" weAreGoingjb: " +weAreGoing.getJB());
					System.out.println("double: humanMoveie: " +humanMove.getIE() +" humanMoveje: " +humanMove.getJE());

					System.out.println("double: before if");

					if(board[whereWeAreLocal.getIB()+2*i][whereWeAreLocal.getJB()+2*j].isStone == false && board[whereWeAreLocal.getIB()+i][whereWeAreLocal.getJB()+j].isStone == true && !inList){
						System.out.println("double: inside first if");
						if (humanMove.getIE()==weAreGoing.getIB() && humanMove.getJE()==weAreGoing.getJB()){
							System.out.println("double: returning true one");
							return true;
						} else {
							System.out.println("double: inside else");
							boolean returnValue=false;
							haveMadeDoubleMove=true;
							
							System.out.println("double: adding to list: " +" weAreGoing.IB: " +weAreGoing.getIB() +"; weAreGoing.JB: " +weAreGoing.getJB());
							
							for(Iterator<Move> listCount = listOfMoves.iterator(); listCount.hasNext(); ) {
								Move item = listCount.next();
								System.out.println("double: in list jb: " +item.getIB() +" in list ib: " +item.getJB());	
							}
							System.out.println("double: doing recursion here ");	
							returnValue=isMoveValid( board, humanMove,weAreGoing,haveMadeDoubleMove,listOfMoves);
							System.out.println("double: back from recursion ");	
							if (returnValue==true){ 
								System.out.println("double: returning true two");
								return true;
							}
							if (humanMove.getIB()==whereWeAreLocal.getIB() && humanMove.getJB()==whereWeAreLocal.getJB()){
								haveMadeDoubleMove=false;
								listOfMoves.clear();
								System.out.println("double: back to first square");
							}

						}
					}
				}
			}
		}
		return false;
	}
}
