package worms.model;

/**
 * A class that implements the food in the game.
 * This food has a x- and y-coordinate in the game world and a certain radius. If the food is eaten by 
 * a worm, that worm's radius will be increased by a certain amount.
 * 
 * @author Matthijs Nelissen & Pieter Jan Vingerhoets
 * @version 0.1a
 */
public class Food {
	/**
	 * Declaration of variables.
	 */
	private double x, y;
	private final double radius = 0.2;
	private final double radiusIncrease = 1.1;

	/**
	 * Constructor of the class Food.
	 * 
	 * @param x
	 * 			The x-coordinate of the food.
	 * @param y
	 * 			The y-coordinate of the food.
	 * @post	The provided x-coordinate has to be equal to the x-coordinate of this piece of food.
	 * 			| (new this).getX() == x
	 * @post	The provided y-coordinate has to be equal to the y-coordinate of this piece of food.
	 * 			| (new this).getY() == y
	 */
	public Food(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Function that returns the x-coordinate of this piece of food.
	 * 
	 * @return this.x
	 * 			The x-coordinate of this piece of food
	 */
	public double getX(){
		return this.x;
	}
	
	/**
	 * Function that returns the y-coordinate of this piece of food.
	 * 
	 * @return this.y
	 * 			The y-coordinate of this piece of food
	 */
	public double getY(){
		return this.y;
	}
	
	/**
	 * Function that returns how much the worm, that ate this piece of food, its radius has to be 
	 * increased.
	 * 
	 * @return this.radiusIncrease
	 * 			The amount the worm's radius has to be increased.
	 */
	public double getRadiusIncrease(){
		return this.radiusIncrease;
	}
	
	/**
	 * Function that returns the radius of this piece of food.
	 * 
	 * @return this.radius
	 * 			The radius of this piece of food
	 */
	public double getRadius(){
		return this.radius;
	}

	/**
	 * Method that checks whether or not this piece of food is active (i.e. 
	 * this piece of food is not a null reference).
	 * 
	 * @return true
	 * 			This piece of food is active
	 * @return false
	 * 			This piece of food is not active
	 */
	public boolean isActive() {
		return (this != null);
	}
}
