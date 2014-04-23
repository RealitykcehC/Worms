package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.model.Worm;

/**
 * A class that implements the Interface IFacade.
 * 
 * @author Pieter Jan Vingerhoets & Mathijs Nelissen
 * @version 1.0
 */
public class Facade implements IFacade {

	/**
	 * Function that adds an empty team with the provided team name to the provided world.
	 * 
	 * @param world
	 * 			The world to which the new team has to be added
	 * @param newName
	 * 			The team name of the team that has to be created
	 * @effect	The world has to contain the newly created team.
	 * 			| world.getTeams().contains(Team(newName))
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public void addEmptyTeam(World world, String newName) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		world.addEmptyTeam(newName);
	}

	/**
	 * Function that adds a piece of food to the provided world.
	 * 
	 * @param world
	 * 			The world to which the piece of food has to be added
	 * @effect	The world has to contain the newly created piece of food.
	 * 			| world.getFood().contains(newFood)
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public void addNewFood(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		world.addFoodToWorld();
	}

	/**
	 * Function that adds a new worm to the provided world.
	 * 
	 * @param world
	 * 			The world to which a new worm has to be added
	 * @effect	The worm should be added to the provided world.
	 * 			| world.getWorms().contains(newWorm)
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public void addNewWorm(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		world.addWormToWorld();
	}

	/**
	 * Function that checks whether the provided worm can fall or not.
	 * 
	 * @param worm
	 * 			The worm for which has to be checked whether it can fall or not
	 * @return true
	 * 			The provided worm can fall
	 * @return false
	 * 			The provided worm can't fall
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public boolean canFall(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.canFall();
	}

	/**
	 * Function that checks whether a provided worm can move or not.
	 * 
	 * @param worm
	 * 			The worm for which has to be checked whether or not it can move
	 * @return true
	 * 			The provided worm can move
	 * @return false
	 * 			The provided worm can't move
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public boolean canMove(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.canMove();
	}

	/**
	 * Function that creates a new piece of food in the provided world.
	 * 
	 * @param world
	 * 			The world to which the piece of food has to be added
	 * @param x
	 * 			The provided x-coordinate for the to be created piece of food
	 * @param y
	 * 			The provided y-coordinate for the to be created piece of food
	 * @effect	The world has to contain a piece of food with the provided coordinates.
	 * 			| world.getFood().contains(createdFood)
	 * @return world.createFood(x,y)
	 * 			The newly created piece of food with coordinates (x,y) in the given world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Food createFood(World world, double x, double y)
			throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.createFood(x, y);
	}

	/**
	 * Function that creates a new world.
	 * 
	 * @param width
	 * 			The width of the to be created world
	 * @param height
	 * 			The height of the to be created world
	 * @param passableMap
	 * 			The array that describes the passable/ impassable parts of the map
	 * @param random
	 * 			...
	 * @return new World(width, height, passableMap, random)
	 * 			The newly created world with the provided properties
	 */
	// TODO ...
	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}

	/**
	 * Function that creates a worm in the provided world.
	 * 
	 * @param world
	 * 			The world in which the new worm has to be created
	 * @param x
	 * 			The x-coordinate of the to be created worm
	 * @param y
	 * 			The y-coordinate of the to be created worm
	 * @param direction
	 * 			The direction in which the to be created worm has to face
	 * @param radius
	 * 			The radius of the to be created worm
	 * @param name
	 * 			The name of the to be created worm
	 * @effect	The provided world has to contain the newly created worm.
	 * 			| world.getWorms().contains(createdWorm)
	 * @return world.createWorm(x, y, direction, radius, name)
	 * 			The newly created worm with the given properties in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.createWorm(x, y, direction, radius, name);
	}

	/**
	 * Method that makes the provided worm fall.
	 * 
	 * @param worm
	 * 			The worm that has to fall
	 * @effect	The provided worm has to be adjacent to impassable terrain.
	 * 			| worm.getWorld().isAdjacent(worm.getX(), worm.getY(), worm.getRadius())
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public void fall(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		worm.fall();
	}

	/**
	 * Function that returns the active projectile in the provided world.
	 * 
	 * @param world
	 * 			The world whose active projectile has to be returned
	 * @return world.getActiveProjectile()
	 * 			The active projectile of the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Projectile getActiveProjectile(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.getActiveProjectile();
	}

	/**
	 * Function that returns the current worm of the provided worm.
	 * 
	 * @param world
	 * 			The world whose active worm has to be returned
	 * @return world.getCurrentWorm()
	 * 			The currently active worm in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Worm getCurrentWorm(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.getCurrentWorm();
	}

	/**
	 * Function that returns all pieces of food in the provided world.
	 * 
	 * @param world
	 * 			The world from which all the pieces of food have to be returned
	 * @return world.getFood()
	 * 			The collection of all pieces of food in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Collection<Food> getFood(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.getFood();
	}

	/**
	 * Function that returns the amount of Hit Points of the provided worm.
	 * 
	 * @param worm
	 * 			The worm whose Hit Points have to be returned
	 * @return worm.getHitpoints()
	 * 			The amount of Hit Points of the provided worm
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public int getHitPoints(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getHitPoints();
	}

	/**
	 * Function that returns the (x,y)-coordinates of the provided projectile at a given time.
	 * 
	 * @param projectile
	 * 			The projectile whose coordinates have to be returned after the provided time
	 * @param t
	 * 			The time after which the projectile's coordinates have to be calculated
	 * @return projectile.getJumpStep(t)
	 * 			The (x,y)-coordinates of the projectile in an array
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public double[] getJumpStep(Projectile projectile, double t)
			throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.getJumpStep(t);
	}

	/**
	 * Function that returns the time that the provided projectile will take to executes its jump.
	 * 
	 * @param projectile
	 * 			The projectile that will execute its jump
	 * @param timeStep
	 * 			The time that the projectile will not pass any impassable terrain
	 * @return projectile.getJumpTime(timeStep)
	 * 			The time the provided projectile will take to execute its jump
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public double getJumpTime(Projectile projectile, double timeStep)
			throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.getJumpTime(timeStep);
	}

	/**
	 * Function that returns the time the provided worm's jump will take.
	 * 
	 * @param worm
	 * 			The worm that will execute its jump
	 * @param timeStep
	 * 			The time that the provided worm won't hit any impassable terrain
	 * @return worm.getJumpTime(timeStep)
	 * 			The time that it takes the provided worm to execute its jump
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getJumpTime(Worm worm, double timeStep) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getJumpTime(timeStep);
	}

	/**
	 * Function that returns the provided worm's maximum amount of Hit Points.
	 * 
	 * @param worm
	 * 			The worm whose maximum amount of Hit Points have to be returned
	 * @return worm.getMaxHitPoints()
	 * 			The maximum amount of Hit Points of the provided worm
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public int getMaxHitPoints(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getMaxHitPoints();
	}

	/**
	 * Function that returns the radius of the provided piece of food.
	 * 
	 * @param food
	 * 			The food of which the radius has to be returned
	 * @return food.getRadius()
	 * 			The radius of the provided piece of food
	 * @throws	ModelException
	 * 			The piece of food is an empty reference (a null pointer), an exception has to be 
	 * 			thrown.
	 * 			| food == null
	 */
	@Override
	public double getRadius(Food food) throws ModelException {
		if (food == null)
			throw new ModelException("Invalid food: null");
		return food.getRadius();
	}

	/**
	 * Function that returns the radius of the provided projectile.
	 * 
	 * @param projectile
	 * 			The projectile of which the radius has to be returned
	 * @return projectile.getRadius()
	 * 			The radius of the provided projectile
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public double getRadius(Projectile projectile) throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.getRadius();
	}

	/**
	 * Function that returns the selected weapon of the provided worm.
	 * 
	 * @param worm
	 * 			The worm of which the active weapon has to be returned
	 * @return worm.getSelectedWeapon()
	 * 			The selected weapon of the provided worm
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public String getSelectedWeapon(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getSelectedWeapon();
	}

	/**
	 * Function that returns the team name of the provided worm.
	 * 
	 * @param worm
	 * 			The worm whose team name has to be returned
	 * @return worm.getTeamName()
	 * 			The team name of the provided worm.
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public String getTeamName(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		try {
			return worm.getTeamName();
		} catch (NullPointerException e) {
			throw new ModelException("Invalid team name");
		}
	}

	/**
	 * Function that returns the winner of the game in the provided world.
	 * The winner of the game is either a team (only worms of that team are still alive) or an 
	 * individual worm (has no team).
	 * 
	 * @param world
	 * 			The world from which the winner has to be returned
	 * @return world.getWinner()
	 * 			The winner of the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public String getWinner(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.getWinner();
	}

	/**
	 * Function that returns all the worms who are in the provided world.
	 * 
	 * @param world
	 * 			The world from which the worms have to be returned
	 * @return world.getWorms()
	 * 			The collection of all worms in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public Collection<Worm> getWorms(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.getWorms();
	}

	/**
	 * Function that returns the x-coordinate of the provided piece of food.
	 * 
	 * @param food
	 * 			The food of which the x-coordinate has to be returned
	 * @return food.getX()
	 * 			The x-coordinate of the piece of food
	 * @throws	ModelException
	 * 			The piece of food is an empty reference (a null pointer), an exception has to be 
	 * 			thrown.
	 * 			| food == null
	 */
	@Override
	public double getX(Food food) throws ModelException {
		if (food == null)
			throw new ModelException("Invalid food: null");
		return food.getX();
	}

	/**
	 * Function that returns the x-coordinate of the provided projectile.
	 * 
	 * @param projectile
	 * 			The projectile of which the x-coordinate has to be returned
	 * @return projectile.getX()
	 * 			The x-coordinate of the provided projectile
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public double getX(Projectile projectile) throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.getX();
	}

	/**
	 * Function that returns the y-coordinate of the provided piece of food.
	 * 
	 * @param food
	 * 			The piece of food of which the y-coordinate has to be returned
	 * @return food.getY()
	 * 			The y-coordinate of the provided piece of food
	 * @throws	ModelException
	 * 			The piece of food is an empty reference (a null pointer), an exception has to be 
	 * 			thrown.
	 * 			| food == null
	 */
	@Override
	public double getY(Food food) throws ModelException {
		if (food == null)
			throw new ModelException("Invalid food: null");
		return food.getY();
	}

	/**
	 * Function that returns the y-coordinate of the provided projectile.
	 * 
	 * @param projectile
	 * 			The projectile of which the y-coordinate has to be returned
	 * @return projectile.getY()
	 * 			The y-coordinate of the provided projectile
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public double getY(Projectile projectile) throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.getY();
	}

	/**
	 * Function that checks whether or not a piece of food is active.
	 * 
	 * @param food
	 * 			The piece of food for which has to be checked whether or not it is active
	 * @return true
	 * 			The provided piece of food is active
	 * @return false
	 * 			The provided piece of food is inactive
	 * @throws	ModelException
	 * 			The piece of food is an empty reference (a null pointer), an exception has to be 
	 * 			thrown.
	 * 			| food == null
	 */
	@Override
	public boolean isActive(Food food) throws ModelException {
		if (food == null)
			throw new ModelException("Invalid food: null");
		return food.isActive();
	}

	/**
	 * Function that checks whether or not the provided projectile is active.
	 * 
	 * @param projectile
	 * 			The projectile for which has to be checked whether or not it is active
	 * @return true
	 * 			The provided projectile is active
	 * @return false
	 * 			The provided projectile is inactive
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public boolean isActive(Projectile projectile) throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		return projectile.isActive();
	}

	/**
	 * Function that checks whether or not an object with provided (x,y)-coordinates and radius is 
	 * adjacent in the provided world.
	 * 
	 * @param world
	 * 			The provided world in which the coordinates have to be tested
	 * @param x
	 * 			The provided x-coordinate that has to be tested in the provided world
	 * @param y
	 * 			The provided y-coordinate that has to be tested in the provided world
	 * @param radius
	 * 			The radius that (together with the provided coordinates) describes a circle for which 
	 * 			has to be checked whether or not it is adjacent in the provided world
	 * @return true
	 * 			The object is adjacent in the provided world
	 * @return false
	 * 			The object isn't adjacent in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public boolean isAdjacent(World world, double x, double y, double radius)
			throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.isAdjacent(x, y, radius);
	}

	/**
	 * Function that checks whether the provided worm is still alive or not.
	 * 
	 * @param worm
	 * 			The worm whose vitals have to be checked
	 * @return true
	 * 			The provided worm's still alive
	 * @return false
	 * 			The provided worms is no longer alive
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public boolean isAlive(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.isAlive();
	}

	/**
	 * Function that checks whether or not the game is finished (i.e. there's a winner) in the 
	 * provided world.
	 * 
	 * @param world
	 * 			The world in which has to be checked whether or not the game is finished
	 * @return true
	 * 			The game is finished in the provided world
	 * @return false
	 * 			The game isn't finished yet in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public boolean isGameFinished(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.isGameFinished();
	}

	/**
	 * Function that checks whether or not an object with coordinates (x,y) and a radius is on 
	 * impassable terrain in the provided world.
	 * 
	 * @param world
	 * 			The world in which has to be checked whether or not the object is on impassable terrain
	 * @param x
	 * 			The x-coordinate of the object
	 * @param y
	 * 			The y-coordinate of the object
	 * @param radius
	 * 			The radius of the object
	 * @return true
	 * 			The object is on impassable terrain in the provided world
	 * @return false
	 * 			The object isn't on impassable terrain in the provided world
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	@Override
	public boolean isImpassable(World world, double x, double y, double radius)
			throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		return world.isImpassable(x, y, radius);
	}

	/**
	 * Function that makes the provided projectile jump.
	 * 
	 * @param projectile
	 * 			The projectile that has to jump
	 * @param timeStep
	 * 			The time that the projectile will not hit any impassable terrain
	 * @effect	The projectile has to have traveled the needed distance.
	 * 			| new.projectile.getX() == projectile.getX() + projectile.getJumpDistance()
	 * @throws	ModelException
	 * 			The projectile is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| projectile == null
	 */
	@Override
	public void jump(Projectile projectile, double timeStep)
			throws ModelException {
		if (projectile == null)
			throw new ModelException("Invalid projectile: null");
		try {
			projectile.jump(timeStep);
		} catch (ArithmeticException e) {
			throw new ModelException("This worm cannot fire now");
		}
	}

	/**
	 * Function that makes the provided worm jump.
	 * 
	 * @param worm
	 * 			The worm that has to jump
	 * @param timeStep
	 * 			The time that the worm will not hit any impassable terrain
	 * @effect	The worm has to have traveled the needed distance.
	 * 			| new.worm.getX() == worm.getX() + worm.getJumpDistance()
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public void jump(Worm worm, double timeStep) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		try {
			worm.jump(timeStep);
		} catch (ArithmeticException e) {
			throw new ModelException(
					"This worm cannot jump or the ending point isn't valid.");
		}
	}

	/**
	 * Function that makes the provided worm move.
	 * 
	 * @param worm
	 * 			The worm that has to move
	 * @effect	The provided worm should have moved.
	 * 			| new.worm.getX() != worm.getX() || new.worm.getY() != worm.getY()
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public void move(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		try {
			worm.move();
		} catch (RuntimeException e) {
			throw new ModelException("This worm cannot move right now.");
		}
	}

	/**
	 * Function that makes the provided worm select its next weapon.
	 * 
	 * @param worm
	 * 			The worm that as to switch weapons
	 * @post	The weapon should have changed.
	 * 			| new.worm.getSelectedWeapon() != worm.getSelectedWeapon()
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public void selectNextWeapon(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		worm.selectNextWeapon();
	}

	/**
	 * Function that makes the given worm shoot a projectile of its current weapon with a provided 
	 * yield.
	 * 
	 * @param worm
	 * 			The worm that has to shoot
	 * @param yield
	 * 			The yield of the projectile
	 * @effect	A projectile should be fired.
	 * 			| ...
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	// TODO ...
	@Override
	public void shoot(Worm worm, int yield) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		try {
			worm.shoot(yield);
		} catch (ArithmeticException e) {
			throw new ModelException("This worm cannot shoot anymore");
		}
	}

	/**
	 * Function that starts the game for a provided world.
	 * 
	 * @param world
	 * 			The world in which the game has to start
	 * @effect	The game has to have started after execution of this method.
	 * 			| ...
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	// TODO ...
	@Override
	public void startGame(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		try {
			world.startGame();
		} catch (RuntimeException e) {
			throw new ModelException("No worms in the world.");
		}
	}

	/**
	 * Function that starts the next turn in the provided world.
	 * 
	 * @param world
	 * 			The world in which a next turn has to be started
	 * @effect 	The next turn has to be started, i.e. the next worm has to have been selected, its 
	 * 			Hit Points have to be increased by 10, respecting the limit of Hit Points and the 
	 * 			Action Points of that worm have to be reset to the maximum amount of Action Points.
	 * 			| ...
	 * @throws	ModelException
	 * 			The world is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| world == null
	 */
	// TODO ...
	@Override
	public void startNextTurn(World world) throws ModelException {
		if (world == null)
			throw new ModelException("Invalid world: null");
		world.startNextTurn();
	}

	/**
	 * Function that checks whether or not a worm can turn over a certain angle (in radians).
	 * 
	 * @param worm
	 * 			The worm for which has to be checked whether or not it can turn over the given angle.
	 * @param angle
	 * 			The angle for which has to be checked whether the worm can turn over it or not.
	 * @return false
	 * 			The worm can't turn over the given angle.
	 * @return true
	 * 			The worm is able to turn over the given angle.
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public boolean canTurn(Worm worm, double angle) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.canTurn(angle);
	}

	/**
	 * Function that returns the Action Points of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose Action Points have to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public int getActionPoints(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getActionPoints();
	}

	/**
	 * Function that returns the x,y-coordinates of the given worm in its jump at time t.
	 * 
	 * @param worm
	 * 			The worm whose x,y-coordinates have to be calculated in its jump at time t.
	 * @param t
	 * 			The time t for which the x,y-coordinates of the given worm have to be be calculated.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double[] getJumpStep(Worm worm, double t) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getJumpStep(t);
	}

	/**
	 * Function that returns the mass of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose mass has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getMass(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getMass();
	}

	/**
	 * Function that returns the maximum action points of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose maximum Action Points has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public int getMaxActionPoints(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getMaxActionPoints();
	}

	/**
	 * Function that returns the minimal radius of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose minimal radius has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getMinimalRadius(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getMinimalRadius();
	}

	/**
	 * Function that returns the name of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose name has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public String getName(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getName();
	}

	/**
	 * Function that returns the orientation of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose orientation has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getOrientation(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getOrientation();
	}

	/**
	 * Function that returns the radius of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose radius has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getRadius(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getRadius();
	}

	/**
	 * Function that returns the x-coordinate of the given worm.
	 * 
	 * @param worm
	 * 			The worm whose x-coordinate has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getX(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getX();
	}

	/**
	 * Function that returns the given worm's y-coordinate.
	 * 
	 * @param worm
	 * 			The worm whose y-coordinate has to be returned.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getY(Worm worm) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		return worm.getY();
	}

	/**
	 * Function that renames the given worm, if the new name is valid.
	 * 
	 * @param worm
	 * 			The worm whose name has to be changed.
	 * @param newName
	 * 			The new name for the given worm.
	 * @post	The name of the given worm has to be set to the new name, if it is a valid new name.
	 * 			| (new worm).getName() == newName
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 * @throws	ModelException
	 * 			If the new name is invalid, an exception has to be thrown.
	 * 			| !isValidName(newName)
	 */
	@Override
	public void rename(Worm worm, String newName) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		worm.rename(newName);
	}

	/**
	 * Function that sets the radius of the given worm to the radius that is given.
	 * 
	 * @param worm
	 * 			The worm whose radius has to be set to the provided one.
	 * @param newRadius
	 * 			The new radius that has to be set for the given worm.
	 * @post	The radius has to be changed to the given radius.
	 * 			| (new worm).getRadius() == newRadius
	 * @post	The mass of the given worm has to be changed accordingly.
	 * 			| (new worm).getMass() == worm.adjustMass(newRadius).getMass()
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 * @throws	ModelException
	 * 			The new radius is invalid.
	 * 			| !isValidRadius(newRadius) || !isValidMass(worm.adjustMass(newRadius).getMass())
	 */
	@Override
	public void setRadius(Worm worm, double newRadius) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		worm.setRadius(newRadius);
	}

	/**
	 * Function that makes the given worm turn over the given angle, if this is possible.
	 * 
	 * @param worm
	 * 			The worm that has to turn.
	 * @param angle
	 * 			The angle over which the given worm has to turn.
	 * @post	The direction has to change to the new direction.
	 * 			| (new worm).getOrientation() == worm.recalculateOrientation(worm.recalculateOrientation(angle) + worm.getOrientation())
	 * @post	The current amount of Action Points have to be edited.
	 * 			| (new worm).getActionPoints() == worm.getActionPoints() - Math.abs(Math.round(60 / (int) Math.round(2 * Math.PI / worm.recalculateAngle(angle))))
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public void turn(Worm worm, double angle) throws ModelException {
		if (worm == null)
			throw new ModelException("Invalid worm: null");
		worm.turn(angle);
	}
}
