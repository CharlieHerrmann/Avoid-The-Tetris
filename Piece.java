import java.awt.Color;
// Description: This piece desscribes the pieces that are falling. A piece constructor takes in 4 inputs (size,
//rotation, color, and points). These define how big the piece, how many roatations it will have, the color, and 
//where the blocks will be on the piece (how it will look). This class stores all the possible rotations of the piece,
//in a 3D array called positons
public class Piece {
	//size of the piece
	private int size;
	//color of piece
	private Color color;
	//number of rotations
	private int rotations; 
	//takes in the input
	private int[] points;
	private int [][][] positions;
	private int rotationCounter;
	public Piece(int size, int rotations, Color color, int[]points){
		this.color = color;
		this.size = size;
		this.rotations = rotations; 
		this.points = points;
		rotationCounter = 0;
		positions = new int[rotations][points.length][2];
		//transform user input to a piece position 
		for(int i = 0; i<points.length; i++){
			positions[0][i][0] = points[i]%size - size/2; 
			positions[0][i][1] = points[i]/size - size/2; 
		}
		rotationCounter++;
		while(rotationCounter<rotations){
			addRotation();
		}
	}
	//name:getPositions
	//description:returns positions
	//input: none
	//output: int[][][]
	public int[][][] getPositions(){
		return positions;
	}
	//name:getRotations
	//description:returns rotations
	//input: none
	//output: int
	public int getRotations(){
		return rotations;
	}
	//name:getColor
	//description:returns color
	//input: none
	//output: Color
	public Color getColor(){
		return color;
	}
	//name:addRotation
	//description:rotates the piece and adds the new piece as a new rotation
	//input: none
	//output: Color
	public void addRotation(){
		for(int i = 0; i<positions[rotationCounter-1].length;i++){
			int extra = positions[rotationCounter-1][i][0];
			positions[rotationCounter][i][0] = -positions[rotationCounter-1][i][1];
			positions[rotationCounter][i][1] = extra;
		}
		rotationCounter++;
	}

}