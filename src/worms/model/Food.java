package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class that implements the food in the game.
 * This food has an x- and y-coordinate in the game world and a certain radius. If the food is eaten by 
 * a worm, that worm's radius will be increased by a certain amount.
 * 
 * @author Matthijs Nelissen & Pieter Jan Vingerhoets
 * @version 1.0
 */
public class Food {
	/**
	 * Declaration of variables.
	 */
	public static final double RADIUS = 0.2;
	private double x, y;
	private boolean isTerminated = false;
	private World world;

	/**
	 * Constructor of the class Food.
	 * 
	 * @param world
	 * 			The world in which this piece of food has to lie
	 * @param x
	 * 			The x-coordinate for this piece of food
	 * @param y
	 * 			The y-coordinate for this piece of food
	 * @post	The provided world has to be equal to the world of this piece of food.
	 * 			| (new this).getWorld() == world
	 * @post	The provided x-coordinate has to be equal to the x-coordinate of this piece of food.
	 * 			| (new this).getX() == x
	 * @post	The provided y-coordinate has to be equal to the y-coordinate of this piece of food.
	 * 			| (new this).getY() == y
	 */
	public Food(World world, double x, double y) {
		this.world = world;
		this.x = x;
		this.y = y;
	}

	/**
	 * Function that returns the world in which this piece of food lies.
	 * 
	 * @return this.world
	 * 			The world in which this piece of food lies
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}

	/**
	 * Function that returns the x-coordinate of this piece of food.
	 * 
	 * @return this.x
	 * 			The x-coordinate of this piece of food
	 */
	@Basic
	public double getX() {
		return this.x;
	}

	/**
	 * Function that returns the y-coordinate of this piece of food.
	 * 
	 * @return this.y
	 * 			The y-coordinate of this piece of food
	 */
	@Basic
	public double getY() {
		return this.y;
	}

	/**
	 * Function that returns the radius of this piece of food.
	 * 
	 * @return RADIUS
	 * 			The radius of this piece of food
	 */
	@Basic
	public double getRadius() {
		return RADIUS;
	}

	/**
	 * Method that checks whether or not this piece of food is active (i.e. 
	 * this piece of food is not terminated).
	 * 
	 * @return true
	 * 			This piece of food is active
	 * @return false
	 * 			This piece of food is not active
	 */
	public boolean isActive() {
		return (!this.isTerminated());
	}

	/**
	 * Method that terminates this piece of food.
	 * 
	 * @post	The world of this piece of food has to be set to the null reference.
	 * 			| (new this).world == null
	 * @post	This piece of food has to know that it has been terminated.
	 * 			| (new this).isTerminated()
	 */
	public void terminate() {
		this.world = null;
		this.isTerminated = true;
	}

	/**
	 * Function that returns whether or not this piece of food has been terminated.
	 * 
	 * @return true
	 * 			This piece of food has been terminated
	 * @return false
	 * 			This piece of food hasn't been terminated
	 */
	public boolean isTerminated() {
		return this.isTerminated;
	}
}
