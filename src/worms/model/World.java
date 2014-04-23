package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class that implements all the aspects of the game world.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 1.0
 */
public class World {
	/**
	 * Declaration of variables.
	 */
	public static final double LOWER_BOUND_X = 0.0;
	public static final double LOWER_BOUND_Y = 0.0;
	public static final double UPPER_BOUND_X = Double.MAX_VALUE;
	public static final double UPPER_BOUND_Y = Double.MAX_VALUE;
	private final double MAX_UPPER_LIMIT_RADIUS_WORM_INIT = 1.0;
	private double width, height;
	private boolean[][] area;
	private Random randomSeed;
	private boolean passable, adjacent, impassable;
	private boolean isStarted = false;
	private ArrayList<Worm> collectionOfWorms = new ArrayList<Worm>();
	private ArrayList<Food> collectionOfFood = new ArrayList<Food>();
	private ArrayList<Team> collectionOfTeams = new ArrayList<Team>();
	private Worm currentWorm;

	/**
	 * Constructor of the class World.
	 * 
	 * @param width
	 * 			The width of the world
	 * @param height
	 * 			The height of the world
	 * @param passableMap
	 * 			An array containing the impassable/passable value in all the pixels of the image file that is the world 
	 * @param random
	 * 			A random generator which is used as a seed to randomize aspects of the game world
	 * @post	The width of the world has to be equal to the provided width.
	 * 			| (new this).getWidth() == width
	 * @post	The height of the world has to be equal to the provided height.
	 * 			| (new this).getHeight() == height
	 * @post	The calculated game map (impassable/passable per pixel) has to be equal to the array area.
	 * 			| (new this).area = passableMap
	 * @post	The random seed generator has to be equal to the random seed generator provided by the arguments.
	 * 			| (new this).randomSeed = random
	 * @throws	IllegalArgumentException
	 * 			The provided width and/or the provided height are invalid.
	 * 			|!isValidWidth(width) || !isValidHeight(height)
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) throws IllegalArgumentException {
		if (!isValidWidth(width) || !isValidHeight(height))
			throw new IllegalArgumentException();
		this.width = width;
		this.height = height;
		this.area = passableMap;
		this.randomSeed = random;
	}

	/**
	 * Function that returns the width of this world.
	 * 
	 * @return this.width
	 * 			The width of this world
	 */
	@Basic
	public double getWidth() {
		return this.width;
	}

	/**
	 * Function that returns the height of this world.
	 * 
	 * @return this.height
	 * 			The height of this world
	 */
	@Basic
	public double getHeight() {
		return this.height;
	}

	/**
	 * Function that returns the random seed generator to randomize certain properties of this world.
	 * 
	 * @return this.randomSeed
	 * 			The random seed generator for this world
	 */
	@Basic
	public Random getRandomSeed() {
		return this.randomSeed;
	}

	/**
	 * Function that returns the current worm of this world (i.e. the worm whose turn it is).
	 * 
	 * @return this.currentWorm
	 * 			The worm whose turn it currently is
	 */
	@Basic
	public Worm getCurrentWorm() {
		return this.currentWorm;
	}
	
	/**
	 * Function that returns the active projectile from this world.
	 * 
	 * @return this.getCurrentWorm().getProjectile()
	 * 			The projectile that is active in this world
	 */
	@Basic
	public Projectile getActiveProjectile() {
		return this.getCurrentWorm().getProjectile();
	}

	/**
	 * Function that returns all the pieces of food that is currently in this world.
	 * 
	 * @return this.collectionOfFood
	 * 			The collection of all the food in this world
	 */
	public Collection<Food> getFood() {
		return this.collectionOfFood;
	}
	
