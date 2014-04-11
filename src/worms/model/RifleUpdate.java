package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import worms.model.Projectile;
import worms.model.Worm;

/**
 * This class extends the Projectile class, it is a specific case of that class.
 * This class implements the methods specific to the Rifle of this game. All methods of Projectile are in here by standard and don't have to be written again, unless they 
 * have to be overridden.
 * The properties specific to the rifle are also declared in this class.
 * 
 * @invar	The mass of the rifle projectile has to be valid at all times during the execution of the game. This condition is not imposed in the superclass, because it only 
 * 			has dummy implementations of the functions, with some invalid properties. Those invalid properties are made concrete in the subclasses. A worm will never be able 
 * 			to shoot an instance of the class Projectile, only of the subclasses of Projectile.
 * 			The class Worm already has a good implementation of the method required to check this.
 * 			| Worm.isValidMass(this.getMass())
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 0.1a
 */
public class Rifle extends Projectile {
	/**
	 * Declaration of variables.
	 */
	private final int actionPointsCost = 10;
	private final int hitPointsReduce = 20;
	private final double mass = 0.01;
	private final double force = 1.5;
	private final String weaponName = "Rifle";

	/**
	 * Constructor of the class Rifle.
	 * The constructor calls on the constructor of the superclass to be created.
	 * 
	 * @param worm
	 * 			The worm that has to shoot
	 */
	public Rifle(Worm worm) {
		super(worm);
	}

	/**
	 * Function which returns the cost of Action Points to shoot with the rifle.
	 * 
	 * @return this.actionPointsCost
	 * 			The cost of Action Points to shoot the rifle
	 */
	@Override
	@Basic
	public int getActionPointsCost() {
		return this.actionPointsCost;
	}

	/**
	 * Function which returns the amount of Hit Points that have to be subtracted from the current amount of Hit Points of the worm that is hit by the rifle bullet.
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
	 * Function which returns the mass of the rifle bullet.
	 * 
	 * @return this.mass
	 * 			The mass of the bullet
	 */
	@Override
	@Basic
	public double getMass() {
		return this.mass;
	}

	/**
	 * Function which returns the initial force of the bullet from a rifle, just before its launch.
	 * 
	 * @return this.force
	 * 			The initial force of the bullet of a rifle.
	 */
	@Basic
	@Override
	public double getForce() {
		return this.force;
	}

	/**
	 * Function that returns the name of this weapon.
	 * 
	 * @return this.weaponName
	 * 			The name of this weapon
	 */
	@Override
	public String getWeaponName() {
		return this.weaponName;
	}
}
