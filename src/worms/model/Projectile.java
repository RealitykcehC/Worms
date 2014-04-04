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
 * @version 0.1a
 */
public class Projectile {
	/**
	 * Declaration of variables.
	 */
	public final double density = 7800.0;
	private double x, y, direction;
	private int actionPointsCost = 0; // Default cost, the amount will be specified in the subclasses.
	private Worm worm;
	private double mass = 0; // Default mass, the mass will be specified in the subclasses.
	private double force = 0; // Default force, the force will be specified in the subclasses.
	private int hitPointsReduce = 0; // Default Hit Points that need to be reduced. It will be specified in the subclasses.
	
	/**
	 * Constructor of the class Projectile.
	 * 
	 * @param worm
	 * 			The worm that has to shoot.
	 * @post	The given worm must be equal to the worm that has to shoot.
	 * 			| (new this).getWorm() == worm
	 * @post	The given x must be equal to the x-coordinate of the worm that is being created.
	 * 			| (new this).getX() == worm.getX()
	 * @post	The given y must be equal to the y-coordinate of the worm that is being created.
	 * 			| (new this).getY() == worm.getY()
	 * @post	The given direction has to be equal to the orientation of the worm that is being created.
	 * 			The given direction first has to be recalculated to an angle in the interval [0, 2 * Math.PI[
	 * 			| (new this).getOrientation() == worm.recalculateOrientation(worm.getOrientation())
	 * @throws	IllegalArgumentException
	 * 			The x- and y-coordinate of this projectile have to be valid x- and y-coordinates. If they are not, an exception has to be thrown.
	 * 			The functions concerning checking valid x- and y-coordinates are written in the class Worm.
	 * 			| !Worm.isValidX(worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())) || 
	 *			|	!Worm.isValidY(worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius()))
	 */
	public Projectile(Worm worm) throws IllegalArgumentException{
		this.worm = worm;
		if (!Worm.isValidX(worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())) || 
				!Worm.isValidY(worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius())))
			throw new IllegalArgumentException();
		this.x = worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius());
		this.y = worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius());
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
	 * Function which returns the initial force of this projectile, just before its launch.
	 * 
	 * @return this.force
	 * 			The initial force of this projectile.
	 */
	@Basic
	public double getForce() {
		return this.force;
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
	public void setY(double newY) throws IllegalArgumentException{
		if (!Worm.isValidY(newY))
			throw new IllegalArgumentException();
		this.y = newY;
	}
	
	/**
	 * Function which fires this projectile.
	 * The x-coordinate of this projectile is the starting x-coordinate added to the distance this projectile travels.
	 * The Action Points of the worm that shot this projectile has to be adjusted. This means that the cost to fire this projectile has to be subtracted from the worm's
	 * current amount of Action Points.
	 * When needed, the projectile will fall if it didn't hit anything yet.
	 * 
	 * @post	The x-coordinate of this projectile has to be adjusted. The new x-coordinate is equal to the old one added to the distance of the shot.
	 * 			| (new this).getX() == this.getX() + this.getJumpDistance()
	 * @post	Should the new amount of Action Points of the worm that fired become 0, the worm will be terminated (i.e. he dies).
	 * 			| if (this.getWorm().getActionPoints() == 0)
	 * 			|	then (this.getWorm().terminate())
	 * @throws	ArithmeticException
	 * 			The x-coordinate, after having traveled the distance of the shot, has to be valid.
	 * 			| !Worm.isValidX(this.getX() + this.getJumpDistance())
	 */
	public void jump() throws ArithmeticException {
		// TODO Hit an impassable object / worm? => Explode!
		if (!Worm.isValidX(this.getX() + this.getJumpDistance()))
			throw new ArithmeticException();
		this.setX(this.getX() + this.getJumpDistance());
		this.getWorm().setActionPoints(this.getWorm().getActionPoints() - this.getActionPointsCost());
		this.fall();
		if (this.getWorm().getActionPoints() == 0)
			this.getWorm().terminate();
	}
	
	/**
	 * Function that returns the time this projectile will travel before falling down or exploding (given that it didn't hit an impassable position already).
	 * 
	 * @return this.getJumpDistance() / (this.getInitialVelocity() * Math.cos(this.getOrientation()))
	 * 			The time this projectile will travel before falling down or exploding.
	 */
	public double getJumpTime() {
		return this.getJumpDistance() / (this.getInitialVelocity() * Math.cos(this.getOrientation()));
	}
	
	/**
	 * Function which returns an array which holds the (x,y)-coordinates of this projectile after being shot at time t.
	 * 
	 * @param t
	 * 			The time after being shot
	 * @return result
	 * 			An array which holds the (x,y)-coordinates of this projectile after being shot at time t
	 */
	public double [] getJumpStep(double t) {
		double initVelocityX = this.getInitialVelocity() * Math.cos(this.getOrientation());
		double initVelocityY = this.getInitialVelocity() * Math.sin(this.getOrientation());
		double Xt = this.getX() + (initVelocityX * t);
		double Yt = this.getY() + ((initVelocityY * t) - (.5 * this.getWorm().g * Math.pow(t, 2)));
		double [] result = {Xt,Yt};
		return result;
	}
	/**
	 * Function which calculates the distance of the jump (i.e. the shot). This is the distance that this projectile will travel before falling.
	 * 
	 * @return (Math.pow(this.getInitialVelocity(), 2) * Math.sin(2 * this.getOrientation())) / this.getWorm().g
	 * 			The distance this projectile travels after it has been shot
	 */
	public double getJumpDistance() {
		return (Math.pow(this.getInitialVelocity(), 2) * Math.sin(2 * this.getOrientation())) / this.getWorm().g;
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
	 * Function that makes this projectile fall, until it either hits another worm of it hits an impassable object.
	 */
	public void fall() {
		// TODO Implement this function.
	}
	
	/**
	 * Function that handles the events that need to be handled when a worm is hit.
	 * 
	 * @param hitWorm
	 * 			The worm that has been hit
	 * @post	The Hit Points of the worm that has been hit have to be adjusted, according to the number of Hit Points that should be removed for a certain projectile.
	 * 			| hitWorm.setHitPoints(hitWorm.getHitPoints() - this.getHitPointsReduction())
	 * @post	If the amount of Hit Points of the hit worm became 0 by hitting it, the hit worm will be terminated (i.e. he died).
	 * 			| if (!hitWorm.checkVitals())
	 * 			|	then (hitWorm.terminate())
	 */
	public void hit(Worm hitWorm) {
		hitWorm.setHitPoints(hitWorm.getHitPoints() - this.getHitPointsReduction());
		if (!hitWorm.checkVitals())
			hitWorm.terminate();
	}
}