	/**
	 * Function that returns all the teams in this world.
	 * 
	 * @return this.collectionOfTeams
	 * 			The teams in this world.
	 */
	public Collection<Team> getTeams() {
		return this.collectionOfTeams;
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
	 * Function that returns all objects (i.e. worms, food, teams, active projectile) in this world.
	 * 
	 * @return objectsList
	 * 			An ArrayList containing all the objects in this world
	 */
	public ArrayList<Object> getObjectsInWorld() {
		ArrayList<Object> objectsList = new ArrayList<Object>();
		objectsList.addAll(this.collectionOfWorms);
		objectsList.addAll(this.collectionOfTeams);
		objectsList.addAll(this.collectionOfFood);
		objectsList.add(this.getActiveProjectile());
		return objectsList;
	}

	/**
	 * Function that returns the name of the winner if that worm is playing solo.
	 * This function will return the team's name of the worm(s) that has (have) won.
	 * If there is no winner yet, this function will null.
	 * 
	 * @return nameOfWinner
	 * 			The name of the winner of the game, if the last worm that is alive, is playing solo.
	 * 			The name of the winner's team, if the only worms alive, are all of the same team.
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
	 * Function that adds a worm to this world on an arbitrarily chosen location, adjacent to impassable terrain.
	 * If the game has already been started, it is no longer allowed to add any worms to this world.
	 * The values of the properties of the newly created worm have to be valid when the new worm is being created.
	 * The worm is added to the collection of worms of this world and is arbitrarily assigned to an arbitrarily 
	 * chosen team, if there already are teams available in this world.
	 * 
	 * @post	The newly created worm has been added to this world.
	 * 			| (new this).getWorms().size == this.getWorms().size + 1 
	 * 			| 	&& (new this).getWorms().get((new this).getWorms().size() - 1) == newWorm
	 * @post	The newly created worm CAN have a team.
	 * 			| (new this).getWorms().get((new this).getWorms().size() - 1).getTeamName() == teamName
	 */
	public void addWormToWorld() {
		if (this.isStarted())
			return;
		Random randomGen = new Random();
		int oldNumberOfWorms = this.collectionOfWorms.size();
		double radius = 0.25 + (this.MAX_UPPER_LIMIT_RADIUS_WORM_INIT - 0.25)
				* randomGen.nextDouble();
		do {
			double[] resultOfLocation = this.locateNewObject(radius);
			if (resultOfLocation[0] != -1)
				// Other values of the result are trivial once the first is -1.
				this.collectionOfWorms.add(new Worm(this, resultOfLocation[0],
						resultOfLocation[1], resultOfLocation[2], radius,
						"Default"));
		} while (oldNumberOfWorms + 1 != this.collectionOfWorms.size());
		// Optionally (at random), add the newly created worm to a random team.
		if (!this.collectionOfTeams.isEmpty())
			if (randomGen.nextDouble() <= .5)
				this.collectionOfWorms
						.get(this.collectionOfWorms.size() - 1)
						.addToTeam(
								this.collectionOfTeams.get(randomGen
										.nextInt(this.collectionOfTeams.size())));
	}

	/**
	 * Method that removes a given worm from this world.
	 * 
	 * @param worm
	 * 			The worm that has to be removed from this world
	 * @post	The worm that is provided has to be deleted from this world.
	 * 			| (new this).getWorms().size() == this.getWorms().size() - 1 
	 * 			|	&& !(new this).getWorms().contains(worm) && (new worm).isTerminated()
	 */
	public void removeWormFromWorld(Worm worm) {
		worm.terminate();
		this.collectionOfWorms.remove(worm);
	}

	/**
	 * Method that adds a piece of food to this world.
	 * The position of the piece of food that is to be added is chosen arbitrarily, but will always be adjacent to impassable terrain.
	 * Once the game has been started, it is no longer allowed to add any more additional pieces of food to this world.
	 * 
	 * @post	The newly created piece of food has been added to this world.
	 * 			| (new this).getFood().size == this.getFood().size + 1 
	 * 			| 	&& (new this).getFood().get((new this).getFood().size() - 1) == newFood
	 */
	public void addFoodToWorld() {
		if (this.isStarted())
			return;
		int oldNumberOfFood = this.collectionOfFood.size();
		do {
			double[] resultOfLocation = this.locateNewObject(Food.RADIUS);
			if (resultOfLocation[0] != -1)
				// Other values of the result are trivial once the first is -1.
				this.collectionOfFood.add(new Food(this, resultOfLocation[0],
						resultOfLocation[1]));
		} while (this.collectionOfFood.size() != oldNumberOfFood + 1);
	}

	/**
	 * Method that removes a given piece of food from this world.
	 * 
	 * @param food
	 * 			The piece of food that has to be removed from this world.
	 * @post	The piece of food that is provided has to be deleted from this world.
	 * 			| (new this).getFood().size() == this.getFood().size() - 1 
	 * 			|	&& !(new this).getFood().contains(food) && (new food).isTerminated()
	 */
	public void removeFoodFromWorld(Food food) {
		food.terminate();
		this.collectionOfFood.remove(food);
	}

	/**
	 * Method that adds a new team of worms to this world, when it is possible (no more than 10 teams can be added to this world).
	 * 
	 * @param newName
	 * 			The name of the new team
	 * @post	The new team has been added to this world.
	 * 			| (new this).getTeams().size == this.getTeams().size() + 1 
	 * 			|	&& (new this).getTeams().size() <= 10 
	 * 			|		&& (new this).getTeams().get((new this).getTeams().size() - 1) == newTeam
	 * 			|			&& (new this).getTeams().get((new this).getTeams().size() - 1).equals(newName)
	 */
	public void addEmptyTeam(String newName) {
		if (this.canCreateTeam())
			this.collectionOfTeams.add(new Team(newName));
	}
	
	/**
	 * Method that removes the provided projectile from this world.
	 * 
	 * @param projectile
	 * 			The projectile that has to be removed from this world
	 * @post	The provided projectile has to have been terminated.
	 * 			| (new projectile).isTerminated()
	 */
	public void removeProjectileFromWorld(Projectile projectile) {
		projectile.terminate();
	}

	/**
	 * Method that creates a new piece of food, adds it to the food of this world (i.e. the collection) 
	 * and returns the newly created piece of food.
	 * Once the game has beens started, no more food is allowed to be added to this world.
	 * 
	 * @param x
	 * 			The x-coordinate of the food that has to be created
	 * @param y
	 * 			The y-coordinate of the food that has to be created
	 * @post	The newly created piece of food has to be a part of this world.
	 * 			| (new this).getFood().size() == this.getFood().size() + 1 &&
	 * 			|	(new this).getFood().get((new this).getFood().size() - 1) == newFood
	 * @return newFood
	 * 			The newly created food
	 * @throws	RuntimeException
	 * 			The game has already been started.
	 * 			| this.isStarted()
	 */
	public Food createFood(double x, double y) throws RuntimeException {
		if (this.isStarted())
			throw new RuntimeException();
		this.collectionOfFood.add(new Food(this, x, y));
		return this.collectionOfFood.get(this.collectionOfFood.size() - 1);
	}

	/**
	 * Method that creates a new worm, adds it to the collection of worms that are part of this world 
	 * and then returns the newly created worm.
	 * Once the game has been started, it is no longer allowed to add new worms to this world.
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
	 * @post	The new worm has been added to this world.
	 * 			| (new this).getWorms().size() == this.getWorms().size() + 1
	 * 			|	&& (new this).getWorms().get((new this).getWorms().size() - 1) == newWorm
	 * @return newWorm
	 * 			The newly created worm
	 * @throws	RuntimeException
	 * 			The game has already been started.
	 * 			| this.isStarted()
	 */
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) throws RuntimeException {
		if (this.isStarted())
			throw new RuntimeException();
		Worm worm = new Worm(this, x, y, direction, radius, name);
		this.collectionOfWorms.add(worm);
		return worm;
	}

