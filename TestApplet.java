import java.applet.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class TestApplet extends Applet implements  MouseListener {

	int i,j,k,deadBalls, triangleSide = 20;
	int pointX,pointY,pickX=-1,pickY=-1,dragX=-1,dragY=-1;
	int map[];
	boolean drawBoard;
	int x1 = 10;
	int y1 = 20;
	int color = 0;
	Stone [][] board = new Stone [16][16]; 
	int runMode = 1;
	Graphics gameGraphics;
	Thread gameThread;
	long startTime;
	int xbmouse = -1, ybmouse = -1, xemouse = -1, yemouse = -1;
	GamePlay makeMove = new GamePlay();
	HumanPlay humanPlay = new HumanPlay();
	boolean haveMoved = false;
	int r0 = 0, r1 = 0;
	
	/*
	public void start(){
		//start thread
		if (gameThread==null)
		{
			gameThread=new Thread(this,"Game");
			gameThread.start();
			//gameThread=System.currentTimeMillis();
		}
	}*/
	/*
	public void run(){  
		//thread!=null
		while(true){
			switch(runMode){
			case 1://computer starts
				//System.out.println("Got here: case 1 in run()");
				makeMove.makeMove(board);
				repaint();
				runMode = 2;
				break;
			case 2://player's turn
				while (xbmouse==-1 || ybmouse== -1 ||  xemouse== -1 || yemouse== -1){

				}
				haveMoved = humanPlay.makeMove(board, xbmouse, ybmouse, xemouse, yemouse);
				if (haveMoved == true){
					runMode = 1;
					repaint();
				} else {
					xbmouse = -1;
					ybmouse = -1;
					xemouse = -1;
					yemouse = -1;
				}
				haveMoved = false;

				break;
			default:
				break;
			}
		}
	}*/

	public void init()
	{
		map=new int[100];
		initiateBoard(board);
		resize(400,400);
		// Add the MouseListener to the applet 
		addMouseListener(this); 
		makeMove.makeMove(board);
		repaint();
	}
	public void deadBall(Graphics g,int x,int y)
	{
		fixDisc(g,x+3,y+3,14,Color.gray);
		fixDisc(g,x,y,14,Color.cyan);
		fixDisc(g,x+6,y+6,7,Color.gray);
		fixDisc(g,x+1,y+1,8,Color.white);
		fixDisc(g,x+9,y+9,3,Color.white);
	}
	public void fillMap()
	{
		for (i=0;i<81;i++)
			map[i]=1;
		for (i=0;i<3;i++)
			for (j=0;j<3;j++)
			{
				map[i*9+j]=-1;
				map[i*9+j+6]=-1;
				map[i*9+j+54]=-1;
				map[i*9+j+60]=-1;
			}
		map[40]=0;
		drawBoard=true;
		deadBalls=0;
	}

	public void boardHole(Graphics g,int x,int y)
	{
		g.setColor(Color.blue);
		g.fillRect(x,y,16,16);
		fixDisc(g,x+1,y+1,12,Color.black);
		fixDisc(g,x+4,y+4,10,Color.blue);
		g.setColor(Color.cyan);
		g.drawArc(x,y,14,14,-135,180);
	}

	public void fixBox(Graphics g,int x,int y,int w,int h,Color c)
	{
		g.setColor(c);
		g.fillRect(x,y,w,h);
	}
	public void fixCircle(Graphics g,int x,int y,int r,Color c)
	{
		g.setColor(c);
		g.drawOval(x,y,r,r);
	}
	public void boardBall(Graphics g,int x,int y)
	{
		x = x + 2;
		y = y + 2;
		g.setColor(Color.blue);
		//g.fillRect(x,y,16,16);
		fixDisc(g,x+1,y+1,15,Color.black);
		fixDisc(g,x,y,14,Color.cyan);
		fixDisc(g,x+6,y+6,7,Color.blue);
		fixDisc(g,x+1,y+1,8,Color.white);
		fixDisc(g,x+9,y+9,3,Color.white);
	}
	public void triangle(Graphics g, int x1, int y1){

		g.setColor(Color.black);
		g.drawLine(x1, y1, x1 + triangleSide, y1 );
		g.drawLine(x1, y1, x1, y1 + triangleSide);
		//g.drawLine(x1,y1 + triangleSide, x1 + triangleSide, y1);
		g.drawLine(x1,y1 +  triangleSide, x1 + triangleSide, y1 + triangleSide);
		g.drawLine(x1 + triangleSide,y1, x1 + triangleSide, y1 + triangleSide);
	}
	public void fillTriangle(Graphics g, int x1, int y1, int color){

		if (color == 0)
			g.setColor(Color.yellow);
		else
			g.setColor(Color.green);
		g.fillRect(x1, y1, triangleSide - 1, triangleSide - 1 );
	}
	public void drawBoard(Graphics g, int color){
		int x1 = 10;
		int y1 = 20;
		for (int j = 0; j < 16; j++){
			for (int i = 0; i < 303; i = i+ triangleSide){
				if ((color % 2) == 0 )
					color = 0; //yellow
				else
					color = 1; //green
				triangle(g, x1, y1 + i);
				fillTriangle(g, x1 + 1, y1 + 1 + i, color);
				color++;
			}
			x1 = x1 + 20;
			color++;
		}
	}
	private void fixDisc(Graphics g,int x,int y,int r,Color c)
	{
		g.setColor(c);
		g.fillOval(x,y,r,r);
	}
	public void drawStone(Graphics g, int x, int y, Color color ){
		x = x - 2;
		y = y - 2;

		fixDisc(g,x+1,y+1,15,Color.black);
		fixDisc(g,x,y,14,color);
		fixDisc(g,x+6,y+6,7,Color.blue);
		fixDisc(g,x+1,y+1,8,Color.white);
		fixDisc(g,x+9,y+9,3,Color.white);
	}

	public void drawStoneOnBoard(Graphics g, Stone [][] board){
		Color red = Color.red;
		Color blue = Color.blue;

		for( int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				if ( board [i][j].isStone == true){
					if ( board [i][j].getColor() == red){
						drawStone( g, board [i][j].getX(), board [i][j].getY(), red);
					} else if ( board [i][j].getColor() == blue){
						drawStone( g, board [i][j].getX(), board [i][j].getY(), blue);
					}
				}
			}
		}
	}

	public void placeStone( Stone [][] board ){
		int i = 0;
		int j = 0;

		//placing red stones
		while ( i < 2 ){
			while ( j < 5 ){
				board [i][j].setColor(Color.red);
				board[i][j].setIsstone(true);
				j++;
			}
			i++;
			j = 0;
		}
		i = 2;
		while ( j < 4 ){
			board [i][j].setColor(Color.red);
			board[i][j].setIsstone(true);
			j++;
		}
		i++;
		j = 0;

		i = 3;
		while ( j < 3 ){
			board [i][j].setColor(Color.red);
			board[i][j].setIsstone(true);
			j++;
		}
		i++;
		j = 0;

		i = 4;
		while ( j < 2 ){
			board [i][j].setColor(Color.red);
			board[i][j].setIsstone(true);
			j++;
		}
		i++;
		j = 0;

		//placing blue stones
		j = 11;
		i = 14;
		while ( i < 16 ){
			while ( j < 16 ){
				board [i][j].setColor(Color.blue);
				board[i][j].setIsstone(true);
				j++;
			}
			i++;
			j = 11;
		}
		j = 12;
		i = 13;
		while ( j < 16 ){
			board [i][j].setColor(Color.blue);
			board[i][j].setIsstone(true);
			j++;
		}
		j = 13;
		i = 12;
		while ( j < 16 ){
			board [i][j].setColor(Color.blue);
			board[i][j].setIsstone(true);
			j++;
		}
		j = 14;
		i = 11;
		while ( j < 16 ){
			board [i][j].setColor(Color.blue);
			board[i][j].setIsstone(true);
			j++;
		}
	}

	public void initiateBoard(Stone [][] board){
		int x1 = 10;
		int x = 10;
		int y = 20;

		for( int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				board [i][j] = new Stone();
				board [i][j].setXY(x + triangleSide/4, y + triangleSide/4);
				board [i][j].setIJ(i, j);
				board [i][j].setIsstone(false);
				x = x + triangleSide;
			}
			x = x1;
			y = y + triangleSide;
		}
		for( int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){

				board [i][j].setR0(calcRadius(board [i][j].getX(), board [i][j].getY()));
				board [i][j].setR1(calcRadius(board [i][j].getX() - board[15][15].getX(), board [i][j].getY()- board[15][15].getY()));	
			}
		}
		placeStone(board);
	}

	private int calcRadius(int x, int y){
		int r = 0;

		r = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return r;
	}

	public void paint (Graphics g) { 

		drawBoard(g, color);
		drawStoneOnBoard( g, board);
/*
		if (drawBoard)
		{
			g.setColor(Color.lightGray);
			g.fillRect(0,0,size().width,size().height);
			fixBox(g,207,166,60,24,Color.black);
			fixBox(g,208,167,58,22,Color.blue);
			g.setColor(Color.cyan);
			g.drawString("Restart",215,183);
			fixDisc(g,2,2,187,Color.gray);
			fixDisc(g,0,0,185,Color.blue);
			fixDisc(g,0,0,183,Color.cyan);
			fixDisc(g,2,2,183,Color.black);
			fixDisc(g,2,2,181,Color.blue);
			fixCircle(g,8,8,171,Color.cyan);
			fixCircle(g,7,7,171,Color.black);

			for (i=0;i<9;i++)
				for (j=0;j<9;j++)
					if (map[i*9+j]>-1)
						if (map[i*9+j]==0)
							boardHole(g,18+j*17,18+i*17);
						else
							boardBall(g,18+j*17,18+i*17);
			for (i=0;i<deadBalls;i++)
			{
				k=i/5;
				j=i-k*5;
				deadBall(g,193+j*17,5+k*17);
			}
		}
		else
		{
			map[pickX+9*pickY]=0;
			map[(pickX+dragX)/2+9*(pickY+dragY)/2]=0;
			map[dragX+9*dragY]=1;
			boardHole(g,18+pickX*17,18+pickY*17);
			boardHole(g,18+((pickX+dragX)/2)*17,18+((pickY+dragY)/2)*17);
			boardBall(g,18+dragX*17,18+dragY*17);
			i=deadBalls/5;
			j=deadBalls-i*5;
			deadBall(g,193+j*17,5+i*17);
			deadBalls++;
			drawBoard=true;
		}*/

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// save coordinates of mouseclick
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		xbmouse = me.getX(); 
		ybmouse = me.getY(); 
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		xemouse = me.getX(); 
		yemouse = me.getY(); 

		haveMoved = humanPlay.makeMove(board, xbmouse, ybmouse, xemouse, yemouse);
		repaint();
		if (haveMoved){
			makeMove.makeMove(board);
			repaint();
			haveMoved = false;}
	}
};
