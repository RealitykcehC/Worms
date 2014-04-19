package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * A class that implements all the aspects of the game world.
 * 
 * @author Pieter Jan Vingerhoets & Mathijs Nelissen
 * @version 0.1b
 */
public class World {
	/**
	 * Declaration of variables.
	 */
	private double width, height;
	public static final double lowerBoundX = 0.0;
	public static final double lowerBoundY = 0.0;
	public static final double upperBoundX = Double.MAX_VALUE;
	public static final double upperBoundY = Double.MAX_VALUE;
	private ArrayList<Worm> collectionOfWorms;
	private ArrayList<Food> collectionOfFood;
	private ArrayList<Team> collectionOfTeams;
	private Worm currentWorm;
	private Projectile activeProjectile;
	private boolean[][] area;

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
		if (!isValidWidth(this.getWidth()) || !isValidHeight(this.getHeight()))
			throw new IllegalArgumentException();
		this.width = width;
		this.height = height;
		this.area = passableMap;
	}

	/**
	 * Function that checks whether or not the provided width is valid.
	 * 
	 * @return true
	 * 			The provided width is valid
	 * @return false
	 * 			The provided width is invalid
	 */
	public static boolean isValidWidth(double width) {
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
	public static boolean isValidHeight(double height) {
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
	// TODO The (x,y)-coordinate has to be adjacent to impassable terrain.
	public void addWormToWorld() {
		double xWorm, yWorm, radiusWorm;
		do {
			xWorm = Math.abs(new Random().nextDouble());
			yWorm = Math.abs(new Random().nextDouble());
			radiusWorm = Math.abs(new Random().nextDouble());
			if (this.isAdjacent(xWorm, yWorm, radiusWorm)) {
				this.collectionOfWorms.add(new Worm(this, xWorm, yWorm, Math
						.abs(new Random().nextDouble()), radiusWorm,
						"Default Name"));
			}
		} while (!(this.isAdjacent(xWorm, yWorm, radiusWorm)));
	}

	/**
	 * Function that adds a piece of food to this world.
	 */
	// TODO The (x,y)-coordinate has to be adjacent to impassable terrain.
	public void addFoodToWorld() {
		double xFood, yFood;
		do {
			xFood = Math.abs(new Random().nextDouble());
			yFood = Math.abs(new Random().nextDouble());
			if (this.isAdjacent(xFood, yFood, Food.radius)) {
				this.collectionOfFood.add(new Food(xFood, yFood));
			}
		} while (!(this.isAdjacent(xFood, yFood, Food.radius)));

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
				else if (searchWinner.isAlive()
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
	 * Function  isAdjacent() gives true if the given circle is passable terrain && is adjacent to impassable terrain at a 1.01 times the radius length

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

		if (downAdjacent(x,y,radius) && upAdjacent(x,y,radius) ){
			return true ;	
		}else{
			return false;
		}
	}
	/**
	 * Function that checks wether the downside of the circle is adjacent
	 * we check the xycoordinate point
	 * from there we use the radius to check the perimeter of the circle if it's adjacent or not
	 * 
	 * @param x		the x coordinate of the middlepoint of the circle
	 * @param y		the y coordinate of the middlepoint of the circle
	 * @param radius	the radius of the circle to check
	 * @return true if the circle is adjacent false if not
	 */
	public boolean downAdjacent(double x, double y, double radius){
		double poolX;
		double poolY;
		int counter = 0;

		for (int i=180; i<=360; i++){
			poolX = (double)(radius * Math.cos(Math.toRadians(i)));
			poolY = (double)(radius * Math.sin(Math.toRadians(i)));

			if (area[ (int)(x + poolX)][ (int)(y -poolY)]== true && area[(int) (x )][(int)(y ]== true && area[(int) (x+ poolX * 1.01) ][ (int) (y - poolY * 1.01) ] == false ){
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
	 * Function gives back wether the upside of an circle is adjacent
	 * we check the xycoordinate point 
	 * from there we use the radius to check the perimeter of the circle if it's adjacent or not
	 * 
	 * @param x 		the xcoordinate of the middlepoint of the circle
	 * @param y			the ycoordinate of the middlepoint of the circle
	 * @param radius	the radius of the given circle
	 * @return 			true if the circle is above adjacent
	 */
	public boolean upAdjacent(double x, double y, double radius){
		double poolX;
		double poolY;
		int counter = 0;

		for (int i=0; i<=180; i++){
			poolX = (double)(radius * Math.cos(Math.toRadians(i)));
			poolY = (double)(radius * Math.sin(Math.toRadians(i)));

			if (area[ (int)(x + poolX)][ (int)(y -poolY)]== true && area[(int) (x )][(int)(y ]== true && area[(int) (x+ poolX * 1.01) ][ (int) (y - poolY * 1.01) ] == false ){
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
	 * Function that checks whether or not there is impassable terrain on the circle described by the 
	 * provided coordinates and the provided radius.
	 * 
	 * @param x
	 * 			The x-coordinate of the middle point
	 * @param y
	 * 			The y-coordinate of the middle point
	 * @param radius
	 * 			The radius of the circle
	 * @invar counter gives how many times the given terrain is impassable at the
	 * 			edge of the radius and middlepoint
	 * 
	 * @return true
	 * 			There is impassable terrain on the provided radius around the provided coordinates
	 * @return false
	 * 			There is no impassable terrain on the provided radius around the provided coordinates
	 */
	
	public boolean isImpassable(double x, double y, double radius) {
		
		double poolX;
		double poolY;
		int counter = 0;
	
		for (int i=0; i<360; i++){
			poolX = (double)(radius * Math.cos(i));
			poolY = (double)(radius * Math.sin(i));
			if (area[ (int)(x + poolX)][ (int)(y -poolY)]== true])
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
	 * Each new turn, the worm whose turn it is, is healed for 10 Hit Points and its 
	 * Action Points are reset to the maximum amount of Action Points that worm can have.
	 */
	public void startNextTurn() {
		this.currentWorm = ((ArrayList<Worm>) this.getWorms())
				.get((((ArrayList<Worm>) this.getWorms()).indexOf(this
						.getCurrentWorm()) + 1) % getWorms().size());
		this.getCurrentWorm().setHitPoints(
				this.getCurrentWorm().getHitPoints() + 10);
		this.getCurrentWorm().setActionPoints(
				this.getCurrentWorm().getMaxActionPoints());
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
		Worm worm = new Worm(this, x, y, direction, radius, name);
		this.collectionOfWorms.add(worm);
		return worm;
	}

	/**
	 * Method that checks whether the object with the provided (x,y)-coordinates and radius lies in this world or not.
	 * 
	 * @param x
	 * 			The x-coordinate of the object
	 * @param y
	 * 			The y-coordinate of the object
	 * @param radius
	 * 			The radius of the object
	 * @return true
	 * 			The object, whose properties were used, lies in this world
	 * @return false
	 * 			The object, whose properties were used, doesn't lie in this world
	 */
	public boolean liesInWorld(double x, double y, double radius) {
		return ((x + radius <= this.getWidth()) && (x - radius >= lowerBoundX)
				&& (y + radius <= this.getHeight()) && (y - radius >= lowerBoundY));
	}
}
