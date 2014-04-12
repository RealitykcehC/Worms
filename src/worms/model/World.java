package worms.model;

// TODO getHSB() to compare colors (if not provided already).

/**
 * ...
 * 
 * @author PieterJan...
 */
public class World {
	/**
	 * Declaration of variables.
	 */
	private double width, height;
	private double lowerBoundX = 0.0;
	private double lowerBoundY = 0.0;
	private double upperBoundX = Double.MAX_VALUE;
	private double upperBoundY = Double.MAX_VALUE;

	/**
	 * Constructor of the class World.
	 * 
	 * @param width
	 * 			The width of the world
	 * @param height
	 * 			The height of the world
	 * @post	The width of the world has to be equal to the provided width.
	 * 			| (new this).getWidth() == width
	 * @post	The height of the world has to be equal to the provided height.
	 * 			| (new this).getHeight() == height
	 * @throws	IllegalArgumentException
	 * 			The provided width and/or the provided height are invalid.
	 * 			|!this.canHaveAsWidth(this.getWidth()) || !this.canHaveAsHeight(this.getHeight())
	 */
	public World(double width, double height) throws IllegalArgumentException {
		if (!this.canHaveAsWidth(this.getWidth()) || !this.canHaveAsHeight(this.getHeight()))
			throw new IllegalArgumentException();
		this.width = width;
		this.height = height;
	}

	/**
	 * Function that checks whether or not the provided width is valid.
	 * 
	 * @return true
	 * 			The provided width is valid
	 * @return false
	 * 			The provided width is invalid
	 */
	public boolean canHaveAsWidth(double width) {
		if (!Double.isNaN(width))
			if (lowerBoundX <= width && width <= upperBoundX)
				return true;
		return false;
	}

	/**
	 * Function that checks whether or not the provided height is valid.
	 * 
	 * @return true
	 * 			The provided height is valid
	 * @return false
	 * 			The provided height is invalid
	 */
	public boolean canHaveAsHeight(double height) {
		if (!Double.isNaN(height))
			if (lowerBoundY <= height && height <= upperBoundY)
				return true;
		return false;
	}
	
	/**
	 * Function that returns the width of this world.
	 * 
	 * @return this.width
	 * 			The width of this world
	 */
	public  double getWidth() {
		return this.width;
	}
	
	/**
	 * Function that returns the height of this world.
	 * 
	 * @return this.height
	 * 			The height of this world
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * 
	 */
	public void addWormToWorld() {
		
	}
	 /**
	 * isImpassable gives back true if the terrain of the given circle is impassable
	 * 
	 * @param x The x-coordinate of the center of the circle to check  
	 * @param y The y-coordinate of the center of the circle to check
	 * @param radius The radius of the circle to check
	 * 
	 * @invar counter gives how many times the given terrain is impassable at the
	 * edge of the radius and middlepoint
	 * 
	 * @return True if the given region is impassable, false otherwise.
	 */
	public boolean isImpassable(double x, double y, double radius){

		double poolX;
		double poolY;
		String [] passable = new String [360];
		int counter = 0;

		for (int i=0; i<360; i++){
			poolX = (double)(radius * Math.cos(i));
			poolY = (double)(radius * Math.sin(i));
                // the middlepoint of a circle is x+radius and y+radius
			if (passableMap[ x + radius + poolX][y + radius + poolY]== true && passableMap[x + radius][y + radius]== true && passableMap [x + radius + poolX * 1.1][y + radius + poolY * 1.1] == false ){
				}
			else {
				counter++;
			}
		}
		
		if (counter != 0){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * isAdjacent() gives back true if the given circle is passable terrain and is adjacent to impassable terrain at a 0.1 times the radius length
	 * 
	 * 
	 * @param x The x-coordinate of the center of the circle to check  
	 * @param y The y-coordinate of the center of the circle to check
	 * @param radius The radius of the circle to check
	 * 
	 * @invar counter gives back how many times the given terrain is impassable at the
	 * edge of the radius and middlepoint
	 * 
	 * @return True if the given region is passable and nearby impassable terrain, false otherwise.
	 */
	public boolean isAdjacent(double x, double y, double radius){

		double poolX;
		double poolY;
		String [] passable = new String [360];
		int counter = 0;

		for (int i=0; i<360; i++){
			poolX = (double)(radius * Math.cos(i));
			poolY = (double)(radius * Math.sin(i));
		// the middlepoint of a circle is x+radius and y+radius
			if (passableMap[ x + radius + poolX][ y + radius + poolY]== true && passableMap[x][y]== true && passableMap [x + radius + poolX * 1.1 ][ y + radius + poolY * 1.1 ] == false ){
				}
			else {
				counter++;
			}
		}
		
		if (counter == 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Function that adds food to the world at the provided (x,y)-coordinates.
	 * 
	 * @param x
	 * 			The x-coordinate where the food has to be placed
	 * @param y
	 * 			The y-coordinate where the food has to be placed
	 */
	public void addFoodToWorld(double x, double y) {
		
	}
	
	public static boolean isValidX() {
		
	}
}