	/**
	 * Method that starts the game.
	 * This method sets the state of the game in this world to started and it also selects the 
	 * first worm that will be allowed to do player-controlled actions.
	 * If there are currently no worms in this world, the game will end.
	 * 
	 * @post	This method sets the state of the game in this world to started.
	 * 			| (new this).isStarted()
	 * @post	The first worm has been selected.
	 * 			| (new this).getWorms().get(0) == (new this).getCurrentWorm()
	 */
	public void startGame() {
		this.isStarted = true;
		if (this.collectionOfWorms.isEmpty())
			return;
		this.currentWorm = this.collectionOfWorms.get(0);
	}

	/**
	 * Function that starts a next turn.
	 * Each new turn, the worm whose turn it is, is healed for 10 Hit Points and its 
	 * Action Points are reset to the maximum amount of Action Points that worm can have.
	 * The next worm's turn cannot start, until the active projectile of this world has been terminated.
	 * 
	 * @effect	First of all, the active projectile is removed from this world, if this didn't happen already.
	 * 			The next worm is selected. This worm's Hit Points is increased by 10 and its Action Points is set 
	 * 			to the maximum amount of Action Points.
	 * 			| if (!this.getActiveProjectile().isTerminated())
	 * 			|	then (this.removeProjectileFromWorld(this.getActiveProjectile()))
	 * 			| (((new this).getCurrentWorm() == this.getWorms().get((this.getWorms().indexOf(this
	 *			|	.getCurrentWorm()) + 1) % getWorms().size()))
	 *			| 		&& ((new this).getCurrentWorm().getHitPoints() == this.getWorms().get(this.getWorms().
	 *			|			indexOf(this.getCurrentWorm() + 1) % this.getWorms().size()).getHitPoints() + 10)
	 *			|				&& ((new this).getCurrentWorm().getActionPoints() == this.getWorms().get(this.getWorms().
	 *			|					indexOf(this.getCurrentWorm() + 1) % this.getWorms().size()).getMaxActionPoints()))
	 */
	public void startNextTurn() {
		if (!this.getActiveProjectile().isTerminated())
			this.removeProjectileFromWorld(this.getActiveProjectile());
		this.currentWorm = ((ArrayList<Worm>) this.getWorms())
				.get((((ArrayList<Worm>) this.getWorms()).indexOf(this
						.getCurrentWorm()) + 1) % getWorms().size());
		this.getCurrentWorm().setHitPoints(
				this.getCurrentWorm().getHitPoints() + 10);
		this.getCurrentWorm().setActionPoints(
				this.getCurrentWorm().getMaxActionPoints());
	}

