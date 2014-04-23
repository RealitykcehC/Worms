package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import worms.model.Projectile;
import worms.model.Worm;

/**
 * This class extends the Projectile class, it is a specific case of that class.
 * This class implements the methods specific to the Bazooka of this game. All methods of Projectile are in here by standard and don't have to be written again, unless they 
 * have to be overridden.
 * The properties specific to the bazooka are also declared in this class.
 * 
 * @author Pieter Jan vingerhoets & Matthijs Nelissen
 * @version 1.0
 */
public class Bazooka extends Projectile {
	/**
	 * Declaration of variables.
	 */
	private final int ACTION_POINTS_COST = 50;
	private final int HIT_POINTS_REDUCE = 80;
	private final double MASS = .3;
	private final String WEAPON_NAME = "Bazooka";
	private double force;
	private double lowerForce = 2.5, upperForce = 9.5;

	/**
	 * Constructor of the class Bazooka.
	 * The constructor calls on the constructor of the superclass to be created.
	 * 
	 * @param worm
	 * 			The worm that has to shoot
	 * @post	The given worm must be equal to the worm that has to shoot.
	 * 			| (new this).getWorm() == worm
	 * @post	The given x must be equal to the x-coordinate of the worm that is being created.
	 * 			| (new this).getX() == worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())
	 * @post	The given y must be equal to the y-coordinate of the worm that is being created.
	 * 			| (new this).getY() == worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius())
	 * @post	The given direction has to be equal to the orientation of the worm that is being created.
	 * 			The given direction first has to be recalculated to an angle in the interval [0, 2 * Math.PI[
	 * 			| (new this).getOrientation() == worm.recalculateOrientation(worm.getOrientation())
	 * @throws	IllegalArgumentException
	 * 			The x- and y-coordinate of this projectile have to be valid x- and y-coordinates. If they are not, an exception has to be thrown.
	 * 			The functions concerning checking valid x- and y-coordinates are written in the class Worm.
	 * 			| !Worm.isValidX(worm.getX() + (Math.cos(worm.getOrientation()) * worm.getRadius())) || 
	 *			|	!Worm.isValidY(worm.getY() + (Math.sin(worm.getOrientation()) * worm.getRadius()))
	 */
	public Bazooka(Worm worm) {
		super(worm);
	}

	/**
	 * Function which returns the cost of Action Points to shoot with the bazooka.
	 * 
	 * @return this.ACTION_POINTS_COST
	 * 			The cost of Action Points to shoot the bazooka
	 */
	@Override
	@Basic
	public int getActionPointsCost() {
		return this.ACTION_POINTS_COST;
	}

	/**
	 * Function which returns the amount of Hit Points that have to be subtracted from the current amount of Hit Points of the worm that is hit by the rocket.
	 * 
	 * @return this.HIT_POINTS_REDUCE
	 * 			The amount of Hit Points that have to be subtracted from the amount of Hit Points of the worm that is hit.
	 */
	@Override
	@Basic
	public int getHitPointsReduction() {
		return this.HIT_POINTS_REDUCE;
	}

	/**
	 * Function which returns the mass of the rocket from a bazooka.
	 * 
	 * @return this.MASS
	 * 			The mass of the rocket
	 */
	@Override
	@Basic
	public double getMass() {
		return this.MASS;
	}
	
	/**
	 * Function that returns the name of this weapon.
	 * 
	 * @return this.WEAPON_NAME
	 * 			The name of this weapon
	 */
	@Basic
	@Override
	public String getWeaponName() {
		return this.WEAPON_NAME;
	}
	
	/**
	 * Function which returns the initial force of the rocket from the bazooka, just before its launch.
	 * 
	 * @return this.force
	 * 			The initial force of the rocket of the bazooka.
	 */
	@Override
	public double getForce() {
		return this.force;
	}
	
	/**
	 * Function that calculates and returns the radius of a bazooka rocket.
	 * 
	 * @return radius
	 * 			The radius of the rocket
	 */
	@Override
	public double getRadius() {
		return Math.cbrt((3 * (MASS / DENSITY)) / (4 * Math.PI));
	}
	
	/**
	 * Function that sets the force to the correct force, using the yield that is determined when the worm wants to shoot.
	 * 
	 * @post	The force has to be set to the correct amount, using the propulsion yield from the shoot.
	 * 			| (new this).getForce() == this.lowerForce + (this.upperForce - this.lowerForce) * (yield / 100)
	 */
	@Override
	public void setForce(int yield) {
		this.force = this.lowerForce + (this.upperForce - this.lowerForce) * (yield / 100);
	}
}
