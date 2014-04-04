import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.model.Worm;
import worms.util.Util;

/**
 * A JUnit Test Suite to test the public methods in the class Worm (contained in worm.model.Worm package).
 * The following constructors are also tested:
 * - A constructor whose arguments are all valid. This doesn't cause any issues.
 * - A constructor whose x-coordinate is invalid. This should cause an error when testing.
 * - A constructor whose y-coordinate is invalid. This should cause an error when testing.
 * - A constructor whose radius is invalid. This should cause an error when testing.
 * - A constructor whose name is invalid. This should cause an error when testing.
 * A constructor with more than one invalid argument, would behave in the same way as one where only one argument
 * is invalid. These won't be tested.
 * 
 * The methods getJumpTime() and getJumpDistance() don't have an illegal case, so these won't be tested.
 * All other methods are tested for legal and illegal cases.
 * 
 * @author Pieter Jan Vingerhoets & Matthijs Nelissen
 * @version 1.0
 */
public class WormTests {
	/**
	 * Declaring variable worm.
	 */
	private Worm worm = new Worm(0.0, 0.0, 0.0, 1.0, "AllValidWorm");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Function that tests whether a valid worm can be created. If it can't, the test will fail.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test_canCreateWorm_LegalCase() {
		try {
			Worm wormAllValid = new Worm(0.0, 0.0, 0.0, 1.0, "AllValidWorm");
		} catch (IllegalArgumentException e) {
			fail("An invalid argument is passed.");
		}
	}
	
	/**
	 * Function that tests whether a worm can be created with an invalid x-coordinate. If it can't, the test
	 * will fail.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test_canCreateWorm_InvalidX() {
		try {
			Worm wormInvalidX = new Worm(Double.POSITIVE_INFINITY, 0.0, 0.0, 1.0, "InvalidXWorm");
		} catch (IllegalArgumentException e) {
			fail("The x-coordinate is invalid.");
		}
	}
	
	/**
	 * Function that tests whether a worm can be created with an invalid y-coordinate. If it can't, the test
	 * will fail.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test_canCreateWorm_InvalidY() {
		try {
			Worm wormInvalidY = new Worm(0.0, Double.NEGATIVE_INFINITY, 0.0, 1.0, "InvalidYWorm");
		} catch (IllegalArgumentException e) {
			fail("The y-coordinate is invalid.");
		}
	}
	
	/**
	 * Function that tests whether a worm can be created with an invalid radius. If it can't, the test
	 * will fail.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test_canCreateWorm_InvalidRadius() {
		try {
			Worm wormInvalidRadius = new Worm(0.0, 0.0, 0.0, 0.1, "InvalidYWorm");
		} catch (IllegalArgumentException e) {
			fail("The radius is invalid.");
		}
	}
	
	/**
	 * Function that tests whether a worm can be created with an invalid name. If it can't, the test will fail.
	 */
	@SuppressWarnings("unused")
	@Test
	public void test_canCreateWorm_InvalidName() {
		try {
			Worm wormInvalidName = new Worm(0.0, 0.0, 0.0, 1.0, "InvalidNameWorm#");
		} catch (IllegalArgumentException e) {
			fail("The name is invalid.");
		}
	}
	
	/**
	 * Function that tests the getX() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getX_LegalCase(){
		assertTrue(worm.getX() == 0.0);
	}
	
	/**
	 * Function that tests the getY() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getY_LegalCase(){
		assertTrue(worm.getY() == 0.0);
	}
	
	/**
	 * Function that tests the getRadius() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getRadius_LegalCase(){
		assertTrue(worm.getRadius() == 1.0);
	}
	
	/**
	 * Function that tests the getOrientation() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getOrientation_LegalCase(){
		assertTrue(worm.getOrientation() == 0.0);
	}
	
	/**
	 * Function that tests the getMass() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMass_LegalCase(){
		assertTrue(worm.getMass() == 1062.0 * ((4.0 / 3.0) * Math.PI * Math.pow(worm.getRadius(), 3)));
	}
	
	/**
	 * Function that tests the getName() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getName_LegalCase(){
		assertTrue(worm.getName() == "AllValidWorm");
	}
	
	/**
	 * Function that tests the getMinimalRadius() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMinimalRadius_LegalCase(){
		assertTrue(worm.getMinimalRadius() == 0.25);
	}
	
	/**
	 * Function that tests the getActionPoints() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getActionPoints_LegalCase(){
		assertTrue(worm.getActionPoints() == worm.getMaxActionPoints());
	}
	
	/**
	 * Function that tests the getMaxActionPoints() method for a worm. There can be no illegal case of this method.
	 */
	@Test
	public void test_worm_getMaxActionPoints_LegalCase(){
		assertTrue(worm.getMaxActionPoints() == (int) Math.round(worm.getMass()));
	}
	
	/**
	 * Function that tests a legal case of the method setRadius() for a worm.
	 */
	@Test
	public void test_worm_setRadius_LegalCase(){
		worm.setRadius(2.0);
		assertTrue(worm.getRadius() == 2.0 && 
				worm.getMass() == 1062.0 * ((4.0 / 3.0) * Math.PI * Math.pow(2.0, 3)) &&
				worm.getMaxActionPoints() == (int) Math.round(worm.getMass()) &&
				worm.getActionPoints() <= worm.getMaxActionPoints());
	}
	
	/**
	 * Function that tests a legal case of the method rename() for a worm.
	 */
	@Test
	public void test_worm_rename_LegalCase(){
		worm.rename("John");
		assertTrue(worm.getName() == "John");
	}
	
	/**
	 * Function that tests a legal case of the method canMove() for a worm.
	 */
	@Test
	public void test_worm_canMove_LegalCase() {
		assertTrue(worm.canMove(1));
	}
	
	/**
	 * Function that tests a legal case of the method move() for a worm.
	 */
	@Test
	public void test_worm_move_LegalCase() {
		double oldX = worm.getX();
		double oldY = worm.getY();
		int oldActionPoints = worm.getActionPoints();
		worm.move(1);
		assertTrue(worm.getX() == oldX + (1 * Math.cos(worm.getOrientation()) * worm.getRadius()) && 
				worm.getY() == oldY + (1 * Math.sin(worm.getOrientation()) * worm.getRadius()) && 
				worm.getActionPoints() == oldActionPoints - ((int) Math.
						ceil(1 * Math.abs(Math.sin(worm.getOrientation()) * 4) + 
								Math.abs(Math.cos(worm.getOrientation())))));
	}
	
	/**
	 * Function that tests a legal case of the method canTurn() for a worm.
	 */
	@Test
	public void test_worm_canTurn_LegalCase() {
		assertTrue(worm.canTurn(Math.PI / 4));
	}
	
	/**
	 * Function that tests a legal case of the method turn() for a worm.
	 */
	@Test
	public void test_worm_turn_LegalCase() {
		double oldDirection = worm.getOrientation();
		int oldActionPoints = worm.getActionPoints();
		worm.turn(Math.PI / 4);
		assertTrue(worm.getOrientation() == (Math.PI / 4) + oldDirection && 
				worm.getActionPoints() == oldActionPoints - Math.abs(Math.round(60 / 
						(int) Math.round(2 * Math.PI / (Math.PI / 4)))));
	}
	
	/**
	 * Function that tests a legal case of the method getJumpTime() for a worm.
	 */
	@Test
	public void test_worm_getJumpTime_LegalCase() {
		assertTrue(worm.getJumpTime() == ((Math.pow(((((5 * worm.getActionPoints()) + (worm.getMass() * worm.g)) 
				/ worm.getMass()) * .5), 2) * Math.sin(2 * worm.getOrientation())) / worm.g) 
					/ (((((5 * worm.getActionPoints()) + (worm.getMass() * worm.g)) / worm.getMass()) * .5) 
							* Math.cos(worm.getOrientation())));
	}
	
	/**
	 * Function that tests a legal case of the method getJumpStep() for a worm.
	 */
	@Test
	public void test_worm_getJumpStep_LegalCase() {
		double[] resultArray = worm.getJumpStep(1);
		assertTrue(resultArray[0] == worm.getX() + ((((((5 * worm.getActionPoints()) + (worm.getMass() * worm.g)) / worm.getMass()) * .5) * Math.cos(worm.getOrientation())) * 1) && 
				resultArray[1] == worm.getY() + (((((((5 * worm.getActionPoints()) + (worm.getMass() * worm.g)) / worm.getMass()) * .5) * Math.sin(worm.getOrientation())) * 1) - (.5 * worm.g * Math.pow(1, 2))));
	}
	
	/**
	 * Function that tests a legal case of the method jump() for a worm.
	 * We have to make sure the direction of this worm has a sine greater than 0 for the test to be legal.
	 */
	@Test
	public void test_worm_jump_LegalCase() {
		double oldX = worm.getX();
		int oldActionPoints = worm.getActionPoints();
		worm.turn(Math.PI / 4);
		worm.jump();
		assertTrue(worm.getActionPoints() == 0 && 
				Util.fuzzyEquals(worm.getX(), oldX + ((Math.
						pow(((((5 * oldActionPoints) + (worm.getMass() * worm.g)) / worm.getMass()) * .5), 2) 
						* Math.sin(2 * worm.getOrientation())) / worm.g), 0.01));
	}
	
	/**
	 * Function that tests a legal case of the method setActionPOints() for a worm.
	 */
	@Test
	public void test_worm_setActionPoints_LegalCase() {
		worm.setActionPoints(20);
		assertTrue(worm.getActionPoints() == 20);
	}
	
	/**
	 * Function that tests an illegal case of the method setRadius() for a worm. If it can't be executed,
	 * the test will fail.
	 */
	@Test
	public void test_worm_setRadius_IllegalCase(){
		try {
			worm.setRadius(0.1);
		} catch (IllegalArgumentException e) {
			fail("The new radius is invalid.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method rename() for a worm. If it can't be executed,
	 * the test will fail.
	 */
	@Test
	public void test_worm_rename_IllegalCase(){
		try {
			worm.rename("tommy");
		} catch (IllegalArgumentException e) {
			fail("The new name is invalid.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method canMove() for a worm.
	 */
	@Test
	public void test_worm_canMove_IllegalCase() {
		assertFalse(worm.canMove(-1));
	}
	
	/**
	 * Function that tests an illegal case of the method move() for a worm. If it can't be executed,
	 * the test will fail.
	 */
	@Test
	public void test_worm_move_IllegalCase() {
		try {
			worm.move(-1);
		} catch (IllegalArgumentException e) {
			fail("The number of steps is invalid.");
		} catch (ArithmeticException e) {
			fail("The new x- and/or y-coordinate are invalid.");
		}
	}
	
	/**
	 * Function that tests the an illegal case of the method canTurn() for a worm.
	 * We have to make sure the worm's Action Points are set to 0, for the sake of not being able to turn at all.
	 */
	@Test
	public void test_worm_canTurn_IllegalCase() {
		worm.setActionPoints(0);
		assertFalse(worm.canTurn(Math.PI));
	}
	
	/**
	 * Function that tests an illegal case of the method turn() for a worm. If it can't be executed,
	 * the test will fail.
	 * We have to make sure the worm's Action Points are set to 0, for the sake of not being able to turn at all.
	 */
	@Test
	public void test_worm_turn_IllegalCase() {
		worm.setActionPoints(0);
		double oldDirection = worm.getOrientation();
		int oldActionPoints = worm.getActionPoints();
		worm.turn(Math.PI);
		if(!(worm.getOrientation() == (Math.PI) + oldDirection) && 
				!(worm.getActionPoints() == oldActionPoints - Math.abs(Math.round(60 / 
						(int) Math.round(2 * Math.PI / (Math.PI))))))
			fail("The turn cannot be made, there are too few Action Points remaining.");
	}
	
	/**
	 * Function that tests an illegal case of the method jump() for a worm. If it can't be executed,
	 * the test will fail.
	 * We have to make sure the direction of this worm has a sine less than 0 to make the testing case illegal.
	 */
	@Test
	public void test_worm_jump_IllegalCase() {
		worm.turn(-(Math.PI / 4));
		try {
			worm.jump();
		} catch (ArithmeticException e) {
			fail("The angle is invalid.");
		}
	}
	
	/**
	 * Function that tests an illegal case of the method setActionPoints() for a worm. If it can't be executed,
	 * the test will fail.
	 */
	@Test
	public void test_worm_setActionPoints_IllegalCase() {
		worm.setActionPoints(-10);
		if(!(worm.getActionPoints() == -10))
			fail("Invalid number of Action Points.");
	}
}
