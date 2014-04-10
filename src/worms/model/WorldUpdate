package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

// TODO getHSB() to compare colors.

/**
 * A class that implements all the aspects of the game world.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 0.1a
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
	private ArrayList<Worm> collectionOfWorms;
	private ArrayList<Food> collectionOfFood;
	private ArrayList<Team> collectionOfTeams;
	private Worm currentWorm;
	private Projectile activeProjectile;

	/**
	 * Constructor of the class World.
	 * 
	 * @param width
	 * 			The width of the world
	 * @param height
	 * 			The height of the world
	 * @param passableMap
	 * 			... 
	 * @param random
	 * 			...
	 * @post	The width of the world has to be equal to the provided width.
	 * 			| (new this).getWidth() == width
	 * @post	The height of the world has to be equal to the provided height.
	 * 			| (new this).getHeight() == height
	 * @throws	IllegalArgumentException
	 * 			The provided width and/or the provided height are invalid.
	 * 			|!this.canHaveAsWidth(this.getWidth()) || !this.canHaveAsHeight(this.getHeight())
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) throws IllegalArgumentException {
		if (!this.canHaveAsWidth(this.getWidth())
				|| !this.canHaveAsHeight(this.getHeight()))
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
			if (this.lowerBoundX <= width && width <= this.upperBoundX)
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
			if (this.lowerBoundY <= height && height <= this.upperBoundY)
				return true;
		return false;
	}

	/**
	 * Function that returns the width of this world.
	 * 
	 * @return this.width
	 * 			The width of this world
	 */
	public double getWidth() {
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
	 * Function that adds a worm to this world.
	 */
	public void addWormToWorld() {
		this.collectionOfWorms.add(arg0);
	}

	/**
	 * Function that adds a piece of food to this world.
	 */
	public void addFoodToWorld() {
		this.collectionOfFood.add(arg0);
	}

	/**
	 * Function that adds a new team of worms to this world.
	 * 
	 * @param newName
	 * 			The name of the new team
	 */
	public void addEmptyTeam(String newName) {
		this.collectionOfTeams.add(new Team(newName));
	}

	/**
	 * Function that returns the active projectile from this world.
	 * 
	 * @return this.activeProjectile
	 * 			The projectile that is active in this world
	 */
	public Projectile getActiveProjectile() {
		return this.activeProjectile;
	}

	/**
	 * Function that returns the current worm of this world (i.e. the worm whose turn it is).
	 * 
	 * @return this.currentWorm
	 * 			The worm whose turn it currently is
	 */
	public Worm getCurrentWorm() {
		return this.currentWorm;
	}

	/**
	 * Function that returns all the food that is currently in this world.
	 * 
	 * @return this.collectionOfFood
	 * 			The collection of all the food in this world
	 */
	public Collection<Food> getFood() {
		return this.collectionOfFood;
	}

	/**
	 * Function that returns the name of the winner.
	 * 
	 * @return nameOfWinner
	 * 			The name of the winner of the game
	 * @return null
	 * 			The game isn't finished yet: there is no winner
	 */
	public String getWinner() {
		if (isGameFinished()) {
			for (Worm searchWinner : this.collectionOfWorms) {
				if (searchWinner.isAlive()
						&& searchWinner.getTeamName() == null)
					return searchWinner.getName();
				if (searchWinner.isAlive()
						&& searchWinner.getTeamName() != null)
					return searchWinner.getTeamName();
			}
		}
		return null;
	}

	/**
	 * Function that returns all the worms that are currently in this world (i.e. in the game).
	 * 
	 * @return this.collectionOfWorms
	 * 			The collection of worms that are in the game.
	 */
	public Collection<Worm> getWorms() {
		return this.collectionOfWorms;
	}

	/**
	 * Function that checks whether or not something is adjacent to impassable terrain.
	 * 
	 * @param x
	 * 			The x-coordinate of the object (worm, food etc.)
	 * @param y
	 * 			The y-coordinate of the object (worm, food etc.)
	 * @param radius
	 * 			The radius of the object.
	 * @return true
	 * 			The object is adjacent to impassable terrain
	 * @return false
	 * 			The object is not adjacent to impassable terrain
	 */
	public boolean isAdjacent(double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Function that checks whether or not the game is finished, i.e. a team or an individual worm has 
	 * won and is the only survivor.
	 * 
	 * @return true
	 * 			The game is finished
	 * @return false
	 * 			The game is not yet finished
	 */
	public boolean isGameFinished() {
		boolean finishedVerification = true;
		boolean solo = false;
		for (Worm potentialWinner : this.collectionOfWorms) {
			if (potentialWinner.isAlive()) {
				if (potentialWinner.getTeamName() == null)
					solo = true;
				int indexCounter = ((ArrayList<Worm>) getWorms())
						.indexOf(potentialWinner);
				for (Worm checkPotentialWinner = ((ArrayList<Worm>) getWorms())
						.get(indexCounter + 1); indexCounter < getWorms()
						.size(); indexCounter++) {
					if (checkPotentialWinner.isAlive()
							&& (solo || checkPotentialWinner.getTeamName() != potentialWinner
									.getTeamName())) {
						finishedVerification = false;
						break;
					}
				}
				break;
			}
		}
		return finishedVerification;
	}

	/**
	 * Function that checks whether or not there is impassable terrain on the circle described by the 
	 * provided coordinates and the provided radius.
	 * 
	 * @param x
	 * 			The x-coordinate of the middle point
	 * @param y
	 * 			The y-coordinate of the middle point
	 * @param radius
	 * 			The radius of the circle
	 * @return true
	 * 			There is impassable terrain on the provided radius around the provided coordinates
	 * @return false
	 * 			There is no impassable terrain on the provided radius around the provided coordinates
	 */
	public boolean isImpassable(double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Function that starts the game.
	 */
	public void startGame() {
		if (this.collectionOfWorms.isEmpty())
			// TODO Defensive, nominal? This is just a temporary solution (total
			// manner)!
			return;
		this.currentWorm = this.collectionOfWorms.get(0);
	}

	/**
	 * Function that starts a next turn.
	 */
	public void startNextTurn() {
		this.currentWorm = ((ArrayList<Worm>) this.getWorms())
				.get((((ArrayList<Worm>) this.getWorms()).indexOf(this
						.getCurrentWorm()) + 1) % getWorms().size());
		this.getCurrentWorm().setHitPoints(
				this.getCurrentWorm().getHitPoints() + 10);
	}

	/**
	 * Function that returns the teams in this world.
	 * 
	 * @return this.collectionOfTeams
	 * 			The teams in this world.
	 */
	public Collection<Team> getTeams() {
		return this.collectionOfTeams;
	}

	/**
	 * Method that creates a new piece of food, adds it to the food of this world (i.e. the collection) 
	 * and returns the newly created piece of food.
	 * 
	 * @param x
	 * 			The x-coordinate of the food that has to be created
	 * @param y
	 * 			The y-coordinate of the food that has to be created
	 * @return food
	 * 			The newly created food
	 */
	public Food createFood(double x, double y) {
		Food food = new Food(x, y);
		this.collectionOfFood.add(food);
		return food;
	}

	/**
	 * Method that creates a new worm, adds it to the collection of worms that are part of this world 
	 * and then returns the newly created worm.
	 * 
	 * @param x
	 * 			The x-coordinate of the to be created worm
	 * @param y
	 * 			The y-coordinate of the to be created worm
	 * @param direction
	 * 			The direction of the to be created worm
	 * @param radius
	 * 			The radius of the to be created worm
	 * @param name
	 * 			The name of the to be created worm
	 * @return worm
	 * 			The newly created worm
	 */
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		Worm worm = new Worm(x, y, direction, radius, name);
		this.collectionOfWorms.add(worm);
		return worm;
	}
}
