import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class GamePlay {

	public void makeMove(Stone [][] board){
		List<Move> listOfMoves = new ArrayList<Move>();

		for( int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				if ( (board[i][j].getIsstone() == true) &&
						(board[i][j].getColor() == Color.red)){
					for( int k = -2; k < 3; k++){
						for (int m = -2; m < 3; m++){
							if( ((k != 0) && (m != 0)) && 
									((((i + m) < 16)) && ((i+m) > -1)) &&
									((((j + k) < 16)) && ((j+k) > -1))){
								if(board[i+m][j+k].isStone == false){
									if( (k % 2 == 0) && (m % 2 == 0) ){
										if(board[i+(m/2)][j+(k/2)].isStone == true){
											//calculate something and add to list
											Move moveStone = new Move();
											moveStone.setIBJB(i, j);
											moveStone.setIEJE(i+m, j+k);
											listOfMoves.add(moveStone);
										}
									} else if ( (k % 2 != 0) && (m % 2 != 0)){
										//calculate something and add to list
										Move moveStone = new Move();
										moveStone.setIBJB(i, j);
										moveStone.setIEJE(i+m, j+k);
										listOfMoves.add(moveStone);
									}
								}
							}
						}
					}
				}
			}
		}

		sortList(listOfMoves, board);
		//System.out.println("length of sortlist1:" +listOfMoves.size());
	}
	
	private void sortList(List<Move> listOfMoves, Stone [][] board){
		Move tempMove = new Move();

		//System.out.println("length of sortlistinside:" +listOfMoves.size());
		for(int i = 0; i < listOfMoves.size()-1; i++){
			for(int j = i+1; j < listOfMoves.size(); j++){
				if( (board[listOfMoves.get(i).getIB()][listOfMoves.get(i).getJB()].getR0() > 
				board[listOfMoves.get(j).getIB()][listOfMoves.get(j).getJB()].getR0())){
					tempMove = listOfMoves.get(i);
					listOfMoves.set(i, listOfMoves.get(j));
					listOfMoves.set(j, tempMove);
				}
			}
		}
		for (int i = 0; i < listOfMoves.size(); i++){
			if(board[listOfMoves.get(i).getIB()][listOfMoves.get(i).getJB()].getR1() > 
			board[listOfMoves.get(i).getIE()][listOfMoves.get(i).getJE()].getR1()){
				board[listOfMoves.get(i).getIB()][listOfMoves.get(i).getJB()].setIsstone(false);
				board[listOfMoves.get(i).getIB()][listOfMoves.get(i).getJB()].setColor(Color.green);
				board[listOfMoves.get(i).getIE()][listOfMoves.get(i).getJE()].setColor(Color.red);
				board[listOfMoves.get(i).getIE()][listOfMoves.get(i).getJE()].setIsstone(true);
				return;
			}
		}
	}	
}

