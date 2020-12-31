// Written by: Charlie Herrmann
// Date: December 1st 2016
// Description: This project is a game where tetris peices are falling onto your character. Using the arrow keys
//to move the character and AWSD to move the peice try to get as high as possible before you get hit by a peice,
//or the peices reach the top. 

	
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements KeyListener, MouseInputListener,MouseMotionListener{
	//variables
	// x and y of peice
	private int x;
	private double y;
	
	private Timer t;	
	
	private int position;
	private int lines; 
	private Color peiceColor;
	private double speed;
	private double changeSpeed;
	private int lineDiff;
	private int score;
	private boolean pause;
	private boolean start;
	List<Color> colors;
	static List<Integer> boardX;
	private int[] boardX_array;
	static ArrayList<Integer> boardY;
	private int[] boardY_array;
	List<Integer> addX;
	List<Integer> addY;
	List<Integer> notAddX;
	List<Integer> notAddY;
	private int direction;
	private Character guy;	
	private boolean[] keys;
	private boolean startScreen;
	Piece[] pieces;
	Piece[] pieces1;
	Piece[] pieces2;
	private int rand;
	private int nextRand;
	private int highlight;
	private boolean begin;

	// method: GraphicsPanel Constructor
	// description: This 'method' runs when a new instance of this class in instantiated.  It sets default values  
	// that are necessary to run this project.  You do not need to edit this method.
	public GraphicsPanel(){
		colors = new ArrayList<Color>();
		boardX = new ArrayList<Integer>();
		boardY = new ArrayList<Integer>();
		boardX_array = new int[]{80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,0,20,40,60,80,100,120,140,160,180,200,220,240,260,280,300,320,340,360,380};
		boardY_array = new int[]{0,20,40,60,80,100,120,140,160,180,200,220,240,260,280,300,320,340,360,380,0,20,40,60,80,100,120,140,160,180,200,220,240,260,280,300,320,340,360,380,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400,400};
		//pentris pieces 
		pieces1 = new Piece[]{
				new Piece(3,4,Color.yellow,new int[]{1,2,3,4,7}),
				new Piece(5,2,Color.red,new int[]{2,7,12,17,22}),
				new Piece(5,4,Color.orange,new int[]{2,7,12,17,18}),
				new Piece(5,4,Color.green,new int[]{8,13,12,17,22}),
				new Piece(5,4,Color.cyan,new int[]{7,11,12,16,17}),
				new Piece(5,4,Color.blue,new int[]{6,7,8,12,17}),
				new Piece(5,4,new Color(255,0,255),new int[]{6,11,12,13,8}),
				new Piece(3,4,Color.orange,new int[]{0,3,6,7,8}),
				new Piece(3,4,Color.white,new int[]{0,3,4,7,8}),
				new Piece(3,1,Color.magenta,new int[]{1,3,4,5,7}),
				new Piece(5,4,Color.pink,new int[]{7,11,12,17,22}),
				new Piece(3,4,Color.gray,new int[]{0,1,4,7,8}),
				
			};
		//tetris pieces
			pieces2 = new Piece[]{
				new Piece(3,4,Color.yellow,new int[]{1,3,4,5}),
				new Piece(2,4,Color.red,new int[]{0,1,2,3}),	
				new Piece(5,2,Color.orange,new int[]{7,12,17,22}),
				new Piece(3,4,Color.pink,new int[]{0,1,4,7}),	
				new Piece(3,4,Color.green,new int[]{2,1,4,7}),
				new Piece(3,2,Color.cyan,new int[]{4,5,6,7}),
				new Piece(3,2,Color.blue,new int[]{3,4,7,8})
			};
		//holds the peice that is fallings's X and Y cords
		addX = new ArrayList<Integer>();
		addY = new ArrayList<Integer>();
		notAddX = new ArrayList<Integer>();
		notAddY = new ArrayList<Integer>();
		direction = 0;
		start = false;
		highlight = 0;
		startScreen = true;
		begin = false;
		setPreferredSize(new Dimension(400, 400));
		t = new Timer(15, new ClockListener(this));   // t is a timer.  This object will call the ClockListener's
													// action performed method every 5 milliseconds once the 
		keys = new boolean[3];										// timer is started. You can change how frequently this									 // method is called by changing the first parameter.
		t.start();
        this.setFocusable(true);			// for keylistener
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		pause = true;
		x = 0;
		y = -40;
		position = 0;
		lines = 0; 
		peiceColor = Color.white;
		speed = 1;
		changeSpeed = 0;
		score = 0;
		guy = new Character(0, 200, 0);
		for(int i= 0; i<60; i++){
			colors.add(Color.BLACK);
		}
		rand = (int)(Math.random()*6);
		nextRand = (int)(Math.random()*6);
		
		for(int i = 0; i<60; i++){
			boardX.add(boardX_array[i]);
		}
		
		for(int i = 0; i<60; i++){
			boardY.add(boardY_array[i]);
			
		}															
	}
	
	// method: paintComponent
	// description: This method is called when the Panel is painted.  It contains code that draws shapes onto the panel.
	// parameters: Graphics g - this object is used to draw shapes onto the JPanel.
	// return: void
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		//Opening Screen - Displays TETRES CLIMBER
		if(startScreen){
			ClassLoader cldr = this.getClass().getClassLoader();	// These five lines of code load the background picture.
			String imagePath = "images/backg.png";			// Change this line if you want to use a different 
			URL imageURL = cldr.getResource(imagePath);				// background image.  The image should be saved in the
			ImageIcon image = new ImageIcon(imageURL);	
			image.paintIcon(this, g2, 0, 0);
			Font f = new Font("font",Font.ROMAN_BASELINE,66);
			g2.setFont(f);
			g2.setColor(Color.red);
			g2.drawString("TETRIS",95,130);
	        g2.setColor(Color.black);
	        g2.drawString("CLIMBER",70,232);
	        g2.drawRect(70,275,100,50);
	        g2.drawRect(230,275,100,50);
	        if(highlight == -1){
	        	g2.setColor(new Color(0,255,0,100));
	        	g2.fillRect(70,275,100,50);
	        }
	        if(highlight == 1){
	        	g2.setColor(new Color(255,0,0,100));
	        	g2.fillRect(230,275,100,50);
	        }
	        
	        f = new Font("font",Font.ROMAN_BASELINE,28);
			g2.setFont(f);
	        g2.setColor(Color.yellow);
	        g2.drawString("Normal",73,310);
	        f = new Font("font",Font.ROMAN_BASELINE,25);
			g2.setFont(f);
	        g2.drawString("Extreme",233,310);
			
	        
		}
		//Real Game
		else{
			ClassLoader cldr = this.getClass().getClassLoader();	// These five lines of code load the background picture.
			String imagePath = "images/back.jpg";			// Change this line if you want to use a different 
			URL imageURL = cldr.getResource(imagePath);				// background image.  The image should be saved in the
			ImageIcon image = new ImageIcon(imageURL);				// images directory.
			
			//when paused or not begun dont move
			if(!pause || !guy.getBegin()){
				controls();
				guy.keyPressedMove(direction);
				guy.jump();
				if(guy.checkDown() && guy.getJump() == 0){
					//System.out.println("yep");
					guy.fall();
				}
			}
			changeSpeed = (int)Math.floor(lines/10)*0.5;
			g2.setColor(Color.white);
			g2.fillRect(0, 0, 400,400);
			image.paintIcon(this, g2, -600, 0);
			//making the board
			for(int i = 0; i<colors.size();i++){
				g2.setColor(Color.BLACK);
				g2.fillRect(boardX.get(i),boardY.get(i),20,20);
				Color blockColor = colors.get(i);
		        g2.setColor(blockColor);
		        g2.fillRect(boardX.get(i)+1,boardY.get(i)+1,18,18);
		    }
			//display board
			for(int i = 0; i<addX.size();i++){
				g2.setColor(Color.black);
				g2.fillRect(addX.get(i),addY.get(i),20,20);
				Color blockColor = peiceColor;
		        g2.setColor(blockColor);
		        g2.fillRect(addX.get(i)+1,addY.get(i)+1,18,18);
		        
		    }//text
			g2.setColor(Color.black);
			g2.fillRect(0,0,100,400);
			g2.fillRect(300,0,100,400);
			Font f = new Font("font",Font.PLAIN,20);
	    	g2.setFont(f);
	    	g2.setColor(Color.white);
	    	g2.drawString("height:",15,61);
	    	g2.drawString(360-guy.getHieght() +"",39,87);
	    	g2.drawString("Next Piece",300,61);
	    	
	    	//displays next piece
	    	for(int i = 0; i<pieces[nextRand].getPositions()[0].length;i++){
				g2.setColor(Color.BLACK);
				g2.fillRect(pieces[nextRand].getPositions()[0][i][0]*20+340,pieces[nextRand].getPositions()[0][i][1]*20+99,20,20);
				g2.setColor(pieces[nextRand].getColor());
		        g2.fillRect(pieces[nextRand].getPositions()[0][i][0]*20+1+340,pieces[nextRand].getPositions()[0][i][1]*20+100,18,18);	
	    	}
	    	//if not lost and not paused 
			if(checkLost(boardY) && !pause){
				//System.out.println(speed);
				addX.clear();
				addY.clear();
				notAddX.clear();
				notAddY.clear();
				//adds x and y cords to addX and addY based on how they are defined
				for(int i = 0; i<pieces[rand].getPositions()[position].length;i++){
					peiceColor = pieces[rand].getColor();
					addX.add(x+160 + 20*pieces[rand].getPositions()[position][i][0]);
					addY.add((int)(Math.floor(y/20)*20)+ 20*pieces[rand].getPositions()[position][i][1]);
					int notPos = position + 1;        
					if(notPos>=pieces[rand].getRotations())
						notPos = 0;
					notAddX.add(x+160 + 20*pieces[rand].getPositions()[notPos][i][0]);
					notAddY.add((int)(Math.floor(y/20)*20)+ 20*pieces[rand].getPositions()[notPos][i][1]);	
				}
				//when hits bottom- stop else- go down
				if(checkTouchBottom(addX, addY, boardX, boardY)||y%20<20-speed){
					y+=speed;
				}
				//adds peice to the board when it has fallen
				else{
					for(int i = 0; i<addX.size();i++){
						boardX.add(addX.get(i));
						boardY.add(addY.get(i));
					}
					rand = nextRand;
					nextRand = (int)(Math.random()*pieces.length);
					for(int i = 0;i<addX.size();i++){
						colors.add(peiceColor);
					}
					lineDiff = lines;
					List checkRow_extra = new ArrayList(checkRow(addY,boardX,boardY,colors));
					addY = (List<Integer>) checkRow_extra.get(0);
					boardX = (List<Integer>) checkRow_extra.get(1);
					boardY = (ArrayList<Integer>) checkRow_extra.get(2);
					colors = (List<Color>) checkRow_extra.get(3);
					lineDiff = lines-lineDiff;
					if(lineDiff>0){
			            score+= Math.round(93.33333*lineDiff*lineDiff*lineDiff -490.0*lineDiff*lineDiff +876.66666*lineDiff-440.0);
			        }
					y = -40;
			        x = 0;
			        position = 0;
				}
			}
				
		    	
				

			//game over
			else if(!checkLost(boardY)){
				pause = true;
				g2.setColor(Color.white);
				f = new Font("font",Font.PLAIN,50);
				g2.setFont(f);
				g2.drawString("GAME OVER",60,200);
			}
			guy.draw(g2, this);
			//if not began ask to press space
			if(!begin){
				f = new Font("font",Font.ROMAN_BASELINE,20);
				g2.setFont(f);
				g2.setColor(Color.yellow);
		        g2.drawString("Space to Start",140,310);
			}
			}
	}
	// method:clock
		// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
		//				of one of your characters in this method so that it moves as time changes.  After you update the
		//				coordinates you should repaint the panel.
		public void clock(){
				
			this.repaint();
		}