	/**
	 * Function that checks whether or not the object with provided (x,y)-coordinates and radius is adjacent to
	 * impassable terrain or not.
	 * An object is adjacent to impassable terrain when the circle describing the object is on impassable terrain 
	 * with 1.1 times the radius of the object and on passable terrain with the radius of the object.
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
		this.calculateLocationStatus(x, y, radius);
		return this.adjacent;
	}

	/**
	 * Function that checks whether or not there is impassable terrain on the circle, described by the 
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
		this.calculateLocationStatus(x, y, radius);
		return this.impassable;
	}

	/**
	 * Function that checks whether or not all terrain on the circle, described by the 
	 * provided coordinates and the provided radius is all passable.
	 * 
	 * @param x
	 * 			The x-coordinate of the middle point
	 * @param y
	 * 			The y-coordinate of the middle point
	 * @param radius
	 * 			The radius of the circle
	 * @return true
	 * 			All terrain is passable on the provided radius around the provided coordinates
	 * @return false
	 * 			Not all of the terrain is passable on the provided radius around the provided coordinates
	 */
	public boolean isPassable(double x, double y, double radius) {
		this.calculateLocationStatus(x, y, radius);
		return this.passable;
	}

	/**
	 * Function that checks whether or not the game is finished, i.e. a team (all remaining worms are from the same team) 
	 * or an individual worm (that worm is the only survivor and is not part of a team) has won.
	 * 
	 * @return true
	 * 			The game is finished
	 * @return false
	 * 			The game is not yet finished
	 */
	public boolean isGameFinished() {
		if (!this.isStarted())
			return false;
		if (this.getWorms().size() == 1)
			return true;
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
		return ((x + radius <= this.getWidth()) && (x - radius >= LOWER_BOUND_X)
				&& (y + radius <= this.getHeight()) && (y - radius >= LOWER_BOUND_Y));
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
			if (LOWER_BOUND_X <= width && width <= UPPER_BOUND_X)
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
			if (LOWER_BOUND_Y <= height && height <= UPPER_BOUND_Y)
				return true;
		return false;
	}

