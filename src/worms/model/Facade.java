package worms.model;

import worms.model.Worm;

/**
 * A class that implements the Interface IFacade.
 * 
 * @author Pieter Jan Vingerhoets & Mathijs Nelissen
 * @version 1.0
 */
public class Facade implements IFacade{
	/**
	 * Function that creates a worm with given x,y-coordinates (in meters), a
	 * direction (expressed in an angle in radians), a radius (in meters) and a name.
	 * It also gives the worm the correct mass (in kg) and Action Points (and calculates the maximum Action Points).
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
	 * @throws	ModelException
	 * 			The given x has to be a valid x-coordinate, otherwise an exception has to be thrown.
	 * 			| !isValidX(x)
	 * @throws	ModelException
	 * 			The given y has to be a valid y-coordinate, otherwise an exception has to be thrown.
	 * 			| !isValidY(y)
	 * @throws 	ModelException
	 * 			The given radius has to be a valid radius, otherwise an exception should be thrown. 
	 * 			| !isValidRadius(radius)
	 * @throws 	ModelException
	 * 			The calculated mass has to be valid, otherwise an exception should be thrown.
	 * 			| !isValidMass(mass)
	 * @throws 	ModelException
	 * 			The given name should be valid, otherwise an exception has to be thrown.
	 * 			| !isValidName(name)
	 * @return worm
	 * 			The worm that has been created.
	 */
	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) throws ModelException{
		try {
			Worm worm = new Worm (x, y, direction, radius, name);
			return worm;
		} catch (IllegalArgumentException e) {
			throw new ModelException("An invalid argument was passed.");
		} catch (ArithmeticException e) {
			throw new ModelException("The calculated mass is invalid.");
		}
	}

	/**
	 * Function that checks whether or not a given worm can move a given number of steps in the direction the worm
	 * is currently facing. The distance the worm travels per step is equal to the radius of the worm.
	 * 
	 * @param worm
	 * 			The worm for which has to be checked whether or not it can move the number of steps in the direction
	 * 			it faces.
	 * @param nbSteps
	 * 			The number of steps that have to be taken.
	 * @return false
	 * 			The move cannot be made.
	 * @return true
	 * 			The move can be made.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public boolean canMove(Worm worm, int nbSteps) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.canMove(nbSteps);
	}

	/**
	 * Function that executes a move.
	 * A move consists of the following:
	 * The worm that has to be moved, is moved over a certain distance (= number of steps * radius of the worm).
	 * 
	 * @param worm
	 * 			The worm that has to be moved.
	 * @param nbSteps
	 * 			The number of steps the given worm has to move.
	 * @post	The x-coordinate of the given worm has to be updated, if this is possible.
	 * 			| (new worm).getX() == worm.getX() + (nbSteps * Math.cos(worm.getOrientation()) * worm.getRadius())
	 * @post	The y-coordinate of this worm has to be updated, if this is possible..
	 * 			| (new worm).getY() == worm.getY() + (nbSteps * Math.sin(worm.getOrientation()) * worm.getRadius())
	 * @post	The current amount of Action Points have to be updated. This will anly be executed when the new x,y-coordinates are both valid.
	 * 			| (new worm).getActionPoints() == worm.getActionPoints() - ((int) Math.ceil(nbSteps * worm.getTotalStepCost()))
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null (checked in canMove(worm, nbSteps))
	 * @throws	ModelException
	 * 			The number of steps can't be negative.
	 * 			| nbSteps < 0
	 */
	@Override
	public void move(Worm worm, int nbSteps) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		try {
			worm.move(nbSteps);
		} catch (IllegalArgumentException e) {
			throw new ModelException ("An illegal argument has been passed.");
		} catch (ArithmeticException e) {
			throw new ModelException("The new x- and/or y-coordinate are/is invalid.");
		}
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
	public boolean canTurn(Worm worm, double angle) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.canTurn(angle);
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
	public void turn(Worm worm, double angle) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		worm.turn(angle);
	}

	/**
	 * Function that makes the given worm jump, if this is possible.
	 * The given worm will consume all its action points by jumping.
	 * 
	 * @param worm
	 * 			The worm that has to jump.
	 * @post	All the worm's action points are being used to jump.
	 * 			| (new worm).getActionPoints() == 0
	 * @post	The location of the worm is updated, if it is possible.
	 * 			| (new worm).getX() == worm.getX() + worm.getJumpDistance()
	 * @throws	ModelException
	 * 			The worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 * @throws	ModelException
	 * 			The orientation of the worm has to be valid to make a jump and there must be some action points left.
	 * 			| !worm.canJump()
	 */
	@Override
	public void jump(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		try {
			worm.jump();
		} catch (ArithmeticException e) {
			throw new ModelException("The orientation of the worm is not accepted (is faced downwards), the new position is not accepted or the worm doesn't have enough action points left.");
		}
	}

	/**
	 * Function that returns the air-time of the given worm.
	 * 
	 * @param worm
	 * 			The worm for which the air-time has to be calculated.
	 * @throws	ModelException
	 * 			If worm is an empty reference (a null pointer), an exception has to be thrown.
	 * 			| worm == null
	 */
	@Override
	public double getJumpTime(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid");
		return worm.getJumpTime();
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
	public double[] getJumpStep(Worm worm, double t) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid");
		return worm.getJumpStep(t);
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
	public double getX(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
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
	public double getY(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getY();
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
	public double getOrientation(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given is invalid.");
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
	public double getRadius(Worm worm) {
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getRadius();
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
	public void setRadius(Worm worm, double newRadius) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		try {
			worm.setRadius(newRadius);
		} catch (IllegalArgumentException e) {
			throw new ModelException("The new radius for this worm is invalid or the new mass will not be valid.");
		}
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
	public double getMinimalRadius(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getMinimalRadius();
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
	public int getActionPoints(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getActionPoints();
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
	public int getMaxActionPoints(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getMaxActionPoints();
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
	public String getName(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getName();
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
	public void rename(Worm worm, String newName) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		try {
			worm.rename(newName);
		} catch (IllegalArgumentException e) {
			throw new ModelException("The new name for this worm is invalid. Please enter a name that corresponds to the naming guidelines.");
		}
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
	public double getMass(Worm worm) throws ModelException{
		if (worm == null)
			throw new ModelException("The given worm is invalid.");
		return worm.getMass();
	}
	
}