//method:checkTouchBottom
//description:checks if the bottom of the piece is touching the board
//input:List<Integer> addX - X cordinates of the piece, List<Integer> addY - Y cordinates of the piece,List<Integer> boardX - X cordinates of the board,List<Integer> boardY - Y cordinates of the board
//output: boolean-false if the piece touches and true if it does not
		
public static boolean checkTouchBottom(List<Integer> addX, List<Integer> addY,List<Integer> boardX,List<Integer> boardY){
		for(int i = 0; i<addX.size();i++){
	        for(int j = 0; j<boardX.size();j++){
	            if((int)addX.get(i) == (int)boardX.get(j) && (int)boardY.get(j)-(int)addY.get(i)==20){
	                return false;
	            }
	        }
		}
	    return true;     
	}
//method:checkTouchRight
//description:checks if the right of the piece is touching the board
//input:List<Integer> addX - X cordinates of the piece, List<Integer> addY - Y cordinates of the piece,List<Integer> boardX - X cordinates of the board,List<Integer> boardY - Y cordinates of the board
//output: boolean-false if the piece touches and true if it does not
	public static boolean checkTouchRight(List<Integer> addX, List<Integer> addY,List<Integer> boardX,List<Integer> boardY){
		for(int i = 0; i<addX.size();i++){
	        for(int j = 0; j<boardX.size();j++){
	            if((int)addY.get(i) == (int)boardY.get(j) && (int)boardX.get(j)-(int)addX.get(i)==20){
	                return false;
	            }
	        }
		}
	    return true;     
	}
	//method:checkTouchLeft
	//description:checks if the left of the piece is touching the board
	//input:List<Integer> addX - X cordinates of the piece, List<Integer> addY - Y cordinates of the piece,List<Integer> boardX - X cordinates of the board,List<Integer> boardY - Y cordinates of the board
	//output: boolean-false if the piece touches and true if it does not
	public static boolean checkTouchLeft(List<Integer> addX, List<Integer> addY,List<Integer> boardX,List<Integer> boardY){
		for(int i = 0; i<addX.size();i++){
	        for(int j = 0; j<boardX.size();j++){
	            if((int)addY.get(i) == (int)boardY.get(j) && (int)addX.get(i) -(int)boardX.get(j)==20){
	                return false;
	            }
	        }
		}
	    return true;     
	}
	//method:checkTurn
		//description:checks the peice has room to turn
		//input:List<Integer> notAddX - X cordinates of the next rotation, List<Integer> notAddY - Y cordinates of the next rotation,List<Integer> boardX - X cordinates of the board,List<Integer> boardY - Y cordinates of the board
		//output: boolean- true if the piece has room to turn false if it doesn't
	public static boolean checkTurn(List<Integer> notAddX, List<Integer> notAddY,List<Integer> boardX,List<Integer> boardY){
		for(int i = 0; i<notAddX.size();i++){
	        for(int j = 0; j<boardX.size();j++){
	            if((int)notAddX.get(i) == (int)boardX.get(j) && Math.abs((int)notAddY.get(i)-(int)boardY.get(j))<20){
	                return false;
	            }
	        }
		}
	    return true;     
	}
	//method:checkRow
	//description:This method will check if row is filled and it is is it will get rid of that row.
	//input:List<Integer> addY - Y cordinates of the piece,List<Integer> boardX - X cordinates of the board,List<Integer> boardY - Y cordinates of the board, List<Integer>colors- colors on the board
	//output: List<List<Integer>>- contains new values for the inputs
	public List<List<Integer>> checkRow(List<Integer>addY, List<Integer>boardX, List<Integer>boardY, List<Color>colors){
		int rowNum = -900;
		for(int i = 0; i<addX.size();i++){
	        int sum = 0; 
	        for(int j = 60;j<boardY.size();j++){
	            if((int)addY.get(i) == (int)boardY.get(j)){
	                sum++;
	            }
	        }
	        if(sum==10){
	            rowNum = addY.get(i);
	        }
	    }
		if(rowNum!=-900){
	        lines++;
	        for(int j = 60; j<boardY.size();j++){
	            if(rowNum==(int)boardY.get(j)){
	                boardX.remove(j);
	                boardY.remove(j);
	                colors.remove(j);
	                j--;
	            }
	            else if(rowNum>=(int)boardY.get(j)){
	                int extra = boardY.get(j);
	                boardY.remove(j);
	                boardY.add(j, extra+20);
	            }
	        }
	        checkRow(addY,boardX,boardY,colors);
	    }
		List Return = new ArrayList();
		Return.add(addY);
		Return.add(boardX);
		Return.add(boardY);
		Return.add(colors);
		return Return;
	}
	//method:checkLost
	//description:checks if the peice is on the last row or the falling piece hit the character(losing the game)
	//input:List<Integer>boardY- Y cordinates of the board
	//output: boolean- false if lost true if not
	public boolean checkLost(List<Integer>boardY){
		for(int i = 60; i<boardY.size();i++){
	        if(boardY.get(i) == 0){
	            return false;
	        }
	    }
		if(intersect()){
			return false;
		}
	    return true;
	}
	//method:controls
		//description:allows the user to go left right and jump
		//input:NA
		//output:void
	public void controls(){
	    if(keys[0] && keys[2]){
	    	direction = 0;
	    }
	    else if(keys[0]){
	    	direction = -1;
	    }
	    else if(keys[2]){
	    	direction = 1;
	    }
	    else 
	    	direction = 0;  
	    if(keys[1] && guy.getJump() == 0 && !guy.checkUp() && !guy.checkDown()){
	        guy.setJump(1);
	        guy.setChange(5);
	    }
	}
	//method:controls
			//description:returns true if falling piece and character intersect
			//input:NA
			//output:boolean
	public boolean intersect(){
		for(int i = 0; i<addX.size(); i++){
			if(guy.getBounds().intersects(new Rectangle(addX.get(i)+1,addY.get(i)+1,18,18)))
				return true;
		}
		return false;
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//change speed
		
		if(e.getKeyCode() == KeyEvent.VK_S){
			//speed up 
			 speed = 4+changeSpeed;
		}
		//arrow keys- checks for multiple keys at once
		if(e.getKeyCode()>36 && e.getKeyCode()<40)
			keys[e.getKeyCode()-37] = true;
		
		switch(e.getKeyCode()){

				//rotate peice
			case KeyEvent.VK_A:
				if(checkTouchLeft(addX, addY, boardX, boardY) )
					 x-=20;
				break;
				//move left
			case KeyEvent.VK_D:
				if(checkTouchRight(addX, addY, boardX, boardY))
					 x+=20;
				break;
			case KeyEvent.VK_W:
				if(checkTurn(notAddX, notAddY, boardX, boardY)){
					position++;
			        if(position >= pieces[rand].getRotations()){
			            position = 0;
			        }
				}
			break;
		
			//pause the game and start the game
			case KeyEvent.VK_SPACE:
				begin = true;
				pause = !pause;
				start = true;
				break;
		}
		this.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()>36 && e.getKeyCode()<40)
			keys[e.getKeyCode()-37] = false;
		//go back to normal speed
		direction = 0;
		speed = 1+changeSpeed;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//select difficulty
		if(startScreen){
			if (highlight == -1){
				pieces = pieces2;
				startScreen = false;
			}
			else if(highlight == 1){
				pieces = pieces1;
				startScreen = false;
			}
			
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//highlight difficulties
		if(startScreen){
			if(e.getX()>70 && e.getX()<170 && e.getY()>250 && e.getY()<350){
				highlight = -1;
			}
			else if(e.getX()>230 && e.getX()<330 && e.getY()>250 && e.getY()<350){
				highlight = 1;
			}
			else{
				highlight = 0;
			}
		}
		//System.out.println(e.getX());
		
	}

	
}