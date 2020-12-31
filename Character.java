// If you modify this class you should add comments that describe when and how you modified the class.  
// Class: Character
// Written by:Charlie
// Date Modified: 12/1/15
// Description: I added some instance varibales that allow the character to jump. I also added my own image and 
// an instance variable to check the highest the character has gotten and a variable to chek if game has begun. 
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Character {
	private ImageIcon image;			// The ImageIcon will be used to hold the Character's png.
										// This png must be saved in the images folder and will be loaded 
										// in the constructor.
	
	private double x_coordinate;			// These ints will be used for the drawing the png on the graphics panel.
	private double y_coordinate;			// When the Character's move method is called you should update one or both
										// of these instance variables.  (0,0) is the top left hand corner of the
										// panel.  x increases as you move to the right, y increases as you move
	
	// down.
	//how high character can jump
	private int heightLim = 50;     
	private double heightTrav = 0;
	private double stepChange = 0.1;
	private double change = 5;
	private int jump = 0;
	private int highest = 360;
	private boolean begin = false;
	// method: Default constructor - see packed constructors comments for a description of parameters.
	public Character(){
		this(0, 200, 300);
	}
		
	// method: Character's packed constructor
	// description: Initialize a new Character object.
	// parameters: imageChoice - used to determine which image to load when a Character is instantiated.  You can change
	//			   existing options or add other options. 0 - pirate, 1 - parrot.
	//			   x_coordinate - the initial x-coordinate for Character.
	//			   y_coordinate - the initial y-coordinate for Character.
	public Character(int imageChoice, int x_coordinate, int y_coordinate){
		
		ClassLoader cldr = this.getClass().getClassLoader();	// These eight lines of code load the Character's png
		String imagePath;										// so that it later be painted on the graphics panel
																// when draw method is called.  You should modify
																// the imagePath if you change the Character's png.
		
									// if statement that determines which image to use for		
		imagePath = "images/kavel-ninja.png";					// a Character object.  You can add other options as well.

		
		URL imageURL = cldr.getResource(imagePath);				
        image = new ImageIcon(imageURL);						
        
        this.x_coordinate = x_coordinate;						// Initial coordinates for the Character.
        this.y_coordinate = y_coordinate;  
	}
	
	// method: getBounds
	// description: This method will return the coordinates of a rectangle that would be drawn around the 
	// 				Character's png.  This rectangle can be used to check to see if the Character bumps into 
	//				another character on your panel by calling the Rectangle's intersects method:
	//
	//							p.getBounds().intersects(c.getBounds());
	//
	//				in this example p is an instance of the Character class and c is an instance of another
	//				class that has a getBounds method that also returns a Rectangle, so p.getBounds and
	//				c.getBounds would both return or evaluate to Rectangle objects.  The intersects method
	//				return true if the two rectangles overlap, false if they do not.
	// return: A Rectangle - This rectangle would be like drawing a rectangle around the Character's image.
	public Rectangle getBounds(){
		return new Rectangle((int)x_coordinate+15, (int)y_coordinate-40, 20, 40);
	}
	
	// method: getX
	// description:  This method will return the x-coordinate of the top left hand corner of the the image.
	// return: int - the x-coordinate of the top left hand corner of the the image.
	public int getX(){
		return (int)x_coordinate;
	}
	
	// method: getY
	// description:  This method will return the y-coordinate of the top left hand corner of the the image.
	// return: int - the y-coordinate of the top left hand corner of the the image.
	public int getY(){
		return (int)y_coordinate;
	}
	// method: getJump
	// description:  returns jump (1 if going up , -1 down, 0 nothing)
	// return: int - jump
	public int getJump(){
		return jump;
	}
	// method: getHieght
	// description:  returns Highest (highest character has gotten)
	// return: int - Highest
	public int getHieght(){
		return highest;
	}
	// method: getBegin
	// description:  returns begin (has the game begun by the character falling to the bottom)
	// return: boolean - begin
	public boolean getBegin(){
		return begin;
	}
	// method: setJump
	// description:  modifies jump
	// input: int jump 
	//output: void
	public void setJump(int jump){
		this.jump = jump;
	}
	// method: setChange
	// description:  modifies change(amount the character goes up everytime) 
	// input: int change
	//output: void
	public void setChange(int change){
		this.change = change;
	}
	// method: fallSpot
	// description:  returns the y coord where the character should fall
	// input: none
	//output: int 
	public int fallSpot(){
		int lowest = 400;
		int ycor = 0;
		int xcor = 0;
		for(int i = 0; i<GraphicsPanel.boardX.size();i++){
			if(x_coordinate > GraphicsPanel.boardX.get(i)-35 && x_coordinate < GraphicsPanel.boardX.get(i)+5 && GraphicsPanel.boardY.get(i)<=lowest && y_coordinate<GraphicsPanel.boardY.get(i)+20){
				lowest = GraphicsPanel.boardY.get(i);
				ycor = (int)y_coordinate;
				xcor = (int)x_coordinate;
			}
		}
		//System.out.println("(" + xcor + " , " + ycor + ") = " + lowest);
		return lowest;
	}
	// method: keyPressedMove
	// description: This method should modify the Character's x or y (or perhaps both) coordinates.  When the 
	//				graphics panel is repainted the Character will then be drawn in it's new location.
	// parameters: int direction - This parameter should represent the direction that you want to move
	//			   the Character, so decide on a standard for what each integer value will stand for and then
	//			   add comments below that describe these integer values, for example...
	//			   1 - move Character to the right.
	public void keyPressedMove(int direction){
		if(direction == 1 && !checkRight()){
			x_coordinate++;
			//System.out.println(x_coordinate);
		}
		if(direction == -1 && !checkLeft()){
			x_coordinate--;
			//System.out.println(x_coordinate);
		}
		
		
	}
	//name:jump
	//description:allows user to jump,go up by int change, modify change, when reached high limit go down
	//input: none
	//output: void
	public void jump(){
		
		if(heightTrav >= heightLim && jump == 1){
	        jump = -1;
	        heightTrav = 0;
	        change = 5;
	    }
	    if(y_coordinate>=fallSpot() && jump == -1){
	        y_coordinate = fallSpot();
	        jump = 0;
	    }
	    if(checkUp()){
	    	jump = 0;
	    	change = 4;
	    }
	    if(jump == 1){
	        y_coordinate-=change;
	        heightTrav +=change;
	        if(change-stepChange>0){
	            change-=stepChange;
	        }
	    }
	    else if (jump == -1){
	        y_coordinate+=5-change;
	        if(change-stepChange>0){
	            change-=stepChange;
	        }
	    }
	}
	//name:fall
	//description:if in air fall
	//input: none
	//output: void
	public void fall(){
		y_coordinate+=5-change;
		//System.out.println(change);
        if(change-stepChange>0){
            change-=stepChange;
        }
	}
	//name:checkLeft
	//description:checks if there is a peice to the left of the guy
	//input: none
	//output: boolean
	public boolean checkLeft(){
		for(int i = 0; i<GraphicsPanel.boardX.size();i++){
			if(GraphicsPanel.boardX.get(i)==x_coordinate-5 && GraphicsPanel.boardY.get(i)>=y_coordinate-40 && GraphicsPanel.boardY.get(i)<y_coordinate)
				return true;
		}
		return false;
	}
	//name:checkRight
	//description:checks if there is a peice to the right of the guy
	//input: none
	//output: boolean
	public boolean checkRight(){
		for(int i = 0; i<GraphicsPanel.boardX.size();i++){
			if(GraphicsPanel.boardX.get(i)==x_coordinate+35 && GraphicsPanel.boardY.get(i)>=y_coordinate-40 && GraphicsPanel.boardY.get(i)<y_coordinate)
				return true;
		}
		return false;
	}
	//name:checkUp
	//description:checks if there is a peice above the guy
	//input: none
	//output: boolean
	public boolean checkUp(){
		for(int i = 0; i<GraphicsPanel.boardX.size();i++){
			if(GraphicsPanel.boardX.get(i)>x_coordinate-5 && GraphicsPanel.boardX.get(i)<x_coordinate+35 && GraphicsPanel.boardY.get(i)<=y_coordinate-40 && GraphicsPanel.boardY.get(i)>=y_coordinate-60)
				return true;
		}
		return false;
	}
	//name:checkDown
	//description:checks if there is a peice below the guy
	//input: none
	//output: boolean
	public boolean checkDown(){
		if(y_coordinate < fallSpot())
			return true;
		y_coordinate = fallSpot();
		change = 5;
		return false;
	}
	// method: draw
	// description: This method is used to draw the image onto the GraphicsPanel.  You shouldn't need to 
	//				modify this method.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	public void draw(Graphics g, Component c) {
		//if fallen begin is true
		if(y_coordinate == fallSpot()){
			begin = true;
		}
		//modifies highest if it acceded
		if(y_coordinate-40 <highest && begin){
			highest = (int)y_coordinate-40;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.magenta);
		//draws line
		g2.drawLine(100, highest, 300, highest);
		//draws the guy
        image.paintIcon(c, g, (int)x_coordinate, (int)y_coordinate-45);
    }
}
