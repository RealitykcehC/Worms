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
