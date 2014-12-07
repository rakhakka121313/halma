import java.awt.*;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;

public class Stone {
	int x, y, i_board, j_board;
	int i_end, j_end, r0, r1;
	Color color;
	Boolean isStone;
	
	public void setR0 (int r0in){
		r0 = r0in;
	}
	public void setR1 (int r1in){
		r1 = r1in;
	}
	public int getR0 (){
		return r0;
	}
	public int getR1 (){
		return r1;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setIsstone (Boolean isStonein){
		isStone = isStonein;
	}
	public Boolean getIsstone (){
		return isStone;
	}
	public Color getColor (){
		return color;
	}
	public void setXY(int xcoord, int ycoord){
		x = xcoord;
		y = ycoord;
	}
	public void setIJ(int i_in, int j_in){
		i_board = i_in;
		j_board = j_in;
	}
	public int getI(){
		return i_board;
	}
	public int getJ(){
		return j_board;
	}
	public int getJ_end(){
		return j_end;
	}
	public int getI_end(){
		return i_end;
	}
	public void setColor(Color colorin){
		color = colorin;
		if (color == Color.blue){
			i_end = 16;
			j_end = 16;
		} else {
			i_end = 0;
			j_end = 0;
		}
	}
}
