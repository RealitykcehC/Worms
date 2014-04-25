package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class that implements all the aspects related to the projectiles in this game. This is the superclass for the subclasses Rifle and Bazooka.
 * The values of the properties for this class are dummy-values. This means that the values that are particular to a certain type of projectile (rifle bullet or bazooka) 
 * will be set in each of the subclasses, while the implementation of the functions is written in this class.
 * When necessary, particular functions in this class will be overridden in the subclasses.
 * 
 * @invar	The x-coordinate of this projectile has to be valid at all times during the execution of the game.
 * 			The class Worm already has a good implementation of this method.
 * 			| Worm.isValidX(this.getX())
 * @invar	The y-coordinate of this projectile has to be valid at all times during the execution of the game.
 * 			The class Worm already has a good implementation of this method.
 * 			| Worm.isValidY(this.getY())
 * @invar	The orientation of this projectile has to be valid at all times during the execution of the game. This means that the orientation of this projectile must lie in 
 * 			the interval [0, 2 * Math.PI[ at all times. This is automatically the case, since the orientation of the projectile is at all times the orientation of the worm
 * 			that has to shoot. The worm always has a valid orientation.
 * 			| 0 <= this.getWorm().getOrientation() && this.getWorm().getOrientation() < 2 * Math.PI
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 1.0
 */
public class Projectile {
	/**
	 * Declaration of variables.
	 */
	public final double DENSITY = 7800.0;
	private double x, y, direction;
	private int actionPointsCost = 0;
	private int hitPointsReduce = 0;
	private double mass = 0;
	private double force = 0;
	private double upperForce, lowerForce;
	private boolean isTerminated = false;
	private String weaponName = "Projectile";
	private Worm worm;

	/**
	 * Constructor of the class Projectile.
	 * 
	 * @param worm
	 * 			The worm to which this projectile belongs
	 * @post	The given worm must be equal to the worm that has to shoot.
	 * 			| (new this).getWorm() == worm
	 * @post	The x-coordinate has to be located at the radius of the worm to which this projectile belongs, while respecting the orientation of the worm.
	 * 			| (new this).getX() == worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())
	 * @post	The y-coordinate has to be located at the radius of the worm to which this projectile belongs, while respecting the orientation of the worm.
	 * 			| (new this).getY() == worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius())
	 * @post	The given direction has to be equal to the orientation of the worm.
	 * 			The given direction first has to be recalculated to an angle in the interval [0, 2 * Math.PI[
	 * 			| (new this).getOrientation() == worm.recalculateOrientation(worm.getOrientation())
	 * @throws	IllegalArgumentException
	 * 			The x- and y-coordinate of this projectile have to be valid x- and y-coordinates. If they are not, an exception has to be thrown.
	 * 			The functions concerning checking valid x- and y-coordinates are written in the class Worm.
	 * 			| !Worm.isValidX(worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())) || 
	 *			|	!Worm.isValidY(worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius()))
	 */
	public Projectile(Worm worm) throws IllegalArgumentException {
		this.worm = worm;
		if (!Worm.isValidX(this.getWorm().getX()
				+ (Math.cos(this.getWorm().getOrientation()) * this.getWorm()
						.getRadius()))
				|| !Worm.isValidY(worm.getY()
						+ (Math.sin(worm.getOrientation()) * worm.getRadius())))
			throw new IllegalArgumentException();
		this.x = worm.getX()
				+ (Math.cos(worm.getOrientation()) * worm.getRadius());
		this.y = worm.getY()
				+ (Math.sin(worm.getOrientation()) * worm.getRadius());
		this.direction = worm.recalculateAngle(worm.getOrientation());
	}

	/**
	 * Function which returns the x-coordinate of this projectile.
	 * 
	 * @return this.x
	 * 			The x-coordinate of this projectile
	 */
	@Basic
	public double getX() {
		return this.x;
	}

	/**
	 * Function which returns the y-coordinate of this projectile.
	 * 
	 * @return this.y
	 * 			The y-coordinate of this projectile
	 */
	@Basic
	public double getY() {
		return this.y;
	}

	/**
	 * Function which returns the current direction of this projectile.
	 * 
	 * @return this.direction
	 * 			The direction of this projectile
	 */
	@Basic
	public double getOrientation() {
		return this.direction;
	}

	/**
	 * Function which returns the worm that has to shoot (i.e. the worm whose turn it currently is).
	 * 
	 * @return this.worm
	 * 			The worm that has to shoot.
	 */
	@Basic
	public Worm getWorm() {
		return this.worm;
	}

	/**
	 * Function which returns the cost of Action Points to shoot with this projectile.
	 * 
	 * @return this.actionPointsCost
	 * 			The cost of Action Points to shoot this projectile
	 */
	@Basic
	public int getActionPointsCost() {
		return this.actionPointsCost;
	}

	/**
	 * Function which returns the amount of Hit Points that have to be subtracted from the current amount of Hit Points of the worm that is hit.
	 * 
	 * @return this.hitPointsReduce
	 * 			The amount of Hit Points that have to be subtracted from the amount of Hit Points of the worm that is hit.
	 */
	@Basic
	public int getHitPointsReduction() {
		return this.hitPointsReduce;
	}

	/**
	 * Function which returns the mass of this projectile.
	 * 
	 * @return this.mass
	 * 			The mass of this projectile
	 */
	@Basic
	public double getMass() {
		return this.mass;
	}
	
	/**
	 * Function that returns the name of this weapon.
	 * 
	 * @return this.weaponName
	 * 			The name of this weapon
	 */
	@Basic
	public String getWeaponName() {
		return this.weaponName;
	}
	
	/**
	 * Function which returns the initial force of this projectile, just before its launch.
	 * 
	 * @return this.force
	 * 			The initial force of this projectile.
	 */
	public double getForce() {
		return this.force;
	}

	/**
	 * Function that calculates and returns the radius of this projectile.
	 * 
	 * @return radius
	 * 			The radius of this projectile
	 */
	public double getRadius() {
		return Math.cbrt((3 * (mass / DENSITY)) / (4 * Math.PI));
	}

	/**
	 * Function which returns the initial velocity at launch of this projectile.
	 * 
	 * @return .5 * (this.getForce() / this.getMass())
	 * 			The initial velocity at launch of this projectile
	 */
	public double getInitialVelocity() {
		return (this.getForce() / this.getMass()) * .5;
	}

	/**
	 * Function that returns the time this projectile will travel before exploding.
	 * 
	 * @param timeStep 
	 * 			A time scale for which the projectile will not go through any impassable terrain
	 * @return totalJumpTime
	 * 			The time this projectile will travel before exploding.
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
		} while (!(this.isJumpFinished(newX, newY)));
		return totalJumpTime;
	}

	/**
	 * Function which returns an array which holds the (x,y)-coordinates of this projectile after being shot at time t.
	 * 
	 * @param t
	 * 			The time after being shot
	 * @return result
	 * 			An array which holds the (x,y)-coordinates of this projectile after being shot at time t
	 */
	public double[] getJumpStep(double t) {
		double initVelocityX = this.getInitialVelocity()
				* Math.cos(this.getOrientation());
		double initVelocityY = this.getInitialVelocity()
				* Math.sin(this.getOrientation());
		double Xt = this.getX() + (initVelocityX * t);
		double Yt = this.getY()
				+ ((initVelocityY * t) - (.5 * this.getWorm().GRAV_CST * Math
						.pow(t, 2)));
		double[] result = { Xt, Yt };
		return result;
	}

	/**
	 * Function which will define the correct force to fire this projectile, according to the given yield.
	 * 
	 * @param yield
	 * 			The propulsion yield with which this projectile has to be fired
	 * @post	The force must be calculated, according to the propulsion yield.
	 * 			| (new this).getForce() == this.lowerForce + (this.upperForce - this.lowerForce) * (yield / 100)
	 */
	public void setForce(int yield) {
		this.force = this.lowerForce + (this.upperForce - this.lowerForce) * (yield / 100);
	}
	
	/**
	 * Method that sets the orientation of this projectile to the given orientation.
	 * 
	 * @param orientation
	 * 			The new orientation for this projectile
	 * @post	The orientation of this projectile has been changed to the provided orientation.
	 * 			| (new this).getOrientation() == orientation
	 */
	public void setOrientation(double orientation) {
		this.direction = orientation;
	}

	/**
	 * Function that sets the x-coordinate of this projectile to the new x-coordinate, which is provided.
	 * 
	 * @param newX
	 * 			The newly provided x-coordinate
	 * @post	The x-coordinate of this projectile has to be set to the new x-coordinate.
	 * 			| (new this).getX() == newX
	 * @throws	IllegalArgumentException
	 * 			The new x-coordinate of this projectile has to be valid, otherwise an exception should be thrown.
	 * 			| !Worm.isValidX(newX)
	 */
	public void setX(double newX) throws IllegalArgumentException {
		if (!Worm.isValidX(newX))
			throw new IllegalArgumentException();
		this.x = newX;
	}

	/**
	 * Function which sets the y-coordinate of this projectile to the newly provided y-coordinate.
	 * 
	 * @param newY
	 * 			The new y-coordinate
	 * @post	The y-coordinate of this projectile has to be set to the new y-coordinate.
	 * 			| (new this).getY() == newY
	 * @throws	IllegalArgumentException
	 * 			The new y-coordinate of this projectile has to be valid, otherwise an exception has to be thrown.
	 * 			| !Worm.isValidY(newY)
	 */
	public void setY(double newY) throws IllegalArgumentException {
		if (!Worm.isValidY(newY))
			throw new IllegalArgumentException();
		this.y = newY;
	}

	/**
	 * Function that checks whether or not this projectile can be fired.
	 * 
	 * @return true
	 * 			This projectile can be fired
	 * @return false
	 * 			This projectile cannot be fired
	 */
	public boolean canJump() {
		if (this.getWorm().getActionPoints() - this.getActionPointsCost() < 0)
			return false;
		if (this.getWorm().getWorld().isImpassable(this.getWorm().getX(), this.getWorm().getY(),
				this.getWorm().getRadius()))
			return false;
		return true;
	}

	/**
	 * Function which fires this projectile.
	 * This projectile stops when it hits impassable terrain, leaves the game world or hits a worm.
	 * 
	 * @post	The x-coordinate of this projectile must be adjusted.
	 * 			| (new this).getX() == newX
	 * @post	The y-coordinate of this projectile must be adjusted.
	 * 			| (new this).getY() == newY
	 * @effect	If the projectile hits a worm, that worm's Hit Points have to be reduced (a worm cannot hit itself).
	 * 			| this.hitsWorm()
	 * @effect	At the end, this projectile has to be removed from the game world.
	 * 			| (new this).getWorm().getWorld().removeProjectileFromWorld(this)
	 * @throws	ArithmeticException
	 * 			This projectile cannot jump.
	 * 			| !this.canJump()
	 */
	public void jump(double timeStep) throws ArithmeticException {
		if (!canJump())
			throw new ArithmeticException();
		double newX = 0, newY = 0;
		double[] jumpStep = new double[2];
		double totalJumpTime = 0;
		do {
			jumpStep = this.getJumpStep(totalJumpTime);
			newX = jumpStep[0];
			newY = jumpStep[1];
			totalJumpTime += timeStep;
		} while (!(this.isJumpFinished(newX, newY)));
		this.getWorm().getWorld().removeProjectileFromWorld(this);
	}

	/**
	 * Function that handles the events that need to be handled when a worm is hit.
	 * 
	 * @post	The Hit Points of the worm that has been hit have to be adjusted, according to the number of Hit Points that should be removed for a certain projectile.
	 * 			| hitWorm.setHitPoints(hitWorm.getHitPoints() - this.getHitPointsReduction())
	 */
	public boolean hitsWorm(double x, double y) {
		for (Worm hitWorm : this.getWorm().getWorld().getWorms()) {
			if (hitWorm != this.getWorm() && Math.sqrt(Math.pow((x - hitWorm.getX()), 2)
					+ Math.pow((y - hitWorm.getY()), 2)) < this
					.getRadius() + hitWorm.getRadius()) {
				hitWorm.setHitPoints(hitWorm.getHitPoints() - this.getHitPointsReduction());
				return true;
			}
		}
		return false;
	}

	/**
	 * Method that checks whether or not this projectile is active (i.e. has not yet been terminated).
	 * 
	 * @return true
	 * 			This projectile is active
	 * @return false
	 * 			This projectile is no longer active.
	 */
	public boolean isActive() {
		return (!this.isTerminated());
	}

	/**
	 * Function that terminates this projectile.
	 * 
	 * @post	The reference to the worm has to be broken.
	 * 			| (new this).getWorm() == null
	 * @post	This projectile has to know it has been terminated.
	 * 			| (new this).isTerminated()
	 */
	public void terminate() {
		this.isTerminated = true;
	}

	/**
	 * Function that returns whether or not this projectile has been terminated.
	 * 
	 * @return true
	 * 			This projectile has been terminated
	 * @return false
	 * 			This projectile has not yet been terminated
	 */
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Function that returns whether or not the jump of this projectile is finished.
	 * 
	 * @param newX
	 * 			The x-coordinate for which has to be checked whether or not this projectile's jump is finished
	 * @param newY
	 * 			The y-coordinate for which has to be checked whether or not this projectile's jump is finished
	 * @return true
	 * 			This projectile's jump is finished
	 * @return false
	 * 			This projectile's jump is not yet finished
	 */
	public boolean isJumpFinished(double newX, double newY) {
		boolean projectileLiesInWorld = this.getWorm().getWorld().liesInWorld(newX, newY,
				this.getRadius());
		boolean projectileHitsImpTerrain = this.getWorm().getWorld().isImpassable(newX, newY,
				this.getRadius());
		boolean hasHitWorm = this.hitsWorm(newX, newY);
		return (!projectileLiesInWorld || projectileHitsImpTerrain || hasHitWorm);
	}
}