	/**
	 * Function that calculates the position a new object in this world with arbitrarily generated properties.
	 * The object will always be located on terrain adjacent to impassable terrain.
	 * 
	 * @param radius
	 * 			The radius the object has to have.
	 * @return locationArray
	 * 			The array containing the following properties that the randomly placed object should get:
	 * 			the x-coordinate, the y-coordinate and the orientation
	 */
	private double[] locateNewObject(double radius) {
		Random randomGen = new Random();
		double initX = 0, initY = 0;
		double angleStartToCenter;
		double xPos, yPos;
		double centerX = this.getWidth() / 2.0, centerY = this.getHeight() / 2.0;
		int random = randomGen.nextInt(4) + 1;
		if (random == 1 || random == 2) { // Lower and upper side as
											// starting point.
			initX = (this.getWidth() - 3.0 * radius) * randomGen.nextDouble()
					+ radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
			if (random == 1) // Upper side.
				initY = this.getHeight() - radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
			if (random == 2) // Lower side.
				initY = radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
		} else if (random == 3 || random == 4) { // Left and right side as
													// starting point.
			initY = (this.getHeight() - 3.0 * radius) * randomGen.nextDouble()
					+ radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
			if (random == 3) // Left side.
				initX = radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
			if (random == 4) // Right side.
				initX = this.getWidth() - radius * 1.5; // Make sure no OutOfBoundsException when radius is out of world.
		}
		xPos = initX;
		yPos = initY;
		// Determine quadrant of initX and initY.
		if (initY >= centerY && initX >= centerX) { // Quadrant 1
			angleStartToCenter = Math.PI
					+ Math.atan((initY - centerY) / (initX - centerX));
			while (xPos >= centerX && yPos >= centerY) {
				if (isAdjacent(xPos, yPos, radius)) {
					double[] result = { xPos, yPos, angleStartToCenter };
					return result;
				} else {
					// Step for iteration is 10% of radius, because the
					// boundary for the worm to be adjacent, is 10% of the
					// radius.
					xPos += Math.cos(angleStartToCenter) * .1 * radius;
					yPos += Math.sin(angleStartToCenter) * .1 * radius;
				}
			}
		} else if (initY >= centerY && initX <= centerX) { // Quadrant 2
			angleStartToCenter = (3.0 / 2.0) * Math.PI
					+ Math.atan((centerX - initX) / (initY - centerY));
			while (xPos <= centerX && yPos >= centerY) {
				if (isAdjacent(xPos, yPos, radius)) {
					double[] result = { xPos, yPos, angleStartToCenter };
					return result;
				} else {
					// Step for iteration is 10% of radius, because the
					// boundary for the worm to be adjacent, is 10% of the
					// radius.
					xPos += Math.cos(angleStartToCenter) * .1 * radius;
					yPos += Math.sin(angleStartToCenter) * .1 * radius;
				}
			}
		} else if (initY <= centerY && initX <= centerX) { // Quadrant 3
			angleStartToCenter = Math.atan((centerY - initY)
					/ (centerX - initX));
			while (xPos <= centerX && yPos <= centerY) {
				if (isAdjacent(xPos, yPos, radius)) {
					double[] result = { xPos, yPos, angleStartToCenter };
					return result;
				} else {
					// Step for iteration is 10% of radius, because the
					// boundary for the worm to be adjacent, is 10% of the
					// radius.
					xPos += Math.cos(angleStartToCenter) * .1 * radius;
					yPos += Math.sin(angleStartToCenter) * .1 * radius;
				}
			}
		} else if (initY <= centerY && initX >= centerX) { // Quadrant 4
			angleStartToCenter = (Math.PI / 2)
					+ Math.atan((initX - centerX) / (centerY - initY));
			while (xPos >= centerX && yPos <= centerY) {
				if (isAdjacent(xPos, yPos, radius)) {
					double[] result = { xPos, yPos, angleStartToCenter };
					return result;
				} else {
					// Step for iteration is 10% of radius, because the
					// boundary for the worm to be adjacent, is 10% of the
					// radius.
					xPos += Math.cos(angleStartToCenter) * .1 * radius;
					yPos += Math.sin(angleStartToCenter) * .1 * radius;
				}
			}
		}
		double[] result = { -1, -1, -1 };
		return result;
	}

