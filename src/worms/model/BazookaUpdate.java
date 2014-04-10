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
 * @invar	The mass of the rifle projectile has to be valid at all times during the execution of the game. This condition is not imposed in the superclass, because it only 
 * 			has dummy implementations of the functions, with some invalid properties. Those invalid properties are made concrete in the subclasses. A worm will never be able 
 * 			to shoot an instance of the class Projectile, only of the subclasses of Projectile.
 * 			The class Worm already has a good implementation of the method required to check this.
 * 			| Worm.isValidMass(this.getMass())
 * @author Pieter Jan vingerhoets & Matthijs Nelissen
 * @version 0.1a
 */
public class Bazooka extends Projectile {
	/**
	 * Declaration of variables.
	 */
	private final int actionPointsCost = 50;
	private final int hitPointsReduce = 80;
	private final double mass = 0.3;
	private double force = 0; // TODO
	
	/**
	 * Constructor of the class Bazooka.
	 * The constructor calls on the constructor of the superclass to be created.
	 * 
	 * @param worm
	 * 			The worm that has to shoot 
	 */
	public Bazooka(Worm worm) {
		super(worm);
	}
	
	/**
	 * Function which returns the cost of Action Points to shoot with the bazooka.
	 * 
	 * @return this.actionPointsCost
	 * 			The cost of Action Points to shoot the bazooka
	 */
	@Override
	@Basic
	public int getActionPointsCost() {
		return this.actionPointsCost;
	}
	
	/**
	 * Function which returns the amount of Hit Points that have to be subtracted from the current amount of Hit Points of the worm that is hit by the rocket.
	 * 
	 * @return this.hitPointsReduce
	 * 			The amount of Hit Points that have to be subtracted from the amount of Hit Points of the worm that is hit.
	 */
	@Override
	@Basic
	public int getHitPointsReduction() {
		return this.hitPointsReduce;
	}
	
	/**
	 * Function which returns the mass of the rocket from a bazooka.
	 * 
	 * @return this.mass
	 * 			The mass of the rocket
	 */
	@Override
	@Basic
	public double getMass() {
		return this.mass;
	}
	
	/**
	 * Function which returns the initial force of the rocket from the bazooka, just before its launch.
	 * 
	 * @return this.force
	 * 			The initial force of the rocket of the bazooka.
	 */
	@Basic
	@Override
	public double getForce() {
		return this.force;
	}
}
