package worms.model;

import java.util.ArrayList;

import worms.util.Util;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class that implements the possible movements of a worm.
 * All aspects related to the position of the worm are worked out defensively.
 * All aspects related to the direction of the worm are worked out nominally.
 * All aspects related to the worm's radius and mass are worked out defensively.
 * All aspects related to action points are worked out by total programming.
 * All characteristics of a worm are treated as numbers of data type double, unless
 * stated otherwise. The values Double.NEGATIVE_INFINITY and Double.POSITIVE_INFINITY
 * are not excluded (unless it is said otherwise).
 * All aspects related to the worm's name are worked out defensively.
 * 
 * @invar	The x-coordinate of a worm has to be valid at all times during the game. They may never stand at an
 * 			invalid x-coordinate.
 * 			| isValidX(this.getX())
 * @invar	The y-coordinate of a worm has to be valid at all times during the game. They may never stand at an
 * 			invalid y-coordinate.
 * 			| isValidY(this.getY())
 * @invar	The radius of a worm must be valid at all times during the game. They may never have an invalid radius
 * 			(i.e. a radius which is less than the minimum radius, which is .25 meters)
 * 			| isValidRadius(this.getRadius())
 * @invar	The mass of a worm has to be valid at all times during the game. They may never have an invalid mass
 * 			(i.e. a mass less than or equal to 0 kg).
 * 			| isValidMass(this.getMass())
 * @invar	The name of a worm has to be valid at all times during the game. They may never have an invalid name
 * 			(i.e. a name that doesn't correspond to the naming guidelines).
 * 			| isValidName(this.getName())
 * @invar	The maximum of Action Points is not allowed to be negative.
 * 			| this.getMaxActionPoints() >= 0
 * @invar	The amount of Action Points cannot be greater than the maximum amount of Action Points.
 * 			| this.getActionPoints() <= this.getMaxActionPoints()
 * @invar	The orientation of this worm has to be a part of the interval [0, 2 * Math.PI[ at all times.
 * 			| 0 <= this.getOrientation() && this.getOrientation() < 2 * Math.PI
 * @invar	Only one weapon can be deployed each time, a worm cannot use multiple weapons at once.
 * 			| this.canHaveAsWeaponUsage()
 * @author Pieter Jan Vingerhoets & Mathijs Nelissen
 * @version 1.0
 */
public class Worm {
	/**
	 * Declaration of variables.
	 */
	public final double g = 9.80665;
	public final double density = 1062.0;
	private static final double minimalRadius = .25;
	private static final double lowerBoundMassExcluded = 0;
	private static final double lowerBoundX = World.lowerBoundX;
	private static final double upperBoundX = World.upperBoundX;
	private static final double lowerBoundY = World.lowerBoundY;
	private static final double upperBoundY = World.upperBoundY;
	private double x, y, direction, radius, mass;
	private int actionPoints, maxActionPoints;
	private int hitPoints, maxHitPoints;
	private int indexOfCurrentWeapon;
	private boolean isTerminated = false;
	private String name;
	@SuppressWarnings("unused")
	private Projectile projectile;
	private Team team;
	private World world;
	private ArrayList<Projectile> collectionOfWeapons = new ArrayList<Projectile>();

	/**
	 * Constructor of the class Worm with given x,y-coordinates (in meters), a
	 * direction (expressed as an angle in radians), a radius (in meters) and a name.
	 * It also gives the worm the correct mass (in kg).
	 * @param world 
	 * @param world 
	 * 
	 * @param x
	 * 			The given x-coordinate of the worm that should be created (in meters).
	 * @param y
	 * 			The given y-coordinate of the worm that should be created (in meters).
	 * @param direction
	 * 			The given direction in which the worm should face (in radians).
	 * @param radius
	 * 			The given radius of the worm (in meters), starting from the center of the worm.
	 * @param name
	 * 			The name the worm should be getting.
	 * @post	The given x must be equal to the x-coordinate of the worm that is being created.
	 * 			| (new this).getX() == x
	 * @post	The given y must be equal to the y-coordinate of the worm that is being created.
	 * 			| (new this).getY() == y
	 * @post	The given direction has to be equal to the orientation of the worm that is being created.
	 * 			The given direction first has to be recalculated to an angle in the interval [0, 2 * Math.PI[
	 * 			| (new this).getOrientation() == recalculateOrientation(direction)
	 * @post	The given radius has to be equal to the radius of the worm that's being created.
	 * 			| (new this).getRadius() == radius
	 * @post	The given name should be equal to the name of the worm that's being created.
	 * 			| (new this).getName() == name
	 * @post	The given mass should be equal to the mass of the worm that's being created.
	 * 			| (new this).getMass() == mass
	 * @post	The maximum amount of Action Points is equal to the mass of this worm, rounded to the
	 * 			nearest integer value.
	 * 			| (new this).getMaxActionPoints() == (int) Math.round(this.getMass())
	 * @post	The maximum amount of Hit Points is equal to the mass of this worm, rounded to the
	 * 			nearest integer value (this is the same as the maximum amount of Action Points).
	 * 			| (new this).getMaxHitPoints() == this.getMaxActionPoints()
	 * @post	The amount of Action Points at the beginning (i.e. at creation) is equal to the maximum
	 * 			amount of Action Points this worm can have.
	 * 			| (new this).getActionPoints() == this.getMaxActionPoints()
	 * @post	The amount of Hit Points at the beginning (i.e. at creation) is equal to the maximum
	 * 			amount of Hit Points this worm can have.
	 * 			| (new this).getHitPoints() == this.getMaxHitPoints()
	 * @throws	IllegalArgumentException
	 * 			The given x has to be a valid x-coordinate, otherwise an exception has to be thrown.
	 * 			| !isValidX(x)
	 * @throws	IllegalArgumentException
	 * 			The given y has to be a valid y-coordinate, otherwise an exception has to be thrown.
	 * 			| !isValidY(y)
	 * @throws 	IllegalArgumentException
	 * 			The given radius has to be a valid radius, otherwise an exception should be thrown. 
	 * 			| !isValidRadius(radius)
	 * @throws 	ArithmeticException
	 * 			The calculated mass has to be valid, otherwise an exception should be thrown.
	 * 			| !isValidMass(mass)
	 * @throws 	IllegalArgumentException
	 * 			The given name should be valid and the reference to it should be different from the null reference, 
	 * 			otherwise an exception has to be thrown.
	 * 			| !isValidName(name)
	 */
	public Worm(World world, double x, double y, double direction,
			double radius, String name) throws IllegalArgumentException,
			ArithmeticException {
		if (!isValidX(x) || !isValidY(y) || !isValidRadius(radius)
				|| !isValidName(name))
			throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
		this.direction = recalculateAngle(direction);
		this.radius = radius;
		this.name = name;
		this.mass = this.density
				* ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3));
		if (!isValidMass(this.mass))
			throw new ArithmeticException();
		this.maxActionPoints = (int) Math.round(this.getMass());
		this.maxHitPoints = (int) Math.round(this.getMass());
		this.actionPoints = this.getMaxActionPoints();
		this.hitPoints = this.getMaxHitPoints();
		this.world = world;
		this.initWeapons();
	}

	public World getWorld() {
		return this.world;
	}

	/**
	 * Function that returns the x-coordinate of this worm (in meters).
	 * The x-coordinate will always be valid, since invalid coordinates aren't accepted by the setters/constructor.
	 * 
	 * @return this.x
	 * 			The x-coordinate of this worm.
	 */
	@Basic
	public double getX() {
		return this.x;
	}

	/**
	 * Function that returns the y-coordinate of this worm (in meters).
	 * The y-coordinate will always be valid, since invalid coordinates aren't accepted by the setters/constructor.
	 * 
	 * @return this.y
	 * 			The y-coordinate of this worm.
	 */
	@Basic
	public double getY() {
		return this.y;
	}

	/**
	 * Function that returns the radius of this worm (in meters).
	 * The radius will always be valid, since the constructor and setters won't allow values which would cause any trouble.
	 * 
	 * @return this.radius
	 * 			The radius of this worm.
	 */
	@Basic
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Function that returns the mass of this worm (in kg).
	 * The mass of this worm will always be valid, since the constructor and setters won't accept values that give
	 * an invalid mass.
	 * 
	 * @return this.mass
	 * 			The mass of this worm.
	 */
	@Basic
	public double getMass() {
		return this.mass;
	}

	/**
	 * Function that returns the orientation of this worm (in radians).
	 * The orientation will always be valid, since the setters/constructor won't accept to set it, unless it is valid.
	 * 
	 * @return this.direction
	 * 			The orientation of this worm.
	 */
	@Basic
	public double getOrientation() {
		return this.direction;
	}

	/**
	 * Function that returns the name of this worm.
	 * The name will always be valid. If given invalid names, it is handled in the constructor/setters.
	 * 
	 * @return this.name
	 * 			The name of this worm.
	 */
	@Basic
	public String getName() {
		return this.name;
	}

	/**
	 * Function that returns the minimal radius of this worm.
	 * 
	 * @return minimalRadius
	 * 			The minimal radius of this worm.
	 */
	public double getMinimalRadius() {
		return minimalRadius;
	}

	/**
	 * Function that returns the current amount of Action Points this worm has.
	 * 
	 * @return actionPoints
	 * 			The number of Action Points this worm currently has.
	 */
	public int getActionPoints() {
		return this.actionPoints;
	}

	/**
	 * Function that returns the maximum Action Points for this worm.
	 * 
	 * @return maxActionPoints
	 * 			The maximum Action Points for this worm.
	 */
	public int getMaxActionPoints() {
		return this.maxActionPoints;
	}

	/**
	 * Function which returns the current amount of Hit Points of this worm.
	 * 
	 * @return this.hitPoints
	 * 			The current amount of Hit Points of this worm
	 */
	public int getHitPoints() {
		return this.hitPoints;
	}

	/**
	 * Function which returns the maximum Hit Points for this worm.
	 * 
	 * @return this.maxHitPoints
	 * 			The maximum amount of Hit Points this worm can have.
	 */
	public int getMaxHitPoints() {
		return this.maxHitPoints;
	}

	public double getTotalStepCostForMove(double angle) {
		return Math.abs(Math.sin(angle) * 4) + Math.abs(Math.cos(angle));
	}

	// /**
	// * Function that returns the total cost of a step for a move.
	// *
	// * @return Math.abs(Math.sin(this.getOrientation()) * 4) +
	// Math.abs(Math.cos(this.getOrientation()))
	// * The total cost of a step for a move (according to the orientation of
	// this worm).
	// */
	// public double getTotalStepCost() {
	// return Math.abs(Math.sin(this.getOrientation()) * 4)
	// + Math.abs(Math.cos(this.getOrientation()));
	// }

	/**
	 * Function that calculates the air-time of this worm when he jumps.
	 * 
	 * @param timeStep 
	 * 			...
	 * @return this.getJumpDistance() / (this.getInitialVelocity() * Math.cos(this.getOrientation()))
	 * 			The time a worm's jump takes (air-time).
	 */
	public double getJumpTime(double timeStep) {
		double newX = 0, newY = 0;
		double[] jumpStep = new double[2];
		double totalJumpTime = 0;
		do {
			jumpStep = this.getJumpStep(totalJumpTime);
			newX = jumpStep[0];
			newY = jumpStep[1];
			totalJumpTime += timeStep;
		} while (this.getRadius() >= Math.sqrt(Math.pow(this.getX() - newX, 2)
				+ Math.pow(this.getY() - newY, 2))|| !(this.isJumpFinished(newX, newY)));
		return totalJumpTime;
	}

	/**
	 * Function that gives the x,y-coordinates of this worm at a given moment t in the jump.
	 * 
	 * @param t 
	 * 			The time for which the x,y-coordinates have to be calculated.
	 * @return result
	 * 			The array which holds the x,y-coordinates of this worm at time t in its jump.
	 */
	public double[] getJumpStep(double t) {
		double initVelocityX = this.getInitialVelocity()
				* Math.cos(this.getOrientation());
		double initVelocityY = this.getInitialVelocity()
				* Math.sin(this.getOrientation());
		double Xt = this.getX() + (initVelocityX * t);
		double Yt = this.getY()
				+ ((initVelocityY * t) - (.5 * this.g * Math.pow(t, 2)));
		double[] result = { Xt, Yt };
		return result;
	}

	/**
	 * Function that calculates the force of this worm to make a jump.
	 * 
	 * @return (5 * this.getActionPoints()) + (mass * g)
	 * 			The force of this worm to make a jump.
	 */
	public double getForce() throws ArithmeticException {
		return (5 * this.getActionPoints()) + (this.getMass() * this.g);
	}

	/**
	 * Function that calculates the initial velocity of the worm before he jumps.
	 * 
	 * @return (this.getForce() / mass) * .5
	 * 			The initial velocity of the worm before he jumps.
	 */
	public double getInitialVelocity() throws ArithmeticException {
		return (this.getForce() / this.getMass()) * .5;
	}

	// /**
	// * Function that calculates the distance this worm jumps.
	// *
	// * @return (Math.pow(this.getInitialVelocity(), 2) * Math.sin(2 *
	// this.getOrientation())) / g
	// * The distance this worm will jump.
	// */
	// public double getJumpDistance() {
	// return (Math.pow(this.getInitialVelocity(), 2) * Math.sin(2 * this
	// .getOrientation())) / this.g;
	// }

	/**
	 * Function that returns the selected weapon of this worm (as a string, i.e. the name of the weapon).
	 * 
	 * @return "Rifle"
	 * 			This function returns "Rifle" when the projectile this worm is using is an instance of
	 * 			the class Rifle
	 * @return "Bazooka"
	 * 			This function returns "Bazooka" when the projectile this worm is using is an instance 
	 * 			of the class Bazooka
	 * @return null
	 * 			No weapon is selected
	 */
	public String getSelectedWeapon() {
		return this.getProjectile().getWeaponName();
	}

	/**
	 * Function that returns the team name of this worm.
	 * 
	 * @return team.getTeamName()
	 * 			This worm's team name
	 */
	public String getTeamName() {
		try {
			return this.team.getTeamName();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Projectile getProjectile() {
		return this.collectionOfWeapons.get(this.indexOfCurrentWeapon);
	}

	/**
	 * Function that changes the radius of this worm, if the given radius is valid.
	 * The mass has to be changed accordingly.
	 * 
	 * @param newRadius
	 * 			The new radius of this worm.
	 * @post	The radius has to be changed to the given radius.
	 * 			| (new this).getRadius() == newRadius
	 * @post	The mass of this worm has to be changed accordingly.
	 * 			| (new this).getMass() == this.adjustMass(newRadius).getMass()
	 * @throws	IllegalArgumentException
	 * 			The new radius or the accordingly changed mass is invalid and mustn't be assigned to the radius of this worm.
	 * 			| !isValidRadius(newRadius) || !isValidMass(adjustMass(newRadius).getMass())
	 */
	public void setRadius(double newRadius) throws IllegalArgumentException {
		try {
			setMass(newRadius);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
		if (!isValidRadius(newRadius))
			throw new IllegalArgumentException();
		this.radius = newRadius;
		this.setMass(newRadius);
	}
	
	/**
	 * Function that adjusts the mass of this worm when the radius has been changed.
	 * The maximum amount of action points has to be changed accordingly.
	 * 
	 * @param newRadius
	 * 			The new radius, to which the mass has to be adjusted.
	 * @post	The mass has been changed to the new mass, according to the given formula, with the new radius.
	 * 			| (new this).getMass() == density * ( (4.0 / 3.0) * Math.PI * Math.pow(newRadius, 3))
	 * @post	The maximum amount of action points has changed accordingly.
	 * 			| (new this).getMaxActionPoints() == this.adjustMaxActionPoints(mass).getMaxActionPoints()
	 * @throws 	IllegalArgumentException
	 * 			The new mass is invalid and the mass shouldn't be changed.
	 * 			| !isValidMass(density * ( (4.0 / 3.0) * Math.PI * Math.pow(newRadius, 3)))
	 */
	public void setMass(double newRadius) throws IllegalArgumentException {
		if (!(isValidMass(this.density
				* ((4.0 / 3.0) * Math.PI * Math.pow(newRadius, 3)))))
			throw new IllegalArgumentException();
		this.mass = this.density
				* ((4.0 / 3.0) * Math.PI * Math.pow(newRadius, 3));
		this.setMaxActionPoints(this.getMass());
		this.setMaxHitPoints(this.getMass());
	}

	public void setMaxHitPoints(double newMass) {
		if ((int) Math.round(newMass) >= 0) {
			this.maxHitPoints = (int) Math.round(mass);
			this.hitPoints = Math.min(this.getMaxHitPoints(),
					this.getHitPoints());
		}
	}

	/**
	 * Function that changes the maximum amount of Action Points according to the new mass.
	 * 
	 * @param mass
	 * 			The new mass to which the maximum action points have to be adjusted.
	 * @post	The maximum amount of action points has been changed to the mass of this worm, rounded to the nearest integer.
	 * 			| (new this).getMaxActionPoints() == (int) Math.round(mass)
	 * @post	The new maximum of action points isn't allowed to be negative.
	 * 			| (new this).getMaxActionPoints() >= 0
	 * @post	The current amount of Action Points has to be less than or equal to the maximum Action Points of this worm
	 * 			after decreasing the size of this worm.
	 * 			| (new this).getActionPoints() == Math.min(this.getActionPoints(), this.getMaxActionPoints())
	 */
	public void setMaxActionPoints(double mass) {
		if ((int) Math.round(mass) >= 0) {
			this.maxActionPoints = (int) Math.round(mass);
			this.actionPoints = Math.min(this.getMaxActionPoints(),
					this.getActionPoints());
		}
	}

	/**
	 * Function that renames this worm, if the new name is valid.
	 * 
	 * @param newName
	 * 			The new name of this worm.
	 * @post	The name of this worm has to be set to the new name, if it is a valid new name.
	 * 			| (new this).getName() == newName
	 * @throws	IllegalArgumentException
	 * 			The name isn't valid or the reference to it is null, an exception has to be thrown.
	 * 			| !isValidName(newName)
	 */
	public void rename(String newName) {
		try {
			isValidName(newName);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
		if (!isValidName(newName))
			throw new IllegalArgumentException();
		this.name = newName;
	}

	/**
	 * Function that adjusts the current Action Points of this worm.
	 * This method is only used for the JUnit test cases.
	 * 
	 * @param newActionPoints
	 * 			The new number of Action Points.
	 * @post	If the new amount of Action Points is valid, the current amount of Action Points of this worm has 
	 * 			to be equal to the given new amount of Action Points.
	 * 			| if (0 <= newActionPoints && newActionPoints <= this.getMaxActionPoints())
	 * 			|	then
	 * 			| 		(new this).getActionPoints() == newActionPoints
	 * @post	If the new amount of Action Points is less than 0, this worm's Action Points have to be set to 0.
	 * 			| if (newActionPoints < 0)
	 * 			|	then ((new this).getActionPoints() == 0)
	 */
	public void setActionPoints(int newActionPoints) {
		if (0 <= newActionPoints
				&& newActionPoints <= this.getMaxActionPoints())
			this.actionPoints = newActionPoints;
		if (newActionPoints < 0)
			this.actionPoints = 0;
	}

	/**
	 * Function which sets the Hit Points of this worm to the provided amount of Hit Points, if the provided amount is valid.
	 * 
	 * @param newHitPoints
	 * 			The new amount of Hit Points of this worm
	 * @post	If the new amount of Hit Points lies in the interval [0, this.getMaxHitPoints()], the current Hit Points will be set to the new amount of Hit Points.
	 * 			| if (0 <= newHitPoints && newHitPoints <= this.getMaxHitPoints())
				|	then ((new this).getHitPoints() == newHitPoints)
	 * @post	If the new amount of Hit Points is less than 0, the current amount should be set to 0.
	 * 			| if (newHitPoints < 0)
	 * 			|	then ((new this).getHitPoints() == 0)
	 */
	public void setHitPoints(int newHitPoints) {
		if (0 <= newHitPoints && newHitPoints <= this.getMaxHitPoints())
			this.hitPoints = newHitPoints;
		if (newHitPoints < 0) {
			this.hitPoints = 0;
			this.getWorld().removeWormFromWorld(this);;
		}
	}

	public void setProjectile(Projectile newProjectile) {
		this.projectile = newProjectile;
	}

	/**
	 * Function that gives back the angle's equal in the interval of [0, 2 * Math.PI[.
	 * At first, the given angle has to be recalculated to a positive angle.
	 * 
	 * @param angle
	 * 			The angle that needs to be recalculated.
	 * @return angle % (2 * Math.PI)
	 * 			The angle in the interval [0, 2 * Math.PI[, if it isn't already in it.
	 */
	public double recalculateAngle(double angle) {
		assert (0 <= angle && angle < 2 * Math.PI);
		while (angle < 0)
			angle += (2 * Math.PI);
		return angle % (2 * Math.PI);
	}

	public void eatFood() {
		ArrayList<Food> eatFood = new ArrayList<Food>();
		for (Food checkFoodOverlaps : this.getWorld().getFood()) {
			if (Math.sqrt(Math.pow((this.getX() - checkFoodOverlaps.getX()), 2)
					+ Math.pow((this.getY() - checkFoodOverlaps.getY()), 2)) < this
					.getRadius() + Food.radius)
				eatFood.add(checkFoodOverlaps);
		}
		if (eatFood.size() > 0) {
			for (Food eatThisFood : eatFood) {
				this.setRadius(this.getRadius() + this.getRadius() * .1);
				this.getWorld().removeFoodFromWorld(eatThisFood);
			}
		}
	}

	/**
	 * Function that adds a worm to this team.
	 * 
	 * @param team
	 * 			The team to which this worm has to be added
	 * @post	The provided worm has to be a part of this team
	 * 			| (new this).teamMembers.contains(worm) == true
	 * @throws	IllegalArgumentException
	 * 			The team has to be a valid worm, i.e. it exists.
	 * 			| team == null
	 */
	public void addToTeam(Team team) throws IllegalArgumentException {
		if (team == null)
			throw new IllegalArgumentException();
		team.addTeamMember(this);
	}

	/**
	 * Method that selects the next weapon in the ArrayList of weapons.
	 */
	public void selectNextWeapon() {
		this.indexOfCurrentWeapon = (this.collectionOfWeapons.indexOf(this
				.getProjectile()) + 1) % this.collectionOfWeapons.size();
		this.setProjectile(this.collectionOfWeapons
				.get(this.indexOfCurrentWeapon));
	}

	/**
	 * Function that checks whether or not this worm can move in the direction its currently facing.
	 * For this to be possible, the worm has to have sufficient Action Points left to execute the 
	 * move and it also has to be facing in a direction in which the worm is able to move (i.e. it is
	 * not facing to any impassable terrain).
	 * 
	 * @return false
	 * 			The move cannot be made.
	 * @return true
	 * 			The move can be made.
	 */
	public boolean canMove() {
		double[] result = this.moveEvaluation();
		return (result[2] == 1 || result[2] == 2);
	}

	/**
	 * Function that executes the move of this worm, if it is allowed to (is able to) move.
	 */
	public void move() throws RuntimeException {
		if (!this.canMove())
			throw new RuntimeException();
		double[] moveEval = this.moveEvaluation();
		this.x = this.getX() + moveEval[1] * Math.cos(moveEval[0]);
		this.y = this.getY() + moveEval[1] * Math.sin(moveEval[0]);
		this.setActionPoints(this.getActionPoints()
				- (int) Math.ceil(this.getTotalStepCostForMove(moveEval[0])));
		if (!this.getWorld().liesInWorld(this.getX(), this.getY(), this.getRadius()))
			this.getWorld().removeWormFromWorld(this);
	}

	/**
	 * Function that checks whether or not a worm can turn over a certain angle (in radians).
	 * The angle must be lying in the interval [0, 2 * Math.PI[.
	 * This worm also has to have enough action points to turn, otherwise, he can't execute it.
	 * 
	 * @param angle
	 * 			The angle for which has to be checked whether this worm can turn over it or not.
	 * @pre	The angle has to be lying in the interval [0, 2 * Math.PI[, if the value is outside this interval,
	 * 		it should have been shrunk down to its equal in that interval.
	 * 		| (0 <= (angle % 2 * Math.PI)) && ((angle % 2 * Math.PI) < 2 * Math.PI)
	 * @return false
	 * 			This worm can't turn over the given angle.
	 * @return true
	 * 			This worm is able to turn over the given angle.
	 */
	public boolean canTurn(double angle) {
		return (this.getActionPoints() >= Math.round(60 / (int) Math.round(2
				* Math.PI / recalculateAngle(angle))));
	}

	/**
	 * Function that makes the worm turn over a certain angle.
	 * This worm has to be able to turn over the given angle. If he isn't able to do so, nothing should be happening.
	 * 
	 * @param angle
	 * 			The angle over which the worm has to be turning.
	 * @pre	This worm has to be able to turn over the specified angle. If he isn't able to do this, nothing should happen.
	 * 		| this.canTurn((angle % 2 * Math.PI)) == true
	 * @post	The direction has to change to the new direction.
	 * 			| (new this).getOrientation() == this.recalculateOrientation(this.recalculateOrientation(angle) + this.getOrientation())
	 * @post	The current amount of Action Points have to be edited.
	 * 			| (new this).getActionPoints() == this.getActionPoints() - Math.abs(Math.round(60 / f))
	 */
	public void turn(double angle) {
		assert (!canTurn(recalculateAngle(angle)));
		int f;
		this.direction = recalculateAngle(recalculateAngle(angle)
				+ this.getOrientation());
		f = (int) Math.round(2 * Math.PI / recalculateAngle(angle));
		this.actionPoints -= Math.abs(Math.round(60 / f));
	}

	/**
	 * Function that returns whether or not this worm can jump or not.
	 * Function that checks whether or not the worm's orientation is an element of [0, Math.PI].
	 * 
	 * @return true
	 * 			The condition for this worm to make a jump, is fulfilled (Math.sin(this.getOrientation()) has to be greater than 0, i.e. quadrants I and II).
	 * @return false
	 * 			The condition for this worm to make a jump, is not fulfilled.
	 */
	public boolean canJump() {
		if (this.getActionPoints() <= 0)
			return false;
		if (this.getWorld().isImpassable(this.getX(), this.getY(),
				this.getRadius()))
			return false;
		return true;
	}

	/**
	 * Function that makes this worm jump in the direction he's currently facing.
	 * The worm has to have some action points left, otherwise he cannot make the jump.
	 * All action points are being consumed when a jump is executed.
	 * This worm cannot be facing downwards when jumping.
	 * 
	 * @param timeStep 
	 * 			...
	 * @post	All the worm's action points are being used to jump.
	 * 			| (new this).getActionPoints() == 0
	 * @post	The location of the worm is updated, if it is possible.
	 * 			| (new this).getX() == this.getX() + getJumpDistance()
	 * @throws	ArithmeticException
	 * 			The orientation of the worm has to be in the range [0, Math.PI].
	 * 			| Math.sin(this.getOrientation()) <= 0
	 * @throws	ArithmeticException
	 * 			The worm has to be able to make the jump, i.e. the new x-coordinate has to be valid.
	 * 			| !isValid(this.getX() + getJumpDistance())
	 */
	public void jump(double timeStep) throws ArithmeticException {
		double[] jumpStep = new double[2];
		if (!canJump())
			throw new ArithmeticException();
		jumpStep = this.getJumpStep(this.getJumpTime(timeStep));
		this.x = jumpStep[0];
		this.y = jumpStep[1];
		this.actionPoints = 0;
		this.eatFood();
		if (!this.getWorld().liesInWorld(this.getX(), this.getY(), this.getRadius()))
			this.getWorld().removeWormFromWorld(this);
	}

	/**
	 * Method that checks whether or not this worm can fall.
	 * 
	 * @return true
	 * 			This worm can fall
	 * @return false
	 * 			This worm can't fall
	 */
	public boolean canFall() {
		if(this.isTerminated()) {
			this.getWorld().startNextTurn();
			return false;
		}
		return !(this.getWorld().isAdjacent(this.getX(), this.getY(),
				this.getRadius()));
	}

	/**
	 * Method that makes this worm fall down, until it hits some impassable terrain.
	 */
	public void fall() {
		if (this.canFall()) {
			double oldY = this.y;
			while (!this.getWorld().isAdjacent(this.getX(), this.getY(),
					this.getRadius())) {
				this.y -= Util.DEFAULT_EPSILON;
				if (!(0.0 <= this.getY() && this.getY() <= this.getWorld()
						.getHeight())) {
					this.terminate();
					return;
				}
			}
			this.hitPoints -= 3 * (int) Math.floor(oldY - this.getY());
		}
		this.eatFood();
	}

	public boolean canShoot() {
		if (this.getActionPoints() >= this.getProjectile()
				.getActionPointsCost()
				&& !(this.getWorld().isImpassable(this.getX(), this.getY(),
						this.getRadius())))
			return true;
		return false;
	}

	/**
	 * Method that makes this worm shoot.
	 * 
	 * @param yield
	 * 			The yield with which this worm fires the weapon.
	 */
	public void shoot(int yield) {
		if (!(0 <= yield && yield <= 100))
			throw new IllegalArgumentException();
		this.getProjectile().setForce(yield);
		if (!canShoot())
			throw new ArithmeticException();
			this.getProjectile().setX(
					this.getX() + Math.cos(this.getOrientation())
							* this.getRadius());
			this.getProjectile().setY(
					this.getY() + Math.sin(this.getOrientation())
							* this.getRadius());
		this.getProjectile().jump(.0001); // timeStep from jump.
		this.setActionPoints(this.getActionPoints()
				- this.getProjectile().getActionPointsCost());
	}

	/**
	 * Function that checks whether a certain x-coordinate is valid or not.
	 * Valid x-coordinates are those that are an element of ]Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY[.
	 * 
	 * @param x
	 * 			The x-coordinate of this worm that needs to be checked.
	 * @return false
	 * 			The given x-coordinate is invalid.
	 * @return true
	 * 			The given x-coordinate is valid.
	 */
	public static boolean isValidX(double x) {
		if (!Double.isNaN(x))
			return (lowerBoundX <= x && x <= upperBoundX);
		return false;
	}

	/**
	 * Function that checks whether a certain y-coordinate is valid or not.
	 * Valid y-coordinates are those that are an element of ]Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY[.
	 * 
	 * @param y
	 * 			The y-coordinate of this worm that has to be checked.
	 * @return false
	 * 			The given y-coordinate is invalid.
	 * @return true
	 * 			The given y-coordinate is valid.
	 */
	public static boolean isValidY(double y) {
		if (!Double.isNaN(y))
			return (lowerBoundY <= y && y <= upperBoundY);
		return false;
	}

	/**
	 * Function that checks whether the mass of this worm is valid or not.
	 * A mass can never be negative or 0.
	 * 
	 * @param mass
	 * 			The mass of this worm that has to be checked.
	 * @return false
	 * 			The mass of this worm is invalid: the mass of this worm is less than or equal to 0.
	 * @return true
	 * 			The mass of this worm is valid: the mass of this worm is greater than 0.
	 */
	public static boolean isValidMass(double mass) {
		if (!Double.isNaN(mass))
			return mass > lowerBoundMassExcluded;
		return false;
	}

	/**
	 * Function that checks whether the name of this worm is valid or not.
	 * The naming guidelines are as follows:
	 * - The name has to be at least 2 characters long;
	 * - The name must start with an uppercase letter;
	 * - The name can contain " and ';
	 * - The name can contain uppercase and lowercase letters;
	 * - The name may contain spaces.
	 * - The name may contain numbers.
	 * 
	 * @param name
	 * 			The name of this worm that has to be checked.
	 * @return false
	 * 			The name of this worm is invalid: it doesn't follow the naming guidelines.
	 * @return true
	 * 			The name of this worm is valid: it follows the naming guidelines.
	 * @throws	NullPointerException
	 * 			The reference to the name of this worm can't be null. If it is, an exception has to be thrown.
	 * 			| name == null
	 */
	public static boolean isValidName(String name) throws NullPointerException {
		if (name == null)
			throw new NullPointerException();
		if (name.length() >= 2 && Character.isUpperCase(name.charAt(0))) {
			for (int i = 0; i < name.length(); i++) {
				if (!(name.charAt(i) == '\'') && !(name.charAt(i) == '\"')
						&& !(name.charAt(i) == ' ')
						&& !(Character.isUpperCase(name.charAt(i)))
						&& !(Character.isLowerCase(name.charAt(i)))
						&& !(Character.isDigit(name.charAt(i)))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Function that checks whether the radius of this worm is valid or not.
	 * The radius of this worm has to be at least (greater than or equal to (fuzzy)) .25 meters at all times.
	 * 
	 * @param radius
	 * 			The radius of this worm that has to be checked.
	 * @return false
	 * 			The radius of this worm is invalid: it's less than .25 meters.
	 * @return true
	 * 			The radius of this worm is valid: it's greater than or equal to .25 meters.
	 */
	public static boolean isValidRadius(double radius) {
		if (!(Double.isNaN(radius)))
			return radius > minimalRadius;
		return false;
	}

	/**
	 * Function that terminates this worm (i.e. he died).
	 * Everything related to this worm will be deleted.
	 * 
	 * @post	The numerical properties (x, y, direction, actionPoints, maxActionPoints, hitPoints, maxHitPoints, radius and mass) of this worm are all set to 0.
	 * 			The boolean properties (useRifle, useBazooka) are set to false.
	 * 			The name of the worm is set to the null reference.
	 * 			| (new this).getX() == 0 && (new this).getY() == 0 && (new this).getOrientation() == 0 &&  
	 * 			|	(new this).getActionPoints() == 0 && (new this).getMaxActionPoints() == 0 && (new this).getHitPoints() == 0 && (new this).getMaxHitPoints() == 0 && 
	 * 			|		(new this).getRadius() == 0 && (new this).getMass == 0 && (new this).getName() == null
	 */
	public void terminate() {
		for (Projectile weapon : this.collectionOfWeapons) {
			weapon.terminate();
			this.collectionOfWeapons.remove(weapon);
		}
		this.getProjectile().terminate();
		this.setProjectile(null);
		this.team = null;
		this.isTerminated = true;
	}

	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Function that checks whether or not this worm is still alive or not.
	 * 
	 * @return true
	 * 			This worm is still alive.
	 * @return false
	 * 			This worm is dead.
	 */
	public boolean isAlive() {
		return (this.getHitPoints() > 0 && !this.isTerminated());
	}

	/**
	 * Method which initializes the weapon collection of this worm. It also sets the standard weapon
	 * of this worm to the first weapon in the collection.
	 */
	private void initWeapons() {
		this.collectionOfWeapons.add(new Rifle(this));
		this.collectionOfWeapons.add(new Bazooka(this));
		this.setProjectile(this.collectionOfWeapons.get(0));
		this.indexOfCurrentWeapon = 0;
	}

	/**
	 * Function that checks all aspects to the worms location for all possible ending points.
	 * It returns a summary array, which contains the information for this worm that describes 
	 * all possible outcomes of the move this worm can make.
	 * 
	 * @return sampleMatrixSummary
	 * 			An array containing all information about the possible outcomes of this worm's move 
	 */
	private double[] moveEvaluation() {
		double divergenceSample = .7875;
		double angleStepSize = .0175;
		double distanceStepSize = .01;
		int maxCounter = (int) Math
				.ceil(2 * (divergenceSample / angleStepSize) + 1);
		double[][] sampleMatrix = new double[maxCounter][5];
		// sampleMatrix[][0] is for storing the angle.
		// sampleMatrix[][1] is for storing whether or not the actual
		// Action Points of this worm are sufficient to execute the move at the
		// angle
		// (0 == enough Action Points available, 1 == insufficient Action Points
		// available).
		// sampleMatrix[][2] is for storing whether or not (1 == true, 0 ==
		// false) the move always gets in impassable terrain for an angle.
		// sampleMatrix[][3] is for storing whether or not (1 == true, 0 ==
		// false) the move always gets in passable terrain for an angle.
		// sampleMatrix[][4] is for storing the largest distance found to
		// travel for an angle (standard: adjacent to impassable terrain,
		// but still passable).
		double[] sampleMatrixSummary = new double[3];
		// sampleMatrixSummary collects the consolidated information for
		// all angles of sampleMatrix.
		// sampleMatrixSummary[2] is 1 if this worm can move in adjacent area.
		// sampleMatrixSummary[2] is 2 of this worm can move in passable area
		// and should fall.
		// sampleMatrixSummary[2] is 3 if this worm can't move (impassable or
		// not enough AP).
		for (int counter = 0; counter < maxCounter; counter++) {
			double angle = this.getOrientation() - divergenceSample + counter
					* angleStepSize;
			sampleMatrix[counter][0] = angle;
			if (this.getActionPoints() < this.getTotalStepCostForMove(angle)) {
				sampleMatrix[counter][1] = 1; // Not enough Action Points
												// available.
				continue;
			}
			if (this.getWorld().isImpassable(
					this.getX() + Math.cos(angle) * .1,
					this.getY() + Math.sin(angle) * .1, this.getRadius())) {
				sampleMatrix[counter][2] = 1;
				continue;
			}
			int counterDistanceMax = (int) Math.floor((this.getRadius() - .1) / distanceStepSize);
			for (int counterDistance = 0; counterDistance <= counterDistanceMax; counterDistance ++) {
				double distance = .1 + counterDistance * distanceStepSize;
				double newX = this.getX() + Math.cos(angle) * distance;
				double newY = this.getY() + Math.sin(angle) * distance;
				if (this.getWorld().isAdjacent(newX, newY, this.getRadius())) {
					sampleMatrix[counter][4] = distance;
					sampleMatrix[counter][3] = 0;
				} else if (this.getWorld().isImpassable(newX, newY, this.getRadius()))
					break;
				if (sampleMatrix[counter][4] == 0 && counterDistance == counterDistanceMax)
					sampleMatrix[counter][3] = 1;
			}
		}
		sampleMatrixSummary[0] = 0;
		sampleMatrixSummary[1] = 0;
		sampleMatrixSummary[2] = 0;
		boolean verifyAP = true;
		// Verify whether the move is impossible, because of insufficient AP
		// available.
		for (int counter = 0; counter < maxCounter; counter++) {
			if (sampleMatrix[counter][1] == 0) {
				verifyAP = false;
				break;
			}
		}
		if (verifyAP) {
			sampleMatrixSummary[2] = 3;
			return sampleMatrixSummary;
		}
		// Verify whether the move is impossible, because of being impassable in
		// all
		// directions.
		boolean verifyImp = true;
		for (int counter = 0; counter < maxCounter; counter++) {
			if (sampleMatrix[counter][2] == 0) {
				verifyImp = false;
				break;
			}
		}
		if (verifyImp) {
			sampleMatrixSummary[2] = 3;
			return sampleMatrixSummary;
		}
		// Verify whether move of this worm is passable (but not adjacent) in
		// all
		// directions.
		boolean verifyPass = true;
		for (int counter = 0; counter < maxCounter; counter++) {
			if (sampleMatrix[counter][3] == 0) {
				verifyPass = false;
				break;
			}
		}
		if (verifyPass) {
			sampleMatrixSummary[0] = this.getOrientation();
			sampleMatrixSummary[1] = this.getRadius();
			sampleMatrixSummary[2] = 2;
			return sampleMatrixSummary;
		}
		// Calculate new position in case of adjacent.
		sampleMatrixSummary[0] = sampleMatrix[0][0];
		sampleMatrixSummary[1] = 0;
		for (int counter = 0; counter < maxCounter; counter++) {
			if (sampleMatrix[counter][1] == 1 || sampleMatrix[counter][2] == 1
					|| sampleMatrix[counter][3] == 1)
				continue;
			if (sampleMatrix[counter][4] > sampleMatrixSummary[1]) {
				sampleMatrixSummary[1] = sampleMatrix[counter][4];
				sampleMatrixSummary[0] = sampleMatrix[counter][0];
			} else if (sampleMatrix[counter][4] == sampleMatrixSummary[1]) {
				if (Math.abs(this.getOrientation() - sampleMatrix[counter][0]) < Math
						.abs(this.getOrientation() - sampleMatrixSummary[0]))
					sampleMatrixSummary[0] = sampleMatrix[counter][0];
			}
		}
		sampleMatrixSummary[2] = 1;
		return sampleMatrixSummary;
	}

	private boolean isJumpFinished(double newX, double newY) {
		boolean wormLiesInWorld = this.getWorld().liesInWorld(newX, newY,
				this.getRadius());
		boolean wormHitsAdjTerrain = this.getWorld().isAdjacent(newX, newY,
				this.getRadius());
		return (!wormLiesInWorld || wormHitsAdjTerrain);
	}
}