	/**
	 * Method that calculates the pixel-coordinates for the given metric coordinates and returns the result.
	 * 
	 * @param x
	 * 			The given metric x-coordinate
	 * @param y
	 * 			The given metric y-coordinate
	 * @return result
	 * 			An array containing the pixel-coordinates that are equal to those given in metric coordinates
	 */
	private int[] metricToPixels(double x, double y) {
		double heightPerPixel = this.getHeight() / this.area.length;
		double widthPerPixel = this.getWidth() / this.area[0].length;
		int xResult = (int) (x / widthPerPixel);
		xResult = Math.max(0, xResult);
		xResult = Math.min(xResult, this.area[0].length - 1);
		int yResult = (int) ((this.getHeight() - y) / heightPerPixel);
		yResult = Math.max(0, yResult);
		yResult = Math.min(yResult, this.area.length - 1);
		int[] result = { xResult, yResult };
		return result;
	}
	
	/**
	 * Method that checks the location status of this world for an object that stands on the given coordinates (x,y) and has the given radius.
	 * 
	 * @param x
	 * 			The x-coordinate of the object
	 * @param y
	 * 			The y-coordinate of the object
	 * @param radius
	 * 			The radius of the worm
	 * @effect	The boolean values impassable, adjacent and passable are set to the correct values for the object's position and radius.
	 * 			| if (object.isImpassable(object.getX(), object.getY, object.getRadius())
	 * 			|	then ((new this).impassable && !((new this).adjacent) && !((new this).passable))
	 * 			| else if (object.isAdjacent(object.getX(), object.getY, object.getRadius())
	 * 			|	then (!((new this).impassable) && (new this).adjacent && !((new this).passable))
	 * 			| else if (object.isPassable(object.getX(), object.getY, object.getRadius())
	 * 			|	then (!((new this).impassable) && !((new this).adjacent) && (new this).passable)
	 */
	private void calculateLocationStatus(double x, double y, double radius) {
		double circleX, circleY;
		this.impassable = false;
		this.adjacent = false;
		this.passable = true;
		for (int i = 0; i < 360; i++) {
			circleX = radius * Math.cos(Math.toRadians(i));
			circleY = radius * Math.sin(Math.toRadians(i));
			int[] objectCircleToPixels = metricToPixels(x + circleX, y
					+ circleY);
			int[] objectBigCircleToPixels = metricToPixels(x + circleX * 1.1, y
					+ circleY * 1.1);
			if (!area[objectCircleToPixels[1]][objectCircleToPixels[0]]) {
				this.impassable = true;
				this.adjacent = false;
				this.passable = false;
				return;
			} else if (this.area[objectCircleToPixels[1]][objectCircleToPixels[0]]
					&& !(this.area[objectBigCircleToPixels[1]][objectBigCircleToPixels[0]])) {
				this.adjacent = true;
				this.passable = false;
			}
		}
	}

	/**
	 * Method that returns whether or not any more teams can be created in this world or not.
	 * No teams are allowed to be created if there already are 10 teams in this world or once the game has been started.
	 * 
	 * @return true
	 * 			Another team can be created in this world
	 * @return false
	 * 			No more teams are allowed to be created in this world
	 */
	private boolean canCreateTeam() {
		return (this.collectionOfTeams.size() < 10 && !this.isStarted());
	}

	/**
	 * Function that return the boolean value that stores whether or not the game has already been started in this world.
	 * 
	 * @return true
	 * 			The game has already been started in this world
	 * @return false
	 * 			The game has not been started yet in this world
	 */
	private boolean isStarted() {
		return this.isStarted;
	}
}
